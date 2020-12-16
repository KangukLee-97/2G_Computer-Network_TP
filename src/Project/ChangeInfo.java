package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/***
 * <사용자 정보 변경 기능>
 * 별칭 변경 -> 자신의 Nickname을 DB에서 변경해줄것
 * 오늘의 한마디 변경 -> todaymessage DB에서 변경!
 */

public class ChangeInfo {
	
	// MySQL과의 연결을 위한 변수들
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	// 오늘의 한마디 변경
	public void ChangeTodayMsg(String ClientID, String msg) {
		Connection conn = null;   // MySQL과의 연결을 위한 변수
		Statement state = null;
		
		try {
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			state = conn.createStatement();
			
			String updateSQL = "UPDATE todaymessage SET todayMsg = '" + msg + "' where ID='" + ClientID + "';";  // 상태를 Offline으로 바꿔줌
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
	
	// 별칭 변경
	public void ChangeNickname(String ClientID, String nick) {
		Connection conn = null;   // MySQL과의 연결을 위한 변수
		Statement state = null;
		
		try {
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			state = conn.createStatement();
			
			String updateSQL1 = "UPDATE list SET Nickname = '" + nick + "' where ID='" + ClientID + "';";  // 상태를 Offline으로 바꿔줌
			String updateSQL2 = "UPDATE todaymessage SET Nickname = '" + nick + "' where ID='" + ClientID + "';";  // 상태를 Offline으로 바꿔줌
			state.executeUpdate(updateSQL1);
			state.executeUpdate(updateSQL2);
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