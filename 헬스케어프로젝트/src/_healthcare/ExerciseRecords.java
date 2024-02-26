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
import javax.swing.JPanel;

import dbutil.MySqlConnectionProvider;

public class ExerciseRecords extends JFrame {
   private JButton btn_Ok;
   private String loginId;
   private String startTime; // 추가: 운동 시작 시간을 저장할 변수
   private String selectedExercise; // 추가: 선택된 운동을 저장할 변수
   private String selectedExercise2;
   private JLabel lbl_selected;
   private JButton btn_start;
   private JComboBox comboBox_Sports;
   private JLabel lbl_start; 
   
   public ExerciseRecords(String loginId) {
	   
      this.loginId = loginId;
      System.out.println(loginId);
      setTitle("운동기록");
      setSize(600, 400); // 창의 너비와 높이를 설정합니다.
      setResizable(false); // 창의 크기를 조절할 수 없도록 설정합니다.
      getContentPane().setLayout(null);
      
      btn_start = new JButton("운동시작");
      btn_start.setBounds(78, 209, 97, 23);
      getContentPane().add(btn_start);

      lbl_start = new JLabel("");
      lbl_start.setBounds(48, 261, 163, 15);
      getContentPane().add(lbl_start);
      
      // 운동시작 버튼 클릭 시 이벤트 리스너
      btn_start.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            startTime = now.format(formatter); // 운동 시작 시간 저장
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               String sql = "UPDATE exerciserecords SET start_time = now() WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
               PreparedStatement stmt = conn.prepareStatement(sql);
//               stmt.setString(1, startTime);
               stmt.setString(1, loginId);

               stmt.executeUpdate();

            } catch (SQLException ex) {
               ex.printStackTrace();
            }
            // lbl_start에 현재 시간 표시
            lbl_start.setText(startTime);
            btn_start.setEnabled(false);
         }

      });

      // 운동목록 comboBox
      comboBox_Sports = new JComboBox();
      comboBox_Sports.addItem("(운동 목록 선택)");
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

      lbl_selected = new JLabel("");
      lbl_selected.setBounds(172, 184, 294, 15);
      getContentPane().add(lbl_selected);

      // '확인' 버튼의 ActionListener 추가
      btn_Ok.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // 선택된 운동 항목 가져오기
            selectedExercise = comboBox_Sports.getSelectedItem().toString(); // 선택된 운동 저장

            // lbl_selected에 선택된 운동 항목 표시
            lbl_selected.setText(selectedExercise);

            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDateTime = now.format(formatter);

            // MySQL에 현재 시간과 선택된 운동 항목 추가
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               String sql = "INSERT INTO exerciserecords (user_id, date, exercise_name) VALUES (?, CURDATE(), ?)";
               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, loginId);
               stmt.setString(2, selectedExercise);
               stmt.executeUpdate();

            } catch (SQLException ex) {
               ex.printStackTrace();
            }
            btn_Ok.setEnabled(false);
         }
      });

      // 운동종료 버튼
      JButton btn_end = new JButton("운동종료");
      btn_end.setBounds(452, 209, 97, 23);
      getContentPane().add(btn_end);

      JLabel lbl_end = new JLabel("");
      lbl_end.setBounds(423, 261, 163, 15);
      getContentPane().add(lbl_end);

      JButton btnNewButton = new JButton("뒤로가기");
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            dispose();
            new Main(loginId);
         }
      });
      btnNewButton.setBounds(296, 228, 97, 23);
      getContentPane().add(btnNewButton);
      ExerciseCalendar exerciseCalendar = new ExerciseCalendar(loginId);
      exerciseCalendar.changeImageOfToday();
      
      // 운동종료 버튼 클릭 시 이벤트 리스너
      btn_end.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              // 현재 시간 가져오기
              LocalDateTime now = LocalDateTime.now();
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
              String formattedDateTimeE = now.format(formatter);
              exerciseCalendar.changeImageOfToday(); // ExerciseCalendar 객체의 메서드 호출
            // lbl_end에 현재 시간 표시
            lbl_end.setText(formattedDateTimeE);

            // MySQL에 현재 시간 삽입
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               // 운동 종료 시간을 갱신
               String sql = "UPDATE exerciserecords SET end_time = now() WHERE start_time = ? AND user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
               PreparedStatement stmt = conn.prepareStatement(sql);
//               stmt.setString(1, formattedDateTimeE);
               stmt.setString(1, startTime);
               stmt.setString(2, loginId);
               stmt.executeUpdate();
               
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         }
      });
      loadExerciseName();
      loadStartTime();
   }

   private void loadStartTime() {
         try (Connection conn = MySqlConnectionProvider.getConnection()) {
         String sql = "SELECT start_time FROM exerciserecords WHERE user_id = ? AND date = CURDATE() AND exercise_name = ? ORDER BY record_id DESC LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, loginId);
            pst.setString(2, selectedExercise2);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               startTime = rs.getString("start_time");
            }
            lbl_start.setText(startTime);

         } catch (SQLException e) {
            e.printStackTrace();
         }
   }
   
   private void loadExerciseName() {
         try (Connection conn = MySqlConnectionProvider.getConnection()) {
         String sql = "SELECT exercise_name FROM exerciserecords WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, loginId);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
               selectedExercise2 = rs.getString("exercise_name");
            }
            lbl_selected.setText(selectedExercise2);

         } catch (SQLException e) {
            e.printStackTrace();
         }
   }
}