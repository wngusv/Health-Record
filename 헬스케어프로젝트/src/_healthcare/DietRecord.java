package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import dbutil.MySqlConnectionProvider;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.*;

public class DietRecord extends JFrame {

   private JLabel lblNewLabel_4;
   private JLabel lblNewLabel_5;
   private JLabel lblNewLabel_6;
   private String user_id;
   // 현재 날짜
   java.util.Date currentDate = new java.util.Date();
   // sql에 넣기 위해 날짜를 date형식으로 변경
   Date sqlDate = new Date(currentDate.getTime());
   private double breakfastSumKcal;
   private double lunchSumKcal;
   private double dinnerSumKcal;
   private double snackSumKcal;
   private JLabel lblNewLabel_7;
   private Double todayEatSumKacl;
   private JLabel lblNewLabel_9;
   private JLabel lblNewLabel_11;

   public DietRecord(String loginId) {
      getContentPane().setBackground(Color.WHITE);
      this.user_id = loginId;
      extracted();
      todayEatKcal();
      showGUI();
   }

   private void showGUI() {
      setSize(370, 447);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
   }

   private void extracted() {
      //setUndecorated(true);

      setTitle("식단 기록");
      getContentPane().setLayout(null);

      JLabel lblNewLabel = new JLabel("");
      lblNewLabel.setIcon(new ImageIcon(DietRecord.class.getResource("/image/아침칼로리.png")));
      lblNewLabel.setBounds(72, 74, 168, 36);
      getContentPane().add(lblNewLabel);

      JLabel lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setIcon(new ImageIcon(DietRecord.class.getResource("/image/점심칼로리.png")));
      lblNewLabel_1.setBounds(72, 138, 168, 42);
      getContentPane().add(lblNewLabel_1);

      JLabel lblNewLabel_2 = new JLabel("");
      lblNewLabel_2.setIcon(new ImageIcon(DietRecord.class.getResource("/image/저녁칼로리.png")));
      lblNewLabel_2.setBounds(32, 207, 208, 33);
      getContentPane().add(lblNewLabel_2);

      JLabel lblNewLabel_3 = new JLabel("");
      lblNewLabel_3.setIcon(new ImageIcon(DietRecord.class.getResource("/image/간식칼로리.png")));
      lblNewLabel_3.setBounds(72, 273, 168, 37);
      getContentPane().add(lblNewLabel_3);

      lblNewLabel_4 = new JLabel(" ");
      lblNewLabel_4.setBounds(131, 65, 109, 30);
      lblNewLabel_4.setFont(new Font("HY엽서M", Font.PLAIN, 12));
      String breakfastKcal_sql = "SELECT SUM(breakfast_kcal) FROM morningdiet WHERE user_id = ? AND date = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(breakfastKcal_sql);) {
         pst.setString(1, user_id);
         pst.setDate(2, sqlDate);

         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            breakfastSumKcal = rs.getDouble("SUM(breakfast_kcal)");

         }
         lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      getContentPane().add(lblNewLabel_4);

