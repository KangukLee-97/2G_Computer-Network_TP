package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/***
 * 메인 페이지에서 로그아웃 버튼을 누름 --> DataBase에서 Offline으로 상태를 변경시켜줌.
 * 이후 모든 프로그램을 종료한다.
 */

public class Logout {

	// MySQL과의 연결을 위한 변수들
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	public static void ClientLogout(String ClientID) {
		Connection conn = null;   // MySQL과의 연결을 위한 변수
		Statement state = null;
		
		try {
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			state = conn.createStatement();
			
			String updateSQL = "UPDATE login_check SET Connection = 'offline' where ID='" + ClientID + "';";  // 상태를 Offline으로 바꿔줌
			state.executeUpdate(updateSQL);
		}
		catch(Exception e) {
			System.out.println("DataBase Connection Error : " + e.getMessage());
			System.exit(1);
		}
		finally {
			try {
				if (state != null) state.close();
				if (conn != null) conn.close();
			}
			catch(Exception e) {
				System.out.println("DataBase Closing Error : " + e.getMessage());
			}
		}
	}
	
}