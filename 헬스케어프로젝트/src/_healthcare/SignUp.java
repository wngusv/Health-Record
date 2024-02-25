package _healthcare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;

public class SignUp extends JFrame {
	
	private JTextField idField;
	private JTextField pwField;
	private JTextField ageField;
	private JTextField heightField;
	private JTextField weightField;
	private JTextField goalField;
	private JTextField nameField;
	private JRadioButton maleButton;
	private JRadioButton femaleButton;
	private ButtonGroup genderButtonGroup;
	private ButtonGroup activtyButtonGroup;
	private JRadioButton actvityButton3;
	private JRadioButton actvityButton2;
	private JRadioButton actvityButton;
	
	public SignUp() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		idField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, idField, 93, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, idField, 93, SpringLayout.WEST, getContentPane());
		getContentPane().add(idField);
		idField.setColumns(10);

		pwField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, pwField, 26, SpringLayout.SOUTH, idField);
		springLayout.putConstraint(SpringLayout.EAST, pwField, 0, SpringLayout.EAST, idField);
		getContentPane().add(pwField);
		pwField.setColumns(10);

		ageField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, ageField, 24, SpringLayout.SOUTH, pwField);
		springLayout.putConstraint(SpringLayout.WEST, ageField, 0, SpringLayout.WEST, idField);
		getContentPane().add(ageField);
		ageField.setColumns(10);

		heightField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, heightField, 18, SpringLayout.SOUTH, ageField);
		springLayout.putConstraint(SpringLayout.EAST, heightField, 0, SpringLayout.EAST, idField);
		getContentPane().add(heightField);
		heightField.setColumns(10);

		weightField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, weightField, 19, SpringLayout.SOUTH, heightField);
		springLayout.putConstraint(SpringLayout.EAST, weightField, 0, SpringLayout.EAST, idField);
		getContentPane().add(weightField);
		weightField.setColumns(10);

		
		nameField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, nameField, 0, SpringLayout.EAST, idField);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		JLabel idlbl = new JLabel("아이디");
		springLayout.putConstraint(SpringLayout.SOUTH, idlbl, 0, SpringLayout.SOUTH, idField);
		springLayout.putConstraint(SpringLayout.EAST, idlbl, -6, SpringLayout.WEST, idField);
		getContentPane().add(idlbl);

		JLabel pwlbl = new JLabel("비밀번호");
		springLayout.putConstraint(SpringLayout.SOUTH, pwlbl, 0, SpringLayout.SOUTH, pwField);
		springLayout.putConstraint(SpringLayout.EAST, pwlbl, -6, SpringLayout.WEST, pwField);
		getContentPane().add(pwlbl);

		JLabel agelbl = new JLabel("나이");
		springLayout.putConstraint(SpringLayout.SOUTH, agelbl, 0, SpringLayout.SOUTH, ageField);
		springLayout.putConstraint(SpringLayout.EAST, agelbl, -6, SpringLayout.WEST, ageField);
		getContentPane().add(agelbl);

		JLabel heightlbl = new JLabel("키");
		springLayout.putConstraint(SpringLayout.SOUTH, heightlbl, 0, SpringLayout.SOUTH, heightField);
		springLayout.putConstraint(SpringLayout.EAST, heightlbl, -6, SpringLayout.WEST, heightField);
		getContentPane().add(heightlbl);

		JLabel weightlbl = new JLabel("현재 몸무게");
		springLayout.putConstraint(SpringLayout.SOUTH, weightlbl, 0, SpringLayout.SOUTH, weightField);
		springLayout.putConstraint(SpringLayout.EAST, weightlbl, -6, SpringLayout.WEST, weightField);
		getContentPane().add(weightlbl);

		JLabel namelbl = new JLabel("이름");
		springLayout.putConstraint(SpringLayout.NORTH, nameField, -3, SpringLayout.NORTH, namelbl);
		springLayout.putConstraint(SpringLayout.EAST, namelbl, 0, SpringLayout.EAST, idlbl);
		getContentPane().add(namelbl);

		JLabel goalWeightlbl = new JLabel("목표 몸무게");
		springLayout.putConstraint(SpringLayout.NORTH, goalWeightlbl, 15, SpringLayout.SOUTH, weightlbl);
		springLayout.putConstraint(SpringLayout.EAST, goalWeightlbl, 0, SpringLayout.EAST, idlbl);
		getContentPane().add(goalWeightlbl);

		goalField = new JTextField(); // 목표 몸무게 입력칸
		springLayout.putConstraint(SpringLayout.NORTH, goalField, 0, SpringLayout.NORTH, goalWeightlbl);
		springLayout.putConstraint(SpringLayout.WEST, goalField, 0, SpringLayout.WEST, idField);
		getContentPane().add(goalField);
		goalField.setColumns(10);
		
		JLabel genderlbl = new JLabel("성별");
		springLayout.putConstraint(SpringLayout.NORTH, genderlbl, 366, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, namelbl, -15, SpringLayout.NORTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, genderlbl, 0, SpringLayout.WEST, idlbl);
		getContentPane().add(genderlbl);

		JButton registerButton = new JButton("가입");
		springLayout.putConstraint(SpringLayout.NORTH, registerButton, -4, SpringLayout.NORTH, genderlbl);
		getContentPane().add(registerButton);
		
		
		maleButton = new JRadioButton("남자");
		springLayout.putConstraint(SpringLayout.NORTH, maleButton, -4, SpringLayout.NORTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, maleButton, 0, SpringLayout.WEST, idField);
		getContentPane().add(maleButton);
		
		femaleButton = new JRadioButton("여자");
		springLayout.putConstraint(SpringLayout.WEST, registerButton, 24, SpringLayout.EAST, femaleButton);
		springLayout.putConstraint(SpringLayout.NORTH, femaleButton, -4, SpringLayout.NORTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, femaleButton, 17, SpringLayout.EAST, maleButton);
		getContentPane().add(femaleButton);
		
		JLabel lblNewLabel = new JLabel("활동 지수");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 26, SpringLayout.SOUTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, weightlbl);
		getContentPane().add(lblNewLabel);
		
		actvityButton = new JRadioButton("거의 움직이지 않음");
		springLayout.putConstraint(SpringLayout.NORTH, actvityButton, 6, SpringLayout.SOUTH, maleButton);
		springLayout.putConstraint(SpringLayout.EAST, actvityButton, 0, SpringLayout.EAST, idField);
		getContentPane().add(actvityButton);
		
		actvityButton2 = new JRadioButton("규칙적인 생활");
		springLayout.putConstraint(SpringLayout.NORTH, actvityButton2, 6, SpringLayout.SOUTH, actvityButton);
		springLayout.putConstraint(SpringLayout.EAST, actvityButton2, 0, SpringLayout.EAST, idField);
		getContentPane().add(actvityButton2);
		
		actvityButton3 = new JRadioButton("활동량이 매우 많음");
		springLayout.putConstraint(SpringLayout.NORTH, actvityButton3, 6, SpringLayout.SOUTH, actvityButton2);
		springLayout.putConstraint(SpringLayout.EAST, actvityButton3, 0, SpringLayout.EAST, idField);
		getContentPane().add(actvityButton3);
		
		// 남성 여성 버튼 그룹
		genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(maleButton);
		genderButtonGroup.add(femaleButton);
		
		// 활동 지수 버튼 그룹
		activtyButtonGroup = new ButtonGroup();
		activtyButtonGroup.add(actvityButton);
		activtyButtonGroup.add(actvityButton2);
		activtyButtonGroup.add(actvityButton3);
		
		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registerMember();
			}
		});
		setSize(360, 530);
		setVisible(true);
	}

	private void registerMember() {
		String id = idField.getText();
		String pw = pwField.getText();
		String name = nameField.getText();
		String age = ageField.getText();
		String height = heightField.getText();
		String weight = weightField.getText();
		String goalWeigth = goalField.getText();
		
		try (Connection connection = MySqlConnectionProvider.getConnection()) {
			String sql = "INSERT INTO users (id, pw, name, age, height, weight, Goal_weight, gender, activity_index) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, age);
			preparedStatement.setString(5, height);
			preparedStatement.setString(6, weight);
			preparedStatement.setString(7, goalWeigth);
			
			if (maleButton.isSelected()) {
				preparedStatement.setString(8, "남자");
			} else if (femaleButton.isSelected()) {
				preparedStatement.setString(8, "여자");
			}
			
			if (actvityButton.isSelected()) {
				preparedStatement.setInt(9, 25);
			} else if (actvityButton2.isSelected()) {
				preparedStatement.setInt(9, 33);
			} else if (actvityButton3.isSelected()) {
				preparedStatement.setInt(9, 40);
			}
			
			int insert = preparedStatement.executeUpdate();
			if (insert > 0) {
				JOptionPane.showMessageDialog(this, "회원 등록 완료");
				 new Login();
				 dispose();
			} else {
				JOptionPane.showMessageDialog(this, "회원 등록 실패");
			}
			
			
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void main(String[] args) {
		new SignUp();
		
	}
}
