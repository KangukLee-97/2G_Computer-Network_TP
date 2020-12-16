package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/***
 * LoginGUI에서 Login Button이 눌리면?
 * 1. MySQL에 연결
 * 2. 해당하는 ID가 DB에 있는지 확인한다 
 *     해당하는 ID가 없다? --> WRONGID
 *     해당하는 ID가 있다? --> 3번으로 넘어감.
 * 3. 검사한 ID에 해당하는 PW가 입력한 PW랑 같은지 확인
 *     같으면? --> SUCCESSLOGIN
 *     다르면? --> WRONGPASSWORD
 *     
 *     중복 로그인도 추가?
 */

public class Login {
	// MySQL과의 연결을 위한 변수들
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	public static int ClientLogin(String ClientID, String ClientPW) {
		final int WRONGID = 0;
		final int WRONGPASSWORD = 1;
		final int SUCCESSLOGIN = 2;
		final int DUPLICATE = 3;
		
		Connection conn = null;   // MySQL과의 연결을 위한 변수
		Statement state = null;
		ResultSet rset1 = null;   // MySQL Query 결과의 Set
		ResultSet rset2 = null;
		int LoginCheck = 0;   // 해당 메소드에서 반환할 검사결과
		
		try {
			// 1. MySQL과 연결
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			state = conn.createStatement();
			
			String sql = "SELECT * FROM login_check WHERE ID='" + ClientID + "';";
			rset2 = state.executeQuery(sql);
			while (rset2.next()) {
				String logcheck =rset2.getString("Connection");
				if (logcheck.equals("online")) {
					LoginCheck = DUPLICATE;
					return LoginCheck;
				}
			}
			
			// 2. 입력한 ID랑 비교하기 위해 DB의 ID들을 검색
			String searchSQL = "SELECT * FROM list WHERE ID=" + "'" + ClientID + "';";
			rset1 = state.executeQuery(searchSQL);   // Query 실행 (SELECT 구문)
			
			int success = 0;
			while (rset1.next()) {
				String srhID = rset1.getString("ID");
				String srhPW = rset1.getString("PW");
				
				if (srhID.equals(ClientID))   // 3. ID가 같을 때 PW도 비교
				{
					success = 1;
					if (BCrypt.checkpw(ClientPW, srhPW))   // PW도 같으면 --> 로그인 성공!
					{
						LoginCheck = SUCCESSLOGIN;
						String updateSQL = "UPDATE login_check SET Connection = 'online' where ID='" + ClientID + "';";   // 상태를 Online으로 바꿔줌
						state.executeUpdate(updateSQL);
						break;
					}
					else   // PW가 다르면 --> 비밀번호 오류!
					{
						LoginCheck = WRONGPASSWORD;
						break;
					}
				}
			}
			if (success == 0)
				LoginCheck = WRONGID;
		}
		catch (Exception e) {
			System.out.println("DataBase Connection Error : " + e.getMessage());
			System.exit(1);
		}
		finally {
			try {
				if (state != null) state.close();
				if (conn != null) conn.close();
			}
			catch (Exception e) {
				System.out.println("DataBase Closing Error : " + e.getMessage());
			}
		}
		
		return LoginCheck;
	}
	
}