package _healthcare;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstPage extends JFrame {
    public FirstPage() {
    	setTitle("활기록");
        extracted();
        showgui();
        
     // 타이머를 이용하여 3초 후에 Login 창을 표시
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 숨기기
                new Login(); // Login 창 표시
            }
        });
        timer.setRepeats(false); // 타이머가 한 번만 실행되도록 설정
        timer.start();
    }

    private void extracted() {
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(FirstPage.class.getResource("/image/Animation2.gif")));
        lblNewLabel.setBounds(0, -50, 405, 517);
        getContentPane().add(lblNewLabel);
    }

    private void showgui() {
        setSize(319, 444); // 프레임 크기 설정
        setLocationRelativeTo(null); // 화면 중앙에 표시
        setResizable(false); // 크기 조절 불가능으로 변경
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FirstPage();
    }
}
