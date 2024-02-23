package _healthcare;
/*
 
package _healthcare;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import dbutil.MySqlConnectionProvider;

// 1. 메이븐 추가
public class FoodCalorieFile {
	
	public static void output(String excel) { // 엑셀파일들을 csv 파일형식으로 만들어주는 메소드
		InputStream is = null;
		Workbook workbook = null;
		try {
			is = new FileInputStream(excel);

			// Apache POI 라이브러리를 사용하여 워크북 생성
			// Workbook 클래스는 Apache POI 라이브러리에서 엑셀 파일 전체를 나타내는 인터페이스
			workbook = WorkbookFactory.create(is);

			// 첫 번째 시트 선택
			Sheet sheet = workbook.getSheetAt(0);

			// 파일로 저장할 csv파일 생성(csv파일: 데이터를 쉼표(,)로 구분하여 나열한 텍스트 파일 형식)
			File csvFile = new File("D:\\KimGayoung\\식품영양성분\\output.csv");
			try (Writer writer = new BufferedWriter(new FileWriter(csvFile, true))) { // true: 내용을 추가함(덮어쓰기x)
				// 각 행을 순회하면서 셀의 내용을 CSV 파일에 쓰기
				for (Row row : sheet) {
					StringBuilder line = new StringBuilder();
					for (Cell cell : row) {
						line.append(cell.toString()).append(",");
					}
					// 콤마로 구분된 한 행의 데이터를 CSV 파일에 쓰기
					writer.write(line.toString());
					writer.write(System.lineSeparator());
				}
			}

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
/*
		File file = new File("D:\\KimGayoung\\식품영양성분\\output.csv");
		if (!file.exists()) {
			String food = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_음식_20240216.xlsx";
			String food_1 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품1.xlsx";
			String food_2 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품2.xlsx";
			String food_3 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품3.xlsx";
			String food_4 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품4.xlsx";
			String food_5 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품5.xlsx";
			String food_6 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품6.xlsx";
			String food_7 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품7.xlsx";
			String food_8 = "D:\\KimGayoung\\식품영양성분\\통합 식품영양성분DB_가공식품8.xlsx";

			output(food);
			output(food_1);
			output(food_2);
			output(food_3);
			output(food_4);
			output(food_5);
			output(food_6);
			output(food_7);
			output(food_8);
			// 만들어진 csv파일에서 수동으로 맨 위에 있는 식품명 행빼고 다 지웠어요..! 근데 이거 다 필요없음.. 따로 수정한 output.csv파일로 디비에 다 옮기면 됨.
		} else {
			System.out.println("파일이 이미 생성되어 있음.");
		}

		String filePath = "D:\\KimGayoung\\식품영양성분\\output.csv";
		Connection conn = null;

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			conn = MySqlConnectionProvider.getConnection();

			// 디비에 테이블을 만들어 주기
			String insert = "INSERT INTO foodCalories (foodName, oneServing, unit, calories) VALUES (?,?,?,?)";

			// 첫 행은 헤더로 생각하고 건너뜀
			reader.readLine();

			// 각 행을 읽어와서 데이터베이스에 추가
			try (PreparedStatement pst = conn.prepareStatement(insert)) {
					 Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
		                for (CSVRecord record : records) {
		                    // CSVRecord의 값은 문자열로 가져옵니다.
		                    
		                	String foodName = record.get(0);
		                    double oneServing = Double.parseDouble(record.get(1));
		                    String unit = record.get(2);
		                    double calories = Double.parseDouble(record.get(3));

		                    // PreparedStatement를 사용하여 데이터베이스에 추가
		                    pst.setString(1, foodName);
		                    pst.setDouble(2, oneServing);
		                    pst.setString(3, unit);
		                    pst.setDouble(4, calories);

		                    pst.executeUpdate();
		                    
				}System.out.println("성공");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
*/


