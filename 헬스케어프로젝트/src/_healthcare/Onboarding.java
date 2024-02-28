package _healthcare;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Onboarding extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    public Onboarding() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        // 이미지 파일 이름
        String[] imageFileNames = {"src/image/온보딩1.png", "src/image/온보딩2.png", "src/image/온보딩3.png", "src/image/온보딩4.png", "src/image/온보딩5.png", "src/image/온보딩6.png"};

        // 각 이미지를 카드에 추가
        for (String fileName : imageFileNames) {
            JPanel cardPanel = new JPanel();
            ImageIcon imageIcon = new ImageIcon(fileName);
            JLabel imageLabel = new JLabel(imageIcon);
            cardPanel.add(imageLabel);
            cardsPanel.add(cardPanel, fileName);
            
        }
        add(cardsPanel, BorderLayout.CENTER);
        

        // 이전 카드로 이동하는 버튼
        JButton prevButton = new JButton("<");
        prevButton.setForeground(Color.GRAY);
        prevButton.setContentAreaFilled(false);
        prevButton.setBorderPainted(false);
        prevButton.setFocusPainted(false);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(cardsPanel);
            }
        });

        // 다음 카드로 이동하는 버튼
        JButton nextButton = new JButton(">");
        nextButton.setForeground(Color.GRAY);
        nextButton.setContentAreaFilled(false);
        nextButton.setBorderPainted(false);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cardsPanel);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("운동 프로그램 온보딩");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(371, 620);

        Onboarding onboardingPanel = new Onboarding();
        frame.add(onboardingPanel);

        frame.setVisible(true);
    }
}


