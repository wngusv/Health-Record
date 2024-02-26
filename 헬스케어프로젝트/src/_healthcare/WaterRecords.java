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

   public WaterRecords(String loginId) {
      getContentPane().setBackground(Color.WHITE); // 오늘 데이트와 아이디로 select해보고/ 행이 없다면 행 insert./ 행이 있다면 행 update.
      this.user_id = loginId;
      try (Connection conn = MySqlConnectionProvider.getConnection()) {
         String selectQuery = "SELECT * FROM waterrecords WHERE user_id = ? AND date = ?";
         try (PreparedStatement pst = conn.prepareStatement(selectQuery)) {
            pst.setString(1, user_id);
            pst.setDate(2, sqlDate);

            try (ResultSet rs = pst.executeQuery()) {
               if (rs.next()) {

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

      extracted();
      showGUI();

   }

   private void showGUI() {
      setSize(478, 405);
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


   private void extracted() {
      setTitle("물 기록");
      getContentPane().setLayout(null);

      btn_cup2 = new JButton("");
      btn_cup2.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup2.setFocusPainted(false);
      btn_cup2.setContentAreaFilled(false);
      btn_cup2.setBorderPainted(false);
      btn_cup2.setBounds(132, 93, 52, 65);
      btnClick(btn_cup2);
      getContentPane().add(btn_cup2);

      JLabel lblNewLabel = new JLabel("물 권장량");
      lblNewLabel.setBounds(12, 69, 52, 15);
      getContentPane().add(lblNewLabel);

      JLabel lblNewLabel_1 = new JLabel("물 음용량: ");
      lblNewLabel_1.setBounds(50, 250, 60, 15);
      getContentPane().add(lblNewLabel_1);

      nowWaterDrinking = new JLabel("0");
      nowWaterDrinking.setBounds(144, 250, 40, 15);
      getContentPane().add(nowWaterDrinking);

      JLabel lblNewLabel_3 = new JLabel(" / 8잔");
      lblNewLabel_3.setBounds(202, 250, 46, 15);
      getContentPane().add(lblNewLabel_3);

      JLabel lblNewLabel_4 = new JLabel("<html> 세계보건기구(WHO)가 권장하는 하루 물 섭취량은 1.5~2ℓ입니다. <br/r>"
            + "200mℓ가 들어가는 일반적인 컵으로 약 8~10잔 정도입니다.<br/>" + "</html>");
      lblNewLabel_4.setBounds(146, 330, 306, 26);
      lblNewLabel_4.setFont(new Font("돋움", Font.PLAIN, 10));
      getContentPane().add(lblNewLabel_4);

      btn_cup1 = new JButton("");
      btn_cup1.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup1.setBounds(64, 93, 52, 65);
      btn_cup1.setContentAreaFilled(false);
      btn_cup1.setBorderPainted(false);
      btn_cup1.setFocusPainted(false);
      btnClick(btn_cup1);
      getContentPane().add(btn_cup1);

      btn_cup3 = new JButton("");
      btn_cup3.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup3.setFocusPainted(false);
      btn_cup3.setContentAreaFilled(false);
      btn_cup3.setBorderPainted(false);
      btn_cup3.setBounds(196, 93, 52, 65);
      btnClick(btn_cup3);
      getContentPane().add(btn_cup3);

      btn_cup4 = new JButton("");
      btn_cup4.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup4.setFocusPainted(false);
      btn_cup4.setContentAreaFilled(false);
      btn_cup4.setBorderPainted(false);
      btn_cup4.setBounds(260, 93, 52, 65);
      btnClick(btn_cup4);
      getContentPane().add(btn_cup4);

      btn_cup5 = new JButton("");
      btn_cup5.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup5.setFocusPainted(false);
      btn_cup5.setContentAreaFilled(false);
      btn_cup5.setBorderPainted(false);
      btn_cup5.setBounds(64, 168, 52, 65);
      btnClick(btn_cup5);
      getContentPane().add(btn_cup5);

      btn_cup6 = new JButton("");
      btn_cup6.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup6.setFocusPainted(false);
      btn_cup6.setContentAreaFilled(false);
      btn_cup6.setBorderPainted(false);
      btn_cup6.setBounds(132, 168, 52, 65);
      btnClick(btn_cup6);
      getContentPane().add(btn_cup6);

      btn_cup7 = new JButton("");
      btn_cup7.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup7.setFocusPainted(false);
      btn_cup7.setContentAreaFilled(false);
      btn_cup7.setBorderPainted(false);
      btn_cup7.setBounds(196, 168, 52, 65);
      btnClick(btn_cup7);
      getContentPane().add(btn_cup7);

      btn_cup8 = new JButton("");
      btn_cup8.setIcon(new ImageIcon(WaterRecords.class.getResource("/image/물마시기전 2.png")));
      btn_cup8.setFocusPainted(false);
      btn_cup8.setContentAreaFilled(false);
      btn_cup8.setBorderPainted(false);
      btn_cup8.setBounds(260, 168, 52, 65);
      btnClick(btn_cup8);
      getContentPane().add(btn_cup8);
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

}