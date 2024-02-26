package _healthcare;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;

import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import java.awt.Color;

public class WaterRecords extends JFrame {
	int cup = 0;
	int num = 0;
	private JLabel nowWaterDrinking;
	// 현재 날짜
	java.util.Date currentDate = new java.util.Date();
	// sql에 넣기 위해 날짜를 date형식으로 변경
	Date sqlDate = new Date(currentDate.getTime());

	private String user_id;
	private JButton btn_cup1;
	private JButton btn_cup2;
	private JButton btn_cup3;
	private JButton btn_cup4;
	private JButton btn_cup5;
	private JButton btn_cup6;
	private JButton btn_cup7;
	private JButton btn_cup8;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_5;
	private int todayDrinkWater;

	public WaterRecords(String loginId) {
		// 물 기록창에 들어왔을 때 유저 아이디가 그 날에 마신 물을 양만큼 버튼 이미지 바꿔서 나오게 하기!!!!
		getContentPane().setBackground(Color.WHITE); // 오늘 데이트와 아이디로 select해보고/ 행이 없다면 행 insert./ 행이 있다면 행 update.
		this.user_id = loginId;
		extracted();
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			String selectQuery = "SELECT * FROM waterrecords WHERE user_id = ? AND date = ?";
			try (PreparedStatement pst = conn.prepareStatement(selectQuery)) {
				pst.setString(1, user_id);
				pst.setDate(2, sqlDate);

				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) { // 데이터가 있다면 버튼이 눌러저있게 하기
						 num = rs.getInt("water");
						 JButton[] waterButtons = new JButton[] { btn_cup1, btn_cup2, btn_cup3, btn_cup4, btn_cup5, btn_cup6, btn_cup7, btn_cup8 };
						    for (int i = 0; i < num; i++) {
						        waterButtons[i].doClick();
						    }
					} else { // 그 데이터가 없는 경우. INSERT사용
						String insertQuery = "INSERT INTO waterrecords (user_id, date, water) VALUES (?,?,?)";
						try (PreparedStatement insertPst = conn.prepareStatement(insertQuery)) {
							insertPst.setString(1, user_id);
							insertPst.setDate(2, sqlDate);
							insertPst.setInt(3, cup);

							// INSERT 실행
							int insertRow = insertPst.executeUpdate();
							if (insertRow == 1) {
								System.out.println("INSERT 성공");
							}
						}

					}
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		showGUI();

	}

