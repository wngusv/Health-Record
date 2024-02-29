package _healthcare;

import java.sql.SQLException;

import dbutil.MySqlConnectionProvider;

public class TEST {
	public static void main(String[] args) {
		try {
			MySqlConnectionProvider.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
