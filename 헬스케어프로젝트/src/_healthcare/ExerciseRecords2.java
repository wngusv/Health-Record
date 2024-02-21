package _healthcare;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

import dbutil.MySqlConnectionProvider;

import javax.swing.ImageIcon;
import javax.swing.SpringLayout;

public class ExerciseRecords extends JFrame {
	
	// 현재 날짜
		java.util.Date currentDate = new java.util.Date();
		// sql에 넣기 위해 날짜를 date형식으로 변경
		Date sqlDate = new Date(currentDate.getTime());
		
		private String user_id;
		private Date formattedTimeS;
		private Date formattedTimeE;
	
	
	public ExerciseRecords(String loginId) {
		this.user_id = loginId;
		setTitle("운동 기록");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JLabel lbl_sports = new JLabel("운동종목");
		springLayout.putConstraint(SpringLayout.NORTH, lbl_sports, 187, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lbl_sports, 98, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lbl_sports, 155, SpringLayout.WEST, getContentPane());
		getContentPane().add(lbl_sports);
		
		JComboBox comboBox_sports = new JComboBox(); // 콤보박스에서 선택된 스포츠 ... 따로 ......
		springLayout.putConstraint(SpringLayout.NORTH, comboBox_sports, 181, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox_sports, 6, SpringLayout.EAST, lbl_sports);
		springLayout.putConstraint(SpringLayout.EAST, comboBox_sports, 226, SpringLayout.EAST, lbl_sports);
		getContentPane().add(comboBox_sports);
		
		// MySQL 연결 및 데이터베이스에서 목록 불러오기
		try(Connection connection = MySqlConnectionProvider.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT sports FROM mets");) {
			

			List<String> sportsList = new ArrayList<>();
			while (resultSet.next()) {
				String sportsName = resultSet.getString("sports");
				sportsList.add(sportsName);
			}

			// 콤보 박스에 목록 추가
			for (String sports : sportsList) {
				comboBox_sports.addItem(sports);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JButton btn_ok = new JButton("확인");
		springLayout.putConstraint(SpringLayout.WEST, btn_ok, 21, SpringLayout.EAST, comboBox_sports);
		springLayout.putConstraint(SpringLayout.SOUTH, btn_ok, 0, SpringLayout.SOUTH, lbl_sports);
		getContentPane().add(btn_ok);
		
		
		// 운동 시작
		JButton btn_Start = new JButton("운동 시작");
		springLayout.putConstraint(SpringLayout.NORTH, btn_Start, 26, SpringLayout.SOUTH, lbl_sports);
		springLayout.putConstraint(SpringLayout.WEST, btn_Start, 88, SpringLayout.WEST, getContentPane());
		getContentPane().add(btn_Start);
		
		JLabel lbl_Start = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lbl_Start, 6, SpringLayout.SOUTH, btn_Start);
		springLayout.putConstraint(SpringLayout.WEST, lbl_Start, 54, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_Start, 40, SpringLayout.SOUTH, btn_Start);
		springLayout.putConstraint(SpringLayout.EAST, lbl_Start, 236, SpringLayout.WEST, getContentPane());
		getContentPane().add(lbl_Start);
		
		// btn_Start에 ActionListener 추가
		btn_Start.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// 현재 시간 가져오기
						LocalDateTime currentTime = LocalDateTime.now();
						// 형식 지정
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						// 현재 시간을 문자열로 변환
						String formattedTimeS = currentTime.format(formatter);

						// 레이블에 현재 시간 표시(운동 시작 시간)
						lbl_Start.setText("운동 시작 시간: " + formattedTimeS);
					}
				});
		

		
		// 운동 종료 버튼 눌렀을때 + 운동 종료 버튼 누른 시간 기록
		JButton btn_End = new JButton("운동 종료");
		springLayout.putConstraint(SpringLayout.SOUTH, btn_End, 0, SpringLayout.SOUTH, btn_Start);
		springLayout.putConstraint(SpringLayout.EAST, btn_End, 0, SpringLayout.EAST, btn_ok);
		getContentPane().add(btn_End);
		
		JLabel lbl_End = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lbl_End, 0, SpringLayout.NORTH, lbl_Start);
		springLayout.putConstraint(SpringLayout.WEST, lbl_End, 71, SpringLayout.EAST, lbl_Start);
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_End, 0, SpringLayout.SOUTH, lbl_Start);
		springLayout.putConstraint(SpringLayout.EAST, lbl_End, 253, SpringLayout.EAST, lbl_Start);
		getContentPane().add(lbl_End);

		// btn_End에 ActionListener 추가
		btn_End.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// 현재 시간 가져오기
						LocalDateTime currentTime = LocalDateTime.now();
						// 형식 지정
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
						// 현재 시간을 문자열로 변환
						String formattedTimeE = currentTime.format(formatter);

						// 레이블에 현재 시간 표시(운동 종료 시간)
						lbl_End.setText("운동 종료 시간: " + formattedTimeE);

					}
				});
		
		

		
		// 운동 시간
		JLabel lbl_showHours = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lbl_showHours, 48, SpringLayout.SOUTH, lbl_Start);
		springLayout.putConstraint(SpringLayout.WEST, lbl_showHours, 24, SpringLayout.EAST, lbl_showHours);
		springLayout.putConstraint(SpringLayout.EAST, lbl_showHours, 293, SpringLayout.EAST, lbl_showHours);
		getContentPane().add(lbl_showHours);
		
		// 운동시간 라벨에 exerciserecords 테이블의 hours_exercise 값 가져오기
				JLabel lblExerciseHours = new JLabel(); // 운동시간 표시 라벨
				lblExerciseHours.setBounds(80, 376, 235, 15);
				getContentPane().add(lblExerciseHours);

				// MySQL 연결
				try(Connection connection = MySqlConnectionProvider.getConnection();
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT hours_exercise FROM exericserecords");) {
					

					// 결과를 JLabel에 설정
					if (resultSet.next()) {
						double hoursExercise = resultSet.getDouble("hours_exercise");
						lbl_showHours.setText("운동시간: " + hoursExercise);
					} else {
						lbl_showHours.setText("운동시간: 데이터 없음");
					}

					// 리소스 해제
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
		
		
		// 소모 칼로리
		JLabel lbl_ShowKcal = new JLabel(""); 
		springLayout.putConstraint(SpringLayout.NORTH, lbl_ShowKcal, 394, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lbl_ShowKcal, 6, SpringLayout.EAST, lbl_ShowKcal);
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_ShowKcal, -74, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lbl_ShowKcal, -132, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lbl_showHours, -28, SpringLayout.NORTH, lbl_ShowKcal);
		getContentPane().add(lbl_ShowKcal);
		
		// 소모 칼로리 라벨에 exerciserecords 테이블의 kcal_exercise 값 가져오기
				try(Connection connection = MySqlConnectionProvider.getConnection();
						Statement statement = connection.createStatement();
						ResultSet resultSet = statement.executeQuery("SELECT kcal_exercise FROM exericserecords");) {
					

					// 결과를 JLabel에 설정
					if (resultSet.next()) {
						double kcalExercise = resultSet.getDouble("kcal_exercise");
						lbl_ShowKcal.setText("소모 칼로리: " + kcalExercise);
					} else {
						lbl_ShowKcal.setText("데이터 없음");
					}

					// 리소스 해제
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				
				setSize(548, 511);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setVisible(true);

	}
	
	 // 데이터베이스에 시작 시간 삽입하는 메소드
    private void insertStartTimeIntoDatabase(Date startTime) {
    	String query = "INSERT INTO exericserecords (user_id, start_time) VALUES (?, ?)";
        try (Connection conn = MySqlConnectionProvider.getConnection();
        		PreparedStatement preparedStatement = conn.prepareStatement(query);
        		) {
            // 삽입 쿼리 작성
            // PreparedStatement 생성
            // 파라미터 설정
            preparedStatement.setString(1, user_id);
            preparedStatement.setDate(2, formattedTimeS);// 사용자 아이디 설정
            // 쿼리 실행
            preparedStatement.executeUpdate();
            // 성공 메시지 출력
            JOptionPane.showMessageDialog(null, "운동 시작 시간이 데이터베이스에 추가되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
 // 데이터베이스에 종료 시간 삽입하는 메소드
    private void insertEndTimeIntoDatabase(Date startTime) {
    	String query = "INSERT INTO exericserecords (user_id, end_time) VALUES (?, ?)";
        try (Connection conn = MySqlConnectionProvider.getConnection();
        		PreparedStatement preparedStatement = conn.prepareStatement(query);
        		) {
            // 삽입 쿼리 작성
            // PreparedStatement 생성
            // 파라미터 설정
            preparedStatement.setString(1, user_id);
            preparedStatement.setDate(2, formattedTimeE);
            // 쿼리 실행
            preparedStatement.executeUpdate();
            // 성공 메시지 출력
            JOptionPane.showMessageDialog(null, "운동 종료 시간이 데이터베이스에 추가되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
	
	
}