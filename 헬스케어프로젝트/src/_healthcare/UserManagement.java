package _healthcare;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.SpringLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserManagement extends JFrame{
	public UserManagement() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JButton btnNewButton = new JButton("비밀번호 찾기");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 67, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 61, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -164, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -62, SpringLayout.EAST, getContentPane());
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("회원탈퇴");
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton_1, 33, SpringLayout.SOUTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton_1, 0, SpringLayout.WEST, btnNewButton);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton_1, -101, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton_1, 0, SpringLayout.EAST, btnNewButton);
		getContentPane().add(btnNewButton_1);
	}
	  class PwDialog extends JDialog {
	       public PwDialog(JFrame frame) {
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
	  class SecessionDialog extends JDialog {
	       public SecessionDialog(JFrame frame) {
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
}
