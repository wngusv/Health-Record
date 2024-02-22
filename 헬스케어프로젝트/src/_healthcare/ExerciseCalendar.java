package _healthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class ExerciseCalendar extends JFrame {
	private JLabel monthLabel; // 월을 표시하는 레이블
	private JPanel calendarPanel; // 캘린더를 표시하는 패널
	private int currentMonth; // 현재 월을 나타내는 변수
	private int currentYear; // 현재 연도를 나타내는 변수
	protected int startDayOfWeek;
	public String loginId;

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
		sl_controlsPanel.putConstraint(SpringLayout.WEST, monthLabel, 142, SpringLayout.WEST, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.EAST, monthLabel, -147, SpringLayout.EAST, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, nextButton, 0, SpringLayout.NORTH, prevButton);
		sl_controlsPanel.putConstraint(SpringLayout.WEST, nextButton, 6, SpringLayout.EAST, monthLabel);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, prevButton, 0, SpringLayout.NORTH, monthLabel);
		sl_controlsPanel.putConstraint(SpringLayout.EAST, prevButton, -6, SpringLayout.WEST, monthLabel);
		sl_controlsPanel.putConstraint(SpringLayout.NORTH, monthLabel, 0, SpringLayout.NORTH, controlsPanel);
		sl_controlsPanel.putConstraint(SpringLayout.SOUTH, monthLabel, -7, SpringLayout.SOUTH, controlsPanel);
		controlsPanel.setLayout(sl_controlsPanel);
		controlsPanel.add(prevButton); // 이전 달 버튼은 서쪽에 배치
		controlsPanel.add(monthLabel); // 월 표시 레이블은 가운데에 배치
		controlsPanel.add(nextButton); // 다음 달 버튼은 동쪽에 배치

		currentMonth = LocalDate.now().getMonthValue() - 1; // 현재 월 설정
		currentYear = LocalDate.now().getYear(); // 현재 연도 설정
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

		pack(); // 컴포넌트들을 적절한 크기로 정렬
		setLocationRelativeTo(null); // 화면 중앙에 표시
	}

	private void displayCalendar() {
		YearMonth yearMonth = YearMonth.of(currentYear, currentMonth + 1);//  현재 연도와 월로 YearMonth 객체 생성
		switch (yearMonth.getMonth()) {
		case JANUARY:
			monthLabel.setText("1월" + " " + yearMonth.getYear() + "년");
			break;
		case FEBRUARY:
			monthLabel.setText("2월" + " " + yearMonth.getYear() + "년");
			break;
		case MARCH:
			monthLabel.setText("3월" + " " + yearMonth.getYear() + "년");
			break;
		case APRIL:
			monthLabel.setText("4월" + " " + yearMonth.getYear() + "년");
			break;
		case MAY:
			monthLabel.setText("5월" + " " + yearMonth.getYear() + "년");
			break;
		case JUNE:
			monthLabel.setText("6월" + " " + yearMonth.getYear() + "년");
			break;
		case JULY:
			monthLabel.setText("7월" + " " + yearMonth.getYear() + "년");
			break;
		case AUGUST:
			monthLabel.setText("8월" + " " + yearMonth.getYear() + "년");
			break;
		case SEPTEMBER:
			monthLabel.setText("9월" + " " + yearMonth.getYear() + "년");
			break;
		case OCTOBER:
			monthLabel.setText("10월" + " " + yearMonth.getYear() + "년");
			break;
		case NOVEMBER:
			monthLabel.setText("11월" + " " + yearMonth.getYear() + "년");
			break;
		case DECEMBER:
			monthLabel.setText("12월" + " " + yearMonth.getYear() + "년");
			break;
		default:
			break;
		}
		LocalDate firstDayOfMonth = yearMonth.atDay(1); // 현재 월의 첫째 날
		int daysInMonth = yearMonth.lengthOfMonth(); // 현재 월의 날 수
		startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

		calendarPanel.removeAll(); // 기존 캘린더 삭제
		for (int i = 1; i < startDayOfWeek; i++) { // 첫째 날이 시작되는 요일 전까지 공백 레이블 추가
			calendarPanel.add(new JLabel(""));
		}

		for (int day = 1; day <= daysInMonth; day++) { // 해당 월의 날짜만큼 패널에 추가
		    JPanel dayPanel = new JPanel(new BorderLayout());
		    dayPanel.setBackground(Color.WHITE);
		    dayPanel.setBorder(new LineBorder(Color.BLACK));
		    JLabel dayLabel = new JLabel(String.valueOf(day), SwingConstants.CENTER); // 텍스트 레이블 생성
		    dayLabel.setBackground(Color.WHITE); // 배경색을 하얀색으로 설정
		    dayLabel.setOpaque(true); // 불투명하게 설정
		    dayLabel.setPreferredSize(new Dimension(50, 50));
		    dayPanel.add(dayLabel, BorderLayout.CENTER); // 텍스트 레이블을 패널에 추가

		    calendarPanel.add(dayPanel); // 날짜 패널을 캘린더 패널에 추가
		}
		revalidate(); // 컴포넌트들을 다시 배치하고 그리기 위해 호출
		repaint(); // 컴포넌트를 다시 그리도록 요청
	}

