package _healthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import dbutil.MySqlConnectionProvider;

public class ExerciseCalendar extends JFrame {
	private JLabel monthLabel; // 월을 표시하는 레이블
	private JPanel calendarPanel; // 캘린더를 표시하는 패널
	private int currentMonth; // 현재 월을 나타내는 변수
	private int currentYear; // 현재 연도를 나타내는 변수
	protected int startDayOfWeek;
	public String loginId;
	private JPanel dayPanel;
	private JLabel dayLabel;

	private ImageIcon newImageIcon;
	private String newImage;
	private LocalDate today;

	public ExerciseCalendar(String loginId) {

		this.loginId = loginId;
		System.out.println(loginId);
		getContentPane().setBackground(Color.WHITE);
		setTitle("캘린다");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(360, 530));

		monthLabel = new JLabel("", SwingConstants.CENTER); // 월 표시 레이블 가운데 정렬
		monthLabel.setBackground(Color.WHITE);
		calendarPanel = new JPanel(new GridLayout(0, 7)); // 캘린더 패널에 그리드 레이아웃 설정
		calendarPanel.setBackground(Color.WHITE);
		JButton prevButton = new JButton("<"); // 이전 달로 이동하는 버튼
		prevButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentMonth == 0) { // 현재가 1월이면
					currentMonth = 11; // 12월로 변경
					currentYear--; // 연도도 하나 줄임
				} else {
					currentMonth--; // 그 외의 경우에는 이전 월로 변경
				}
				displayCalendar(); // 달력을 다시 표시
			}
		});

		JButton nextButton = new JButton(">"); // 다음 달로 이동하는 버튼
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentMonth == 11) { // 현재가 12월이면
					currentMonth = 0; // 1월로 변경
					currentYear++; // 연도 증가
				} else {
					currentMonth++; // 그 외의 경우에는 다음 월로 변경
				}
				displayCalendar(); // 달력을 다시 표시
			}
		});

		JPanel controlsPanel = new JPanel();
		controlsPanel.setBackground(Color.WHITE);
		SpringLayout sl_controlsPanel = new SpringLayout();
		sl_controlsPanel.putConstraint(SpringLayout.EAST, prevButton, -220, SpringLayout.EAST, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.WEST, monthLabel, 6, SpringLayout.EAST, prevButton);
		sl_controlsPanel.putConstraint(SpringLayout.WEST, nextButton, 213, SpringLayout.WEST, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, prevButton, 0, SpringLayout.NORTH, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.EAST, monthLabel, -6, SpringLayout.WEST, nextButton);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, monthLabel, 0, SpringLayout.NORTH, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.SOUTH, monthLabel, -7, SpringLayout.SOUTH, controlsPanel);
		controlsPanel.setLayout(sl_controlsPanel);
		controlsPanel.add(prevButton); // 이전 달 버튼은 서쪽에 배치
		controlsPanel.add(monthLabel); // 월 표시 레이블은 가운데에 배치
		controlsPanel.add(nextButton); // 다음 달 버튼은 동쪽에 배치

		currentMonth = LocalDate.now().getMonthValue() - 1; // 현재 월 설정
		currentYear = LocalDate.now().getYear(); // 현재 연도 설정
		calendarPanel.removeAll();
		displayCalendar(); // 초기 달력 표시
		
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, controlsPanel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, controlsPanel, -53, SpringLayout.NORTH, calendarPanel);
		springLayout.putConstraint(SpringLayout.NORTH, calendarPanel, 90, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, calendarPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, calendarPanel, 491, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, calendarPanel, 344, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, controlsPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, controlsPanel, 344, SpringLayout.WEST, getContentPane());
		getContentPane().setLayout(springLayout);

		getContentPane().add(controlsPanel); // 제어 패널을 프레임의 상단에 추가

		JButton btnBack = new JButton("New button");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Main(loginId);
			}
		});
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, btnBack, 0, SpringLayout.NORTH, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.WEST, btnBack, 0, SpringLayout.WEST, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.EAST, btnBack, 31, SpringLayout.WEST, controlsPanel);
		controlsPanel.add(btnBack);
		getContentPane().add(calendarPanel);

		JLabel lblNewLabel = new JLabel("월");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 20, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -17, SpringLayout.NORTH, calendarPanel);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -305, SpringLayout.EAST, getContentPane());
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("화\r\n");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 25, SpringLayout.EAST, lblNewLabel);
		lblNewLabel_1.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_1.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("수");
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel_2, -17, SpringLayout.NORTH, calendarPanel);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_2, -219, SpringLayout.EAST, getContentPane());
		lblNewLabel_2.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_2.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("목");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel_3, 37, SpringLayout.EAST, lblNewLabel_2);
		lblNewLabel_3.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_3.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("금");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_4, 0, SpringLayout.NORTH, lblNewLabel);
		lblNewLabel_4.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_4.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("토");
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_4, -34, SpringLayout.WEST, lblNewLabel_5);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_5, 0, SpringLayout.NORTH, lblNewLabel);
		lblNewLabel_5.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_5.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("일");
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_5, -36, SpringLayout.WEST, lblNewLabel_6);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_6, 18, SpringLayout.SOUTH, controlsPanel);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_6, -21, SpringLayout.EAST, getContentPane());
		lblNewLabel_6.setForeground(Color.LIGHT_GRAY);
		lblNewLabel_6.setFont(new Font("한컴산뜻돋움", Font.PLAIN, 14));
		getContentPane().add(lblNewLabel_6);

		pack(); // 컴포넌트들을 적절한 크기로 정렬
		setLocationRelativeTo(null); // 화면 중앙에 표시

	}

	public void displayCalendar() {
		YearMonth yearMonth = YearMonth.of(currentYear, currentMonth + 1);// 현재 연도와 월로 YearMonth 객체 생성

		// 캘린더 내용을 변경하기 전에 현재 화면의 내용을 저장합니다.
		calendarPanel.removeAll(); // 기존 캘린더 삭제

		switch (yearMonth.getMonth()) {
		case JANUARY:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "1월");
			break;
		case FEBRUARY:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "2월");
			break;
		case MARCH:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "3월");
			break;
		case APRIL:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "4월");
			break;
		case MAY:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "5월");
			break;
		case JUNE:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "6월");
			break;
		case JULY:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "7월");
			break;
		case AUGUST:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "8월");
			break;
		case SEPTEMBER:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "9월");
			break;
		case OCTOBER:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "10월");
			break;
		case NOVEMBER:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "11월");
			break;
		case DECEMBER:
			monthLabel.setText(yearMonth.getYear() + "년" + " " + "12월");
			break;
		default:
			break;
		}
		
		LocalDate firstDayOfMonth = yearMonth.atDay(1); // 현재 월의 첫째 날
		int daysInMonth = yearMonth.lengthOfMonth(); // 현재 월의 날 수
		startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

		for (int i = 1; i < startDayOfWeek; i++) { // 첫째 날이 시작되는 요일 전까지 공백 레이블 추가
			calendarPanel.add(new JLabel(""));
		}

		for (int day = 1; day <= daysInMonth; day++) { // 해당 월의 날짜만큼 패널에 추가
			dayPanel = new JPanel(new BorderLayout());
			dayPanel.setBackground(Color.WHITE);

			// 텍스트 레이블 추가
			dayLabel = new JLabel("", SwingConstants.CENTER);
			dayLabel.setText(String.valueOf(day));
			dayLabel.setBackground(Color.WHITE); // 배경색을 하얀색으로 설정
			dayLabel.setOpaque(true); // 불투명하게 설정
			dayLabel.setPreferredSize(new Dimension(50, 50));
			dayPanel.add(dayLabel, BorderLayout.CENTER); // 텍스트 레이블을 패널에 추가

			String imagePath = getImagePathForDate(loginId, LocalDate.of(currentYear, currentMonth + 1, day));
			ImageIcon imageIcon = new ImageIcon(imagePath);
			dayPanel.add(new JLabel(imageIcon), BorderLayout.NORTH);
	 
			// --- 라벨 추가
			JLabel kcalLabel = new JLabel("-", SwingConstants.CENTER);
			dayPanel.add(kcalLabel, BorderLayout.CENTER); // --- 라벨을 패널에 추가

			calendarPanel.add(dayPanel); // 날짜 패널을 캘린더 패널에 추가

		}
		calendarPanel.revalidate();
		calendarPanel.repaint();
	}
	// 오늘의 날짜 이미지 변경 메서드
	public void changeImageOfToday() {
		today = LocalDate.now();

		newImage = "Check" + today.getDayOfMonth() + ".png";
		newImageIcon = new ImageIcon(newImage);

		// 패널을 생성하면서 오늘의 날짜인지 확인하고 이미지를 변경
		for (Component component : calendarPanel.getComponents()) {
			if (component instanceof JPanel) {
				JPanel dayPanel = (JPanel) component;
				JLabel dayLabel = (JLabel) dayPanel.getComponent(0); // 첫 번째 컴포넌트는 날짜를 표시하는 레이블

				// 현재 반복 중인 날짜가 오늘의 날짜와 같은지 확인
				if (dayLabel.getText().equals(String.valueOf(today.getDayOfMonth()))) {
					// 이미지 레이블을 생성하여 이미지 추가
					JLabel imageLabel = new JLabel(newImageIcon);
					dayPanel.add(imageLabel, BorderLayout.NORTH); // 이미지 레이블을 패널에 추가하여 텍스트 레이블 위에 표시

					calendarPanel.revalidate();
					calendarPanel.repaint();
					saveImagePath(loginId, currentYear, currentMonth + 1, today.getDayOfMonth(), newImage);
					break; // 이미지를 추가한 후에는 더 이상 반복할 필요가 없으므로 반복문 종료
				}
			}
		}
	}
	// 변경된 이미지 db 저장
	public void saveImagePath(String userId, int year, int month, int day, String imagePath) {
		try (Connection conn = MySqlConnectionProvider.getConnection()) {
			String query = "INSERT INTO user_calendar (user_id, year, month, day, image_path, image_changed) VALUES (?, ?, ?, ?, ?, 1)";
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, loginId);
				statement.setInt(2, currentYear);
				statement.setInt(3, currentMonth + 1);
				statement.setInt(4, today.getDayOfMonth());
				statement.setString(5, newImage);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 저장된 이미지 db에서 불러옴
	private String getImagePathForDate(String userId, LocalDate date) {
        try (Connection conn = MySqlConnectionProvider.getConnection()) {
            String query = "SELECT image_path FROM user_calendar WHERE user_id = ? AND year = ? AND month = ? AND day = ? AND image_changed = 1";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, userId);
                statement.setInt(2, date.getYear());
                statement.setInt(3, date.getMonthValue());
                statement.setInt(4, date.getDayOfMonth());
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("image_path");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 기본 이미지 경로
        return "Date" + date.getDayOfMonth() + ".png";
    }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		});

	}
}
