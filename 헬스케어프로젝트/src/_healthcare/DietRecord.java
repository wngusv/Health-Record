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
	private JLabel lblNewLabel_7;
	private String user_id;
	// 현재 날짜
	java.util.Date currentDate = new java.util.Date();
	// sql에 넣기 위해 날짜를 date형식으로 변경
	Date sqlDate = new Date(currentDate.getTime());
	private double breakfastSumKcal;
	private double lunchSumKcal;
	private double dinnerSumKcal;
	private double snackSumKcal;

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
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, lblNewLabel);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("저녁: ");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_2, 0, SpringLayout.WEST, lblNewLabel);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("간식: ");
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_3, 0, SpringLayout.EAST, lblNewLabel);
		getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel(" ");
		String breakfastKcal_sql = "SELECT SUM(breakfast_kcal) FROM morningdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(breakfastKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				breakfastSumKcal = rs.getDouble("SUM(breakfast_kcal)");
				
			}
			lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_4, 89, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_4, 6, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_4, 157, SpringLayout.EAST, lblNewLabel);
		getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel(" ");
		String lunchKcal_sql = "SELECT SUM(lunch_kcal) FROM lunchdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(lunchKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				lunchSumKcal = rs.getDouble("SUM(lunch_kcal)");
				
			}
			lblNewLabel_5.setText(String.valueOf(lunchSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_5, 0, SpringLayout.NORTH, lblNewLabel_1);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_5, 6, SpringLayout.EAST, lblNewLabel_1);
		getContentPane().add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel(" ");
		String dinnerKcal_sql = "SELECT SUM(dinner_kcal) FROM dinnerdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(dinnerKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				dinnerSumKcal = rs.getDouble("SUM(dinner_kcal)");
				
			}
			lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_6, 0, SpringLayout.NORTH, lblNewLabel_2);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_6, 6, SpringLayout.EAST, lblNewLabel_2);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_6, -370, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblNewLabel_6);

		lblNewLabel_7 = new JLabel(" ");
		String snackKcal_sql = "SELECT SUM(snack_kcal) FROM snackdiet WHERE user_id = ? AND date = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(snackKcal_sql);) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				snackSumKcal = rs.getDouble("SUM(snack_kcal)");
				
			}
			lblNewLabel_7.setText(String.valueOf(snackSumKcal));

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_7, 0, SpringLayout.NORTH, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_7, 132, SpringLayout.EAST, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_7, 0, SpringLayout.SOUTH, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_7, -429, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblNewLabel_7);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Morning_FindFoodCalories m = new Morning_FindFoodCalories(user_id);
				m.setVisible(true);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, -4, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, lblNewLabel_4);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lunch_FindFoodCalories l = new Lunch_FindFoodCalories(user_id);
				l.setVisible(true);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_5, -18, SpringLayout.WEST, btnNewButton_1);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_1, -4, SpringLayout.NORTH, lblNewLabel_1);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_1, 0, SpringLayout.EAST, btnNewButton);
		getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dinner_FindFoodCalories d = new Dinner_FindFoodCalories(user_id);
				d.setVisible(true);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_2, 228, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_2, 258, SpringLayout.WEST, getContentPane());
		getContentPane().add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Snack_FindFoodCalories s = new Snack_FindFoodCalories(user_id);
				s.setVisible(true);
				dispose();
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_3, -4, SpringLayout.NORTH, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_3, 0, SpringLayout.EAST, btnNewButton_2);
		getContentPane().add(btnNewButton_3);

		JCheckBox chckbxNewCheckBox = new JCheckBox("단식했어요");
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
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 6, SpringLayout.SOUTH, chckbxNewCheckBox);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox, 8, SpringLayout.SOUTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox, 41, SpringLayout.WEST, getContentPane());
		getContentPane().add(chckbxNewCheckBox);

		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("단식했어요");
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
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_1, 6, SpringLayout.SOUTH, lblNewLabel_1);
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_1, 0, SpringLayout.WEST, lblNewLabel);
		getContentPane().add(chckbxNewCheckBox_1);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("단식했어요");
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
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_2, 239, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_2, -6, SpringLayout.NORTH, chckbxNewCheckBox_2);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 20, SpringLayout.SOUTH, chckbxNewCheckBox_2);
		springLayout.putConstraint(SpringLayout.EAST, chckbxNewCheckBox_2, 0, SpringLayout.EAST, chckbxNewCheckBox);
		getContentPane().add(chckbxNewCheckBox_2);

		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("단식했어요");
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
		springLayout.putConstraint(SpringLayout.NORTH, chckbxNewCheckBox_3, 6, SpringLayout.SOUTH, lblNewLabel_3);
		springLayout.putConstraint(SpringLayout.WEST, chckbxNewCheckBox_3, 0, SpringLayout.WEST, lblNewLabel);
		getContentPane().add(chckbxNewCheckBox_3);

		JLabel lblNewLabel_8 = new JLabel("금일 칼로리: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_8, 26, SpringLayout.SOUTH, chckbxNewCheckBox_3);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_8, 47, SpringLayout.WEST, getContentPane());
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("New label");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_9, 46, SpringLayout.EAST, lblNewLabel_8);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_9, -87, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_9, -18, SpringLayout.EAST, lblNewLabel_5);
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("/ 권장칼로리: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_10, 51, SpringLayout.SOUTH, btnNewButton_3);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_10, 17, SpringLayout.EAST, lblNewLabel_9);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_10, 119, SpringLayout.EAST, lblNewLabel_9);
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("New label");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_11, 51, SpringLayout.SOUTH, btnNewButton_3);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_11, 12, SpringLayout.EAST, lblNewLabel_10);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_11, 97, SpringLayout.EAST, lblNewLabel_10);
		getContentPane().add(lblNewLabel_11);
	}

//	public static void main(String[] args) {
//		new DietRecord("asd");
//	}
}
