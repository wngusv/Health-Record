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
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

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
		setTitle("회원가입");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(0, 0, 56, 32);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});

		nameField = new JTextField();
		nameField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		nameField.setBounds(136, 319, 116, 21);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		weightField = new JTextField();
		weightField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		weightField.setBounds(136, 245, 116, 21);
		getContentPane().add(weightField);
		weightField.setColumns(10);

		heightField = new JTextField();
		heightField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		heightField.setBounds(136, 208, 116, 21);
		getContentPane().add(heightField);
		heightField.setColumns(10);

		ageField = new JTextField();
		ageField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		ageField.setBounds(136, 169, 116, 21);
		getContentPane().add(ageField);
		ageField.setColumns(10);

		pwField = new JTextField();
		pwField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		pwField.setBounds(136, 130, 116, 21);
		getContentPane().add(pwField);
		pwField.setColumns(10);

		idField = new JTextField();
		idField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		idField.setBounds(136, 92, 116, 21);
		getContentPane().add(idField);
		idField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(SignUp.class.getResource("/image/회원가입사람2.png")));
		lblNewLabel_4.setBounds(134, 18, 260, 252);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(SignUp.class.getResource("/image/회원가입창.png")));
		lblNewLabel_3.setBounds(55, 0, 101, 32);
		getContentPane().add(lblNewLabel_3);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		btnNewButton.setIcon(new ImageIcon(SignUp.class.getResource("/image/뒤로가기.png")));
		getContentPane().add(btnNewButton);

		JLabel idlbl = new JLabel("아이디");
		idlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		idlbl.setBounds(76, 96, 36, 15);
		getContentPane().add(idlbl);

		JLabel pwlbl = new JLabel("비밀번호");
		pwlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		pwlbl.setBounds(75, 133, 48, 15);
		getContentPane().add(pwlbl);

		JLabel agelbl = new JLabel("나이");
		agelbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		agelbl.setBounds(87, 173, 24, 15);
		getContentPane().add(agelbl);

		JLabel heightlbl = new JLabel("키");
		heightlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		heightlbl.setBounds(100, 212, 12, 15);
		getContentPane().add(heightlbl);

		JLabel weightlbl = new JLabel("현재 몸무게");
		weightlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		weightlbl.setBounds(60, 249, 64, 15);
		getContentPane().add(weightlbl);

		JLabel namelbl = new JLabel("이름");
		namelbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		namelbl.setBounds(87, 323, 24, 15);
		getContentPane().add(namelbl);

		JLabel goalWeightlbl = new JLabel("목표 몸무게");
		goalWeightlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		goalWeightlbl.setBounds(64, 286, 64, 15);
		getContentPane().add(goalWeightlbl);

		goalField = new JTextField();
		goalField.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		goalField.setBounds(136, 282, 116, 21);
		getContentPane().add(goalField);
		goalField.setColumns(10);

		JLabel genderlbl = new JLabel("성별");
		genderlbl.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		genderlbl.setBounds(91, 358, 24, 15);
		getContentPane().add(genderlbl);

		JButton registerButton = new JButton("");
		registerButton.setIcon(new ImageIcon(SignUp.class.getResource("/image/회원가입버튼.png")));
		registerButton.setBounds(110, 500, 118, 31);
		registerButton.setContentAreaFilled(false);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		getContentPane().add(registerButton);

		maleButton = new JRadioButton("남자");
		maleButton.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		maleButton.setBounds(139, 354, 49, 23);
		getContentPane().add(maleButton);

		femaleButton = new JRadioButton("여자");
		femaleButton.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		femaleButton.setBounds(203, 354, 49, 23);
		getContentPane().add(femaleButton);

		JLabel lblNewLabel = new JLabel("활동 지수");
		lblNewLabel.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		lblNewLabel.setBounds(60, 431, 52, 15);
		getContentPane().add(lblNewLabel);

		actvityButton = new JRadioButton("거의 움직이지 않음");
		actvityButton.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		actvityButton.setBounds(136, 396, 129, 23);
		getContentPane().add(actvityButton);

		actvityButton2 = new JRadioButton("규칙적인 생활");
		actvityButton2.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		actvityButton2.setBounds(136, 427, 101, 23);
		getContentPane().add(actvityButton2);

		actvityButton3 = new JRadioButton("활동량이 매우 많음");
		actvityButton3.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		actvityButton3.setBounds(136, 459, 129, 23);
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

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(0, 0, 344, 32);
		lblNewLabel_1.setIcon(new ImageIcon(SignUp.class.getResource("/image/큰초록바.png")));
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(SignUp.class.getResource("/image/큰아이보리.png")));
		lblNewLabel_2.setBounds(27, 62, 296, 450);
		getContentPane().add(lblNewLabel_2);

		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				registerMember();
			}
		});
		setSize(360, 581);
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