	private void showGUI() {
		setSize(403, 416);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void updateCupCount() {
		nowWaterDrinking.setText(String.valueOf(cup));
		updateDatabase();
	}

	// 디비를 업데이트하는 메소드
	private void updateDatabase() {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {

			String updateQuery = "UPDATE waterrecords SET water = ? WHERE user_id = ? AND date = ?";
			try (PreparedStatement updatePst = conn.prepareStatement(updateQuery)) {
				updatePst.setInt(1, cup);
				updatePst.setString(2, user_id);
				updatePst.setDate(3, sqlDate);

				// UPDATE 실행
				int updateRow = updatePst.executeUpdate();
				if (updateRow == 1) {
					System.out.println("UPDATE 성공");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("SQL 오류: " + e.getMessage());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private void btnClick(JButton btn) {
		btn.addActionListener(new ActionListener() {
			private boolean clicked = false;

			public void actionPerformed(ActionEvent e) {
				if (clicked) {
					btn.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
					cup--;
				} else {
					btn.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마신후 2.png")));
					cup++;
				}
				updateCupCount();
				clicked = !clicked; // 클릭 상태를 토글
			}
		});

	}

	private void extracted() {
		setTitle("물 기록");
		getContentPane().setLayout(null);

		nowWaterDrinking = new JLabel("0");
		nowWaterDrinking.setFont(new Font("휴먼편지체", Font.BOLD, 13));
		nowWaterDrinking.setBounds(157, 300, 40, 15);
		getContentPane().add(nowWaterDrinking);

		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물 기록3.png")));
		lblNewLabel_2.setBounds(61, 0, 102, 36);
		getContentPane().add(lblNewLabel_2);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Main(user_id);
			}
		});
		btnNewButton.setToolTipText("뒤로가기");
		btnNewButton.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/뒤로가기.png")));
		btnNewButton.setBounds(0, 0, 56, 36);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		getContentPane().add(btnNewButton);

		btn_cup2 = new JButton("");
		btn_cup2.setToolTipText("");
		btn_cup2.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup2.setFocusPainted(false);
		btn_cup2.setContentAreaFilled(false);
		btn_cup2.setBorderPainted(false);
		btn_cup2.setBounds(132, 93, 52, 65);
		btnClick(btn_cup2);
		getContentPane().add(btn_cup2);

		JLabel lblNewLabel_4 = new JLabel("<html> 세계보건기구(WHO)가 권장하는 하루 물 섭취량은 1.5~2ℓ입니다. <br/r>"
				+ "200mℓ가 들어가는 일반적인 컵으로 약 8~10잔 정도입니다.<br/>" + "</html>");
		lblNewLabel_4.setBounds(121, 344, 306, 26);
		lblNewLabel_4.setFont(new Font("휴먼편지체", Font.PLAIN, 10));
		getContentPane().add(lblNewLabel_4);

		btn_cup1 = new JButton("");
		btn_cup1.setToolTipText("");
		btn_cup1.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup1.setBounds(64, 93, 52, 65);
		btn_cup1.setContentAreaFilled(false);
		btn_cup1.setBorderPainted(false);
		btn_cup1.setFocusPainted(false);
		btnClick(btn_cup1);
		getContentPane().add(btn_cup1);

		btn_cup3 = new JButton("");
		btn_cup3.setToolTipText("");
		btn_cup3.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup3.setFocusPainted(false);
		btn_cup3.setContentAreaFilled(false);
		btn_cup3.setBorderPainted(false);
		btn_cup3.setBounds(196, 93, 52, 65);
		btnClick(btn_cup3);
		getContentPane().add(btn_cup3);

		btn_cup4 = new JButton("");
		btn_cup4.setToolTipText("");
		btn_cup4.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup4.setFocusPainted(false);
		btn_cup4.setContentAreaFilled(false);
		btn_cup4.setBorderPainted(false);
		btn_cup4.setBounds(260, 93, 52, 65);
		btnClick(btn_cup4);
		getContentPane().add(btn_cup4);

		btn_cup5 = new JButton("");
		btn_cup5.setToolTipText("");
		btn_cup5.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup5.setFocusPainted(false);
		btn_cup5.setContentAreaFilled(false);
		btn_cup5.setBorderPainted(false);
		btn_cup5.setBounds(64, 168, 52, 65);
		btnClick(btn_cup5);
		getContentPane().add(btn_cup5);

		btn_cup6 = new JButton("");
		btn_cup6.setToolTipText("");
		btn_cup6.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup6.setFocusPainted(false);
		btn_cup6.setContentAreaFilled(false);
		btn_cup6.setBorderPainted(false);
		btn_cup6.setBounds(132, 168, 52, 65);
		btnClick(btn_cup6);
		getContentPane().add(btn_cup6);

		btn_cup7 = new JButton("");
		btn_cup7.setToolTipText("");
		btn_cup7.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup7.setFocusPainted(false);
		btn_cup7.setContentAreaFilled(false);
		btn_cup7.setBorderPainted(false);
		btn_cup7.setBounds(196, 168, 52, 65);
		btnClick(btn_cup7);
		getContentPane().add(btn_cup7);

		btn_cup8 = new JButton("");
		btn_cup8.setToolTipText("");
		btn_cup8.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
		btn_cup8.setFocusPainted(false);
		btn_cup8.setContentAreaFilled(false);
		btn_cup8.setBorderPainted(false);
		btn_cup8.setBounds(260, 168, 52, 65);
		btnClick(btn_cup8);
		getContentPane().add(btn_cup8);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/큰초록바.png")));
		lblNewLabel.setBounds(0, 0, 461, 36);
		getContentPane().add(lblNewLabel);

		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물 음용량 표시4.png")));
		lblNewLabel_5.setBounds(36, 241, 319, 107);
		getContentPane().add(lblNewLabel_5);
	}
	
	

}