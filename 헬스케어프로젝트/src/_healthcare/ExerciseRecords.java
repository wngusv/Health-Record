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
   private String startTime; // 추가: 운동 시작 시간을 저장할 변수
   private String selectedExercise; // 추가: 선택된 운동을 저장할 변수
   ExerciseCalendar exerciseCalendar = new ExerciseCalendar(loginId);
   
   
   
   public ExerciseRecords(String loginId) {
      this.loginId = loginId;
      System.out.println(loginId);
      setTitle("운동기록");
      setSize(600, 400); // 창의 너비와 높이를 설정합니다.
      setResizable(false); // 창의 크기를 조절할 수 없도록 설정합니다.
      getContentPane().setLayout(null);
      
      // 운동시작버튼
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
            startTime = now.format(formatter); // 운동 시작 시간 저장
            
            // lbl_start에 현재 시간 표시
            lbl_start.setText(startTime);
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
            selectedExercise = comboBox_Sports.getSelectedItem().toString(); // 선택된 운동 저장
            
            // lbl_selected에 선택된 운동 항목 표시
            lbl_selected.setText(selectedExercise);
            
            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            
            // MySQL에 현재 시간과 선택된 운동 항목 추가
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               String sql = "INSERT INTO exericserecords (user_id, date, exercise_name, start_time, end_time) VALUES (?, CURDATE(), ?, ?, ?)";
               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, loginId);
               stmt.setString(2, selectedExercise);
               stmt.setString(3, formattedDateTime);
               stmt.setString(4, formattedDateTime); // 시작 시간과 종료 시간은 동일하게 설정
               stmt.executeUpdate();
               
//               exerciseCalendar.updateCalendarImage(now.getDayOfMonth());
//               System.out.println(now.getDayOfMonth());
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
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
      
      // 운동종료 버튼 클릭 시 이벤트 리스너
      btn_end.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // 현재 시간 가져오기
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTimeE = now.format(formatter);
            
            // lbl_end에 현재 시간 표시
            lbl_end.setText(formattedDateTimeE);
            
            // MySQL에 현재 시간 삽입
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               // 운동 종료 시간을 갱신
               String sql = "UPDATE exericserecords SET end_time = ? WHERE user_id = ? AND start_time = ? AND end_time = ?";
               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, formattedDateTimeE);
               stmt.setString(2, loginId);
               stmt.setString(3, startTime); // 시작 시간 사용
               stmt.setString(4, startTime); // 종료 시간을 시작 시간과 동일하게 설정
               stmt.executeUpdate();
               
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
         }
      });
   }
}