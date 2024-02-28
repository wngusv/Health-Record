package _healthcare;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.general.DefaultPieDataset;

import dbutil.MySqlConnectionProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HalfRingChart extends JFrame {
	private double todayKcal;
	private double recommendedKcal;
	private static String loginId;
	private BufferedImage chartImage;

	public HalfRingChart(String loginId) {
		this.loginId = loginId;
		// 오늘의 섭취칼로리 - 소모칼로리 불러오기
		String query = "SELECT eat_kcal FROM all_kcal WHERE user_id = ? AND date = CURRENT_DATE() ORDER BY record_id DESC LIMIT 1";
		try (Connection conn = MySqlConnectionProvider.getConnection();
				PreparedStatement pst = conn.prepareStatement(query)) {

			pst.setString(1, loginId);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					todayKcal = rs.getDouble("eat_kcal");
					System.out.println(todayKcal);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 권장칼로리 불러오기
		String query2 = "SELECT recommended_kcal FROM users WHERE id = ?";
		try (Connection conn2 = MySqlConnectionProvider.getConnection();
				PreparedStatement pst2 = conn2.prepareStatement(query2)) {

			pst2.setString(1, loginId);

			try (ResultSet rs2 = pst2.executeQuery()) {
				if (rs2.next()) {
					recommendedKcal = rs2.getDouble("recommended_kcal");
					System.out.println(recommendedKcal);
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		setTitle("섭취량");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 250);

		// Define dataset
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		 if (todayKcal >= recommendedKcal) {
	            dataset.setValue("섭취칼로리", 100); // Set the ratio of consumed calories to 100
	            dataset.setValue("권장칼로리", 0);   // Set the ratio of recommended calories to 0
	        } else {
	            double consumedRatio = (todayKcal / recommendedKcal) * 100; // Calculate the ratio of consumed calories
	            double recommendedRatio = 100 - consumedRatio; // Calculate the ratio of recommended calories
	            dataset.setValue("섭취칼로리", consumedRatio); // Set the ratio of consumed calories
	            dataset.setValue("권장칼로리", recommendedRatio); // Set the ratio of recommended calories
	        }
		// Create chart
		JFreeChart chart = ChartFactory.createRingChart("", dataset, true, true, false);

		// Customize chart properties
		RingPlot plot = (RingPlot) chart.getPlot();

		// Set legend visibility through the chart
		chart.getLegend().setVisible(false);
		
		

		plot.setBackgroundPaint(null);
		plot.setOutlineVisible(false);
		plot.setShadowPaint(null);
		plot.setSectionDepth(0.5); // Set section depth to 0.5 for half-ring chart
		plot.setSeparatorsVisible(false);

		// Set section outline stroke for each section individually
		plot.setSectionOutlineStroke(new BasicStroke(2.0f));
		plot.setSectionOutlineStroke(new BasicStroke(0.0f));

		// Set section outline paint using Color objects
		plot.setSectionOutlinePaint(new Color(135, 184, 14));
		plot.setSectionPaint("섭취칼로리", new Color(166, 203, 74));
		plot.setSectionPaint("권장칼로리", new Color(215, 215, 215));

		// Set center text
		plot.setLabelGenerator(null); // Remove section labels
		plot.setInteriorGap(0.3); // Set interior gap to create a half-ring effect

		// Display chart in a panel
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(300, 150));
		add(chartPanel, BorderLayout.CENTER);
		
		 this.chartImage = chart.createBufferedImage(300, 150);
	}
	
	public BufferedImage getChartImage() {
		return chartImage;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			HalfRingChart example = new HalfRingChart(loginId);
			example.setVisible(true);
		});
	}
}
