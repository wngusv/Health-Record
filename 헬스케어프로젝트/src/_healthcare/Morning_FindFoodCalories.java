package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import dbutil.MySqlConnectionProvider;

import javax.swing.JTextField;
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

public class Morning_FindFoodCalories extends JFrame {
	private JTextField textField;
	private JButton btnNewButton;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton btnNewButton_1;
	private String user_id;
	private String foodName; // 사용자가 선택한 음식명
	private double breakfast_kcal;
	
	
	// 현재 날짜
	java.util.Date currentDate = new java.util.Date();
	// sql에 넣기 위해 날짜를 date형식으로 변경
	Date sqlDate = new Date(currentDate.getTime());

	public Morning_FindFoodCalories(String loginId) {
		this.user_id = loginId;
		extracted();
		showGUI();
	}

	private void findFoodList(String findFoodName) {
		String selectQuery = "SELECT * FROM foodcalories WHERE foodName LIKE ? LIMIT 0,1000000";
		List<FoodData> list = new ArrayList<>();

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(selectQuery);) {
			pst.setString(1, "%" + findFoodName + "%");

			ResultSet rs = pst.executeQuery();
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
		setSize(484, 592);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void extracted() {
		setTitle("음식칼로리");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 97, SpringLayout.WEST, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(new ActionListener() {
			private String findFoodName;

			public void actionPerformed(ActionEvent arg0) {
				findFoodName = textField.getText();
				System.out.println(findFoodName);
				findFoodList(findFoodName);

			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 10, SpringLayout.EAST, textField);
		getContentPane().add(btnNewButton);

		JLabel lblNewLabel = new JLabel("아침 :");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 80, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -49, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -351, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 6, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, -56, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, -131, SpringLayout.EAST, getContentPane());
		getContentPane().add(lblNewLabel_1);

		// 테이블 모델 초기화
		tableModel = new DefaultTableModel();
		tableModel.addColumn("음식명");
		tableModel.addColumn("1회 제공량");
		tableModel.addColumn("칼로리");

		// JTable 초기화 및 모델 설정
		resultTable = new JTable(tableModel);

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
						breakfast_kcal = (Double) resultTable.getValueAt(selectedRow, 2); // 더블형식의 아침 칼로리

						System.out.println("선택된 항목: " + foodName + ", " + oneServing + ", " + calories);

						// 선택된 행의 foodName을 lblNewLabel_1에 설정
						lblNewLabel_1.setText(foodName);
					}
				}
			}
		});

		// JScrollPane에 JTable 추가하여 스크롤 가능하도록 설정
		JScrollPane scrollPane = new JScrollPane(resultTable);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 187, SpringLayout.SOUTH, scrollPane);

		// 컨테이너에 컴포넌트들 추가
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 267, SpringLayout.SOUTH, textField);
		getContentPane().add(scrollPane);

		btnNewButton_1 = new JButton("확인");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String breakfast_meal = foodName;
				
				String insertQeury = "INSERT INTO morningdiet (user_id, date, breakfast_meal, breakfast_kcal) VALUES (?,?,?,?)";
				try(Connection conn = MySqlConnectionProvider.getConnection();
						PreparedStatement pst = conn.prepareStatement(insertQeury)){
					pst.setString(1, user_id);
					pst.setDate(2, sqlDate);
					pst.setString(3, breakfast_meal);
					pst.setDouble(4, breakfast_kcal);
					
					 // executeUpdate()를 사용하여 DML 쿼리 실행
		            int affectedRows = pst.executeUpdate();

		            if (affectedRows > 0) {
		                System.out.println("데이터가 성공적으로 삽입되었습니다.");
		            } else {
		                System.out.println("데이터 삽입에 실패했습니다.");
		            }
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
		
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_1, -2, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_1, 5, SpringLayout.EAST, lblNewLabel_1);
		getContentPane().add(btnNewButton_1);
	}

	public static void main(String[] args) {
		new Morning_FindFoodCalories("asd");
	}
}

