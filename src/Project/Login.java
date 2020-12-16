package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/***
 * LoginGUI���� Login Button�� ������?
 * 1. MySQL�� ����
 * 2. �ش��ϴ� ID�� DB�� �ִ��� Ȯ���Ѵ� 
 *     �ش��ϴ� ID�� ����? --> WRONGID
 *     �ش��ϴ� ID�� �ִ�? --> 3������ �Ѿ.
 * 3. �˻��� ID�� �ش��ϴ� PW�� �Է��� PW�� ������ Ȯ��
 *     ������? --> SUCCESSLOGIN
 *     �ٸ���? --> WRONGPASSWORD
 *     
 *     �ߺ� �α��ε� �߰�?
 */

public class Login {
	// MySQL���� ������ ���� ������
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	public static int ClientLogin(String ClientID, String ClientPW) {
		final int WRONGID = 0;
		final int WRONGPASSWORD = 1;
		final int SUCCESSLOGIN = 2;
		final int DUPLICATE = 3;
		
		Connection conn = null;   // MySQL���� ������ ���� ����
		Statement state = null;
		ResultSet rset1 = null;   // MySQL Query ����� Set
		ResultSet rset2 = null;
		int LoginCheck = 0;   // �ش� �޼ҵ忡�� ��ȯ�� �˻���
		
		try {
			// 1. MySQL�� ����
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
			
			// 2. �Է��� ID�� ���ϱ� ���� DB�� ID���� �˻�
			String searchSQL = "SELECT * FROM list WHERE ID=" + "'" + ClientID + "';";
			rset1 = state.executeQuery(searchSQL);   // Query ���� (SELECT ����)
			
			int success = 0;
			while (rset1.next()) {
				String srhID = rset1.getString("ID");
				String srhPW = rset1.getString("PW");
				
				if (srhID.equals(ClientID))   // 3. ID�� ���� �� PW�� ��
				{
					success = 1;
					if (BCrypt.checkpw(ClientPW, srhPW))   // PW�� ������ --> �α��� ����!
					{
						LoginCheck = SUCCESSLOGIN;
						String updateSQL = "UPDATE login_check SET Connection = 'online' where ID='" + ClientID + "';";   // ���¸� Online���� �ٲ���
						state.executeUpdate(updateSQL);
						break;
					}
					else   // PW�� �ٸ��� --> ��й�ȣ ����!
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