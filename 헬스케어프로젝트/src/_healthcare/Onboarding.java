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
    private int currentCardIndex = 0; // 현재 보여지는 카드의 인덱스를 추적하는 변수 추가!

    public Onboarding() {
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        Dimension preferredSize = new Dimension(368, 540); // 원하는 크기로 설정
        cardsPanel.setPreferredSize(preferredSize); // 크기 설정

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
                currentCardIndex++; // 다음 카드로 이동할 때 현재 카드의 인덱스를 증가시킴
                
                // 만약 마지막 카드라면
                if (currentCardIndex == imageFileNames.length - 1) {
                    // 현재 JFrame 닫기
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Onboarding.this);
                    frame.dispose(); // JFrame 닫기
                }
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
        
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("운동 프로그램 온보딩");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(368, 620);

        // 현재 화면 크기 가져오기
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // JFrame을 화면 중앙에 위치시키기
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        Onboarding onboardingPanel = new Onboarding();
        frame.getContentPane().add(onboardingPanel);
     // 나가기 버튼 숨기기
        frame.setUndecorated(true); // 창 장식 요소(타이틀 바, 테두리) 제거

        frame.setVisible(true);
    }
}
