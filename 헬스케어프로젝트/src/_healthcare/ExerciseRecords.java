package _healthcare;

import javax.swing.JFrame;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dbutil.MySqlConnectionProvider;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

public class ExerciseRecords extends JFrame {
	private JButton btn_Ok;
	private String loginId;
	private String startTime; // 추가: 운동 시작 시간을 저장할 변수
	private String selectedExercise; // 추가: 선택된 운동을 저장할 변수
	private String selectedExercise2;
	private JLabel lbl_selected;
	private JButton btn_start;
	private JComboBox comboBox_Sports;
	private JLabel lbl_start;
	private JLabel lblTimeDiff;
	private String timediff;

	public ExerciseRecords(String loginId) {
		this.loginId = loginId;
		getContentPane().setBackground(Color.WHITE);
		System.out.println(loginId);
		setTitle("운동기록");
		setSize(422, 546); // 창의 너비와 높이를 설정합니다.
		setResizable(false); // 창의 크기를 조절할 수 없도록 설정합니다.
		getContentPane().setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("운동 시간 :");
		lblNewLabel_1.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(91, 456, 84, 24);
		getContentPane().add(lblNewLabel_1);

		JLabel lbl_end = new JLabel("운동 종료 시간");
		lbl_end.setIcon(null);
		lbl_end.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
		lbl_end.setBounds(248, 377, 91, 58);
		getContentPane().add(lbl_end);

		lbl_start = new JLabel("운동 시작 시간");
		lbl_start.setIcon(null);
		lbl_start.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
		lbl_start.setBounds(70, 377, 91, 48);
		getContentPane().add(lbl_start);

		JLabel lblTitle = new JLabel("운동 기록");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("휴먼편지체", Font.BOLD, 23));
		lblTitle.setBounds(47, -6, 125, 55);
		getContentPane().add(lblTitle);

