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
        exerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 운동기록 창 열기
                ExerciseRecords exerciseRecords = new ExerciseRecords(loginId);
                exerciseRecords.setVisible(true);
                dispose(); // 현재 창 닫기
            }
        });
        
        
        JButton waterButton = new JButton("물 기록");
        springLayout.putConstraint(SpringLayout.NORTH, waterButton, 36, SpringLayout.SOUTH, exerciseButton);
        springLayout.putConstraint(SpringLayout.WEST, waterButton, 104, SpringLayout.WEST, getContentPane());
        getContentPane().add(waterButton);
        
        JButton btnNewButton = new JButton("식단 기록");
        btnNewButton.addActionListener(new ActionListener() {
        	

			public void actionPerformed(ActionEvent e) {
        		DietRecord dietRecord = new DietRecord(loginId);
        		dietRecord.setVisible(true);
        		dispose();
        	}
        });
        springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, exerciseButton);
        springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -40, SpringLayout.NORTH, exerciseButton);
        getContentPane().add(btnNewButton);
        
        JButton calButton = new JButton("캘린더");
        calButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ExerciseCalendar calendar = new ExerciseCalendar(loginId);
        		calendar.setVisible(true);
        	}
        });
        springLayout.putConstraint(SpringLayout.NORTH, calButton, 26, SpringLayout.SOUTH, waterButton);
        springLayout.putConstraint(SpringLayout.EAST, calButton, 0, SpringLayout.EAST, boardButton);
        getContentPane().add(calButton);
        waterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		// 물 기록 창 열기
        		WaterRecords waterRecords = new WaterRecords(loginId);
        		waterRecords.setVisible(true);
        		dispose(); // 현재 창 닫기
        	}
        });
        



        setSize(360, 530);
        setVisible(true);
    }
}
