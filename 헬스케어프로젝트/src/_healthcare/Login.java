package _healthcare;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dbutil.MySqlConnectionProvider;
import java.awt.Toolkit;

public class Login extends JFrame {
   private JTextField txtID;
   private JPasswordField txtPW;
   private JButton loginButton;
   public String loginId;

   public Login() {
   	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/image/_ICON.png")));
      setTitle("활기록");
      getContentPane().setBackground(Color.WHITE);
      getContentPane().setLayout(null);
      
      JButton managementButton = new JButton("비밀번호 찾기 / 회원탈퇴");
      managementButton.setFont(new Font("휴먼편지체", Font.ITALIC, 12));
      managementButton.setContentAreaFilled(false);
      managementButton.setBorderPainted(false);
      managementButton.setFocusPainted(false);
      managementButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		new UserManagement();
      	}
      });
      managementButton.setBounds(75, 372, 186, 23);
      getContentPane().add(managementButton);

      JLabel lblNewLabel_3 = new JLabel("New label");
      lblNewLabel_3.setIcon(new ImageIcon(Login.class.getResource("/image/요가하는 옥쓔.png")));
      lblNewLabel_3.setBounds(32, 264, 129, 102);
      getContentPane().add(lblNewLabel_3);

      JLabel lblNewLabel_4 = new JLabel("New label");
      lblNewLabel_4.setIcon(new ImageIcon(Login.class.getResource("/image/헬스토매통.png")));
      lblNewLabel_4.setBounds(0, 0, 129, 133);
      getContentPane().add(lblNewLabel_4);

      loginButton = new JButton("");
      loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/로그인.png")));
      loginButton.setContentAreaFilled(false);
      loginButton.setBorderPainted(false);
      loginButton.setFocusPainted(false);
      loginButton.setBounds(62, 170, 215, 46);
      getContentPane().add(loginButton);
      getRootPane().setDefaultButton(loginButton);

      loginButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String id = txtID.getText();
            String pw = txtPW.getText();

            if (login(id, pw)) {
               System.out.println("로그인성공");
               loginId = id;
               System.out.println(loginId);
               dispose();
               new Main(loginId);
            } else {
                new LoginDialog(Login.this).setVisible(true);
            }
         }
      });

      loginButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            loginButton.setIcon(new ImageIcon(Login.class.getResource("/image/로그인선택.png")));
         }
      });

      JButton signUpButton = new JButton("");
      signUpButton.setIcon(new ImageIcon(Login.class.getResource("/image/회원가입.png")));
      signUpButton.setContentAreaFilled(false);
      signUpButton.setBorderPainted(false);
      signUpButton.setFocusPainted(false);
      signUpButton.setBounds(51, 209, 239, 45);
      getContentPane().add(signUpButton);

      signUpButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new SignUp();
            dispose();
         }
      });

      txtPW = new JPasswordField();
      txtPW.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      txtPW.setBounds(62, 139, 215, 21);
      txtPW.setEchoChar((char) 0);
      txtPW.setText("비밀 번호를 입력하세요");
      getContentPane().add(txtPW);
      txtPW.setColumns(10);
      
      // 텍스트 필드에 포커스를 얻었을 때 힌트가 사라지도록 함
      txtPW.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            if (txtPW.getText().equals("비밀 번호를 입력하세요")) {
               txtPW.setText("");
               txtPW.setEchoChar('*'); // 비밀번호를 입력하면 '*'로 표시
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (txtPW.getText().isEmpty()) {
               txtPW.setEchoChar((char) 0);
               txtPW.setText("비밀 번호를 입력하세요");
            }
         }
      });
      
      txtPW.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			loginButton.doClick();
		}
	});
      
      txtID = new JTextField();
      txtID.setFont(new Font("휴먼편지체", Font.PLAIN, 14));
      txtID.setBounds(62, 108, 215, 21);
      txtID.setText("아이디를 입력하세요");
      getContentPane().add(txtID);
      txtID.setColumns(10);

      // 텍스트 필드에 포커스를 얻었을 때 힌트가 사라지도록 함
      txtID.addFocusListener(new FocusListener() {
         @Override
         public void focusGained(FocusEvent e) {
            if (txtID.getText().equals("아이디를 입력하세요")) {
               txtID.setText("");
            }
         }

         @Override
         public void focusLost(FocusEvent e) {
            if (txtID.getText().isEmpty()) {
               txtID.setText("아이디를 입력하세요");
            }
         }
      });

      JLabel lblNewLabel_1 = new JLabel("");
      lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/image/흰색이미지.png")));
      lblNewLabel_1.setBounds(38, 92, 259, 162);
      getContentPane().add(lblNewLabel_1);

      JLabel lblNewLabel_5 = new JLabel("New label");
      lblNewLabel_5.setIcon(new ImageIcon(Login.class.getResource("/image/엄청큰진한초록.png")));
      lblNewLabel_5.setBounds(32, 83, 272, 181);
      getContentPane().add(lblNewLabel_5);

      JLabel lblNewLabel_2 = new JLabel("New label");
      lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/image/운동하는브로콜리2.png")));
      lblNewLabel_2.setBounds(194, 263, 89, 132);
      getContentPane().add(lblNewLabel_2);

      JLabel lbl_background = new JLabel("");
      lbl_background.setIcon(new ImageIcon(Login.class.getResource("/image/로그인창배경3.png")));
      lbl_background.setBounds(-106, -128, 528, 693);
      getContentPane().add(lbl_background);
      setResizable(false); // 창 크기 고정
      setSize(343, 433);
      setLocationRelativeTo(null); // 화면 중앙에 위치
      setVisible(true);
   }

   private boolean login(String id, String pw) {

      try (Connection connection = MySqlConnectionProvider.getConnection()) {
         String query = "SELECT * FROM users WHERE id = ? AND pw = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, pw);

            try (ResultSet resultSet = statement.executeQuery()) {
               return resultSet.next(); // 결과가 있으면 true 반환
            }
         }
      } catch (SQLException ex) {
         ex.printStackTrace();
         return false;
      }
   }
   class LoginDialog extends JDialog {
       public LoginDialog(JFrame frame) {
           super(frame, "", true); 
           setSize(300, 110);
           setLocationRelativeTo(frame);

           JPanel panel = new JPanel();
           JLabel label = new JLabel("아이디 또는 비밀번호가 잘못되었습니다.");
           label.setFont(new Font("HY엽서M", Font.PLAIN, 15));
           
           JButton closeButton = new JButton();
           closeButton.setIcon(new ImageIcon(DietRecord.class.getResource("/image/재입력.png")));
           closeButton.setContentAreaFilled(false);
           closeButton.setBorderPainted(false);
           closeButton.setFocusPainted(false);
           panel.add(label);
           panel.add(closeButton);
           panel.setBackground(Color.WHITE);

           closeButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   dispose();
               }
           });

           add(panel);
       }
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> new Login());
	   
   }
}