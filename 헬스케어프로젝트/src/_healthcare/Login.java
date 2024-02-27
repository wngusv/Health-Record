package _healthcare;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dbutil.MySqlConnectionProvider;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Font;

public class Login extends JFrame {
	private JTextField txtID;
	private JPasswordField txtPW;
	private JButton loginButton;
	public String loginId;
	private JLabel lblNewLabel;

	public Login() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		txtID = new JTextField();
		txtID.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		txtID.setBounds(65, 281, 215, 21);
		txtID.setText("아이디를 입력하세요");
		getContentPane().add(txtID);
		txtID.setColumns(10);

		// 텍스트 필드에 포커스를 얻었을 때 힌트가 사라지도록 함
		txtID.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtID.getText().equals("아이디를 입력하세요")) {
					txtID.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtID.getText().isEmpty()) {
					txtID.setText("아이디를 입력하세요");
				}
			}
		});

		txtPW = new JPasswordField();
		txtPW.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		txtPW.setBounds(65, 312, 215, 21);
		txtPW.setEchoChar((char) 0);
		txtPW.setText("비밀 번호를 입력하세요");
		getContentPane().add(txtPW);
		txtPW.setColumns(10);

		// 텍스트 필드에 포커스를 얻었을 때 힌트가 사라지도록 함
		txtPW.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtPW.getText().equals("비밀 번호를 입력하세요")) {
					txtPW.setText("");
					txtPW.setEchoChar('*'); // 비밀번호를 입력하면 '*'로 표시
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtPW.getText().isEmpty()) {
					txtPW.setEchoChar((char) 0);
					txtPW.setText("비밀 번호를 입력하세요");
				}
			}
		});

		JButton signUpButton = new JButton("");
		signUpButton.setIcon(new ImageIcon(Login.class.getResource("/image/회원가입.png")));
		signUpButton.setOpaque(false);
		signUpButton.setContentAreaFilled(false); // 내용 영역을 채우지 않음
		signUpButton.setBorderPainted(false); // 테두리 제거
		signUpButton.setBounds(54, 394, 239, 45);
		getContentPane().add(signUpButton);

		signUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp();
				dispose();
			}
		});

		loginButton = new JButton("");
		loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/로그인.png")));
		loginButton.setOpaque(false);
		loginButton.setContentAreaFilled(false); // 내용 영역을 채우지 않음
		loginButton.setBorderPainted(false); // 테두리 제거
		loginButton.setBounds(65, 350, 215, 46);
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
				} else {
					System.out.println("로그인실패");
				}
			}
		});

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/로그인선택.png")));
			}
		});
		setResizable(false); // 창 크기 고정
		setSize(360, 530);
		setLocationRelativeTo(null); // 화면 중앙에 위치
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