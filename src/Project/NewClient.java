package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/***
 * 회원가입에서 Submit 버튼을 누르면 MySQL Server에 Data 삽입
 * DataBase : list, login_check
 * 회원가입 -> 새로운 Client 생성 -> list에 추가 -> login_check에 offline으로 setting
 */

public class NewClient {

	// MySQL과 연동을 위한 변수들
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	public NewClient(ArrayList list) {
		Connection conn = null;
		Statement state = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			state = conn.createStatement();
			
			// 가입된 사람들 리스트
			String insertSQL1 = "INSERT INTO list(ID, PW, Name, Nickname, Gender, Email, Birthday, Note) " + 
					"VALUES(" + "'" + list.get(0) + "', " + "'" + BCrypt.hashpw(list.get(1).toString(), BCrypt.gensalt(12)) + "', " + "'" + list.get(2) + "', " +
					"'" + list.get(3) + "', " + "'" + list.get(4) + "', " + "'" + list.get(5) + "', " + "'" + 
					list.get(6) + "', " + "'" + list.get(7) + "');";
			
			// 로그인 상태
			String insertSQL2 = "INSERT INTO login_check(ID, Name, Connection) " + "VALUES(" + "'" + list.get(0) + "'," + "'"
			          + list.get(2) + "'," + "'" + "offline" + "');";
			
			// 오늘의 한마디
			String insertSQL3 = "INSERT INTO todaymessage(ID, Nickname, todayMsg) " + "VALUES(" + "'" + list.get(0) + "'," + "'"
			          + list.get(3) + "'," + "'" + "Nothing" + "');";
			
			state.executeUpdate(insertSQL1);   // Query 실행 (INSERT 구문)
			state.executeUpdate(insertSQL2);
			state.executeUpdate(insertSQL3);
		}
		catch (Exception e) {
			System.out.println("Database Connection Error : " + e.getMessage());
		}
		finally {
			try {
				if (state != null) state.close();
				if (conn != null) conn.close();
			}
			catch (Exception e) {
				System.out.println("Database Closing Error : " + e.getMessage());
			}
		}
	}	
	
}