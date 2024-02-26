package _healthcare;


import javax.swing.JFrame;
import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;

import dbutil.MySqlConnectionProvider;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Graph extends JFrame {
	private String user_id;
    public Graph(String loginId) {
    	setTitle("몸무게 변화 추이");
    	this.user_id = loginId;
        //super("체중 기록 차트");

        // 차트 생성
        XYChart chart = new XYChart(800, 600);

        // 차트 설정
        chart.setTitle("체중 기록");
        chart.setXAxisTitle("날짜");
        chart.setYAxisTitle("체중 (kg)");

        // 데이터베이스에 연결하고 데이터 가져오기
        String sql = "SELECT date, weight FROM weightrecords WHERE user_id = ?";
        try (Connection conn = MySqlConnectionProvider.getConnection();
        		PreparedStatement pst = conn.prepareStatement(sql)) {
            //Statement statement = conn.createStatement();
        	pst.setString(1, user_id);
        	
        	try (ResultSet resultSet = pst.executeQuery()) {
                // 데이터 포인트를 담을 리스트 생성
                List<Date> dates = new ArrayList<>();
                List<Double> weights = new ArrayList<>();

                // 결과셋에서 데이터 읽기
                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");
                    double weight = resultSet.getDouble("weight");
                    dates.add(date);
                    weights.add(weight);
                }

                // 차트에 데이터 포인트 추가
                XYSeries series = chart.addSeries("체중", dates, weights);

                // 차트 패널을 프레임에 추가
                JPanel chartPanel = new XChartPanel<>(chart);
                getContentPane().add(chartPanel, BorderLayout.CENTER);

                // 프레임 속성 설정
                setSize(800, 600);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null); // 화면 중앙에 표시
                setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
