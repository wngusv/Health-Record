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
        springLayout.putConstraint(SpringLayout.NORTH, waterButton, 440, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, boardButton, 0, SpringLayout.NORTH, waterButton);
        springLayout.putConstraint(SpringLayout.WEST, exerciseButton, 0, SpringLayout.WEST, waterButton);
        springLayout.putConstraint(SpringLayout.SOUTH, exerciseButton, -6, SpringLayout.NORTH, waterButton);
        springLayout.putConstraint(SpringLayout.WEST, waterButton, 99, SpringLayout.WEST, getContentPane());
        getContentPane().add(waterButton);
        waterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		// 물 기록 창 열기
        		WaterRecords waterRecords = new WaterRecords(loginId);
        		waterRecords.setVisible(true);
        		dispose(); // 현재 창 닫기
        	}
        });
        
        
        JButton btnNewButton = new JButton("식단 기록");
        springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 0, SpringLayout.WEST, exerciseButton);
        springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -6, SpringLayout.NORTH, exerciseButton);
        btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		DietRecord dietRecord = new DietRecord(loginId);
        		dietRecord.setVisible(true);
        		dispose();
        	}
        });
        getContentPane().add(btnNewButton);
        
        
        JButton calButton = new JButton("캘린더");
        springLayout.putConstraint(SpringLayout.WEST, boardButton, 0, SpringLayout.WEST, calButton);
        springLayout.putConstraint(SpringLayout.NORTH, calButton, 0, SpringLayout.NORTH, btnNewButton);
        springLayout.putConstraint(SpringLayout.WEST, calButton, 103, SpringLayout.EAST, btnNewButton);
        calButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        		ExerciseCalendar calendar = new ExerciseCalendar(loginId);
        		calendar.setVisible(true);
        	}
        });
        getContentPane().add(calButton);
        
        



        setSize(795, 530);
        setVisible(true);
    }
}
