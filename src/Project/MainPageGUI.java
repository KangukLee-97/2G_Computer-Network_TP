package Project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainPageGUI extends JFrame implements WindowListener {

   FriendListMain FLM = new FriendListMain();
   private JPanel NorthPanel, CenterPanel;
   private JLabel NickLB, TodayLB, OnlineLB, OfflineLB;
   private JButton modifyNick, modifyTodayMsg, addFriend, MyPage, Logout;
   private JList OnlineFriends, OfflineFriends;
   
   // ���������� �κ�
   private JPanel SouthPanel;
   private JLabel DataLB, RealDataLB;

   public MainPageGUI() {
      // Frame Setting
      setTitle(ChatClient.getClientName());
      setSize(400, 700);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      
      // �� ���κ� �г� (�г���, ������ �޼���)
      NorthPanel = new JPanel();
      NorthPanel.setBounds(0, 0, 400, 125);
      NorthPanel.setBackground(Color.PINK);
      NorthPanel.setLayout(null);
      
      // �߰� �г� (�¶��� / �������� ģ����)
      CenterPanel = new JPanel();
      CenterPanel.setBounds(0, 120, 400, 410);
      CenterPanel.setLayout(null);
      
      // �¶��� �� + List (Scroll)
      OnlineLB = new JLabel("Online");
      OnlineLB.setBounds(3, 5, 40, 30);
      OnlineLB.setFont(new Font("����", Font.BOLD, 12));
      OnlineLB.setBackground(Color.BLACK);
      CenterPanel.add(OnlineLB);
      
      OnlineFriends = new JList<String>();
      OnlineFriends.setBounds(3, 30, 378, 170);
      OnlineFriends.setSelectionBackground(Color.PINK);
      OnlineFriends.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      //OnlineFriends.setVisibleRowCount(300);
      //OnlineFriends.addMouseListener(this);
      OnlineFriends.setBorder(new LineBorder(Color.BLACK));
      CenterPanel.add(OnlineFriends);
      
      // �г��� ��
      String nick = getNickname(ChatClient.getClientName());
      if (nick == null) {
         NickLB = new JLabel("Default�� �ȳ��ϼ���!");
      }
      else {
         NickLB = new JLabel(nick + "�� �ȳ��ϼ���!");
      }
      NickLB.setBounds(0, 15, 200, 40);
      NickLB.setFont(new Font("����", Font.BOLD, 15));
      NickLB.setBackground(Color.BLACK);
      NorthPanel.add(NickLB);
      
      // ������ �޼��� ��
      TodayLB = new JLabel();
      if (getTodayMsg(ChatClient.getClientName()).equals("Nothing")) {
         TodayLB.setText("������ �޼����� �������ּ���!");
      }
      else {
         TodayLB.setText(getTodayMsg(ChatClient.getClientName()));
      }
      TodayLB.setBounds(0, 45, 190, 40);
      TodayLB.setFont(new Font("����", Font.BOLD, 12));
      TodayLB.setBackground(Color.BLACK);
      NorthPanel.add(TodayLB);
      
      
      // ��Ī ���� ��ư
      modifyNick = new JButton("��Ī ����");
      modifyNick.setBounds(234, 0, 150, 25);
      modifyNick.setFont(new Font("����", Font.BOLD, 13));
      modifyNick.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String newNick = JOptionPane.showInputDialog("�����Ͻ� �г����� �Է��ϼ���.(10���� ����)");
            if (newNick == null || newNick.equals("")) return;
            else {
               ChangeInfo chg = new ChangeInfo();
               chg.ChangeNickname(ChatClient.getClientName(), newNick);
               NickLB.setText(newNick + "�� �ȳ��ϼ���!");
            }
         }
         
      });
      NorthPanel.add(modifyNick);
      
      // ������ �޼��� ���� ��ư
      modifyTodayMsg = new JButton("������ �޼��� ����");
      modifyTodayMsg.setBounds(234, 25, 150, 25);
      modifyTodayMsg.setFont(new Font("����", Font.BOLD, 13));
      modifyTodayMsg.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String message = JOptionPane.showInputDialog("�����Ͻ� ������ �޼����� �Է��ϼ���.");
            if (message == null || message.equals("")) return;
            else {
               ChangeInfo chg = new ChangeInfo();
               chg.ChangeTodayMsg(ChatClient.getClientName(), message);
               TodayLB.setText(message);
            }
         }
         
      });
      NorthPanel.add(modifyTodayMsg);
      
      // ģ�� �߰� ��ư
      addFriend = new JButton("ģ�� �߰�");
      addFriend.setBounds(234, 50, 150, 25);
      addFriend.setFont(new Font("����", Font.BOLD, 13));
      addFriend.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            String input= JOptionPane.showInputDialog("Enter the ID you want to add to friends list.");
            addFriend aF = new addFriend();
            aF.addFriend(ChatClient.getClientName(),input);
      
            OnlineFriends.setListData(FLM.getFriendListMain());  //new JList(FLM.getFriendListMain());
            OnlineFriends.setVisible(true);
            
            //offline manage
            //String sql = "SELECT ID FROM login_check where Connection='online' and ID='"+
            
         }
         
      });
      NorthPanel.add(addFriend);
      
      // ���������� ��ư
      MyPage = new JButton("����������");
      MyPage.setBounds(234, 75, 150, 25);
      MyPage.setFont(new Font("����", Font.BOLD, 13));
      MyPage.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new MyPage(ChatClient.getClientName());  // �ڽ��� ���������� Load  
         }
         
      });
      NorthPanel.add(MyPage);
      
      // �α׾ƿ� ��ư
      Logout = new JButton("�α� �ƿ�");
      Logout.setBounds(234, 100, 150, 25);
      Logout.setFont(new Font("����", Font.BOLD, 13));
      Logout.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Logout out = new Logout();
            out.ClientLogout(ChatClient.getClientName());   // �������������� �α׾ƿ� ��ư ����
            
            // ���α׷� ���� �Ҳ��� ���Ҳ��� �˾�â�� �����
            int result = JOptionPane.showConfirmDialog(null, "���α׷��� �����Ͻðڽ��ϱ�?",
                  "Confirm", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
               ChatClient.sendMsg("LOGOUT#"+ ChatClient.getClientName());
               System.exit(0);
            }
         }
         
      });
      NorthPanel.add(Logout);
      
      // �������� �� + List (Scroll)
      OfflineLB = new JLabel("Offline");
      OfflineLB.setBounds(3, 205, 40, 30);
      OfflineLB.setFont(new Font("����", Font.BOLD, 12));
      OfflineLB.setBackground(Color.BLACK);
      CenterPanel.add(OfflineLB);
      
      OfflineFriends = new JList<String>();
      OfflineFriends.setBounds(3, 230, 378, 170);
      OfflineFriends.setSelectionBackground(Color.PINK);
      OfflineFriends.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      OfflineFriends.setBorder(new LineBorder(Color.BLACK));
      //OfflineFriends.setVisibleRowCount(300);
      //OfflineFriends.addMouseListener(this);
      CenterPanel.add(OfflineFriends);
      
      // ������ ���������� �κ� �г�
      SouthPanel = new JPanel();
      SouthPanel.setBounds(0, 550, 400, 110);
      SouthPanel.setBackground(Color.LIGHT_GRAY);
      SouthPanel.setLayout(null);
      
      DataLB = new JLabel("[ ������ �ڷγ� Ȯ���� �� ]");
      DataLB.setBounds(110, 20, 200, 50);
      DataLB.setFont(new Font("����", Font.BOLD, 13));
      DataLB.setBackground(Color.BLACK);
      SouthPanel.add(DataLB);
      
      RealDataLB = screenInit();
      RealDataLB.setBounds(165, 40, 200, 50);
      RealDataLB.setFont(new Font("����", Font.BOLD, 13));
      RealDataLB.setBackground(Color.BLACK);
      SouthPanel.add(RealDataLB);
      
      add(NorthPanel);
      add(CenterPanel);
      add(SouthPanel);
      setVisible(true);
      addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
				final String USER_NAME = "root";
				final String USER_PASSWORD = "rkddnrdl1863^^";
				
				Connection conn;
				Statement state;
				final String sql = "UPDATE login_check SET Connection = 'offline' where ID='" + ChatClient.getClientName() + "';";
				
				int popup_result  = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?",
						"Confirm", JOptionPane.YES_NO_OPTION);
				
				if (popup_result == JOptionPane.YES_OPTION) {
					try {
						conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
						//conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
						state = conn.createStatement();
						state.executeUpdate(sql);
						
						state.close();
						conn.close();
					}
					catch(SQLException ee) {}
				}
				dispose();
			}
		});
   }
   
     // ���������� �κ� ������ ǥ�� -> Label Return
     public JLabel screenInit(){

    	  String str = httpConnection("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=%2Blu0Y8Kf28cpH9IMwrAM0zun%2BE4YvdkmETJfqz2xlpHbgtgI4uGwmWGa5m0sfBx6k0x3lz9YllQyOfT%2Ftx%2Buug%3D%3D&pageNo=1&numOfRows=10&startCreateDt=20201216&endCreateDt=20201217");
          JSONParser parser = new JSONParser();
          try {
              JSONObject openAPIObj = (JSONObject)parser.parse(str);
              JSONObject response = (JSONObject)openAPIObj.get("response");
              JSONObject header = (JSONObject)response.get("header");

              if(header.get("resultCode").toString().equals("00")){
                  JSONObject body = (JSONObject)response.get("body");
                  JSONObject items = (JSONObject)body.get("items");
                  JSONObject item = (JSONObject)items.get("item");
                  
                  DataLB = new JLabel(item.get("decideCnt").toString() + "��");
              }
          } catch (ParseException e) {
              e.printStackTrace();
          }
          
          return DataLB;
      }

      // HttpConnection ���� ��û
      public String httpConnection(String targetUrl) {
          URL url = null;
          HttpURLConnection conn = null;
          String jsonData = "";
          BufferedReader br = null;
          StringBuffer sb = null;
          String returnText = "";

          try {
              url = new URL(targetUrl);

              conn = (HttpURLConnection) url.openConnection();
              conn.setRequestProperty("Accept", "application/json");
              conn.setRequestMethod("GET");
              conn.connect();

              br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

              sb = new StringBuffer();

              while ((jsonData = br.readLine()) != null) {
                  sb.append(jsonData);
              }

              returnText = sb.toString();

          } catch (IOException e) {
              e.printStackTrace();
          } finally {
              try {
                  if (br != null) br.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }

          return returnText;
      }

   // ID�� �´� �г��� ������ �޼ҵ�
   public String getNickname(String ID) {
      final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
      final String USER_NAME = "root";
      final String USER_PASSWORD = "rkddnrdl1863^^";
      
      Connection conn;
      Statement state;
      ResultSet rset;
      String returnNick = null;
      final String selectSQL = "SELECT * FROM list WHERE ID='" + ID + "';";
      
      try {
         //conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
         conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
         state = conn.createStatement();
         rset = state.executeQuery(selectSQL);
         
         while (rset.next()) {
            returnNick = rset.getString("Nickname");
            break;
         }
      }
      catch(Exception e) {
         System.out.println("DataBase Connection Error : " + e.getMessage());
         System.exit(1);
      }
      
      return returnNick;
   }
   
   // ID�� �ش��ϴ� ������ �޼����� ����
   public String getTodayMsg(String ID) {
      final String DB_URL = "jdbc:mysql://localhost:3306/chatproject?serverTimezone=UTC";
      final String USER_NAME = "root";
      final String USER_PASSWORD = "rkddnrdl1863^^";
      
      Connection conn;
      Statement state;
      ResultSet rset;
      String returnMsg = null;
      final String selectSQL = "SELECT * FROM todaymessage WHERE ID='" + ID + "';";
      
      try {
         //conn = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12381711", "sql12381711", "tj8zNbR2TM");
         conn = DriverManager.getConnection(DB_URL, USER_NAME, USER_PASSWORD);
         state = conn.createStatement();
         rset = state.executeQuery(selectSQL);
         
         while (rset.next()) {
            returnMsg = rset.getString("todayMsg");
            break;
         }
      }
      catch(Exception e) {
         System.out.println("DataBase Connection Error : " + e.getMessage());
         System.exit(1);
      }
      
      return returnMsg;
   }

@Override
public void windowOpened(WindowEvent e) {}
@Override
public void windowClosing(WindowEvent e) {}
@Override
public void windowClosed(WindowEvent e) {}
@Override
public void windowIconified(WindowEvent e) {}
@Override
public void windowDeiconified(WindowEvent e) {}
@Override
public void windowActivated(WindowEvent e) {}
@Override
public void windowDeactivated(WindowEvent e) {}
   
}