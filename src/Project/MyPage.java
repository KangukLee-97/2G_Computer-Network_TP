package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/***
 * DB내에 있는 자신의 ID에 대한 정보들을 GUI로 통해서 보여줌.
 * 추가 기능으로, 정보 변경 가능!
 */

public class MyPage extends JFrame {
	
	// MySQL과의 연결을 위한 변수들
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
	private static final String USER_NAME = "root";
	private static final String USER_PASSWORD = "rkddnrdl1863^^";
	
	private JPanel MyPagePanel;
	private JLabel InfoLabel, IDLabel, PWLabel, NameLabel, NickLabel;
	private JLabel GenderLabel, EmailLabel, BirthLabel, NoteLabel;
	private JTextField PWField, NameField, NickField; 
	private JTextField GenderField, EmailField, BirthField;
	private JTextArea NoteField;
	private JButton EditBtn;
	
	public MyPage(String ClientID) {
		//======================= DB Connection =======================
		Connection conn1 = null;
		Statement state1 = null;
		ResultSet rset1 = null;
		
		String MyID = null;
		String MyPW = null;
		String MyName = null;
		String MyEmail = null;
		String MyNick = null;
		String MyGender = null;
		String MyBirth = null;
		String MyNote = null;
		
		try {
			//conn1 = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			conn1 = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			state1 = conn1.createStatement();
			
			String sql1 = "SELECT * FROM list WHERE ID = '" + ClientID + "';";
			rset1 = state1.executeQuery(sql1);
			
			while (rset1.next()) {
				MyID = rset1.getString("ID");
				MyPW = rset1.getString("PW");
				MyName = rset1.getString("Name");
				MyNick = rset1.getString("Nickname");
				MyGender = rset1.getString("Gender");
				MyEmail = rset1.getString("Email");
				MyBirth = rset1.getString("Birthday");
				MyNote = rset1.getString("Note");
			}
			
		}
		catch(Exception e) {
			System.out.println("DataBase Connection Error : " + e.getMessage());
			System.exit(1);
		}
		finally {
			try {
				if (state1 != null) state1.close();
				if (conn1 != null) conn1.close();
			}
			catch(Exception e) {
				System.out.println("DataBase Closing Error : " + e.getMessage());
			}
		}
		
		//======================= GUI  =======================
		// Frame Setting
		setTitle("MyPage-" + ClientID);
		setSize(400, 450);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		
		// 전체 패널 (정보들)
		MyPagePanel = new JPanel();
		MyPagePanel.setBounds(0, 0, 400, 450);
		MyPagePanel.setLayout(null);
		
		// <나의 정보>
		InfoLabel = new JLabel("<나의 정보>");
		InfoLabel.setBounds(20, 10, 100,30);
		InfoLabel.setFont(new Font("굴림", Font.BOLD, 14));
		MyPagePanel.add(InfoLabel);
		
		// ID
		IDLabel = new JLabel(" ID : " + ClientID);
		IDLabel.setBounds(78, 50, 100, 20);
		IDLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(IDLabel);
		
		// PW (PW부터는 수정가능)
		PWLabel = new JLabel(" Password : ");
		PWLabel.setBounds(30, 80, 100, 20);
		PWLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(PWLabel);
		
		PWField = new JTextField(MyPW);
		PWField.setBounds(110, 75, 100, 30);
		MyPagePanel.add(PWField);
		
		// Name
		NameLabel = new JLabel(" Name : ");
		NameLabel.setBounds(56, 110, 100, 20);
		NameLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(NameLabel);
		
		NameField = new JTextField(MyName);
		NameField.setBounds(110, 105, 100, 30);
		//NameField.setEditable(false);
		NameField.setBackground(Color.WHITE);
		NameField.disable();
		MyPagePanel.add(NameField);
		
		// Nickname (별칭 변경에서 수정)
		NickLabel = new JLabel(" Nickname : ");
		NickLabel.setBounds(30, 140, 100, 20);
		NickLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(NickLabel);
		
		NickField = new JTextField(MyNick);
		NickField.setBounds(110, 135, 100, 30);
		NickField.disable();
		MyPagePanel.add(NickField);
		
		// Gender (Gender는 변경 불가능)
		GenderLabel = new JLabel(" Gender : ");
		GenderLabel.setBounds(46, 170, 100, 20);
		GenderLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(GenderLabel);
		
		GenderField = new JTextField(MyGender);
		GenderField.setBounds(110, 165, 100, 30);
		//GenderField.setEditable(false);
		GenderField.setBackground(Color.WHITE);
		GenderField.disable();
		MyPagePanel.add(GenderField);
		
		// Email
		EmailLabel = new JLabel(" E-mail : ");
		EmailLabel.setBounds(50, 200, 100, 20);
		EmailLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(EmailLabel);
		
		EmailField = new JTextField(MyEmail);
		EmailField.setBounds(110, 195, 150, 30);
		MyPagePanel.add(EmailField);
		
		// Birthday (수정 불가능)
		BirthLabel = new JLabel(" Birthday : ");
		BirthLabel.setBounds(40, 230, 100, 20);
		BirthLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(BirthLabel);
		
		BirthField = new JTextField(MyBirth);
		BirthField.setBounds(110, 225, 100, 30);
		BirthField.disable();
		MyPagePanel.add(BirthField);
		
		// Note
		NoteLabel = new JLabel(" Note : ");
		NoteLabel.setBounds(62, 260, 100, 20);
		NoteLabel.setFont(new Font("굴림", Font.BOLD, 13));
		MyPagePanel.add(NoteLabel);
		
		NoteField = new JTextArea(MyNote);
		NoteField.setBounds(110, 260, 140, 100);
		NoteField.setBorder(new LineBorder(Color.BLACK));
		MyPagePanel.add(NoteField);
		
		// 확인 버튼
		EditBtn = new JButton("확인");
		EditBtn.setBackground(new Color(230, 110, 100));
		EditBtn.setBounds(280, 370, 90, 30);
		EditBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 데이터 Update 해줌
				String Info_PW = PWField.getText();
				String Info_Email = EmailField.getText();
				String Info_Note = NoteField.getText();
				
				Connection conn2 = null;
				Statement state2 = null;
				
				try {
					//conn2 = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
					conn2 = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
					state2 = conn2.createStatement();
					
					String sql2_1 = "UPDATE list SET PW='" + Info_PW + "', Email='" + Info_Email + 
							"', Note='" + Info_Note + "' WHERE ID='" + ClientID +"';";
					state2.executeUpdate(sql2_1);
				}
				catch(Exception e2) {
					System.out.println("DataBase Connection Error : " + e2.getMessage());
					System.exit(1);
				}
				finally {
					try {
						if (state2 != null) state2.close();
						if (conn2 != null) conn2.close();
					}
					catch(Exception e2) {
						System.out.println("DataBase Closing Error : " + e2.getMessage());
					}
				}
				JOptionPane.showMessageDialog(null, "저장되었습니다!");  // 팝업창 띄워주기
				dispose();  // 해당 프레임 끄기
			}
		});
		MyPagePanel.add(EditBtn);
		
		add(MyPagePanel);
		setVisible(true);
	}
	
}