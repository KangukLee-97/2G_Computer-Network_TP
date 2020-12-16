package Project;

import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.net.*;

public class addFriend {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	static public void addFriend(String client_id, String friend_id) {
		try {
			Connection con = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			//Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			String create = "CREATE TABLE IF NOT EXISTS "
					+ "friendslist(client_id varChar(255),"
					+ "friend_id varChar(255),"
					+ "Connection varChar(20))";
			String sql = "INSERT INTO friendslist(client_id, friend_id) SELECT ?,? FROM DUAL WHERE NOT EXISTS (SELECT * FROM friendslist WHERE client_id=? and friend_id=?)";			
			String sql1 = "INSERT INTO friendslist(Connection) SELECT login_check.Connection FROM login_check WHERE login_check.ID=?";

			java.sql.PreparedStatement pstmt = con.prepareStatement(create);
			pstmt.execute();
			System.out.println("table created");

			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, client_id);
			pstmt.setString(2, friend_id);
			pstmt.setString(3, client_id);
			pstmt.setString(4, friend_id);
			pstmt.executeUpdate();
			System.out.println("id is inserted to friendslist");
			
			pstmt = con.prepareStatement(sql1);
			pstmt.setString(1, friend_id);
			pstmt.executeUpdate();
			System.out.println("state is inserted to friendslist");
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*public static void main(String[] args) {
		addFriend(String client_id,friend_id);
	}*/
}