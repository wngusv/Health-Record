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

	public DietRecord(String loginId) {
		this.user_id = loginId;
		extracted();
		showGUI();
	}

	private void showGUI() {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void extracted() {
		setTitle("식단 기록");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JLabel lblNewLabel = new JLabel("아침: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 89, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 45, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("점심: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 141, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 45, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("저녁: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 218, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_2, 45, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("간식: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 282, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_3, 45, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel(" ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_4, 89, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_4, 83, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_4, 234, SpringLayout.WEST, getContentPane());
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
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_5, 141, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_5, 83, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_5, 222, SpringLayout.WEST, getContentPane());
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
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_6, 218, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_6, 83, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_6, 155, SpringLayout.WEST, getContentPane());
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
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_7, 0, SpringLayout.NORTH, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_7, 6, SpringLayout.EAST, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_7, 0, SpringLayout.EAST, lblNewLabel_6);
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
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 85, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 240, SpringLayout.WEST, getContentPane());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Morning_FindFoodCalories m = new Morning_FindFoodCalories(user_id);
				m.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("음식 추가");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_1, 137, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_1, 240, SpringLayout.WEST, getContentPane());
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lunch_FindFoodCalories l = new Lunch_FindFoodCalories(user_id);
				l.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("음식 추가");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_2, 228, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_2, 258, SpringLayout.WEST, getContentPane());
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dinner_FindFoodCalories d = new Dinner_FindFoodCalories(user_id);
				d.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("음식 추가");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_3, 278, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_3, 258, SpringLayout.WEST, getContentPane());
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Snack_FindFoodCalories s = new Snack_FindFoodCalories(user_id);
				s.setVisible(true);
				dispose();
			}
		});
		getContentPane().add(btnNewButton_3);

		JCheckBox chckbxNewCheckBox = new JCheckBox("단식했어요");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 112, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 41, SpringLayout.WEST, getContentPane());
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox.isSelected()) {
					lblNewLabel_4.setText("단식");
				} else {
					lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("단식했어요");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_1, 162, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_1, 45, SpringLayout.WEST, getContentPane());
		chckbxNewCheckBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_1.isSelected()) {
					lblNewLabel_5.setText("단식");
				} else {
					lblNewLabel_5.setText(String.valueOf(lunchSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("단식했어요");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_2, 239, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_2, 41, SpringLayout.WEST, getContentPane());
		chckbxNewCheckBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_2.isSelected()) {
					lblNewLabel_6.setText("단식");
				} else {
					lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("단식했어요");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_3, 303, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_3, 45, SpringLayout.WEST, getContentPane());
		chckbxNewCheckBox_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 체크박스가 선택되면 레이블 텍스트를 변경
				if (chckbxNewCheckBox_3.isSelected()) {
					lblNewLabel_7.setText("단식");
				} else {
					lblNewLabel_7.setText(String.valueOf(snackSumKcal));
				}
			}
		});
		getContentPane().add(chckbxNewCheckBox_3);

		JLabel lblNewLabel_8 = new JLabel("금일 칼로리: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_8, 352, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_8, 47, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("New label");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_9, 359, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_9, 165, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_9, 204, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("/ 권장칼로리: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_10, 352, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_10, 221, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_10, 323, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("New label");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_11, 352, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_11, 335, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_11, 420, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_11);

		JButton btnNewButton_4 = new JButton("뒤로가깅ㅎ");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_4, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_4, 21, SpringLayout.WEST, getContentPane());
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(user_id);
			}
		});
		getContentPane().add(btnNewButton_4);

	}
}