		btn_start = new JButton("");
		btn_start.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/start.png")));
		btn_start.setOpaque(false); // 배경 투명하게 설정
		btn_start.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
		btn_start.setBorderPainted(false); // 테두리 제거
		btn_start.setBounds(32, 253, 152, 120);
		getContentPane().add(btn_start);
		// 운동시작 버튼 클릭 시 이벤트 리스너
		btn_start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 현재 시간 가져오기
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				startTime = now.format(formatter); // 운동 시작 시간 저장
				try (Connection conn = MySqlConnectionProvider.getConnection()) {
					String sql = "UPDATE exerciserecords SET start_time = NOW() WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setString(1, loginId);

					stmt.executeUpdate();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				// lbl_start에 현재 시간 표시
				lbl_start.setText(startTime);

				// 버튼 이미지 변경
				// ImageIcon newIcon = new
				// ImageIcon(getClass().getResource("/image/actionstart.png"));
				// System.out.println("New icon: " + newIcon); // 콘솔에 이미지 아이콘 정보 출력
				// btn_start.setIcon(newIcon);
				btn_start.setEnabled(false); // 버튼을 클릭한 후에 비활성화
			}
		});

		// 운동목록 comboBox
		comboBox_Sports = new JComboBox();
		comboBox_Sports.setBackground(new Color(255, 255, 240));
		comboBox_Sports.addItem("(운동 목록 선택)");
		comboBox_Sports.setBounds(32, 217, 264, 29);
		getContentPane().add(comboBox_Sports);

		// MySQL 연결 및 데이터베이스에서 목록 불러오기
		try (Connection connection = MySqlConnectionProvider.getConnection();
				Statement statement = connection.createStatement();
				) {

			List<String> sportsList = new ArrayList<>();
			try(ResultSet resultSet = statement.executeQuery("SELECT sports FROM mets");){
				
				while (resultSet.next()) {
					String sportsName = resultSet.getString("sports");
					sportsList.add(sportsName);
				}
			}

			// 콤보 박스에 목록 추가
			for (String sports : sportsList) {
				comboBox_Sports.addItem(sports);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		btn_Ok = new JButton("");
		btn_Ok.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/ok.png")));
		btn_Ok.setBounds(308, 214, 91, 36);
		btn_Ok.setOpaque(false); // 배경 투명하게 설정
		btn_Ok.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
		btn_Ok.setBorderPainted(false); // 테두리 제거
		getContentPane().add(btn_Ok);

		lbl_selected = new JLabel("");
		lbl_selected.setBounds(32, 192, 294, 15);
		getContentPane().add(lbl_selected);

		// '확인' 버튼의 ActionListener 추가
		btn_Ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 선택된 운동 항목 가져오기
				selectedExercise = comboBox_Sports.getSelectedItem().toString(); // 선택된 운동 저장

				// lbl_selected에 선택된 운동 항목 표시
				lbl_selected.setText(selectedExercise);

				// 현재 시간 가져오기
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				String formattedDateTime = now.format(formatter);

				// MySQL에 현재 시간과 선택된 운동 항목 추가
				String sql = "INSERT INTO exerciserecords (user_id, date, exercise_name) VALUES (?, CURDATE(), ?)";
				try (Connection conn = MySqlConnectionProvider.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql);) {
					stmt.setString(1, loginId);
					stmt.setString(2, selectedExercise);
					stmt.executeUpdate();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				btn_Ok.setEnabled(false);
			}
		});

		// 운동종료 버튼
		JButton btn_end = new JButton("");
		btn_end.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/finish.png")));
		btn_end.setBounds(217, 253, 152, 120);
		btn_end.setOpaque(false); // 배경 투명하게 설정
		btn_end.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
		btn_end.setBorderPainted(false); // 테두리 제거
		getContentPane().add(btn_end);

		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/뒤로가기.png")));
		btnBack.setFocusPainted(false); // 배경 투명하게 설정
		btnBack.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
		btnBack.setBorderPainted(false); // 테두리 제거
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(loginId);
			}
		});
		btnBack.setBounds(0, 1, 45, 36);
		getContentPane().add(btnBack);

		JLabel lblgreenbar = new JLabel("");
		lblgreenbar.setForeground(Color.WHITE);
		lblgreenbar.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblgreenbar.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/큰초록바.png")));
		lblgreenbar.setBounds(0, 0, 416, 38);
		getContentPane().add(lblgreenbar);

		JLabel lblstartbackground = new JLabel("");
		lblstartbackground.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/starttime.png")));
		lblstartbackground.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblstartbackground.setBounds(57, 377, 138, 48);
		getContentPane().add(lblstartbackground);

		JLabel lblfinishbackground = new JLabel("");
		lblfinishbackground.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/finishtime.png")));
		lblfinishbackground.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
		lblfinishbackground.setBounds(234, 380, 138, 48);
		getContentPane().add(lblfinishbackground);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/exercisehours.png")));
		lblNewLabel.setBounds(74, 442, 271, 48);
		getContentPane().add(lblNewLabel);

		lblTimeDiff = new JLabel("");
		lblTimeDiff.setBounds(228, 462, 57, 15);
		getContentPane().add(lblTimeDiff);

		JPanel calendarPanel = new JPanel();
		ExerciseCalendar exerciseCalendar = new ExerciseCalendar(loginId);
		// 운동종료 버튼 클릭 시 이벤트 리스너
		btn_end.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 현재 시간 가져오기
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				String formattedDateTimeE = now.format(formatter);
				exerciseCalendar.changeImageOfToday(); // ExerciseCalendar 객체의 메서드 호출
				// lbl_end에 현재 시간 표시
				lbl_end.setText(formattedDateTimeE);
				lblTimeDiff.setText(timediff);
				// MySQL에 현재 시간 삽입
				// 운동 종료 시간을 갱신
				String sql = "UPDATE exerciserecords SET end_time = NOW() WHERE start_time = ? AND user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
				try (Connection conn = MySqlConnectionProvider.getConnection();
						PreparedStatement stmt = conn.prepareStatement(sql);) {
					stmt.setString(1, startTime);
					stmt.setString(2, loginId);
					stmt.executeUpdate();

				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});

		loadExerciseName();
		loadStartTime();
		loadHours();
		setLocationRelativeTo(null);
	}

	private void loadStartTime() {
		String sql = "SELECT start_time FROM exerciserecords WHERE user_id = ? AND date = CURDATE() AND exercise_name = ? ORDER BY record_id DESC LIMIT 1;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, loginId);
			pst.setString(2, selectedExercise2);

			
			try(ResultSet rs = pst.executeQuery();){
				
				while (rs.next()) {
					startTime = rs.getString("start_time");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadExerciseName() {
		String sql = "SELECT exercise_name FROM exerciserecords WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);

		) {

			pst.setString(1, loginId);

			
			try(ResultSet rs = pst.executeQuery();){
				
				while (rs.next()) {
					selectedExercise2 = rs.getString("exercise_name");
				}
			}
			lbl_selected.setText(selectedExercise2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadHours() {
		String sql = "SELECT TIME_DIFF FROM exerciserecords WHERE user_id = ? AND date = CURDATE() AND exercise_name = ? ORDER BY record_id DESC LIMIT 1;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, loginId);
			pst.setString(2, selectedExercise2);

			
			try(ResultSet rs = pst.executeQuery();){
				
				while (rs.next()) {
					timediff = rs.getString("TIME_DIFF");
				}
			}
//	        lblTimeDiff.setText(timediff); // 라벨에 값 설정
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}