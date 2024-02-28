package _healthcare;

import javax.swing.JFrame;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dbutil.MySqlConnectionProvider;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class EditProfile extends JFrame {
	private String userId;
	private JTextField nameField;
	private JTextField ageField;
	private JTextField heightField;
	private JTextField weightField;
	private String name;
	private int age;
	private double height;
	private double weight;

	private String updateName;
	private int updateAge;
	private double updateHeight;
	private double updateWeight;
	private int updateImage;
	java.util.Date currentDate = new java.util.Date();
	Date sqlDate = new Date(currentDate.getTime());
	private JRadioButton broccoli = new JRadioButton("1. 브로콜리");
	private JRadioButton corn = new JRadioButton("2. 옥수수");
	private JRadioButton tomato = new JRadioButton("3. 토마토");
	private ButtonGroup charButtonGroup;
	private JButton btnNewButton;

	public EditProfile(String loginID) {
		this.userId = loginID;
		extracted();
		// 생성자 내에서 이벤트 리스너 등록
		broccoli.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	selectImage();
		    }
		});
		corn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	selectImage();
		    }
		});
		tomato.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	selectImage();
		    }
		});
		existingInformation();

		nameField.setText(name);
		ageField.setText(String.valueOf(age));
		heightField.setText(String.valueOf(height));
		weightField.setText(String.valueOf(weight));

		showgui();

	}

	private void showgui() {
		setSize(346, 418);
		setVisible(true);
		getContentPane().setLayout(null);
	}

	private void extracted() {
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(EditProfile.class.getResource("/image/수정하기버튼2.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 아직 캐릭터 수정은 update에 추가 안됨..!!!!!!!!
				updateInfomation();

				dispose();
				new Main(userId);
			}
		});
		// 비활성화
		btnNewButton.setEnabled(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("수정없이 뒤로가기");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(userId);
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(EditProfile.class.getResource("/image/뒤로가기.png")));
		btnNewButton_1.setBounds(0, 0, 46, 30);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		getContentPane().add(btnNewButton_1);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니헬스토매토.png")));
		lblNewLabel_8.setBounds(193, 283, 67, 64);
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니요가하는 옥쓔.png")));
		lblNewLabel_7.setBounds(192, 228, 67, 54);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니브로콜리.png")));
		lblNewLabel_6.setBounds(210, 161, 46, 64);
		getContentPane().add(lblNewLabel_6);
		btnNewButton.setBounds(227, 342, 95, 30);
		getContentPane().add(btnNewButton);

		nameField = new JTextField();
		nameField.setBounds(122, 40, 116, 21);
		getContentPane().add(nameField);
		nameField.setColumns(10);

		weightField = new JTextField();
		weightField.setBounds(122, 143, 116, 21);
		getContentPane().add(weightField);
		weightField.setColumns(10);

		ageField = new JTextField();
		ageField.setBounds(122, 73, 116, 21);
		getContentPane().add(ageField);
		ageField.setColumns(10);

		heightField = new JTextField();
		heightField.setBounds(122, 108, 116, 21);
		getContentPane().add(heightField);
		heightField.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("캐릭터");
		lblNewLabel_5.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_5.setBounds(47, 190, 57, 15);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_4 = new JLabel("몸무게");
		lblNewLabel_4.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_4.setBounds(50, 143, 57, 23);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("키");
		lblNewLabel_3.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(71, 109, 57, 15);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("나이");
		lblNewLabel_2.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(60, 75, 57, 18);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(61, 40, 63, 23);
		getContentPane().add(lblNewLabel_1);

		tomato = new JRadioButton("3. 토마토");
		tomato.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		tomato.setBackground(Color.WHITE);
		tomato.setBounds(126, 298, 75, 23);
		getContentPane().add(tomato);

		corn = new JRadioButton("2. 옥수수");
		corn.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		corn.setBackground(Color.WHITE);
		corn.setBounds(126, 240, 75, 23);
		getContentPane().add(corn);

		broccoli = new JRadioButton("1. 브로콜리");
		broccoli.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
		broccoli.setBackground(Color.WHITE);
		broccoli.setBounds(126, 186, 83, 23);
		getContentPane().add(broccoli);

		// 과일 버튼 그룹
		charButtonGroup = new ButtonGroup();
		charButtonGroup.add(broccoli);
		charButtonGroup.add(corn);
		charButtonGroup.add(tomato);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(EditProfile.class.getResource("/image/더큰아이보리.png")));
		lblNewLabel.setBounds(0, 0, 437, 460);
		getContentPane().add(lblNewLabel);
		setTitle("프로필 수정");
	}

	private void existingInformation() { // 유저의 기본 정보들 가져오는 메소드
		String sql = "SELECT * FROM users WHERE id = ?";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql)) {

			pst.setString(1, userId);
			ResultSet rs = pst.executeQuery();

			try {
				while (rs.next()) {
					name = rs.getString("name");
					age = rs.getInt("age");
					height = rs.getDouble("height");
					weight = rs.getDouble("weight");
				}
				System.out.println(name);
			} finally {
				if (rs != null) {
					rs.close();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateInfomation() {
		updateName = nameField.getText();
		updateAge = Integer.parseInt(ageField.getText());
		updateHeight = Double.parseDouble(heightField.getText());
		updateWeight = Double.parseDouble(weightField.getText());

		String updateSql = "UPDATE users SET name = ?, age = ?, height = ?, weight = ?, user_char = ?  WHERE id = ?";
		String updateWeightSql = "INSERT INTO weightrecords (user_id, weight, date) VALUES (?, ?, ?)";

		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(updateSql);
				PreparedStatement pst2 = conn.prepareStatement(updateWeightSql)) {

			pst.setString(1, updateName);
			pst.setInt(2, updateAge);
			pst.setDouble(3, updateHeight);
			pst.setDouble(4, updateWeight);
			pst.setString(6, userId);

			if (broccoli.isSelected()) {
				pst.setInt(5, 1);
			} else if (corn.isSelected()) {
				pst.setInt(5, 2);
			} else if (tomato.isSelected()) {
				pst.setInt(5, 3);
			} else {
				// 라디오 버튼이 선택되지 않은 경우 처리
				System.err.println("캐릭터를 선택하세요.");
				return;
			}

			pst.executeUpdate();

			if (updateWeight != Double.parseDouble(weightField.getText())) {

				pst2.setString(1, userId);
				pst2.setDouble(2, updateWeight);
				pst2.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis())); // Timestamp 사용

				pst2.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void selectImage() {
		/*
		if (!broccoli.isSelected() \ !corn.isSelected() && !tomato.isSelected()) {
			btnNewButton.setEnabled(false);
		} else if (broccoli.isSelected() || corn.isSelected() || tomato.isSelected()) {

			btnNewButton.setEnabled(true);
		}
		*/
		btnNewButton.setEnabled(true);
	}
}