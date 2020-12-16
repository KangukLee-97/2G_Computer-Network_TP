package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/***
 * 메인페이지 이전 로그인 화면 구현 (로그인, 회원가입 기능)
 * 로그인 버튼 -> new Login();
 * 회원가입 버튼 -> new NewClient();
 */

public class LoginGUI {
	
	private JFrame LoginFrame;
	private JPanel WelcomePanel;
	private JLabel LoginLB, IdLB, PassLB;
	private JTextField IdField;
	private JPasswordField PassField;
	private JButton LoginButton, SignUpButton;
	
	public LoginGUI() {
		// Frame Setting
		LoginFrame = new JFrame();
		LoginFrame.setTitle("Login");
		LoginFrame.setSize(383, 339);
		LoginFrame.setLocationRelativeTo(null);   // 가운데에 창 띄우기
		LoginFrame.setResizable(false);
		LoginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel Setting
		WelcomePanel = new JPanel();
		WelcomePanel.setBounds(0, 0, 383, 339);
		WelcomePanel.setLayout(null);
		
		// Login Label
		LoginLB = new JLabel("Log-in");
		LoginLB.setBounds(157, 66, 50, 30);
		LoginLB.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		LoginLB.setBackground(Color.BLACK);
		WelcomePanel.add(LoginLB);
		
		// ID Label & TextField
		IdLB = new JLabel("ID : ");
		IdLB.setBounds(77, 117, 40, 20);
		IdLB.setFont(new Font("굴림", Font.BOLD, 13));
		WelcomePanel.add(IdLB);
		IdField = new JTextField(20);
		IdField.setBounds(107, 112, 130, 27);
		IdField.setToolTipText("Enter ID");
		WelcomePanel.add(IdField);
		
		// PW Label & PasswordField
		PassLB = new JLabel("PW : ");
		PassLB.setBounds(70, 150, 71, 16);
		PassLB.setFont(new Font("굴림", Font.BOLD, 13));
		WelcomePanel.add(PassLB);
		PassField = new JPasswordField(30);
		PassField.setBounds(107, 145, 130, 27);
		PassField.setToolTipText("Enter Password");
		WelcomePanel.add(PassField);
		
		// Login Button
		LoginButton = new JButton("LOGIN");
		LoginButton.setFont(new Font("굴림", Font.BOLD, 11));
		LoginButton.setBounds(250, 112, 79, 59);
		LoginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Login Button을 누르면? 
				// ID, PW 적는 칸의 데이터를 받아와서..
				// 잘못된 ID or PW? 아니면 로그인 성공!
				String ClientID = IdField.getText();
				char[] ps = PassField.getPassword();
				String ClientPW = new String(ps);
				
				Login log = new Login();
				int res = log.ClientLogin(ClientID, ClientPW); 
				
				if (res == 0) {   // 아이디가 잘못된 경우
					JOptionPane.showMessageDialog(null, "잘못된 아이디 입니다!");
					IdField.setText("");
					PassField.setText("");
				}
				else if (res == 1) {   // 아이디는 맞는데 비밀번호가 잘못된 경우
					JOptionPane.showMessageDialog(null, "잘못된 비밀번호 입니다!");
					PassField.setText("");
				}
				else if (res == 2)  // 로그인을 성공했기 때문에 Login창 닫고 새로운 창 Open!
				{
					JOptionPane.showMessageDialog(null, "로그인 성공!");
					ChatClient.RunClient();   // 로그인 성공일 때 서버와 연결 실행
					ChatClient.setClientName(ClientID);   // 서버에게 ID를 보낸다 
					LoginFrame.dispose();   // 로그인 창 닫기
					new MainPageGUI();   // 메인 페이지 열기 (수정 필요)
				}
				else if (res == 3) {  // 중복 접속
					JOptionPane.showMessageDialog(null, "중복 접속입니다!!");
					IdField.setText("");
					PassField.setText("");
				}
			}
		});
		WelcomePanel.add(LoginButton);
		
		// Sign-Up Button
		SignUpButton = new JButton("회원가입");
		SignUpButton.setBounds(130, 190, 85, 29);
		SignUpButton.setFont(new Font("굴림", Font.BOLD, 11));
		SignUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUpGUI();
			}
		});
		WelcomePanel.add(SignUpButton);
		
		LoginFrame.add(WelcomePanel);	
		LoginFrame.setVisible(true);
	}
}

