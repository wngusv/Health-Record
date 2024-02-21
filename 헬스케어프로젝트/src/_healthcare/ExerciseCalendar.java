package _healthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ExerciseCalendar extends JFrame {
   private JLabel monthLabel; // 월을 표시하는 레이블
   private JPanel calendarPanel; // 캘린더를 표시하는 패널
   private int currentMonth; // 현재 월을 나타내는 변수
   private int currentYear; // 현재 연도를 나타내는 변수

   public ExerciseCalendar() {
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

      JPanel controlsPanel = new JPanel(new BorderLayout());
      controlsPanel.setBackground(Color.WHITE);
      controlsPanel.add(prevButton, BorderLayout.WEST); // 이전 달 버튼은 서쪽에 배치
      controlsPanel.add(monthLabel, BorderLayout.CENTER); // 월 표시 레이블은 가운데에 배치
      controlsPanel.add(nextButton, BorderLayout.EAST); // 다음 달 버튼은 동쪽에 배치

      currentMonth = LocalDate.now().getMonthValue() - 1; // 현재 월 설정
      currentYear = LocalDate.now().getYear(); // 현재 연도 설정
      displayCalendar(); // 초기 달력 표시

      getContentPane().add(controlsPanel, BorderLayout.NORTH); // 제어 패널을 프레임의 상단에 추가
      getContentPane().add(calendarPanel, BorderLayout.CENTER); // 캘린더 패널을 프레임의 중앙에 추가

      pack(); // 컴포넌트들을 적절한 크기로 정렬
      setLocationRelativeTo(null); // 화면 중앙에 표시
   }

   private void displayCalendar() {
      YearMonth yearMonth = YearMonth.of(currentYear, currentMonth + 1); // 현재 연도와 월로 YearMonth 객체 생성
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
      int startDayOfWeek = firstDayOfMonth.getDayOfWeek().getValue(); // 첫째 날의 요일 (1:월요일, 7:일요일)

      calendarPanel.removeAll(); // 기존 캘린더 삭제
      for (int i = 1; i < startDayOfWeek; i++) { // 첫째 날이 시작되는 요일 전까지 공백 레이블 추가
         calendarPanel.add(new JLabel(""));
      }

      for (int day = 1; day <= daysInMonth; day++) { // 해당 월의 날짜만큼 패널에 추가
         JPanel dayPanel = new JPanel(new BorderLayout());
         dayPanel.setBackground(Color.WHITE);

         // 이미지를 추가할 경우
         try {
            String imageName = "Defaultimage" + day + ".png";// 이미지 파일 경로 설정
            File imageFile = new File(imageName);
            BufferedImage image = ImageIO.read(imageFile); // 이미지 파일 읽기
            JLabel imageLabel = new JLabel(new ImageIcon(image), SwingConstants.CENTER); // 이미지를 표시할 레이블 생성
            imageLabel.setBackground(Color.WHITE); // 배경색을 하얀색으로 설정
            imageLabel.setOpaque(true); // 불투명하게 설정
            imageLabel.setPreferredSize(new Dimension(50, 50));
            dayPanel.add(imageLabel, BorderLayout.CENTER); // 이미지 레이블을 패널에 추가

         } catch (IOException e) {
            e.printStackTrace();
         }

         calendarPanel.add(dayPanel); // 날짜 패널을 캘린더 패널에 추가
      }

      revalidate(); // 컴포넌트들을 다시 배치하고 그리기 위해 호출
      repaint(); // 컴포넌트를 다시 그리도록 요청
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         ExerciseCalendar calendar = new ExerciseCalendar();
         calendar.setVisible(true); // 프레임 표시
      });
   }
}