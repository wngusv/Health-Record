package _healthcare;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dbutil.MySqlConnectionProvider;
import java.awt.Toolkit;

public class ExerciseRecords extends JFrame {
   private JButton btn_Ok;
   private String loginId;
   private String startTime; // 추가: 운동 시작 시간을 저장할 변수
   private String selectedExercise; // 추가: 선택된 운동을 저장할 변수
   private JLabel lbl_selected;
   private JButton btn_start;
   private JComboBox comboBox_Sports;
   private JLabel lbl_start;
   private JLabel lblTimeDiff;
   private String timediff;
   private JLabel lbl_image;
private JButton btn_end;

   public ExerciseRecords(String loginId) {
   	setIconImage(Toolkit.getDefaultToolkit().getImage(ExerciseRecords.class.getResource("/image/_ICON.png")));
      this.loginId = loginId;
      getContentPane().setBackground(Color.WHITE);
      System.out.println(loginId);
      setTitle("운동기록");
      setSize(422, 655); // 창의 너비와 높이를 설정합니다.
      setResizable(false); // 창의 크기를 조절할 수 없도록 설정합니다.
      getContentPane().setLayout(null);
                  // 나의 운동기록
                  JButton btnMyExerciseRecords = new JButton("");
                  btnMyExerciseRecords.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/나의운동기록.png")));
                  btnMyExerciseRecords.setContentAreaFilled(false);
                  btnMyExerciseRecords.setBorderPainted(false);
                  btnMyExerciseRecords.setFocusPainted(false);
                  btnMyExerciseRecords.setBounds(239, 5, 174, 28);
                  getContentPane().add(btnMyExerciseRecords);
                  
                  btnMyExerciseRecords.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                         // 운동기록 창 열기
                         MyExerciseRecords myExerciseRecords = new MyExerciseRecords(loginId);
                         myExerciseRecords.setVisible(true);
                         dispose(); // 현재 창 닫기
                      }
                   });
                  
                  JLabel lblNewLabel_4 = new JLabel("");
                  lblNewLabel_4.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
                  lblNewLabel_4.setBounds(208, 564, 96, 28);
                  getContentPane().add(lblNewLabel_4);
            
                  JLabel lblNewLabel_3 = new JLabel("소모 칼로리: ");
                  lblNewLabel_3.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
                  lblNewLabel_3.setBounds(100, 561, 104, 41);
                  getContentPane().add(lblNewLabel_3);
            
            JLabel lblNewLabel_6 = new JLabel("선택한 운동: ");
            lblNewLabel_6.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
            lblNewLabel_6.setBounds(49, 100, 95, 19);
            getContentPane().add(lblNewLabel_6);
      
            lbl_selected = new JLabel("");
            lbl_selected.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
            lbl_selected.setBounds(125, 98, 185, 22);
            getContentPane().add(lbl_selected);
      
      JLabel lblNewLabel_5 = new JLabel("");
      lblNewLabel_5.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      lblNewLabel_5.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/아이보리색2.png")));
      lblNewLabel_5.setBounds(39, 94, 337, 29);
      getContentPane().add(lblNewLabel_5);

      lblTimeDiff = new JLabel("");
      lblTimeDiff.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblTimeDiff.setBounds(197, 505, 95, 21);
      getContentPane().add(lblTimeDiff);

      JLabel lblNewLabel_1 = new JLabel("운동 시간 :");
      lblNewLabel_1.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      lblNewLabel_1.setBounds(101, 505, 84, 24);
      getContentPane().add(lblNewLabel_1);

      JLabel lbl_end = new JLabel("운동 종료 시간");
      lbl_end.setIcon(null);
      lbl_end.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
      lbl_end.setBounds(255, 423, 91, 58);
      getContentPane().add(lbl_end);

      lbl_start = new JLabel("운동 시작 시간");
      lbl_start.setIcon(null);
      lbl_start.setFont(new Font("휴먼편지체", Font.PLAIN, 16));
      lbl_start.setBounds(77, 426, 91, 48);
      getContentPane().add(lbl_start);

      JLabel lblTitle = new JLabel("운동 기록");
      lblTitle.setForeground(Color.WHITE);
      lblTitle.setFont(new Font("휴먼편지체", Font.BOLD, 23));
      lblTitle.setBounds(47, -6, 125, 55);
      getContentPane().add(lblTitle);

      btn_start = new JButton("");
      btn_start.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/start.png")));
      btn_start.setOpaque(false); // 배경 투명하게 설정
      btn_start.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
      btn_start.setBorderPainted(false); // 테두리 제거
      btn_start.setBounds(39, 302, 152, 120);
      btn_start.setEnabled(false);
      getContentPane().add(btn_start);
      // 운동시작 버튼 클릭 시 이벤트 리스너
      btn_start.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            // 현재 시간 가져오기
            try (Connection conn = MySqlConnectionProvider.getConnection()) {
               String sql = "UPDATE exerciserecords SET start_time = NOW() WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
               PreparedStatement stmt = conn.prepareStatement(sql);
               stmt.setString(1, loginId);

               stmt.executeUpdate();

            } catch (SQLException ex) {
               ex.printStackTrace();
            }
            // lbl_start에 현재 시간 표시
            loadStartTime();
            lbl_start.setText(startTime);
            // 버튼 이미지 변경
            // ImageIcon newIcon = new
            // ImageIcon(getClass().getResource("/image/actionstart.png"));
            // System.out.println("New icon: " + newIcon); // 콘솔에 이미지 아이콘 정보 출력
            // btn_start.setIcon(newIcon);
            btn_start.setEnabled(false); // 버튼을 클릭한 후에 비활성화
            btn_Ok.setEnabled(false);
            btn_end.setEnabled(true);
            
         }
      });

      // 운동목록 comboBox
      comboBox_Sports = new JComboBox();
      comboBox_Sports.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      comboBox_Sports.setBackground(new Color(255, 255, 240));
      comboBox_Sports.addItem("(운동 목록 선택)");
      comboBox_Sports.setBounds(29, 55, 264, 29);
      getContentPane().add(comboBox_Sports);

      // MySQL 연결 및 데이터베이스에서 목록 불러오기
      try (Connection connection = MySqlConnectionProvider.getConnection();
            Statement statement = connection.createStatement();) {

         List<String> sportsList = new ArrayList<>();
         try (ResultSet resultSet = statement.executeQuery("SELECT sports FROM mets");) {

            while (resultSet.next()) {
               String sportsName = resultSet.getString("sports");
               sportsList.add(sportsName);
            }
         }

         // 콤보 박스에 목록 추가
         for (String sports : sportsList) {
            comboBox_Sports.addItem(sports);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      comboBox_Sports.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String selectedExercise = (String) comboBox_Sports.getSelectedItem();
              loadExerciseImage(selectedExercise);
          }
      });


      
      

      btn_Ok = new JButton("");
      btn_Ok.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/ok.png")));
      btn_Ok.setBounds(307, 51, 91, 36);
      btn_Ok.setOpaque(false); // 배경 투명하게 설정
      btn_Ok.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
      btn_Ok.setBorderPainted(false); // 테두리 제거
      getContentPane().add(btn_Ok);

      // '확인' 버튼의 ActionListener 추가
        btn_Ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btn_start.setEnabled(true);
                
                String selectedSport = (String) comboBox_Sports.getSelectedItem();
                if ("(운동 목록 선택)".equals(selectedSport)) {
                    openSportSelectionDialog();
                    btn_Ok.setEnabled(true);
                    return;
                }

                // 선택된 운동 항목 가져오기
                selectedExercise = comboBox_Sports.getSelectedItem().toString(); // 선택된 운동 저장

                // lbl_selected에 선택된 운동 항목 표시
                lbl_selected.setText(selectedExercise);

                // 운동에 따른 이미지 로드 및 표시
                loadExerciseImage(selectedExercise);

                // 현재 시간 가져오기
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                String sql = "INSERT INTO exerciserecords (user_id, date, exercise_name) VALUES (?, CURDATE(), ?)";
                try (Connection conn = MySqlConnectionProvider.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);) {
                    stmt.setString(1, loginId);
                    stmt.setString(2, selectedExercise);
                    stmt.executeUpdate();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        

      btn_end = new JButton("");
      btn_end.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/finish.png")));
      btn_end.setBounds(224, 302, 152, 120);
      btn_end.setOpaque(false); // 배경 투명하게 설정
      btn_end.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
      btn_end.setBorderPainted(false); // 테두리 제거
      btn_end.setEnabled(false);
      getContentPane().add(btn_end);

      JButton btnBack = new JButton("");
      btnBack.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/뒤로가기.png")));
      btnBack.setFocusPainted(false); // 배경 투명하게 설정
      btnBack.setContentAreaFilled(false); // 콘텐츠 영역도 투명하게 설정
      btnBack.setBorderPainted(false); // 테두리 제거
      btnBack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            dispose();
            new Main(loginId);
         }
      });
      btnBack.setBounds(0, 1, 45, 36);
      getContentPane().add(btnBack);

      JLabel lblgreenbar = new JLabel("");
      lblgreenbar.setForeground(Color.WHITE);
      lblgreenbar.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      lblgreenbar.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/큰초록바.png")));
      lblgreenbar.setBounds(0, 0, 416, 38);
      getContentPane().add(lblgreenbar);

      JLabel lblstartbackground = new JLabel("");
      lblstartbackground.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/starttime.png")));
      lblstartbackground.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      lblstartbackground.setBounds(64, 426, 138, 48);
      getContentPane().add(lblstartbackground);

      JLabel lblfinishbackground = new JLabel("");
      lblfinishbackground.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/finishtime.png")));
      lblfinishbackground.setFont(new Font("휴먼편지체", Font.PLAIN, 20));
      lblfinishbackground.setBounds(241, 427, 138, 48);
      getContentPane().add(lblfinishbackground);

      JLabel lblNewLabel = new JLabel("");
      lblNewLabel.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/exercisehours.png")));
      lblNewLabel.setBounds(77, 491, 278, 48);
      getContentPane().add(lblNewLabel);

      JLabel lblNewLabel_2 = new JLabel("");
      lblNewLabel_2.setIcon(new ImageIcon(ExerciseRecords.class.getResource("/image/exercisehours.png")));
      lblNewLabel_2.setBounds(78, 549, 269, 54);
      getContentPane().add(lblNewLabel_2);
      
      lbl_image = new JLabel("");
      lbl_image.setBounds(39, 127, 337, 165);
      getContentPane().add(lbl_image);

      ExerciseCalendar exerciseCalendar = new ExerciseCalendar(loginId);
      // 운동종료 버튼 클릭 시 이벤트 리스너
      btn_end.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            btn_Ok.setEnabled(true);
            btn_end.setEnabled(false);
            try {
               // 현재 시간 가져오기
               LocalDateTime now = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
               String formattedDateTimeE = now.format(formatter);
               exerciseCalendar.changeImageOfToday(); // ExerciseCalendar 객체의 메서드 호출
               // lbl_end에 현재 시간 표시
               lbl_end.setText(formattedDateTimeE);
               // MySQL에 현재 시간 삽입
               // 운동 종료 시간을 갱신
               String sql = "UPDATE exerciserecords SET end_time = NOW() WHERE exercise_name = ? AND start_time = ? AND user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
               String sql2 = "SELECT kcal_exercise FROM exerciserecords WHERE start_time = ? AND user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1";
               try (Connection conn = MySqlConnectionProvider.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql);
                     PreparedStatement stmt2 = conn.prepareStatement(sql2);
                     ) {
                  stmt.setString(1, selectedExercise);
                  stmt.setString(2, startTime);
                  stmt.setString(3, loginId);
                  stmt.executeUpdate();
                  
                  stmt2.setString(1, startTime);
                  stmt2.setString(2, loginId);
                  stmt2.execute();
                  
                  try(ResultSet rs = stmt2.executeQuery()){
                     Double kcal = 0.0;
                     while(rs.next()) {
                        kcal = rs.getDouble("kcal_exercise");
                     }
                     lblNewLabel_4.setText(String.valueOf(kcal));
                  }
               } catch (SQLException ex) {
                  ex.printStackTrace();
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            } catch (Exception ex) {
               ex.printStackTrace();
            }
            loadHours();
            lblTimeDiff.setText(timediff);
         }
      });
      
      setLocationRelativeTo(null);
   }
   
   // 선택된 운동에 따라 이미지 로드 및 표시하는 메서드
    private void loadExerciseImage(String exerciseName) {
        ImageIcon exerciseIcon = null;
        switch (exerciseName) {
            case "걷기":
                exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/걷기(수정완).gif"));
                break;
            case "걷기(보통 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/걷기(수정완).gif"));
               break;
            case "계단 오르내리기":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/계단(수정완).gif"));
               break;
            case "달리기(느린 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/달리기(수정완).gif"));
               break;
            case "달리기(보통 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/달리기(수정완).gif"));
               break;
            case "달리기(빠른 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/달리기(수정완).gif"));
               break;
            case "등산":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/등산(수정완).gif"));
               break;
            case "러닝머신(느린 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/러닝머신(수정완).gif"));
               break;
            case "러닝머신(보통 속도)":
               exerciseIcon = new ImageIcon(ExerciseRecords.class.getResource("/image/러닝머신(수정완).gif"));
               break;
            case "무게 트레이닝(고강도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/무게트레이닝(수정완).gif"));
               break;
            case "무게 트레이닝(저강도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/무게트레이닝(수정완).gif"));
               break;
            case "복싱":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/복싱(수정완).gif"));
               break;
            case "수영":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/수영(수정완).gif"));
               break;
            case "수영(느린 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/수영(수정완).gif"));
               break;
            case "수영(보통 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/수영(수정완).gif"));
               break;
            case "스쿼시":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/스쿼시(수정완).gif"));
               break;
            case "스쿼트":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/스쿼트(수정완).gif"));
               break;
            case "스트레칭":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/스트레칭3(수정완).gif"));
               break;
            case "실내 사이클":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/사이클(수정완).gif"));
               break;
            case "에어로빅":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/에어로빅(수정완).gif"));
               break;
            case "요가":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/요가(수정완).gif"));
               break;
            case "윗몸일으키기(느린 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/윗몸일으키기(수정완).gif"));
               break;
            case "윗몸일으키기(보통 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/윗몸일으키기(수정완).gif"));
               break;
            case "윗몸일으키기(빠른속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/윗몸일으키기(수정완).gif"));
               break;
            case "자전거 타기(느린 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/자전거(수정완).gif"));
               break;
            case "자전거 타기(보통 속도)":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/자전거(수정완).gif"));
               break;
            case "조깅":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/달리기(수정완).gif"));
               break;
            case "줄넘기":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/줄넘기(수정완).gif"));
               break;
            case "훌라후프":
               exerciseIcon = new ImageIcon(getClass().getResource("/image/훌라후프(수정완).gif"));
               break;

           default:
               //  특정 이미지를 찾을 수 없는 경우 기본 이미지 설정
                exerciseIcon = new ImageIcon(getClass().getResource("/image/로딩(리얼완전수정완).gif"));
                break;
        }
        lbl_image.setIcon(exerciseIcon);
    }

   private void loadStartTime() {
      System.out.println("DEBUG: loadStartTime() method called");
      String sql = "SELECT start_time FROM exerciserecords WHERE user_id = ? AND date = CURDATE() AND exercise_name = ? ORDER BY record_id DESC LIMIT 1;";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);) {
         pst.setString(1, loginId);
         pst.setString(2, selectedExercise);

         try (ResultSet rs = pst.executeQuery();) {

            while (rs.next()) {
               startTime = rs.getString("start_time");
            }
         }

      } catch (SQLException e) {
         e.printStackTrace();
      }
      
      System.out.println("DEBUG: loadStartTime() - startTime: " + startTime); // 디버깅 로그 추가
   }

   private void loadExerciseName() {
      String sql = "SELECT exercise_name FROM exerciserecords WHERE user_id = ? AND date = CURDATE() ORDER BY record_id DESC LIMIT 1;";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

      ) {

         pst.setString(1, loginId);

         try (ResultSet rs = pst.executeQuery();) {

            while (rs.next()) {
               selectedExercise = rs.getString("exercise_name");
            }
         }
         lbl_selected.setText(selectedExercise);

      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   private void loadHours() {
      String sql = "SELECT TIME_DIFF FROM exerciserecords WHERE user_id = ? AND date = CURDATE() AND exercise_name = ? ORDER BY record_id DESC LIMIT 1;";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);) {
         pst.setString(1, loginId);
         pst.setString(2, selectedExercise);

         try (ResultSet rs = pst.executeQuery();) {

            while (rs.next()) {
               timediff = rs.getString("TIME_DIFF");
            }
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
      System.out.println(timediff);
//      lblTimeDiff.setText(timediff); // 라벨에 값 설정
   }
   
   private void openSportSelectionDialog() {
       // 다이얼로그 생성
       JDialog dialog = new JDialog(this, "운동 목록 선택", true);
       dialog.setSize(300, 110); // 다이얼로그 크기 설정
       dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // 다이얼로그 닫기 설정
       dialog.setLocationRelativeTo(this); // 다이얼로그를 부모 창 중앙에 위치

       // 다이얼로그에 추가할 컴포넌트들 생성  아이디 또는 비밀번호가 잘못되었습니다.
       JLabel label = new JLabel("          운동 목록을 선택하세요.           ");
       label.setFont(new Font("HY엽서M", Font.PLAIN, 15));
       
       JButton button = new JButton("");
       button.setIcon(new ImageIcon(DietRecord.class.getResource("/image/재입력.png")));
       button.setContentAreaFilled(false);
       button.setBorderPainted(false);
       button.setFocusPainted(false);
       
       // 다이얼로그에 컴포넌트들 추가
       JPanel panel = new JPanel();
       panel.add(label);
       panel.add(button);
       dialog.getContentPane().add(panel);
       panel.setBackground(Color.WHITE);

       // 확인 버튼의 ActionListener 추가
       button.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               // 다이얼로그를 닫기
               dialog.dispose();
           }
       });


       // 다이얼로그 표시
       dialog.setVisible(true);
   }
}