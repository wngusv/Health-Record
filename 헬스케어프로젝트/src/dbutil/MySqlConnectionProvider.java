package dbutil; // 해당 메소드들을 외부에서 사용할 수 있게 패키지 사용 => 라이브러리로 배포를 하여 이 라이브러리를 포함하면 이 메소드들을 수행할 수 있음

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// 먼저 JDBC 참조 넣어주고
public class MySqlConnectionProvider {
//	private static final String URL = "jdbc:mysql://localhost:3306/my_db";
//	private static final String ID = "root";
//	private static final String PASSWORD = "root"; 
	// 주소, 아이디 ,비번이 코드에 적혀져있음. 변경이 있다면 수정하고 재배포해야되고 등등.ㅜ => 파일로 보관을 하자! 파일을 로드해서
	// 설정값을 사용하는 걸로 하자 => 클래스패스에(src안) file 생성( mysql.properties) properties란? 이름과 값을 쌍으로 저장가능)
	// 변경이 있다면 파일 안에 내용만 수정하면 끄읏~

	private static Properties properties = new Properties();

	static { // static: 이 클래스 로드가 일어나면 실행이 됨.
		System.out.println("프로퍼티 설정 파일을 읽습니다.");
		ClassLoader classLoader = MySqlConnectionProvider.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("mysql.properties");
		try (BufferedInputStream br = new BufferedInputStream(inputStream)) {
			properties.load(br);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("MySql JDBC 드라이버를 적재합니다. ");
		try {
//			Class.forName("com.mysql.cj.jdbc.Driver"); 
			Class.forName(properties.getProperty("DRIVER"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		//return DriverManager.getConnection(URL, ID, PASSWORD); // Connection 객체를 생성하여 반환하는 메소드
		return DriverManager.getConnection(properties.getProperty("URL")
				, properties.getProperty("ID")
				, properties.getProperty("PASSWORD"));
	}

	// 커넥션을 닫는 정적 메소드
	public static void closeConnection(Connection conn) {
		if (conn != null) { // 커넥션 객체를 전달받아서 널값이 아니면 닫아줌
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
