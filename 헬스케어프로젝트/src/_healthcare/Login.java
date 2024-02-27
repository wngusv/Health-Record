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
		setTitle("활기록");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
										
										JLabel lblNewLabel_3 = new JLabel("New label");
										lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/image/요가하는 옥쓔.png")));
										lblNewLabel_3.setBounds(33, 256, 129, 102);
										getContentPane().add(lblNewLabel_3);
										
										JLabel lblNewLabel_4 = new JLabel("New label");
										lblNewLabel_4.setIcon(new ImageIcon(Login.class.getResource("/image/헬스토매통.png")));
										lblNewLabel_4.setBounds(0, 0, 129, 133);
										getContentPane().add(lblNewLabel_4);
								
										loginButton = new JButton("");
										loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/로그인.png")));
										loginButton.setOpaque(false);
										loginButton.setContentAreaFilled(false); // 내용 영역을 채우지 않음
										loginButton.setBorderPainted(false); // 테두리 제거
										loginButton.setBounds(62, 170, 215, 46);
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
						
								JButton signUpButton = new JButton("");
								signUpButton.setIcon(new ImageIcon(Login.class.getResource("/image/회원가입.png")));
								signUpButton.setOpaque(false);
								signUpButton.setContentAreaFilled(false); // 내용 영역을 채우지 않음
								signUpButton.setBorderPainted(false); // 테두리 제거
								signUpButton.setBounds(51, 209, 239, 45);
								getContentPane().add(signUpButton);
								
										signUpButton.addActionListener(new ActionListener() {
											@Override
											public void actionPerformed(ActionEvent e) {
												new SignUp();
												dispose();
											}
										});
				
						txtPW = new JPasswordField();
						txtPW.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
						txtPW.setBounds(62, 139, 215, 21);
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
		
				txtID = new JTextField();
				txtID.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
				txtID.setBounds(62, 108, 215, 21);
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
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/image/흰색이미지.png")));
		lblNewLabel_1.setBounds(38, 92, 259, 162);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setIcon(new ImageIcon(Login.class.getResource("/image/엄청큰진한초록.png")));
		lblNewLabel_5.setBounds(32, 83, 272, 181);
		getContentPane().add(lblNewLabel_5);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/image/운동하는브로콜리2.png")));
		lblNewLabel_2.setBounds(196, 256, 89, 132);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lbl_background = new JLabel("");
		lbl_background.setIcon(new ImageIcon(Login.class.getResource("/image/로그인창배경3.png")));
		lbl_background.setBounds(-106, -128, 528, 693);
		getContentPane().add(lbl_background);
		setResizable(false); // 창 크기 고정
		setSize(343, 425);
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