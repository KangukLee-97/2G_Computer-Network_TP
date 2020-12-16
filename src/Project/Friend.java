package Project;

import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.net.*;

public class Friend {
	public static void friend_Info(String friendID) {
		String Friend_Id = null;
		String Friend_Name = null;
		String Friend_Nickname = null;
		
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			PreparedStatement ps1 = null;
			ResultSet rs1 = null;
			String sql1 = "SElECT ID, Name, Nickname FROM list WHERE ID=?";
			
			ps1 = con.prepareStatement(sql1);
			ps1.setString(1,  friendID);
			rs1 = ps1.executeQuery();
			
			while(rs1.next()) {
				Friend_Id = rs1.getString("ID");
				Friend_Name = rs1.getString("Name");
				Friend_Nickname = rs1.getString("Nickname");
			}
			con.close();
		}catch(SQLException sqex){
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
		
		JFrame a = new JFrame();
//		  BorderLayout f = new BorderLayout();
		  
		  Label ShowFrdName = new Label(" Name : "+ Friend_Name);
		  Label ShowFrdNickname = new Label(" Nickname : "+ Friend_Nickname);
		  Label ShowFrdID = new Label(" ID : "+ Friend_Id);
		  
		  a.setLayout(null);
		  
		  ShowFrdName.setBounds(30,50,100,30);
		  ShowFrdID.setBounds(30,100,100,30);
		  ShowFrdNickname.setBounds(30,150,200,30);

		  
		  a.add(ShowFrdName);
		  a.add(ShowFrdID);
		  a.add(ShowFrdNickname);
		  a.setResizable(false); // false 
		  a.setVisible(true);
		  a.setSize(300,300);
		  a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	}
	
	public static void main(String[] args) {
		friend_Info("hellopapa3");
	}
}