class SignUpGUI {
	
	JFrame SignUpFrame;
	JPanel SignUpPanel;
	JLabel SignLB, NewIdLB, NewPassLB;
	JLabel NameLB, NickLB, GenderLB, EmailLB, BirthLB, BirthExpLB, NoteLB;
	JTextField NewIdField, NameField, NickField, EmailField, BirthField;
	JPasswordField NewPassField;
	JComboBox GenderBox;
	JTextArea NoteArea;
	JButton SignUpButton, CheckDupID;
	
	ArrayList<String> InfoList = new ArrayList<>();
	
	public SignUpGUI() {
		// Frame Setting 
		SignUpFrame = new JFrame();
		SignUpFrame.setTitle("Sign-up");
		SignUpFrame.setSize(500, 500);
		SignUpFrame.setLocationRelativeTo(null);
		SignUpFrame.setResizable(false);
		
		// Panel Setting
		SignUpPanel = new JPanel();
		SignUpPanel.setBackground(Color.WHITE);
		SignUpPanel.setBounds(0, 0, 500, 500);
		SignUpPanel.setLayout(null);
		
		// Sign-up Label 
		SignLB = new JLabel("SIGN UP");
		SignLB.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		SignLB.setBounds(205, 10, 70, 50);
		SignUpPanel.add(SignLB);
		
		// ID
		NewIdLB = new JLabel("ID");
		NewIdLB.setFont(new Font("굴림", Font.BOLD, 14));
		NewIdLB.setBounds(135, 80, 63, 23);
		SignUpPanel.add(NewIdLB);
		NewIdField = new JTextField(20);
		NewIdField.setBounds(165, 79, 170, 26);
		SignUpPanel.add(NewIdField);
		
		// ID Duplicate check (아이디 중복 검사)
		CheckDupID = new JButton("중복확인");
		CheckDupID.setFont(new Font("굴림", Font.BOLD, 12));
		CheckDupID.setBounds(355, 79, 90, 26);
		CheckDupID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String NewID = NewIdField.getText();
				int res = CheckID(NewID);
				
				if (res == 0)
					JOptionPane.showMessageDialog(null, "사용 가능한 ID 입니다!");
				else if (res == 1)
					JOptionPane.showMessageDialog(null, "이미 사용중인 ID 입니다!");
			}
		});
		SignUpPanel.add(CheckDupID);
		
		// PW 
		NewPassLB = new JLabel("PW");
		NewPassLB.setFont(new Font("굴림", Font.BOLD, 14));
		NewPassLB.setBounds(127, 120, 63, 23);
		SignUpPanel.add(NewPassLB);
		NewPassField = new JPasswordField(30);
		NewPassField.setBounds(165, 119, 170, 26);
		SignUpPanel.add(NewPassField);
		
		// Name 
		NameLB = new JLabel("Name");
		NameLB.setFont(new Font("굴림", Font.BOLD, 14));
		NameLB.setBounds(110, 160, 63, 23);
		SignUpPanel.add(NameLB);
		NameField = new JTextField(10);
		NameField.setBounds(165, 159, 170, 26);
		SignUpPanel.add(NameField);
		
		// Nickname
		NickLB = new JLabel("Nickname");
		NickLB.setFont(new Font("굴림", Font.BOLD, 14));
		NickLB.setBounds(83, 200, 70, 23);
		SignUpPanel.add(NickLB);
		NickField = new JTextField(10);
		NickField.setBounds(165, 199, 170, 26);
		SignUpPanel.add(NickField);
		
		// Gender
		GenderLB = new JLabel("Gender");
		GenderLB.setFont(new Font("굴림", Font.BOLD, 14));
		GenderLB.setBounds(100, 240, 63, 23);
		SignUpPanel.add(GenderLB);
		GenderBox = new JComboBox(new String[] {"Male","Female"});
		GenderBox.setBounds(165, 239, 170, 26);
		SignUpPanel.add(GenderBox);
		
		// Email
		EmailLB = new JLabel("E-mail");
		EmailLB.setFont(new Font("굴림", Font.BOLD, 14));
		EmailLB.setBounds(103, 280, 63, 23);
		SignUpPanel.add(EmailLB);
		EmailField = new JTextField(50);
		EmailField.setBounds(165, 279, 170, 26);
		SignUpPanel.add(EmailField);
		
		// Birthday
		BirthLB = new JLabel("Birthday");
		BirthLB.setFont(new Font("굴림", Font.BOLD, 14));
		BirthLB.setBounds(95, 320, 63, 23);
		SignUpPanel.add(BirthLB);
		BirthField = new JTextField(15);
		BirthField.setBounds(165, 319, 170, 26);
		SignUpPanel.add(BirthField);
		
		// Birthday 예시 보여주는 Label
		BirthExpLB = new JLabel("(예:1997-10-06)");
		BirthExpLB.setFont(new Font("굴림", Font.BOLD, 13));
		BirthExpLB.setBounds(340, 323, 110, 23);
		SignUpPanel.add(BirthExpLB);
		
		// Note
		NoteLB = new JLabel("Note");
		NoteLB.setFont(new Font("굴림", Font.BOLD, 14));
		NoteLB.setBounds(117, 360, 63, 23);
		SignUpPanel.add(NoteLB);
		NoteArea = new JTextArea();
		NoteArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		NoteArea.setBounds(165, 359, 170, 94);
		SignUpPanel.add(NoteArea);
		
		// Sign-up Button
		SignUpButton = new JButton("Submit");
		SignUpButton.setBounds(365, 400, 80, 50);
		if (CheckID(NewIdField.getText()) == 1)    // ID가 중복이면?
		{
			SignUpButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "다시한번 ID를 체크해주세요!");
				}
			});
		}
		else   // 중복이 아니면(정상)
		{
			SignUpButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						InfoList.add(0, NewIdField.getText());   // ID
						char[] ps = NewPassField.getPassword();
						String password = new String(ps);
						InfoList.add(1, password);   // PW
						InfoList.add(2, NameField.getText());   // Name
						InfoList.add(3, NickField.getText());   // Nickname
						InfoList.add(4, GenderBox.getSelectedItem().toString());   // Gender
						InfoList.add(5, EmailField.getText());   // Email
						InfoList.add(6, BirthField.getText());   // Birthday
						InfoList.add(7, NoteArea.getText());   // NoteArea
						new NewClient(InfoList);   // MySQL에 Data 추가
						JOptionPane.showMessageDialog(null, "회원가입 성공!");
						SignUpFrame.dispose();
				}
			});
		}
		SignUpPanel.add(SignUpButton);
		
		SignUpFrame.add(SignUpPanel);
		SignUpFrame.setVisible(true);
		
	}
	
	// 회원가입 할 때 ID가 중복인지 아닌지 확인! --> Primary Key
	public int CheckID(String NewID) {
		final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
		final String USER_NAME = "root";
		final String USER_PASSWORD = "rkddnrdl1863^^";
		
		final int YES = 1;
		final int NO = 0;
		
		Connection conn = null;
		Statement state = null;
		ResultSet rset = null;
		int DupCheck = 0;   // 해당 ID가 중복인지 아닌지 확인결과
		
		try {
			conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
			//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
			state = conn.createStatement();
			
			String checkSQL = "SELECT * FROM list WHERE ID=" + "'" + NewID + "';";
			rset = state.executeQuery(checkSQL);
			
			int duplicate = -1;
			while (rset.next()) {
				String checkID = rset.getString("ID");
				if (checkID.equals(NewID))
				{
					duplicate = 1;
					break;
				}
				else
					duplicate = 0;
			}
			
			if (duplicate == 0)
				DupCheck = NO;
			else if (duplicate == 1)
				DupCheck = YES;
		}
		catch (Exception e) {
			System.out.println("DataBase Connection Error : " + e.getMessage());
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
		
		return DupCheck;
	}
	
}