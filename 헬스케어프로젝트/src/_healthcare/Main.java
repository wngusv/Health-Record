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

        JButton exerciseButton = new JButton("운동기록");
        springLayout.putConstraint(SpringLayout.NORTH, exerciseButton, 109, SpringLayout.SOUTH, boardButton);
        springLayout.putConstraint(SpringLayout.WEST, exerciseButton, 102, SpringLayout.WEST, getContentPane());
        getContentPane().add(exerciseButton);
        
        JButton btnNewButton = new JButton("물 기록");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		// 물 기록 창 열기
        		WaterRecords waterRecords = new WaterRecords(loginId);
        		waterRecords.setVisible(true);
        		dispose(); // 현재 창 닫기
        	}
        });
        springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 36, SpringLayout.SOUTH, exerciseButton);
        springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 104, SpringLayout.WEST, getContentPane());
        getContentPane().add(btnNewButton);

        exerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 운동기록 창 열기
                ExerciseRecords exerciseRecords = new ExerciseRecords();
                exerciseRecords.setVisible(true);
                dispose(); // 현재 창 닫기
            }
        });

        setSize(360, 530);
        setVisible(true);
    }
}
