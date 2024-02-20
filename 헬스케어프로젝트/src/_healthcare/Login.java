package _healthcare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;

public class Login extends JFrame {
	private JTextField txtID;
	private JTextField txtPW;
	private JButton loginButton;
	public String loginId; 

	public Login() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		txtID = new JTextField(); // 아이디 적는곳
		springLayout.putConstraint(SpringLayout.NORTH, txtID, 234, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtID, 77, SpringLayout.WEST, getContentPane());
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtPW = new JTextField(); // 비밀번호 적는곳
		springLayout.putConstraint(SpringLayout.NORTH, txtPW, 24, SpringLayout.SOUTH, txtID);
		springLayout.putConstraint(SpringLayout.EAST, txtPW, 0, SpringLayout.EAST, txtID);
		getContentPane().add(txtPW);
		txtPW.setColumns(10);

		JButton signUpButton = new JButton("회원가입"); // 회원가입 버튼
		springLayout.putConstraint(SpringLayout.SOUTH, signUpButton, -147, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, signUpButton, -47, SpringLayout.EAST, getContentPane());
		getContentPane().add(signUpButton);

		signUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp();
				dispose();
			}
		});

		loginButton = new JButton("로그인");
		springLayout.putConstraint(SpringLayout.NORTH, loginButton, 31, SpringLayout.SOUTH, signUpButton);
		springLayout.putConstraint(SpringLayout.EAST, loginButton, -102, SpringLayout.EAST, getContentPane());
		getContentPane().add(loginButton);

		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = txtID.getText();
				String pw = txtPW.getText();

				if (login(id, pw)) {
					System.out.println("로그인성공");
					loginId = id;
					System.out.println(loginId);
					dispose();
					new Main(loginId);
					new WaterRecords(loginId);
				} else {
					System.out.println("로그인실패");
				}
			}
		});

		setSize(360, 530);
		setVisible(true);
	}

	private boolean login(String id, String pw) {

		try (Connection connection = MySqlConnectionProvider.getConnection()) {
			String query = "SELECT * FROM users WHERE id = ? AND pw = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, id);
				statement.setString(2, pw);

				try (ResultSet resultSet = statement.executeQuery()) {
					return resultSet.next(); // 결과가 있으면 true 반환
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		new Login();
	}
}
