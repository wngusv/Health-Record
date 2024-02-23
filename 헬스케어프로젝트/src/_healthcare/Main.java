package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import dbutil.MySqlConnectionProvider;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
   private JLabel label;

    
    
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
        
        // 몸무게 표시 라벨 추가
     // 몸무게 표시 라벨 추가

        
        // JSlider 초기화
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

                // JSlider에 BMI 값 표시
//                JLabel lblBmi = new JLabel("BMI: " + bmi);
//                getContentPane().add(lblBmi);
//                springLayout.putConstraint(SpringLayout.WEST, lblBmi, 10, SpringLayout.WEST, getContentPane());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        label = new JLabel("New label");
        label.setBounds(541, 256, 57, 15);
        getContentPane().add(label);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(541, 281, 57, 15);
        getContentPane().add(lblNewLabel);
        
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String name = resultSet.getString("name"); // 이름 가져오기
                System.out.println("name from database: " + name);

                lblNewLabel.setText(name); // JLabel에 이름 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(541, 310, 57, 15);
        getContentPane().add(lblNewLabel_1);
        
        
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT age FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String age = resultSet.getString("age"); // 나이 가져오기
                System.out.println("name from database: " + age);

                lblNewLabel_1.setText(age); // JLabel에 나이 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(541, 343, 57, 15);
        getContentPane().add(lblNewLabel_2);
      
        try {
            Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT height FROM users WHERE id = '" + loginId + "'");
            if (resultSet.next()) {
                String height = resultSet.getString("height"); // 키 가져오기
                System.out.println("height from database: " + height);

                lblNewLabel_2.setText(height); // JLabel에 키 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    
    

    // 몸무게 표시 라벨 업데이트 메서드 (인스턴스 메서드로 변경)
    public void updateWeight(String weight) {
       label.setText(weight + " kg"); // 입력 받은 몸무게 값을 라벨에 표시
    }
}