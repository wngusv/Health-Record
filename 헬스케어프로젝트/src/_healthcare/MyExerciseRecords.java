package _healthcare;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dbutil.MySqlConnectionProvider;
// 나의운동기록창

public class MyExerciseRecords extends JFrame {
    private JTable exerciseTable;
    private DefaultTableModel exerciseTableModel;
    private String user_id;
    
   public MyExerciseRecords(String loginId) {
      this.user_id = loginId;
              getContentPane().setBackground(Color.WHITE);
              
              initComponents();
              showGUI();
              fetchExerciseRecords();
          }

          private void initComponents() {
              setTitle("나의 운동 기록");
              setResizable(false);
              setSize(768, 400);
              setDefaultCloseOperation(EXIT_ON_CLOSE);
              setLocationRelativeTo(null);
              getContentPane().setLayout(null);
              
              JButton btnBack = new JButton("");
              btnBack.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent e) {
                 }
              });
              btnBack.setIcon(new ImageIcon(MyExerciseRecords.class.getResource("/image/뒤로가기.png")));
              btnBack.setContentAreaFilled(false);
              btnBack.setBorderPainted(false);
              btnBack.setFocusPainted(false);
              btnBack.setBounds(0, 7, 56, 23);
              getContentPane().add(btnBack);
              btnBack.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent arg0) {                  
                  
                  ExerciseRecords exerciseRecords = new ExerciseRecords(user_id);
                  exerciseRecords.setVisible(true);
                  dispose();
               }
            });

              JLabel titleLabel = new JLabel("나의 운동 기록");
              titleLabel.setForeground(Color.WHITE);
              titleLabel.setFont(new Font("휴먼편지체", Font.BOLD, 20));
              titleLabel.setBounds(63, 7, 120, 25);
              getContentPane().add(titleLabel);

              exerciseTableModel = new DefaultTableModel();
              exerciseTableModel.addColumn("날짜");
              exerciseTableModel.addColumn("운동명");
              exerciseTableModel.addColumn("운동 시작 시간");
              exerciseTableModel.addColumn("운동 종료 시간");
              exerciseTableModel.addColumn("운동 시간");
              exerciseTableModel.addColumn("소모 칼로리");

              exerciseTable = new JTable(exerciseTableModel);
              exerciseTable.setFont(new Font("휴먼편지체", Font.PLAIN, 12));
              exerciseTable.setSelectionBackground(new Color(240, 240, 240));
              exerciseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                  @Override
                  public void valueChanged(ListSelectionEvent e) {
                      // Handle selection event if needed
                  }
              });

              JScrollPane scrollPane = new JScrollPane(exerciseTable);
              scrollPane.setBounds(30, 50, 698, 300);
              getContentPane().add(scrollPane);
              
              JLabel lblNewLabel = new JLabel("");
              lblNewLabel.setIcon(new ImageIcon(MyExerciseRecords.class.getResource("/image/큰초록바.png")));
              lblNewLabel.setBounds(0, 0, 762, 35);
              getContentPane().add(lblNewLabel);
          }

          private void showGUI() {
              setVisible(true);
          }

          private void fetchExerciseRecords() {
              String sqlSelectRecords = "SELECT * FROM exerciserecords WHERE user_id = ?";
              try (Connection conn = MySqlConnectionProvider.getConnection();
                   PreparedStatement pst = conn.prepareStatement(sqlSelectRecords)) {
                  pst.setString(1, user_id);
                  try (ResultSet rs = pst.executeQuery()) {
                      while (rs.next()) {
                         String date = rs.getString("date");
                          String exercise_name = rs.getString("exercise_name");
                          String start_time = rs.getString("start_time");
                          String end_time = rs.getString("end_time");
                          String time_diff = rs.getString("TIME_DIFF");
                          double hours_exercise = rs.getDouble("hours_exercise");
                          double kcal_exercise = rs.getDouble("kcal_exercise");

                          exerciseTableModel.addRow(new Object[]{date, exercise_name, start_time, end_time, time_diff, kcal_exercise});
                      }
                  }
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
}

