package _healthcare;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
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

public class ExerciseRecords extends JFrame {
	private Connection connection;
	private JButton startBtn;
	private JLabel lblExerciseStartTime;

	public ExerciseRecords() {
		getContentPane().setLayout(null);

// 운동 시작 버튼 눌렀을때 + 운동 시작 버튼 누른 시간 기록
		JButton startBtn = new JButton("운동시작");
		startBtn.setBounds(52, 253, 97, 23);
		getContentPane().add(startBtn);

		JLabel lblExerciseStartTime = new JLabel("New label");
		lblExerciseStartTime.setBounds(12, 286, 196, 23);
		getContentPane().add(lblExerciseStartTime);

		// startBtn에 ActionListener 추가
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 시간 가져오기
				LocalDateTime currentTime = LocalDateTime.now();
				// 형식 지정
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				// 현재 시간을 문자열로 변환
				String formattedTime = currentTime.format(formatter);

				// 레이블에 현재 시간 표시(운동 시작 시간)
				lblExerciseStartTime.setText("운동 시작 시간: " + formattedTime);

				// 데이터베이스에 현재 시간 삽입
				insertStartTimeIntoDatabase(formattedTime);
			}
		});

// 운동 종료 버튼 눌렀을때 + 운동 종료 버튼 누른 시간 기록
		JButton endBtn = new JButton("운동종료");
		endBtn.setBounds(284, 253, 97, 23);
		getContentPane().add(endBtn);

		JLabel lblExerciseEndTime = new JLabel("New label");
		lblExerciseEndTime.setBounds(247, 286, 209, 23);
		getContentPane().add(lblExerciseEndTime);

		// endBtn에 ActionListener 추가
		endBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 현재 시간 가져오기
				LocalDateTime currentTime = LocalDateTime.now();
				// 형식 지정
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				// 현재 시간을 문자열로 변환
				String formattedTime = currentTime.format(formatter);

				// 레이블에 현재 시간 표시(운동 종료 시간)
				lblExerciseEndTime.setText("운동 종료 시간: " + formattedTime);

				// 데이터베이스에 현재 시간 삽입
				insertEndTimeIntoDatabase(formattedTime);
			}
		});

// 운동목록 선택 comboBox
		JComboBox ComboBox_SelectSports = new JComboBox();
		ComboBox_SelectSports.setToolTipText("");
		ComboBox_SelectSports.setBounds(70, 165, 190, 21);
		getContentPane().add(ComboBox_SelectSports);

		// MySQL 연결 및 데이터베이스에서 목록 불러오기
		try {
			Connection connection = MySqlConnectionProvider.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT sports FROM mets");

			List<String> sportsList = new ArrayList<>();
			while (resultSet.next()) {
				String sportsName = resultSet.getString("sports");
				sportsList.add(sportsName);
			}

			// 콤보 박스에 목록 추가
			for (String sports : sportsList) {
				ComboBox_SelectSports.addItem(sports);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

// 운동시간 라벨에 exerciserecords 테이블의 hours_exercise 값 가져오기
		JLabel lblExerciseHours = new JLabel(); // 운동시간 표시 라벨
		lblExerciseHours.setBounds(80, 376, 235, 15);
		getContentPane().add(lblExerciseHours);

		// MySQL 연결
		try {
			Connection connection = MySqlConnectionProvider.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT hours_exercise FROM exericserecords");

			// 결과를 JLabel에 설정
			if (resultSet.next()) {
				double hoursExercise = resultSet.getDouble("hours_exercise");
				lblExerciseHours.setText("운동시간: " + hoursExercise);
			} else {
				lblExerciseHours.setText("운동시간: 데이터 없음");
			}

			// 리소스 해제
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

// 운동시간 라벨에 exerciserecords 테이블의 kcal_exercise 값 가져오기
		JLabel lblkcalExercise = new JLabel(); // 소모칼로리 표시 라벨
		lblkcalExercise.setBounds(80, 427, 284, 15);
		getContentPane().add(lblkcalExercise);

		try {
			Connection connection = MySqlConnectionProvider.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT kcal_exercise FROM exericserecords");

			// 결과를 JLabel에 설정
			if (resultSet.next()) {
				double kcalExercise = resultSet.getDouble("kcal_exercise");
				lblkcalExercise.setText("소모 칼로리: " + kcalExercise);
			} else {
				lblkcalExercise.setText("운동시간: 데이터 없음");
			}

			// 리소스 해제
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		/*
		 * JLabel lblAmoutExerciseHours = new JLabel("총 운동시간:");
		 * lblAmoutExerciseHours.setBounds(80, 427, 89, 15);
		 * getContentPane().add(lblAmoutExerciseHours);
		 * 
		 * 
		 * JLabel lblAmountExercisekcal = new JLabel("총 소모칼로리:");
		 * lblAmountExercisekcal.setBounds(70, 454, 97, 15);
		 * getContentPane().add(lblAmountExercisekcal);
		 */

		JButton btnOk = new JButton("확인");
		btnOk.setBounds(284, 164, 97, 23);
		getContentPane().add(btnOk);

		JTextPane txtpnMetablicEquivalentsOf = new JTextPane();
		txtpnMetablicEquivalentsOf.setText("Metablic Equivalents of Task(대사당량)");
		txtpnMetablicEquivalentsOf.setBounds(324, 84, -128, 21);
		getContentPane().add(txtpnMetablicEquivalentsOf);

		JLabel lblExplainMets = new JLabel("운동과 신체활동의 강도를 나타내는 지수");
		lblExplainMets.setBounds(125, 214, 256, 15);
		getContentPane().add(lblExplainMets);

		JLabel lblExplainMETs = new JLabel("METs란? Metablic Equivalents of Task(대사당량)");
		lblExplainMETs.setBounds(70, 197, 294, 15);
		getContentPane().add(lblExplainMETs);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/cubby.gif")));
		lblNewLabel.setBounds(125, 41, 190, 102);
		getContentPane().add(lblNewLabel);

		setSize(500, 530);
		setVisible(true);
	}

	// 데이터베이스에 시작 시간 삽입하는 메소드
	private void insertStartTimeIntoDatabase(String startTime) {
		try {
			// 데이터베이스 연결
			Connection connection = MySqlConnectionProvider.getConnection();
			// 삽입 쿼리 작성
			String query = "INSERT INTO exericserecords (start_time) VALUES (?)";
			// PreparedStatement 생성
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			// 파라미터 설정
			preparedStatement.setString(1, startTime);
			// 쿼리 실행
			preparedStatement.executeUpdate();
			// 성공 메시지 출력
			JOptionPane.showMessageDialog(null, "운동 시작 시간이 데이터베이스에 추가되었습니다.");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 데이터베이스에 종료 시간 삽입하는 메소드
	private void insertEndTimeIntoDatabase(String endTime) {
		try {
			// 데이터베이스 연결
			Connection connection = MySqlConnectionProvider.getConnection();
			// 삽입 쿼리 작성
			String query = "INSERT INTO exericserecords (end_time) VALUES (?)";
			// PreparedStatement 생성
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			// 파라미터 설정
			preparedStatement.setString(1, endTime);
			// 쿼리 실행
			preparedStatement.executeUpdate();
			// 성공 메시지 출력
			JOptionPane.showMessageDialog(null, "운동 종료 시간이 데이터베이스에 추가되었습니다.");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ExerciseRecords();
	}
}
