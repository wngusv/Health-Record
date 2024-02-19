package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Main extends JFrame {
	private String loginId;
	
	public Main(String loginId) {
		this.loginId = loginId;
		System.out.println("로그인한 ID:" + loginId);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JButton boardButton = new JButton("게시판");
		springLayout.putConstraint(SpringLayout.NORTH, boardButton, 171, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, boardButton, 114, SpringLayout.WEST, getContentPane());
		getContentPane().add(boardButton);

		boardButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MessageBoard messageBoard = new MessageBoard(loginId);
				messageBoard.setVisible(true);
				dispose();
			}
		});

		setSize(360, 530);
		setVisible(true);
	}
	
}
