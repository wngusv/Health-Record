package _healthcare;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FindFoodCalories extends JFrame{
	private JTextField textField;
	public FindFoodCalories() {
		extracted();
		
		
		showGUI();
		
	}
	private void createFoodList() {
		
	}
	
	
	private void showGUI() {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	
	private void extracted() {
		setTitle("음식칼로리");
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -193, SpringLayout.SOUTH, getContentPane());
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO 검색을 눌렀을 때 그 단어가 있는 음식들 찾기
				
				
				
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 225, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnNewButton);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, -1, SpringLayout.NORTH, textField);
		getContentPane().add(btnNewButton);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("안녕");
		springLayout.putConstraint(SpringLayout.NORTH, mntmNewMenuItem, 327, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, mntmNewMenuItem, -46, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, mntmNewMenuItem, 88, SpringLayout.WEST, getContentPane());
		getContentPane().add(mntmNewMenuItem);
	}
	
	public static void main(String[] args) {
		new FindFoodCalories();
	}
}
