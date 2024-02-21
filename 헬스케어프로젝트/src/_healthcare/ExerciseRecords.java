package _healthcare;

import javax.swing.JFrame;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;

import dbutil.MySqlConnectionProvider;

public class ExerciseRecords extends JFrame {
	private JButton btn_Ok;
	private AbstractButton btn_Start;
	private String loginId;
	
	
	public ExerciseRecords(String loginId) {
		this.loginId = loginId;
		setTitle("운동기록");
		getContentPane().setLayout(null);
		
		JButton btn_start = new JButton("운동시작");
		btn_start.setBounds(78, 209, 97, 23);
		getContentPane().add(btn_start);
		
		JLabel lbl_start = new JLabel("");
		lbl_start.setBounds(48, 261, 163, 15);
		getContentPane().add(lbl_start);
		
		// 운동시작 버튼 클릭 시 이벤트 리스너
        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 시간 가져오기
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                
                // lbl_start에 현재 시간 표시
                lbl_start.setText(formattedDateTime);

                // MySQL에 현재 시간 삽입
                try (Connection conn = MySqlConnectionProvider.getConnection()) {
                    String sql = "INSERT INTO exericserecords (user_id, start_time) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, loginId);
                    stmt.setString(2, formattedDateTime);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        JButton btn_end = new JButton("운동종료");
		btn_end.setBounds(452, 209, 97, 23);
		getContentPane().add(btn_end);
		
		JLabel lbl_end = new JLabel("");
		lbl_end.setBounds(423, 261, 163, 15);
		getContentPane().add(lbl_end);
		

		
		// 운동종료 버튼 클릭 시 이벤트 리스너
        btn_end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 시간 가져오기
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDateTime = now.format(formatter);
                
                // lbl_end에 현재 시간 표시
                lbl_end.setText(formattedDateTime);

                // MySQL에 현재 시간 삽입
                try (Connection conn = MySqlConnectionProvider.getConnection()) {
                    String sql = "UPDATE exericserecords SET end_time = ? WHERE user_id = ? AND end_time IS NULL";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, formattedDateTime);
                    stmt.setString(2, loginId);
                    stmt.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
		
		// 운동목록 comboBox
		JComboBox comboBox_Sports = new JComboBox();
		comboBox_Sports.setBounds(187, 139, 206, 21);
		getContentPane().add(comboBox_Sports);
		
				// MySQL 연결 및 데이터베이스에서 목록 불러오기
				try {
					Connection connection = MySqlConnectionProvider.getConnection();
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery("SELECT sports FROM mets");

					List<String> sportsList = new ArrayList<>();
					while (resultSet.next()) {
						String sportsName = resultSet.getString("sports");
						sportsList.add(sportsName);
					}

					// 콤보 박스에 목록 추가
					for (String sports : sportsList) {
						comboBox_Sports.addItem(sports);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
		
		btn_Ok = new JButton("확인");
		btn_Ok.setBounds(429, 138, 97, 23);
		getContentPane().add(btn_Ok);
		
		JLabel lbl_selected = new JLabel("");
		lbl_selected.setBounds(172, 184, 294, 15);
		getContentPane().add(lbl_selected);
		
		// '확인' 버튼의 ActionListener 추가
		btn_Ok.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // 선택된 운동 항목 가져오기
		        String selectedExercise = comboBox_Sports.getSelectedItem().toString();

		        // lbl_selected에 선택된 운동 항목 표시
		        lbl_selected.setText(selectedExercise);

		        // MySQL에 현재 시간과 선택된 운동 항목 추가
		        try (Connection conn = MySqlConnectionProvider.getConnection()) {
		            String sql = "INSERT INTO exericserecords (user_id, exercise_name) VALUES (?, ?)";
		            PreparedStatement stmt = conn.prepareStatement(sql);
		            stmt.setString(1, loginId);
		            stmt.setString(2, selectedExercise);
		            stmt.executeUpdate();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		});
	    
		
		
		
		
		
	}
}