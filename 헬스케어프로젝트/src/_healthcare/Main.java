package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import dbutil.MySqlConnectionProvider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JPanel;

public class Main extends JFrame {
    private String loginId;
    private JLabel lblKg;
   private JSlider slider;
   private JLabel lalKg;

    
    
    public Main(String loginId) {
       getContentPane().setBackground(Color.WHITE);
        this.loginId = loginId;
        System.out.println("로그인한 ID:" + loginId);

        
        
        setSize(795, 530);
        setVisible(true);
        getContentPane().setLayout(null);

        JButton boardButton = new JButton("게시판");
        boardButton.setBounds(287, 440, 118, 23);
        getContentPane().add(boardButton);

        boardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageBoard messageBoard = new MessageBoard(loginId);
                messageBoard.setVisible(true);
                dispose();
            }
        });

        
        JButton exerciseButton = new JButton("운동기록");
        exerciseButton.setBounds(99, 411, 137, 23);
        getContentPane().add(exerciseButton);
        exerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 운동기록 창 열기
                ExerciseRecords exerciseRecords = new ExerciseRecords(loginId);
                exerciseRecords.setVisible(true);
                dispose(); // 현재 창 닫기
            }
        });
        
        
        JButton waterButton = new JButton("물 기록");
        waterButton.setBounds(99, 440, 137, 23);
        getContentPane().add(waterButton);
        waterButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
              // 물 기록 창 열기
              WaterRecords waterRecords = new WaterRecords(loginId);
              waterRecords.setVisible(true);
              dispose(); // 현재 창 닫기
           }
        });
        
        
        JButton btnNewButton = new JButton("식단 기록");
        btnNewButton.setBounds(95, 382, 141, 23);
        btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
              DietRecord dietRecord = new DietRecord(loginId);
              dietRecord.setLocationRelativeTo(Main.this); // Main 클래스의 위치에 맞추어 창이 생성됨
                dietRecord.setVisible(true);
              dispose();
           }
        });
        getContentPane().add(btnNewButton);
        
        
        JButton calButton = new JButton("캘린더");
        calButton.setBounds(287, 382, 118, 23);
        calButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              dispose();
              ExerciseCalendar calendar = new ExerciseCalendar(loginId);
              calendar.setVisible(true);
           }
        });
        getContentPane().add(calButton);
        
        JButton graphButton = new JButton("몸무게 변화");
        graphButton.setBounds(287, 411, 118, 23);
        getContentPane().add(graphButton);
        graphButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               dispose();
               Graph graph = new Graph(loginId);
               graph.setVisible(true);
            }
         });

       
      
       // JSlider 초기화(BMI표시)
        slider = new JSlider();
        slider.setBounds(56, 314, 402, 46);
        slider.setMinorTickSpacing(1); // 최소 틱 간격
        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setEnabled(false);
        slider.setMaximum(50);
        slider.setBackground(Color.WHITE);
        getContentPane().add(slider);
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BMI FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                double bmi = resultSet.getDouble("BMI");
                System.out.println("BMI from database: " + bmi);

                int sliderValue = (int) bmi; // BMI 값을 정수로 변환하여 슬라이더 값으로 설정
                slider.setValue(sliderValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        lblKg = new JLabel("New label");
        lblKg.setBounds(541, 256, 57, 15);
        getContentPane().add(lblKg);
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT weight FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String weight = resultSet.getString("weight"); // 몸무게 가져오기
                System.out.println("weight from database: " + weight);

                lblKg.setText(weight); // JLabel에 몸무게 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
     // 몸무게 입력 버튼
        JButton btnRecordWeight = new JButton("몸무게 입력");
        btnRecordWeight.setBounds(637, 262, 113, 23);
        getContentPane().add(btnRecordWeight);

        btnRecordWeight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // 몸무게 입력창 열기
                UpdateWeight updateWeight = new UpdateWeight(Main.this); // Main 클래스의 인스턴스 전달
                updateWeight.setVisible(true);
            }
        });
        
        
        JLabel lblName = new JLabel("New label");
        lblName.setBounds(541, 281, 57, 15);
        getContentPane().add(lblName);
        
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String name = resultSet.getString("name"); // 이름 가져오기
                System.out.println("name from database: " + name);

                lblName.setText(name); // JLabel에 이름 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        JLabel lblAge = new JLabel("New label");
        lblAge.setBounds(541, 310, 57, 15);
        getContentPane().add(lblAge);
        
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT age FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String age = resultSet.getString("age"); // 나이 가져오기
                System.out.println("name from database: " + age);

                lblAge.setText(age); // JLabel에 나이 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JLabel lblHeight = new JLabel("New label");
        lblHeight.setBounds(541, 343, 57, 15);
        getContentPane().add(lblHeight);
      
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT height FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String height = resultSet.getString("height"); // 키 가져오기
                System.out.println("height from database: " + height);

                lblHeight.setText(height); // JLabel에 키 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    

    // 몸무게 표시 라벨 업데이트 + 데이터베이스에 몸무게 추가 메서드
    public void updateWeight(int inputValue) {
       LocalDate today = LocalDate.now();
       lblKg.setText(String.valueOf(inputValue)); // 입력 받은 몸무게 값을 라벨에 표시 
       try {
    	    Connection connection = MySqlConnectionProvider.getConnection();

    	    // 사용자의 몸무게를 변경하기 위해 사용자의 ID를 가져오는 쿼리 실행
    	    PreparedStatement userIdStatement = connection.prepareStatement("SELECT id FROM users WHERE id = ?");
    	    userIdStatement.setString(1, loginId);
    	    ResultSet userIdResultSet = userIdStatement.executeQuery();

    	    // 사용자 ID를 가져왔다면
    	    if (userIdResultSet.next()) {
    	        String userId = userIdResultSet.getString("id");

    	        // 사용자 ID와 함께 몸무게를 weightrecords 테이블에 삽입하는 쿼리 실행
    	        String sql = "INSERT INTO weightrecords (user_id, weight, date) VALUES (?, ?, ?)";
    	        PreparedStatement statement = connection.prepareStatement(sql);
    	        statement.setString(1, userId);
    	        statement.setInt(2, inputValue);
    	        statement.setDate(3, java.sql.Date.valueOf(today)); // 오늘 날짜를 SQL Date 형식으로 변환하여 설정
    	        statement.executeUpdate();
    	        System.out.println("몸무게가 성공적으로 추가되었습니다.");

    	        // users 테이블의 몸무게(weight) 열을 변경하는 쿼리 실행
    	        String updateUserWeightSql = "UPDATE users SET weight = ? WHERE id = ?";
    	        PreparedStatement updateUserWeightStatement = connection.prepareStatement(updateUserWeightSql);
    	        updateUserWeightStatement.setInt(1, inputValue);
    	        updateUserWeightStatement.setString(2, userId);
    	        updateUserWeightStatement.executeUpdate();
    	        System.out.println("사용자의 몸무게가 성공적으로 업데이트되었습니다.");
    	    } else {
    	        System.out.println("사용자 ID를 가져오지 못했습니다.");
    	    }
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}


   }
    
}