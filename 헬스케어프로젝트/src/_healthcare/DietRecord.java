package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DietRecord extends JFrame {

	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private String user_id;
	// 현재 날짜
	java.util.Date currentDate = new java.util.Date();
	// sql에 넣기 위해 날짜를 date형식으로 변경
	Date sqlDate = new Date(currentDate.getTime());
	private double breakfastSumKcal;
	private double lunchSumKcal;
	private double dinnerSumKcal;
	private double snackSumKcal;
	private JLabel lblNewLabel_7;
	private Double todayEatSumKacl;
	private JLabel lblNewLabel_9;
	private JLabel lblNewLabel_11;

	public DietRecord(String loginId) {
		getContentPane().setBackground(Color.WHITE);
		this.user_id = loginId;
		extracted();
		todayEatKcal();
		showGUI();
	}

	private void showGUI() {
		setSize(456, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void extracted() {
		setUndecorated(true);

		setTitle("식단 기록");
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("아침: ");
		lblNewLabel.setIcon(new ImageIcon(DietRecord.class.getResource("/image/아침칼로리.png")));
		lblNewLabel.setBounds(36, 85, 168, 36);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("점심: ");
		lblNewLabel_1.setBounds(45, 141, 32, 15);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("저녁: ");
		lblNewLabel_2.setBounds(45, 218, 32, 15);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("간식: ");
		lblNewLabel_3.setBounds(45, 282, 32, 15);
		getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel(" ");
		lblNewLabel_4.setBounds(95, 76, 78, 30);
		lblNewLabel_4.setFont(new Font("HY엽서M", Font.PLAIN, 12));
		String breakfastKcal_sql = "SELECT SUM(breakfast_kcal) FROM morningdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(breakfastKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				breakfastSumKcal = rs.getDouble("SUM(breakfast_kcal)");

			}
			lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel(" ");
		lblNewLabel_5.setBounds(83, 141, 139, 15);
		lblNewLabel_5.setFont(new Font("HY엽서M", Font.PLAIN, 12));
		String lunchKcal_sql = "SELECT SUM(lunch_kcal) FROM lunchdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(lunchKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				lunchSumKcal = rs.getDouble("SUM(lunch_kcal)");

			}
			lblNewLabel_5.setText(String.valueOf(lunchSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getContentPane().add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel(" ");
		lblNewLabel_6.setBounds(83, 218, 72, 15);
		lblNewLabel_6.setFont(new Font("HY엽서M", Font.PLAIN, 12));
		String dinnerKcal_sql = "SELECT SUM(dinner_kcal) FROM dinnerdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(dinnerKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				dinnerSumKcal = rs.getDouble("SUM(dinner_kcal)");

			}
			lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getContentPane().add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel(" ");
		lblNewLabel_7.setBounds(83, 282, 72, 15);
		lblNewLabel_7.setFont(new Font("HY엽서M", Font.PLAIN, 12));
		String snackKcal_sql = "SELECT SUM(snack_kcal) FROM snackdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(snackKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				snackSumKcal = rs.getDouble("SUM(snack_kcal)");

			}
			lblNewLabel_7.setText(String.valueOf(snackSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getContentPane().add(lblNewLabel_7);

		JButton btnNewButton = new JButton("음식 추가");
		btnNewButton.setBounds(240, 85, 85, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Morning_FindFoodCalories m = new Morning_FindFoodCalories(user_id);
				m.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("음식 추가");
		btnNewButton_1.setBounds(240, 137, 85, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lunch_FindFoodCalories l = new Lunch_FindFoodCalories(user_id);
				l.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("음식 추가");
		btnNewButton_2.setBounds(258, 228, 85, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dinner_FindFoodCalories d = new Dinner_FindFoodCalories(user_id);
				d.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("음식 추가");
		btnNewButton_3.setBounds(258, 278, 85, 23);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Snack_FindFoodCalories s = new Snack_FindFoodCalories(user_id);
				s.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_3);

		JCheckBox chckbxNewCheckBox = new JCheckBox("단식했어요");
		chckbxNewCheckBox.setBounds(46, 112, 85, 23);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox.isSelected()) {
					lblNewLabel_4.setText("단식");
					fastSql("morningdiet", "breakfast_meal", "breakfast_kcal");
					breakfastSumKcal = 0.0;
					todayEatKcal();
				} else {
					lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("단식했어요");
		chckbxNewCheckBox_1.setBounds(46, 189, 85, 23);
		chckbxNewCheckBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_1.isSelected()) {
					lblNewLabel_5.setText("단식");
					fastSql("lunchdiet", "lunch_meal", "lunch_kcal");
					lunchSumKcal = 0.0;
					todayEatKcal();
				} else {
					lblNewLabel_5.setText(String.valueOf(lunchSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("단식했어요");
		chckbxNewCheckBox_2.setBounds(41, 239, 85, 23);
		chckbxNewCheckBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_2.isSelected()) {
					lblNewLabel_6.setText("단식");
					fastSql("dinnerdiet", "dinner_meal", "dinner_kcal");
					dinnerSumKcal = 0.0;
					todayEatKcal();
				} else {
					lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("단식했어요");
		chckbxNewCheckBox_3.setBounds(45, 303, 85, 23);
		chckbxNewCheckBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_3.isSelected()) {
					lblNewLabel_7.setText("단식");
					fastSql("snackdiet", "snack_meal", "snack_kcal");
					snackSumKcal = 0.0;
					todayEatKcal();
				} else {
					lblNewLabel_7.setText(String.valueOf(snackSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_3);

		JLabel lblNewLabel_8 = new JLabel("금일 칼로리: ");
		lblNewLabel_8.setBounds(12, 356, 85, 15);
		getContentPane().add(lblNewLabel_8);

		lblNewLabel_9 = new JLabel(" ");
		lblNewLabel_9.setBounds(131, 348, 93, 23);
		// todayEatKcal();
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("/ 권장칼로리: ");
		lblNewLabel_10.setBounds(247, 352, 78, 15);
		getContentPane().add(lblNewLabel_10);

		lblNewLabel_11 = new JLabel(" ");
		lblNewLabel_11.setBounds(342, 350, 102, 30);
		String recommendKcalQuery = "SELECT recommended_kcal FROM users WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement ps = conn.prepareStatement(recommendKcalQuery);) {
			ps.setString(1, user_id);

			ResultSet rs = ps.executeQuery();
			String recommendKcal = "";
			while (rs.next()) {
				recommendKcal = rs.getBigDecimal("recommended_kcal").toString();
			}
			lblNewLabel_11.setText(recommendKcal.toString());

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		getContentPane().add(lblNewLabel_11);

		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setBounds(0, 0, 67, 36);
		getContentPane().add(btnNewButton_4);
		btnNewButton_4.setIcon(new ImageIcon(DietRecord.class.getResource("/image/뒤로가기.png")));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(user_id);
			}
		});
		// 배경을 투명하게 만드는 코드--------
		btnNewButton_4.setContentAreaFilled(false);
		btnNewButton_4.setBorderPainted(false);
		btnNewButton_4.setFocusPainted(false);

		JButton btnNewButton_5 = new JButton("");
		btnNewButton_5.setBounds(389, 0, 72, 36);
		getContentPane().add(btnNewButton_5);
		btnNewButton_5.setIcon(new ImageIcon(DietRecord.class.getResource("/image/종료.png")));
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 프로그램 종료
				System.exit(0);
			}
		});
		btnNewButton_5.setContentAreaFilled(false);
		btnNewButton_5.setBorderPainted(false);
		btnNewButton_5.setFocusPainted(false);
		
		JLabel lblNewLabel_12 = new JLabel("식단 기록");
		lblNewLabel_12.setForeground(Color.WHITE);
		lblNewLabel_12.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_12.setBounds(67, 0, 102, 36);
		getContentPane().add(lblNewLabel_12);
		
		JLabel lblNewLabel_14 = new JLabel("");
		lblNewLabel_14.setIcon(new ImageIcon(DietRecord.class.getResource("/image/큰초록바.png")));
		lblNewLabel_14.setBounds(0, 0, 461, 36);
		getContentPane().add(lblNewLabel_14);

	}

	private void fastSql(String whenFast, String whenMeal, String whenKcal) { // 삭제하지말고 업데이트 음식:"단식",칼로리 0 으로 해주자
		// String deleteSql = "DELETE FROM "+ whenFast +" WHERE user_id = ? AND date =
		// ?";
		String updateQuery = "UPDATE " + whenFast + " SET " + whenMeal + " = '단식'," + whenKcal
				+ "= 0.0 WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement ps = conn.prepareStatement(updateQuery);) {
			ps.setString(1, user_id);
			ps.setDate(2, sqlDate);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void todayEatKcal() {
		String sumKcal = "SELECT \r\n" + "    COALESCE(SUM(value), 0) AS '금일 칼로리'\r\n" + "FROM (\r\n"
				+ "    SELECT COALESCE(SUM(breakfast_kcal), 0) AS value FROM morningdiet WHERE user_id = ? AND date = ? \r\n"
				+ "    UNION ALL\r\n"
				+ "    SELECT COALESCE(SUM(lunch_kcal), 0) AS value FROM lunchdiet WHERE user_id = ? AND date = ? \r\n"
				+ "    UNION ALL\r\n"
				+ "    SELECT COALESCE(SUM(dinner_kcal), 0) AS value FROM dinnerdiet WHERE user_id = ? AND date = ? \r\n"
				+ "    UNION ALL\r\n"
				+ "    SELECT COALESCE(SUM(snack_kcal), 0) AS value FROM snackdiet WHERE user_id = ? AND date = ? \r\n"
				+ ") AS subquery;";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sumKcal);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);
			pst.setString(3, user_id);
			pst.setDate(4, sqlDate);
			pst.setString(5, user_id);
			pst.setDate(6, sqlDate);
			pst.setString(7, user_id);
			pst.setDate(8, sqlDate);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				todayEatSumKacl = rs.getDouble("금일 칼로리");
			}
			lblNewLabel_9.setText(todayEatSumKacl.toString());

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
