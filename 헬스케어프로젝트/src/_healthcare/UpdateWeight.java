package _healthcare;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateWeight extends JFrame {
    private Main mainInstance; // Main 클래스의 인스턴스를 저장할 필드

    public UpdateWeight(Main mainInstance) {
        this.mainInstance = mainInstance;
        
        initializeUI();
    }

    private void initializeUI() {
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JTextField textField = new JTextField();
        textField.setBounds(40, 30, 200, 25);
        getContentPane().add(textField);

        JButton btnInput = new JButton("입력");
        btnInput.setBounds(88, 102, 97, 23);
        getContentPane().add(btnInput);

        btnInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int inputValue = Integer.parseInt(textField.getText());
                mainInstance.updateWeight(inputValue); // Main 클래스의 updateWeight 메서드 호출
                dispose();
            }
        });
    }
}