package _healthcare;

import javax.swing.JFrame;
import java.awt.Color;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
   private double Goal_weight;



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
   private JTextField goalWeightField;
private double updateGoal_weight;

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
      goalWeightField.setText(String.valueOf(Goal_weight));
      
      
      JLabel lblGoalKg = new JLabel("목표 몸무게");
      lblGoalKg.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblGoalKg.setBounds(36, 202, 71, 23);
      getContentPane().add(lblGoalKg);
      
            JLabel lblNewLabel = new JLabel("");
            lblNewLabel.setIcon(new ImageIcon(EditProfile.class.getResource("/image/더큰아이보리.png")));
            lblNewLabel.setBounds(0, 0, 437, 460);
            getContentPane().add(lblNewLabel);

      showgui();
      setLocationRelativeTo(null); // 화면 중앙에 위치
   }

   private void showgui() {
      setSize(346, 485);
      setVisible(true);
      getContentPane().setLayout(null);
      setResizable(false); 
      
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
            
            JLabel lblNewLabel_10 = new JLabel("프로필 수정");
            lblNewLabel_10.setForeground(Color.WHITE);
            lblNewLabel_10.setFont(new Font("휴먼편지체", Font.BOLD, 20));
            lblNewLabel_10.setBounds(47, 3, 154, 30);
            getContentPane().add(lblNewLabel_10);
            btnNewButton_1.setIcon(new ImageIcon(EditProfile.class.getResource("/image/뒤로가기.png")));
            btnNewButton_1.setBounds(0, 0, 46, 30);
            btnNewButton_1.setContentAreaFilled(false);
            btnNewButton_1.setBorderPainted(false);
            btnNewButton_1.setFocusPainted(false);
            getContentPane().add(btnNewButton_1);
      
      JLabel lblNewLabel_9 = new JLabel("New label");
      lblNewLabel_9.setIcon(new ImageIcon(EditProfile.class.getResource("/image/큰초록바.png")));
      lblNewLabel_9.setBounds(0, 0, 340, 32);
      getContentPane().add(lblNewLabel_9);
      
      goalWeightField = new JTextField();
      goalWeightField.setText((String) null);
      goalWeightField.setColumns(10);
      goalWeightField.setBounds(120, 201, 116, 21);
      getContentPane().add(goalWeightField);

      JLabel lblNewLabel_8 = new JLabel("");
      lblNewLabel_8.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니헬스토매토.png")));
      lblNewLabel_8.setBounds(193, 356, 67, 64);
      getContentPane().add(lblNewLabel_8);

      JLabel lblNewLabel_7 = new JLabel("");
      lblNewLabel_7.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니요가하는 옥쓔.png")));
      lblNewLabel_7.setBounds(192, 301, 67, 54);
      getContentPane().add(lblNewLabel_7);

      JLabel lblNewLabel_6 = new JLabel("");
      lblNewLabel_6.setIcon(new ImageIcon(EditProfile.class.getResource("/image/미니브로콜리.png")));
      lblNewLabel_6.setBounds(210, 234, 46, 64);
      getContentPane().add(lblNewLabel_6);
      btnNewButton.setBounds(227, 415, 95, 30);
      getContentPane().add(btnNewButton);

      nameField = new JTextField();
      nameField.setBounds(122, 59, 116, 21);
      getContentPane().add(nameField);
      nameField.setColumns(10);

      weightField = new JTextField();
      weightField.setBounds(122, 162, 116, 21);
      getContentPane().add(weightField);
      weightField.setColumns(10);

      ageField = new JTextField();
      ageField.setBounds(122, 92, 116, 21);
      getContentPane().add(ageField);
      ageField.setColumns(10);

      heightField = new JTextField();
      heightField.setBounds(122, 127, 116, 21);
      getContentPane().add(heightField);
      heightField.setColumns(10);

      JLabel lblNewLabel_5 = new JLabel("캐릭터");
      lblNewLabel_5.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblNewLabel_5.setBounds(47, 263, 57, 15);
      getContentPane().add(lblNewLabel_5);

      JLabel lblNewLabel_4 = new JLabel("현재 몸무게");
      lblNewLabel_4.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblNewLabel_4.setBounds(36, 162, 71, 23);
      getContentPane().add(lblNewLabel_4);

      JLabel lblNewLabel_3 = new JLabel("키");
      lblNewLabel_3.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblNewLabel_3.setBounds(71, 128, 57, 15);
      getContentPane().add(lblNewLabel_3);

      JLabel lblNewLabel_2 = new JLabel("나이");
      lblNewLabel_2.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblNewLabel_2.setBounds(60, 94, 57, 18);
      getContentPane().add(lblNewLabel_2);

      JLabel lblNewLabel_1 = new JLabel("이름");
      lblNewLabel_1.setFont(new Font("휴먼편지체", Font.PLAIN, 15));
      lblNewLabel_1.setBounds(61, 59, 63, 23);
      getContentPane().add(lblNewLabel_1);

      tomato = new JRadioButton("3. 토마토");
      tomato.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      tomato.setBackground(Color.WHITE);
      tomato.setBounds(126, 371, 75, 23);
      getContentPane().add(tomato);

      corn = new JRadioButton("2. 옥수수");
      corn.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      corn.setBackground(Color.WHITE);
      corn.setBounds(126, 313, 75, 23);
      getContentPane().add(corn);

      broccoli = new JRadioButton("1. 브로콜리");
      broccoli.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      broccoli.setBackground(Color.WHITE);
      broccoli.setBounds(126, 259, 83, 23);
      getContentPane().add(broccoli);

      // 과일 버튼 그룹
      charButtonGroup = new ButtonGroup();
      charButtonGroup.add(broccoli);
      charButtonGroup.add(corn);
      charButtonGroup.add(tomato);
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
               Goal_weight = rs.getDouble("Goal_weight");
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
       try {
          updateAge = Integer.parseInt(ageField.getText());
          updateHeight = Double.parseDouble(heightField.getText());
          updateWeight = Double.parseDouble(weightField.getText());
          updateGoal_weight = Double.parseDouble(goalWeightField.getText());
       } catch(NumberFormatException e) {
          // 숫자 변환 오류 발생 시 다이얼로그 창 띄우기
           JOptionPane.showMessageDialog(null, "나이,키,몸무게 칸에는 숫자를 입력해야 합니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
       }
       
       LocalDate today = LocalDate.now();

       String updateSql = "UPDATE users SET name = ?, age = ?, height = ?, weight = ?, Goal_weight = ?, user_char = ?  WHERE id = ?";

       try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(updateSql);
       ) {
           pst.setString(1, updateName);
           pst.setInt(2, updateAge);
           pst.setDouble(3, updateHeight);
           pst.setDouble(4, updateWeight);
           pst.setDouble(5,  updateGoal_weight);
           pst.setString(7, userId);

           if (broccoli.isSelected()) {
               pst.setInt(6, 1);
           } else if (corn.isSelected()) {
               pst.setInt(6, 2);
           } else if (tomato.isSelected()) {
               pst.setInt(6, 3);
           } else {
               // 라디오 버튼이 선택되지 않은 경우 처리
               System.err.println("캐릭터를 선택하세요.");
               return;
           }

           pst.executeUpdate();

           // 여기에 pst2에 대한 설정 추가
           String updateWeightSql = "INSERT INTO weightrecords (user_id, date, weight) VALUES (?, ?, ?)";
           try (PreparedStatement pst2 = conn.prepareStatement(updateWeightSql);) {
              System.out.println(updateWeight);
              System.out.println(Double.parseDouble(weightField.getText()));
               if (updateWeight != weight) {
                   pst2.setString(1, userId);
                   pst2.setDate(2, java.sql.Date.valueOf(today));
                   pst2.setDouble(3, updateWeight);

                   int rowsAffected = pst2.executeUpdate();
                   System.out.println("pst2에 의해 영향을 받은 행 수: " + rowsAffected);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
   }


   private void selectImage() {
      
      btnNewButton.setEnabled(true);
   }
}