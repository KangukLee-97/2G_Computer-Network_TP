package Project;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.*;
public class FriendListMain{

	private static String[] Friend_ID= new String[100]; 
	private static String Login_ID = ChatClient.getClientName();
	private static JList FriendList;
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	public static String[] getFriendListMain() {
		int f_count = 0;
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			//con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select * from friendslist where client_id=?;";
			ps = con.prepareStatement(sql);	
			ps.setString(1, Login_ID); 
			rs = ps.executeQuery();

			while (rs.next()) {
				String str = rs.getString("friend_id");
				String state = rs.getString("Connection");
				System.out.println(str+" | "+state);
				
				Friend_ID[f_count] = str;		
				f_count++;
			}			
		}catch (SQLException sqex) {
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		} 
		
		return Friend_ID;
	}
	
	/*
	public static void main(String[] args) {
		addFriend fr = new addFriend();
		fr.addFriend("hellopapa3","hellopapa4");
		FriendListMain frd = new FriendListMain();
		JFrame frame = new JFrame();
		JList friends = new JList(getFriendListMain());
		frame.setSize(300,300);
		frame.add(friends);
		frame.setVisible(true);
		friends.setVisible(true);
		
	}
	*/
}