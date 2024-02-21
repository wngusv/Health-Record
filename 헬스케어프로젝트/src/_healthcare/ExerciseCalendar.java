package _healthcare;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

//import com.toedter.calendar.IDayChooser;
import com.toedter.calendar.JCalendar;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

public class ExerciseCalendar extends JFrame {

    private JPanel contentPane;
    private JCalendar calendar;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ExerciseCalendar frame = new ExerciseCalendar();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
                
    public ExerciseCalendar() {
        setTitle("Exercise Calendar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 641, 477);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        calendar = new JCalendar();
        calendar.getYearChooser().getSpinner().setFont(new Font("맑은 고딕", Font.BOLD, 12));
        calendar.getMonthChooser().getComboBox().setFont(new Font("맑은 고딕", Font.BOLD, 12));
        calendar.getDayChooser().setDecorationBackgroundColor(SystemColor.menu);
        calendar.getDayChooser().getDayPanel().setBackground(Color.WHITE);
        calendar.getDayChooser().addPropertyChangeListener("day", e -> highlightSelectedDate());
        contentPane.add(calendar, BorderLayout.CENTER);

        highlightSelectedDate(); // Initially highlight the selected date
    }

    // Highlight the selected date with an image
    private void highlightSelectedDate() {
        Date selectedDate = calendar.getDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);

        // Example: Check if the selected date matches a specific date and then add an image
        if (isExerciseDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))) {
            JLabel label = new JLabel(new ImageIcon("image/circle.png")); // Path to your image
            label.setHorizontalAlignment(SwingConstants.CENTER);
           // calendar.getDayChooser().add(label, IDayChooser.DAYS_OF_WEEK * (cal.get(Calendar.WEEK_OF_MONTH) - 1) + cal.get(Calendar.DAY_OF_WEEK));
        }
    }

    // Example method to check if the date has exercise records
    private boolean isExerciseDate(int year, int month, int day) {
        // Implement your logic to check if there are exercise records for the given date
        // For this example, let's assume that exercise records exist for every Monday
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }
}
