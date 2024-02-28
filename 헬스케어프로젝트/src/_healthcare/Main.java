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
import javax.swing.ImageIcon;
import java.awt.Font;

public class Main extends JFrame {
    private String loginId;
    private JLabel lblKg;
   private JSlider slider;
   private JLabel lalKg;
private JLabel user_character = new JLabel();
private int userCharacterNum;

    
    
    public Main(String loginId) {
    	setTitle("마이 페이지");
       getContentPane().setBackground(Color.WHITE);
        this.loginId = loginId;
        System.out.println("로그인한 ID:" + loginId);
        

        
        
        setSize(408, 561);
        setVisible(true);
        getContentPane().setLayout(null);
        
        JButton btnNewButton_1 = new JButton("");
        btnNewButton_1.setIcon(new ImageIcon(Main.class.getResource("/image/로그아웃버튼3.png")));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dispose();
				new Login();
        	}
        });
        btnNewButton_1.setContentAreaFilled(false);
        btnNewButton_1.setBorderPainted(false);
        btnNewButton_1.setFocusPainted(false);
        
        JButton btnNewButton_2 = new JButton("프로필수정버튼");
        btnNewButton_2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		 new EditProfile(loginId);
                 dispose();
        	}
        });
        btnNewButton_2.setBounds(190, 193, 149, 23);
        getContentPane().add(btnNewButton_2);
        
        JLabel lblNewLabel_5 = new JLabel("활기록");
        lblNewLabel_5.setForeground(Color.WHITE);
        lblNewLabel_5.setFont(new Font("휴먼편지체", Font.BOLD, 21));
        lblNewLabel_5.setBounds(12, 1, 97, 39);
        getContentPane().add(lblNewLabel_5);
        btnNewButton_1.setBounds(276, 5, 113, 31);
        getContentPane().add(btnNewButton_1);

        JButton boardButton = new JButton("");
        boardButton.setIcon(new ImageIcon(Main.class.getResource("/image/게시판버튼.png")));
        boardButton.setOpaque(false); // 배경 투명하게 설정
        boardButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        boardButton.setBorderPainted(false); // 테두리 제거
        boardButton.setBounds(266, 419, 115, 77);
        getContentPane().add(boardButton);

        boardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageBoard messageBoard = new MessageBoard(loginId);
                messageBoard.setVisible(true);
                dispose();
            }
        });

        
        JButton exerciseButton = new JButton("");
        exerciseButton.setIcon(new ImageIcon(Main.class.getResource("/image/운동기록버튼.png")));
        exerciseButton.setBounds(149, 327, 101, 79);
        exerciseButton.setOpaque(false); // 배경 투명하게 설정
        exerciseButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        exerciseButton.setBorderPainted(false); // 테두리 제거
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
        waterButton.setIcon(new ImageIcon(Main.class.getResource("/image/물기록버튼.png")));
        waterButton.setOpaque(false); // 배경 투명하게 설정
        waterButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        waterButton.setBorderPainted(false); // 테두리 제거
        waterButton.setBounds(266, 325, 114, 85);
        getContentPane().add(waterButton);
        waterButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent arg0) {
              // 물 기록 창 열기
              WaterRecords waterRecords = new WaterRecords(loginId);
              waterRecords.setVisible(true);
              dispose(); // 현재 창 닫기
           }
        });
        
        
        JButton btnNewButton = new JButton("");
        btnNewButton.setIcon(new ImageIcon(Main.class.getResource("/image/식단기록버튼2.png")));
        btnNewButton.setOpaque(false); // 배경 투명하게 설정
        btnNewButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        btnNewButton.setBorderPainted(false); // 테두리 제거
        btnNewButton.setBounds(28, 326, 106, 79);
        btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
              DietRecord dietRecord = new DietRecord(loginId);
              dietRecord.setLocationRelativeTo(Main.this); // Main 클래스의 위치에 맞추어 창이 생성됨
                dietRecord.setVisible(true);
              dispose();
           }
        });
        getContentPane().add(btnNewButton);
        
        
        JButton calButton = new JButton("");
        calButton.setIcon(new ImageIcon(Main.class.getResource("/image/캘린더버튼.png")));
        calButton.setOpaque(false); // 배경 투명하게 설정
        calButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        calButton.setBorderPainted(false); // 테두리 제거
        calButton.setBounds(28, 419, 105, 76);
        calButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              dispose();
              ExerciseCalendar calendar = new ExerciseCalendar(loginId);
              calendar.setVisible(true);
           }
        });
        getContentPane().add(calButton);
        
        JButton graphButton = new JButton("");
        graphButton.setIcon(new ImageIcon(Main.class.getResource("/image/몸무게변화버튼.png")));
        graphButton.setOpaque(false); // 배경 투명하게 설정
        graphButton.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
        graphButton.setBorderPainted(false); // 테두리 제거
        graphButton.setBounds(151, 416, 109, 80);
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
        slider.setBounds(59, 223, 280, 46);
        slider.setMinorTickSpacing(1); // 최소 틱 간격
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setEnabled(false);
        slider.setMaximum(50);
        slider.setBackground(Color.WHITE);
        getContentPane().add(slider);
        
        try(Connection connection = MySqlConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT BMI FROM users WHERE id = '" + loginId + "'");) {
            
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
        lblKg.setBounds(232, 168, 57, 15);
        getContentPane().add(lblKg);
        
        try( Connection connection = MySqlConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT weight FROM users WHERE id = '" + loginId + "'");) {
           
            if (resultSet.next()) {
                String weight = resultSet.getString("weight"); // 몸무게 가져오기
                System.out.println("weight from database: " + weight);

                lblKg.setText(weight); // JLabel에 몸무게 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        JLabel lblName = new JLabel("New label");
        lblName.setBounds(232, 93, 57, 15);
        getContentPane().add(lblName);
        
        
        try( Connection connection = MySqlConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT name FROM users WHERE id = '" + loginId + "'");) {
           
            if (resultSet.next()) {
                String name = resultSet.getString("name"); // 이름 가져오기
                System.out.println("name from database: " + name);

                lblName.setText(name); // JLabel에 이름 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        JLabel lblAge = new JLabel("New label");
        lblAge.setBounds(232, 118, 57, 15);
        getContentPane().add(lblAge);
        
        
        try( Connection connection = MySqlConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT age FROM users WHERE id = '" + loginId + "'");) {
           
            if (resultSet.next()) {
                String age = resultSet.getString("age"); // 나이 가져오기
                System.out.println("name from database: " + age);

                lblAge.setText(age); // JLabel에 나이 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        JLabel lblHeight = new JLabel("New label");
        lblHeight.setBounds(232, 143, 57, 15);
        getContentPane().add(lblHeight);
        
        JLabel lblNewLabel = new JLabel("이름");
        lblNewLabel.setBounds(180, 93, 57, 15);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("나이");
        lblNewLabel_1.setBounds(180, 118, 57, 15);
        getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("키");
        lblNewLabel_2.setBounds(190, 143, 57, 15);
        getContentPane().add(lblNewLabel_2);
        
        JLabel lblNewLabel_3 = new JLabel("몸무게");
        lblNewLabel_3.setBounds(169, 168, 57, 15);
        getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon(Main.class.getResource("/image/큰초록바.png")));
        lblNewLabel_4.setBounds(-11, 0, 403, 39);
        getContentPane().add(lblNewLabel_4);
        
        user_character = new JLabel("New label");
        //user_character.setIcon(new ImageIcon(Main.class.getResource("/image/운동하는 브로콜리.png")));
        user_character.setBounds(22, 59, 135, 154);
        getContentPane().add(user_character);
        
        JLabel lblNewLabel_7 = new JLabel("New label");
        lblNewLabel_7.setIcon(new ImageIcon(Main.class.getResource("/image/아이보리색2.png")));
        lblNewLabel_7.setBounds(24, 58, 340, 164);
        getContentPane().add(lblNewLabel_7);
        
        JLabel lblNewLabel_8 = new JLabel("");
        lblNewLabel_8.setIcon(new ImageIcon(Main.class.getResource("/image/그림6.png")));
        lblNewLabel_8.setBounds(64, 271, 271, 23);
        getContentPane().add(lblNewLabel_8);
        
        JLabel lblNewLabel_9 = new JLabel("BMI");
        lblNewLabel_9.setBounds(32, 273, 33, 15);
        getContentPane().add(lblNewLabel_9);
      
        try( Connection connection = MySqlConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT height FROM users WHERE id = '" + loginId + "'");) {
           
            if (resultSet.next()) {
                String height = resultSet.getString("height"); // 키 가져오기
                System.out.println("height from database: " + height);

                lblHeight.setText(height); // JLabel에 키 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        callUserCharacter();  
    }
    
    private void callUserCharacter() {
    	String sql = "SELECT user_char FROM users WHERE id = ?";
    	try(Connection conn = MySqlConnectionProvider.getConnection();
    			PreparedStatement pst = conn.prepareStatement(sql);
    			) {
			pst.setString(1, loginId);
			
			
			try(ResultSet rs = pst.executeQuery();){
				
				while(rs.next()) {
					userCharacterNum = rs.getInt("user_char");
				}
			}
			
			switch(userCharacterNum) {
			case 1:
				user_character.setIcon(new ImageIcon(Main.class.getResource("/image/운동하는 브로콜리.png")));
				break;
			case 2:
				user_character.setIcon(new ImageIcon(Main.class.getResource("/image/요가하는 옥쓔.png")));
				break;
			case 3:
				user_character.setIcon(new ImageIcon(Main.class.getResource("/image/헬스토매통.png")));
				break;
			}
			
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
        setLocationRelativeTo(null);
    }
    

    // 몸무게 표시 라벨 업데이트 + 데이터베이스에 몸무게 추가 메서드
    public void updateWeight(int inputValue) {
       LocalDate today = LocalDate.now();
       lblKg.setText(String.valueOf(inputValue)); // 입력 받은 몸무게 값을 라벨에 표시 
       try(Connection connection = MySqlConnectionProvider.getConnection();
    		// 사용자의 몸무게를 변경하기 위해 사용자의 ID를 가져오는 쿼리 실행
    	    PreparedStatement userIdStatement = connection.prepareStatement("SELECT id FROM users WHERE id = ?");   
    		   ) {
    	    userIdStatement.setString(1, loginId);
    	    try(ResultSet userIdResultSet = userIdStatement.executeQuery();){
    	    	
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
    	    }
    	    

    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}


   }
}