      lblNewLabel_5 = new JLabel(" ");
      lblNewLabel_5.setBounds(131, 138, 109, 23);
      lblNewLabel_5.setFont(new Font("HY엽서M", Font.PLAIN, 12));
      String lunchKcal_sql = "SELECT SUM(lunch_kcal) FROM lunchdiet WHERE user_id = ? AND date = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(lunchKcal_sql);) {
         pst.setString(1, user_id);
         pst.setDate(2, sqlDate);

         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            lunchSumKcal = rs.getDouble("SUM(lunch_kcal)");

         }
         lblNewLabel_5.setText(String.valueOf(lunchSumKcal));

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      getContentPane().add(lblNewLabel_5);

      lblNewLabel_6 = new JLabel(" ");
      lblNewLabel_6.setBounds(131, 207, 109, 15);
      lblNewLabel_6.setFont(new Font("HY엽서M", Font.PLAIN, 12));
      String dinnerKcal_sql = "SELECT SUM(dinner_kcal) FROM dinnerdiet WHERE user_id = ? AND date = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(dinnerKcal_sql);) {
         pst.setString(1, user_id);
         pst.setDate(2, sqlDate);

         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            dinnerSumKcal = rs.getDouble("SUM(dinner_kcal)");

         }
         lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      getContentPane().add(lblNewLabel_6);

      lblNewLabel_7 = new JLabel(" ");
      lblNewLabel_7.setBounds(131, 271, 109, 15);
      lblNewLabel_7.setFont(new Font("HY엽서M", Font.PLAIN, 12));
      String snackKcal_sql = "SELECT SUM(snack_kcal) FROM snackdiet WHERE user_id = ? AND date = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(snackKcal_sql);) {
         pst.setString(1, user_id);
         pst.setDate(2, sqlDate);

         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            snackSumKcal = rs.getDouble("SUM(snack_kcal)");

         }
         lblNewLabel_7.setText(String.valueOf(snackSumKcal));

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      getContentPane().add(lblNewLabel_7);

      JButton btnNewButton = new JButton("");
      btnNewButton.setToolTipText("음식 추가 버튼");
      btnNewButton.setIcon(new ImageIcon(DietRecord.class.getResource("/image/plus 1.png")));
      btnNewButton.setBounds(233, 65, 56, 30);
      btnNewButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Morning_FindFoodCalories m = new Morning_FindFoodCalories(user_id);
            m.setVisible(true);
            dispose();
         }
      });
      btnNewButton.setContentAreaFilled(false);
      btnNewButton.setBorderPainted(false);
      btnNewButton.setFocusPainted(false);
      getContentPane().add(btnNewButton);

      JButton btnNewButton_1 = new JButton("");
      btnNewButton_1.setToolTipText("음식 추가 버튼");
      btnNewButton_1.setIcon(new ImageIcon(DietRecord.class.getResource("/image/plus 1.png")));
      btnNewButton_1.setBounds(233, 138, 56, 23);
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Lunch_FindFoodCalories l = new Lunch_FindFoodCalories(user_id);
            l.setVisible(true);
            dispose();
         }
      });
      btnNewButton_1.setContentAreaFilled(false);
      btnNewButton_1.setBorderPainted(false);
      btnNewButton_1.setFocusPainted(false);
      getContentPane().add(btnNewButton_1);

      JButton btnNewButton_2 = new JButton("");
      btnNewButton_2.setToolTipText("음식 추가 버튼");
      btnNewButton_2.setIcon(new ImageIcon(DietRecord.class.getResource("/image/plus 1.png")));
      btnNewButton_2.setBounds(233, 201, 56, 23);
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Dinner_FindFoodCalories d = new Dinner_FindFoodCalories(user_id);
            d.setVisible(true);
            dispose();
         }
      });
      btnNewButton_2.setContentAreaFilled(false);
      btnNewButton_2.setBorderPainted(false);
      btnNewButton_2.setFocusPainted(false);
      getContentPane().add(btnNewButton_2);

      JButton btnNewButton_3 = new JButton("");
      btnNewButton_3.setToolTipText("음식 추가 버튼");
      btnNewButton_3.setIcon(new ImageIcon(DietRecord.class.getResource("/image/plus 1.png")));
      btnNewButton_3.setBounds(233, 274, 56, 23);
      btnNewButton_3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Snack_FindFoodCalories s = new Snack_FindFoodCalories(user_id);
            s.setVisible(true);
            dispose();
         }
      });
      btnNewButton_3.setContentAreaFilled(false);
      btnNewButton_3.setBorderPainted(false);
      btnNewButton_3.setFocusPainted(false);
      getContentPane().add(btnNewButton_3);

      JCheckBox chckbxNewCheckBox = new JCheckBox("단식");
      chckbxNewCheckBox.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      chckbxNewCheckBox.setBounds(86, 101, 78, 23);
      chckbxNewCheckBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 체크박스가 선택되면 레이블 텍스트를 변경
            if (chckbxNewCheckBox.isSelected()) {
               lblNewLabel_4.setText("단식");
               fastSql("morningdiet", "breakfast_meal", "breakfast_kcal");
               breakfastSumKcal = 0.0;
               todayEatKcal();
            } else {
               lblNewLabel_4.setText(String.valueOf(breakfastSumKcal));
            }
         }
      });
      chckbxNewCheckBox.setContentAreaFilled(false);
      chckbxNewCheckBox.setBorderPainted(false);
      chckbxNewCheckBox.setFocusPainted(false);
      getContentPane().add(chckbxNewCheckBox);

      JCheckBox chckbxNewCheckBox_1 = new JCheckBox("단식");
      chckbxNewCheckBox_1.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      chckbxNewCheckBox_1.setBounds(86, 168, 112, 23);
      chckbxNewCheckBox_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 체크박스가 선택되면 레이블 텍스트를 변경
            if (chckbxNewCheckBox_1.isSelected()) {
               lblNewLabel_5.setText("단식");
               fastSql("lunchdiet", "lunch_meal", "lunch_kcal");
               lunchSumKcal = 0.0;
               todayEatKcal();
            } else {
               lblNewLabel_5.setText(String.valueOf(lunchSumKcal));
            }
         }
      });
      chckbxNewCheckBox_1.setContentAreaFilled(false);
      chckbxNewCheckBox_1.setBorderPainted(false);
      chckbxNewCheckBox_1.setFocusPainted(false);
      getContentPane().add(chckbxNewCheckBox_1);

      JCheckBox chckbxNewCheckBox_2 = new JCheckBox("단식");
      chckbxNewCheckBox_2.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      chckbxNewCheckBox_2.setBounds(86, 233, 85, 23);
      chckbxNewCheckBox_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 체크박스가 선택되면 레이블 텍스트를 변경
            if (chckbxNewCheckBox_2.isSelected()) {
               lblNewLabel_6.setText("단식");
               fastSql("dinnerdiet", "dinner_meal", "dinner_kcal");
               dinnerSumKcal = 0.0;
               todayEatKcal();
            } else {
               lblNewLabel_6.setText(String.valueOf(dinnerSumKcal));
            }
         }
      });
      chckbxNewCheckBox_2.setContentAreaFilled(false);
      chckbxNewCheckBox_2.setBorderPainted(false);
      chckbxNewCheckBox_2.setFocusPainted(false);
      getContentPane().add(chckbxNewCheckBox_2);

      JCheckBox chckbxNewCheckBox_3 = new JCheckBox("단식");
      chckbxNewCheckBox_3.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      chckbxNewCheckBox_3.setBounds(85, 301, 85, 23);
      chckbxNewCheckBox_3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 체크박스가 선택되면 레이블 텍스트를 변경
            if (chckbxNewCheckBox_3.isSelected()) {
               lblNewLabel_7.setText("단식");
               fastSql("snackdiet", "snack_meal", "snack_kcal");
               snackSumKcal = 0.0;
               todayEatKcal();
            } else {
               lblNewLabel_7.setText(String.valueOf(snackSumKcal));
            }
         }
      });
      chckbxNewCheckBox_3.setContentAreaFilled(false);
      chckbxNewCheckBox_3.setBorderPainted(false);
      chckbxNewCheckBox_3.setFocusPainted(false);
      getContentPane().add(chckbxNewCheckBox_3);

      lblNewLabel_9 = new JLabel(" ");
      lblNewLabel_9.setFont(new Font("휴먼편지체", Font.BOLD, 14));
      lblNewLabel_9.setBounds(99, 369, 56, 23);
      // todayEatKcal();
      getContentPane().add(lblNewLabel_9);

      lblNewLabel_11 = new JLabel(" ");
      lblNewLabel_11.setFont(new Font("휴먼편지체", Font.BOLD, 14));
      lblNewLabel_11.setBounds(181, 370, 85, 23);
      String recommendKcalQuery = "SELECT recommended_kcal FROM users WHERE id = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(recommendKcalQuery);) {
         ps.setString(1, user_id);

         ResultSet rs = ps.executeQuery();
         String recommendKcal = "";
         while (rs.next()) {
            recommendKcal = rs.getBigDecimal("recommended_kcal").toString();
         }
         lblNewLabel_11.setText(recommendKcal+"kcal");

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      getContentPane().add(lblNewLabel_11);

      JButton btnNewButton_4 = new JButton("");
      btnNewButton_4.setToolTipText("뒤로가기");
      btnNewButton_4.setBounds(0, 0, 56, 36);
      getContentPane().add(btnNewButton_4);
      btnNewButton_4.setIcon(new ImageIcon(DietRecord.class.getResource("/image/뒤로가기.png")));
      btnNewButton_4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            dispose();
            new Main(user_id);
         }
      });
      // 배경을 투명하게 만드는 코드--------
      btnNewButton_4.setContentAreaFilled(false);
      btnNewButton_4.setBorderPainted(false);
      btnNewButton_4.setFocusPainted(false);

      JButton btnNewButton_5 = new JButton("");
      btnNewButton_5.setToolTipText("종료 버튼");
      btnNewButton_5.setBounds(303, 0, 67, 36);
      getContentPane().add(btnNewButton_5);
      btnNewButton_5.setIcon(new ImageIcon(DietRecord.class.getResource("/image/종료.png")));
      btnNewButton_5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // 프로그램 종료
            System.exit(0);
         }
      });
      
      btnNewButton_5.setContentAreaFilled(false);
      btnNewButton_5.setBorderPainted(false);
      btnNewButton_5.setFocusPainted(false);
      
      JLabel lblNewLabel_12 = new JLabel("");
      lblNewLabel_12.setIcon(new ImageIcon(DietRecord.class.getResource("/image/식단 기록3.png")));
      lblNewLabel_12.setForeground(Color.WHITE);
      lblNewLabel_12.setFont(new Font("휴먼편지체", Font.BOLD, 23));
      lblNewLabel_12.setBounds(61, 0, 102, 36);
      getContentPane().add(lblNewLabel_12);
      
      JLabel lblNewLabel_14 = new JLabel("");
      lblNewLabel_14.setIcon(new ImageIcon(DietRecord.class.getResource("/image/큰초록바.png")));
      lblNewLabel_14.setBounds(0, 0, 461, 36);
      getContentPane().add(lblNewLabel_14);
      
      JLabel lblNewLabel_13 = new JLabel("");
      lblNewLabel_13.setIcon(new ImageIcon(DietRecord.class.getResource("/image/금일칼로리표시2.png")));
      lblNewLabel_13.setBounds(26, 299, 318, 107);
      getContentPane().add(lblNewLabel_13);

   }

   private void fastSql(String whenFast, String whenMeal, String whenKcal) { // 삭제하지말고 업데이트 음식:"단식",칼로리 0 으로 해주자
      // String deleteSql = "DELETE FROM "+ whenFast +" WHERE user_id = ? AND date =
      // ?";
      String updateQuery = "UPDATE " + whenFast + " SET " + whenMeal + " = '단식'," + whenKcal
            + "= 0.0 WHERE user_id = ? AND date = ?";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement ps = conn.prepareStatement(updateQuery);) {
         ps.setString(1, user_id);
         ps.setDate(2, sqlDate);

         ps.executeUpdate();

      } catch (SQLException e) {
         e.printStackTrace();
      }

   }

   private void todayEatKcal() {
      String sumKcal = "SELECT \r\n" + "    COALESCE(SUM(value), 0) AS '금일 칼로리'\r\n" + "FROM (\r\n"
            + "    SELECT COALESCE(SUM(breakfast_kcal), 0) AS value FROM morningdiet WHERE user_id = ? AND date = ? \r\n"
            + "    UNION ALL\r\n"
            + "    SELECT COALESCE(SUM(lunch_kcal), 0) AS value FROM lunchdiet WHERE user_id = ? AND date = ? \r\n"
            + "    UNION ALL\r\n"
            + "    SELECT COALESCE(SUM(dinner_kcal), 0) AS value FROM dinnerdiet WHERE user_id = ? AND date = ? \r\n"
            + "    UNION ALL\r\n"
            + "    SELECT COALESCE(SUM(snack_kcal), 0) AS value FROM snackdiet WHERE user_id = ? AND date = ? \r\n"
            + ") AS subquery;";
      try (Connection conn = MySqlConnectionProvider.getConnection();
            PreparedStatement pst = conn.prepareStatement(sumKcal);) {
         pst.setString(1, user_id);
         pst.setDate(2, sqlDate);
         pst.setString(3, user_id);
         pst.setDate(4, sqlDate);
         pst.setString(5, user_id);
         pst.setDate(6, sqlDate);
         pst.setString(7, user_id);
         pst.setDate(8, sqlDate);

         ResultSet rs = pst.executeQuery();
         while (rs.next()) {
            todayEatSumKacl = rs.getDouble("금일 칼로리");
         }
         lblNewLabel_9.setText(todayEatSumKacl.toString());

      } catch (SQLException e1) {
         e1.printStackTrace();
      }
   }
}