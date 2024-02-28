package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import dbutil.MySqlConnectionProvider;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;

public class Dinner_FindFoodCalories extends JFrame {
	private JTextField textField;
	private JButton btnNewButton;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton btnNewButton_1;
	private String user_id;
	private String foodName; // 사용자가 선택한 음식명
	private double dinner_kcal;

	// 현재 날짜
	java.util.Date currentDate = new java.util.Date();
	// sql에 넣기 위해 날짜를 date형식으로 변경
	Date sqlDate = new Date(currentDate.getTime());
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_7;
	private JTextPane lbl_list;
	private List<String> list = new ArrayList<>();
	private JLabel lblNewLabel_6;

	public Dinner_FindFoodCalories(String loginId) {
		getContentPane().setBackground(Color.WHITE);
		this.user_id = loginId;
		extracted();
		showGUI();

		writeTodayEat();

	}

	private void writeTodayEat() {
		String sqlSelectEatFood = "SELECT dinner_meal FROM dinnerdiet WHERE user_id = ? AND date = ? ";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sqlSelectEatFood);
				ResultSet rs = pst.executeQuery();
				
				) {
			pst.setString(1, user_id);
			pst.setDate(2, sqlDate);

			while (rs.next()) {
				String eatenFoodList = rs.getString("dinner_meal");
				// "단식"이 아닌 경우에만 리스트에 추가
				if (!"단식".equals(eatenFoodList)) {
					list.add(eatenFoodList);
				}

			}

			StringBuilder stringBuilder = new StringBuilder();
			for (String element : list) {
				stringBuilder.append("<font face='휴먼편지체' size='4' color='black'>").append(element).append("<br/>");
			}
			String htmlText = stringBuilder.toString();

			// HTML 텍스트를 레이블에 설정
			lbl_list.setText("<html>" + htmlText + "</html>");
			list.clear();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void findFoodList(String findFoodName) {
		String selectQuery = "SELECT * FROM foodcalories WHERE foodName LIKE ? LIMIT 0,1000000";
		List<FoodData> list = new ArrayList<>();

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(selectQuery);
				ResultSet rs = pst.executeQuery();
				
				) {
			pst.setString(1, "%" + findFoodName + "%");

			while (rs.next()) {
				String foodName = rs.getString("foodName");

				double oneServing = rs.getDouble("oneServing");
				String unit = rs.getString("unit");
				String oneServingAndUnit = oneServing + unit;

				double calories = rs.getDouble("calories");

				FoodData foodData = new FoodData(foodName, oneServingAndUnit, calories);
				list.add(foodData);
			}

			// 검색 결과를 테이블에 업데이트
			updateTable(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void updateTable(List<FoodData> list) { // JTable업데이트하는 메소드
		// 테이블 모델 초기화
		tableModel.setRowCount(0);

		// 검색 결과를 테이블 모델에 추가
		for (FoodData foodData : list) {
			tableModel
					.addRow(new Object[] { foodData.getFoodName(), foodData.getOneServing(), foodData.getCalories() });
		}
	}

	private void showGUI() {
		setResizable(false); // 창 크기 고정
		setSize(491, 601);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 화면 중앙에 위치
		setVisible(true);
	}

	private void extracted() {
		setTitle("저녁");
		getContentPane().setLayout(null);

		lbl_list = new JTextPane();
		lbl_list.setFont(new Font("휴먼편지체", Font.PLAIN, 13));
		lbl_list.setBackground(new Color(255, 228, 181));
		lbl_list.setContentType("text/html");
		lbl_list.setEditable(false);
		JScrollPane scrollPaneForTextPane = new JScrollPane(lbl_list);
		scrollPaneForTextPane.setBounds(66, 447, 344, 103);
		getContentPane().add(scrollPaneForTextPane);

		lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/저녁해.png")));
		lblNewLabel_6.setBounds(33, 382, 72, 51);
		getContentPane().add(lblNewLabel_6);

		lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/음식칼로리.png")));
		lblNewLabel_3.setBounds(58, 0, 105, 33);
		getContentPane().add(lblNewLabel_3);

		textField = new JTextField();
		textField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		textField.setBounds(161, 69, 116, 21);
		getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/검색 1.png")));
		btnNewButton.setBounds(296, 68, 67, 23);
		btnNewButton.addActionListener(new ActionListener() {
			private String findFoodName;

			public void actionPerformed(ActionEvent arg0) {
				findFoodName = textField.getText();
				System.out.println(findFoodName);
				findFoodList(findFoodName);

			}
		});
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("저녁 :");
		lblNewLabel.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
		lblNewLabel.setBounds(111, 397, 44, 19);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(155, 396, 158, 21);
		getContentPane().add(lblNewLabel_1);

		// 테이블 모델 초기화
		tableModel = new DefaultTableModel();
		tableModel.addColumn("음식명");
		tableModel.addColumn("1회 제공량");
		tableModel.addColumn("칼로리");

		// JTable 초기화 및 모델 설정
		resultTable = new JTable(tableModel);
		resultTable.setFont(new Font("휴먼편지체", Font.PLAIN, 13));

		// 더블클릭으로 셀 수정을 막기 위해 DefaultCellEditor를 사용하여 에디터 설정
		resultTable.setDefaultEditor(Object.class, null);

		// JTable에 ListSelectionListener 추가
		resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = resultTable.getSelectedRow();
					if (selectedRow >= 0) {
						foodName = resultTable.getValueAt(selectedRow, 0).toString();
						String oneServing = resultTable.getValueAt(selectedRow, 1).toString();
						String calories = resultTable.getValueAt(selectedRow, 2).toString();
						dinner_kcal = (Double) resultTable.getValueAt(selectedRow, 2); // 더블형식의 아침 칼로리

						System.out.println("선택된 항목: " + foodName + ", " + oneServing + ", " + calories);

						// 선택된 행의 foodName을 lblNewLabel_1에 설정
						lblNewLabel_1.setText(foodName);
					}
				}
			}
		});

		// JScrollPane에 JTable 추가하여 스크롤 가능하도록 설정
		JScrollPane scrollPane = new JScrollPane(resultTable);
		scrollPane.setBounds(12, 117, 452, 257);
		getContentPane().add(scrollPane);

		btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/섭취.png")));
		btnNewButton_1.setBounds(315, 395, 67, 23);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO 확인버튼을 누르면 디비 아침다이어트 테이블에 생성
				String dinner_meal = foodName;

				if (dinner_meal != null && !dinner_meal.isEmpty()) {
					String insertQeury = "INSERT INTO dinnerdiet (user_id, date, dinner_meal, dinner_kcal) VALUES (?,?,?,?)";
					try (Connection conn = MySqlConnectionProvider.getConnection();
							PreparedStatement pst = conn.prepareStatement(insertQeury)) {
						pst.setString(1, user_id);
						pst.setDate(2, sqlDate);
						pst.setString(3, dinner_meal);
						pst.setDouble(4, dinner_kcal);

						// executeUpdate()를 사용하여 DML 쿼리 실행
						int affectedRows = pst.executeUpdate();

						writeTodayEat();

						if (affectedRows > 0) {
							System.out.println("데이터가 성공적으로 삽입되었습니다.");
						} else {
							System.out.println("데이터 삽입에 실패했습니다.");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("dinner_meal이 비어있습니다. 데이터를 삽입하지 않습니다.");
				}

			}
		});
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		getContentPane().add(btnNewButton_1);

		btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/뒤로가기.png")));
		btnNewButton_2.setToolTipText("뒤로가기");
		btnNewButton_2.setBounds(0, 1, 57, 33);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new DietRecord(user_id);
			}
		});
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setFocusPainted(false);
		getContentPane().add(btnNewButton_2);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Snack_FindFoodCalories.class.getResource("/image/큰초록바.png")));
		lblNewLabel_2.setBounds(0, 0, 479, 38);
		getContentPane().add(lblNewLabel_2);

		lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(Morning_FindFoodCalories.class.getResource("/image/연한연두.png")));
		lblNewLabel_7.setBounds(45, 62, 381, 36);
		getContentPane().add(lblNewLabel_7);

		lblNewLabel_4 = new JLabel("음식명: ");
		lblNewLabel_4.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(108, 72, 52, 15);
		getContentPane().add(lblNewLabel_4);

		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Morning_FindFoodCalories.class.getResource("/image/연한연두.png")));
		lblNewLabel_5.setBounds(50, 389, 381, 36);
		getContentPane().add(lblNewLabel_5);
	}

}
