package _healthcare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

public class SignUp extends JFrame {
	
	private JTextField idField;
	private JTextField pwField;
	private JTextField ageField;
	private JTextField heightField;
	private JTextField weightField;
	private JTextField nameField;
	private JRadioButton maleButton;
	private JRadioButton femaleButton;

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
		springLayout.putConstraint(SpringLayout.NORTH, nameField, 23, SpringLayout.SOUTH, weightField);
		springLayout.putConstraint(SpringLayout.WEST, nameField, 0, SpringLayout.WEST, idField);
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

		JLabel weightlbl = new JLabel("몸무게");
		springLayout.putConstraint(SpringLayout.SOUTH, weightlbl, 0, SpringLayout.SOUTH, weightField);
		springLayout.putConstraint(SpringLayout.EAST, weightlbl, -6, SpringLayout.WEST, weightField);
		getContentPane().add(weightlbl);

		JLabel namelbl = new JLabel("이름");
		springLayout.putConstraint(SpringLayout.SOUTH, namelbl, 0, SpringLayout.SOUTH, nameField);
		springLayout.putConstraint(SpringLayout.EAST, namelbl, -6, SpringLayout.WEST, nameField);
		getContentPane().add(namelbl);

		JLabel genderlbl = new JLabel("성별");
		springLayout.putConstraint(SpringLayout.NORTH, genderlbl, 37, SpringLayout.SOUTH, namelbl);
		springLayout.putConstraint(SpringLayout.WEST, genderlbl, 0, SpringLayout.WEST, idlbl);
		getContentPane().add(genderlbl);

		JButton registerButton = new JButton("가입");
		springLayout.putConstraint(SpringLayout.SOUTH, registerButton, -89, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, registerButton, -67, SpringLayout.EAST, getContentPane());
		getContentPane().add(registerButton);
		
		maleButton = new JRadioButton("남자");
		springLayout.putConstraint(SpringLayout.NORTH, maleButton, -4, SpringLayout.NORTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, maleButton, 0, SpringLayout.WEST, idField);
		getContentPane().add(maleButton);
		
		femaleButton = new JRadioButton("여자");
		springLayout.putConstraint(SpringLayout.NORTH, femaleButton, -4, SpringLayout.NORTH, genderlbl);
		springLayout.putConstraint(SpringLayout.WEST, femaleButton, 17, SpringLayout.EAST, maleButton);
		getContentPane().add(femaleButton);

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

		try (Connection connection = MySqlConnectionProvider.getConnection()) {
			String sql = "INSERT INTO member (id, pw, name, age, height, weight, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, pw);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, age);
			preparedStatement.setString(5, height);
			preparedStatement.setString(6, weight);
			if (maleButton.isSelected()) {
				preparedStatement.setString(7, "남자");
			} else if (femaleButton.isSelected()) {
				preparedStatement.setString(7, "여자");
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
