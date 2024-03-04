package _healthcare;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import dbutil.MySqlConnectionProvider;

public class UserManagement extends JFrame {
	public UserManagement() {
		getContentPane().setBackground(Color.WHITE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JButton SecessionButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, SecessionButton, 90, SpringLayout.NORTH, getContentPane());
		SecessionButton.setContentAreaFilled(false);
		SecessionButton.setBorderPainted(false);
		SecessionButton.setFocusPainted(false);
		SecessionButton.setIcon(new ImageIcon(UserManagement.class.getResource("/image/회원탈퇴.png")));
		springLayout.putConstraint(SpringLayout.EAST, SecessionButton, -62, SpringLayout.EAST, getContentPane());
		SecessionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SecessionDialog(UserManagement.this).setVisible(true);
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, SecessionButton, 61, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, SecessionButton, -41, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(SecessionButton);

		JButton PwButton = new JButton("");
		springLayout.putConstraint(SpringLayout.NORTH, PwButton, 44, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, PwButton, 61, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, PwButton, -6, SpringLayout.NORTH, SecessionButton);
		springLayout.putConstraint(SpringLayout.EAST, PwButton, 0, SpringLayout.EAST, SecessionButton);
		PwButton.setContentAreaFilled(false);
		PwButton.setBorderPainted(false);
		PwButton.setFocusPainted(false);
		PwButton.setIcon(new ImageIcon(UserManagement.class.getResource("/image/비밀번호찾기.png")));
		PwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PwDialog(UserManagement.this).setVisible(true);
			}
		});
		getContentPane().add(PwButton);

		setResizable(false);
		setSize(250, 200);
		setLocationRelativeTo(null); // 화면 중앙에 위치
		setVisible(true);
	}

	class SecessionDialog extends JDialog {
		private JTextField idTextField;
		private JPasswordField passwordField;
		private JTextField nameTextField;

		public SecessionDialog(JFrame frame) {
			super(frame, "", true);
			setSize(300, 150);
			setLocationRelativeTo(frame);

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 2));

			JLabel idLabel = new JLabel("아이디:");
			idLabel.setFont(new Font("HY엽서M", Font.PLAIN, 15));
			idTextField = new JTextField();
			panel.add(idLabel);
			panel.add(idTextField);

			JLabel passwordLabel = new JLabel("비밀번호:");
			passwordLabel.setFont(new Font("HY엽서M", Font.PLAIN, 15));
			passwordField = new JPasswordField();
			panel.add(passwordLabel);
			panel.add(passwordField);

			JLabel nameLabel = new JLabel("이름:");
			nameLabel.setFont(new Font("HY엽서M", Font.PLAIN, 15));
			nameTextField = new JTextField();
			panel.add(nameLabel);
			panel.add(nameTextField);

			JButton closeButton = new JButton();
			closeButton.setIcon(new ImageIcon(getClass().getResource("/image/완료.png")));
			closeButton.setContentAreaFilled(false);
			closeButton.setBorderPainted(false);
			closeButton.setFocusPainted(false);
			panel.add(closeButton);

			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = idTextField.getText();
					String password = new String(passwordField.getPassword());
					String name = nameTextField.getText();

					// 아이디, 비밀번호, 이름이 일치하는 사용자 정보 삭제
					boolean deleted = deleteUser(id, password, name);
					if (deleted) {
						UIManager.put("OptionPane.messageFont", new Font("HY엽서M", Font.PLAIN, 15));
						UIManager.put("OptionPane.background", Color.WHITE);
						UIManager.put("Panel.background", Color.WHITE);
						UIManager.put("Button.border", BorderFactory.createEmptyBorder()); // 테두리 제거
						UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0))); // 포커스 표시 제거
						UIManager.put("OptionPane.okButtonText", ""); // 확인 버튼 텍스트를 없앰
						UIManager.put("OptionPane.okIcon", new ImageIcon(getClass().getResource("/image/완료.png")));
						JOptionPane.showMessageDialog(SecessionDialog.this, "사용자 정보가 성공적으로 삭제되었습니다.", "알림",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						UIManager.put("OptionPane.messageFont", new Font("HY엽서M", Font.PLAIN, 15));
						UIManager.put("OptionPane.background", Color.WHITE);
						UIManager.put("Panel.background", Color.WHITE);
						UIManager.put("Button.border", BorderFactory.createEmptyBorder()); // 테두리 제거
						UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0))); // 포커스 표시 제거
						UIManager.put("OptionPane.okButtonText", ""); // 확인 버튼 텍스트를 없앰
						UIManager.put("OptionPane.okIcon", new ImageIcon(getClass().getResource("/image/재입력.png")));
						JOptionPane.showMessageDialog(SecessionDialog.this, "일치하는 사용자 정보가 없습니다.", "알림",
								JOptionPane.WARNING_MESSAGE);
					}

				}
			});

			panel.setBackground(Color.WHITE);
			add(panel);
		}

	}

	class PwDialog extends JDialog {
		private JTextField idTextField;
		private JTextField nameTextField;

		public PwDialog(JFrame frame) {
			super(frame, "", true);
			setSize(200, 150);
			setLocationRelativeTo(frame);

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(3, 1));

			JLabel idLabel = new JLabel("아이디:");
			idLabel.setFont(new Font("HY엽서M", Font.PLAIN, 15));
			idTextField = new JTextField();
			panel.add(idLabel);
			panel.add(idTextField);

			JLabel nameLabel = new JLabel("이름:");
			nameLabel.setFont(new Font("HY엽서M", Font.PLAIN, 15));
			nameTextField = new JTextField();
			panel.add(nameLabel);
			panel.add(nameTextField);

			JButton closeButton = new JButton();
			closeButton.setIcon(new ImageIcon(DietRecord.class.getResource("/image/완료.png")));
			closeButton.setContentAreaFilled(false);
			closeButton.setBorderPainted(false);
			closeButton.setFocusPainted(false);
			panel.add(closeButton);

			closeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String id = idTextField.getText();
					String name = nameTextField.getText();
					// 데이터베이스에서 사용자 정보 조회하는 메서드 호출
					String password = checkUserValidity(id, name);
					if (password != null) {
						UIManager.put("OptionPane.messageFont", new Font("HY엽서M", Font.PLAIN, 15));
						UIManager.put("OptionPane.background", Color.WHITE);
						UIManager.put("Panel.background", Color.WHITE);
						UIManager.put("Button.border", BorderFactory.createEmptyBorder()); // 테두리 제거
						UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0))); // 포커스 표시 제거
						UIManager.put("OptionPane.okButtonText", ""); // 확인 버튼 텍스트를 없앰
						UIManager.put("OptionPane.okIcon", new ImageIcon(getClass().getResource("/image/완료.png")));

						JOptionPane.showMessageDialog(PwDialog.this, "아이디 " + id + "의 비밀번호는" + " " + password + " 입니다.",
								"알림", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					} else {
						// 사용자가 유효하지 않은 경우 메시지 표시 등의 처리
						JOptionPane.showMessageDialog(PwDialog.this, "일치하는 사용자 정보가 없습니다.", "알림",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			});

			panel.setBackground(Color.WHITE);
			add(panel);
		}
	}

	private String checkUserValidity(String id, String name) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// JDBC를 사용하여 MySQL 데이터베이스에 연결
			conn = MySqlConnectionProvider.getConnection();

			// 사용자 테이블에서 아이디와 이름으로 비밀번호 조회하는 쿼리 작성
			String query = "SELECT pw FROM users WHERE id = ? AND name = ?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, name);

			// 쿼리 실행하여 결과셋 얻기
			resultSet = preparedStatement.executeQuery();

			// 결과셋에 데이터가 있는지 확인하여 유효한 사용자인지 여부 판단
			if (resultSet.next()) {
				return resultSet.getString("pw"); // 일치하는 사용자가 있는 경우 true 반환
			} else {
				return null; // 일치하는 사용자가 없는 경우 false 반환
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null; // 예외 발생 시 false 반환
		} finally {
			// 연결, statement, resultSet 등의 자원을 해제
			try {
				if (resultSet != null)
					resultSet.close();
				if (preparedStatement != null)
					preparedStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean deleteUser(String id, String password, String name) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			// JDBC를 사용하여 MySQL 데이터베이스에 연결
			conn = MySqlConnectionProvider.getConnection();

			// 사용자 테이블에서 아이디, 비밀번호, 이름이 일치하는 행 삭제하는 쿼리 작성
			String query = "DELETE FROM users WHERE id = ? AND pw = ? AND name = ?";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);

			// 쿼리 실행하여 행 삭제
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("사용자 정보가 성공적으로 삭제되었습니다.");
				return true;
			} else {
				System.out.println("삭제할 사용자 정보가 없습니다.");
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		} finally {
			// 연결과 statement 등의 자원을 해제
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

}