//	public void updateCalendarImage(int dayOfMonth) {
//		try {
//			String imageName = "Date" + dayOfMonth + ".png"; // 이미지 파일 경로 설정
//			File imageFile = new File(imageName);
//			BufferedImage image = ImageIO.read(imageFile); // 이미지 파일 읽기
//			System.out.println(imageName);
//			System.out.println(image);
//			// 해당 날짜의 패널을 찾아 이미지를 변경
//			JPanel dayPanel = (JPanel) calendarPanel.getComponent((dayOfMonth - 1) + startDayOfWeek - 1); // dayOfMonth를
//																											// 0부터 시작하도록
//			JLabel imageLabel = (JLabel) dayPanel.getComponent(0); // 이미지가 있는 첫 번째 컴포넌트 가져오기
//			imageLabel.setIcon(new ImageIcon(image)); // 이미지 변경
//			imageLabel.setBackground(Color.WHITE); // 배경색을 하얀색으로 설정
//			imageLabel.setOpaque(true); // 불투명하게 설정
//			imageLabel.setPreferredSize(new Dimension(50, 50));
//			dayPanel.add(imageLabel, BorderLayout.CENTER); // 이미지 레이블을 패널에 추가
//			System.out.println(dayPanel);
//		System.out.println(imageLabel);
//			revalidate(); // 컴포넌트들을 다시 배치하고 그리기 위해 호출
//			repaint(); // 컴포넌트를 다시 그리도록 요청
//			System.out.println(calendarPanel.getComponent((dayOfMonth - 1) + startDayOfWeek - 1)); // 확인을 위한 출력
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		});
		// SwingUtilities.invokeLater()는 스윙(Swing) GUI 업데이트 작업을 안전하게 처리하기 위한 방법 중 하나입니다. Swing은 단일 스레드 모델(single-threaded model)을 사용하기 때문에, GUI 컴포넌트에 대한 작업은 주로 이벤트 디스패치 스레드(Event Dispatch Thread)에서 처리됩니다.
// 따라서 다른 스레드에서 GUI를 변경해야 하는 경우, SwingUtilities.invokeLater()를 사용하여 해당 작업을 이벤트 디스패치 스레드로 보내어 안전하게 처리할 수 있습니다. 이것은 다른 스레드에서 GUI 업데이트를 수행할 때 발생할 수 있는 동시성 문제를 방지하는데 도움이 됩니다.
// 즉, 위의 코드는 빈 람다식을 invokeLater()에 전달하고 있으므로, 이 안에 GUI 관련 작업을 넣으면 됩니다. 이렇게 하면 해당 작업이 이벤트 디스패치 스레드에서 실행됩니다.
	}
}
