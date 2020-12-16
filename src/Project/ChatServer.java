package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * Ŭ���̾�Ʈ�� ���� ���� �����͸� ó���ϰ� �����ϴ� ���� ����
 * Client�� ���� Message�� �޴´� (LOGIN, LOGOUT)
 * �ް� �Ǹ� �ش� �޼��� ���� ��ɵ��� �����Ѵ�.
 */

public class ChatServer {
	
	private final static int PORT = 1863;   // ä�ü��� ��Ʈ�ѹ�
	private ServerSocket listener;   // ���� ����
	
	Map<String, Connection> ClientAll = new HashMap<>();   // ��ü ����
	
	// �������� ���� �� �ʱ�ȭ
	public ChatServer() throws IOException {
		listener = new ServerSocket(PORT);
	}
	
	// ���� ����(Ŭ���̾�Ʈ�� ���� �� ���� ��ٸ�)
	public void RunServer() {
		try {
			System.out.println("Waiting for Client...");
			while (true)
			{
				Socket socket = listener.accept();
				Connection conn = new Connection(socket);
				conn.start();   // Thread ����
			}
		}
		catch(Exception e) {
			System.out.println("Connection Error : " + e.getMessage());
			System.exit(1);
		}
	}
	
	public class Connection extends Thread {
		public Scanner in;
		public PrintWriter out;
		
		private String ClientName;   // ������ ȸ���� �̸�
		
		public Connection(Socket sock) throws IOException {
			in = new Scanner(sock.getInputStream());
			out = new PrintWriter(sock.getOutputStream(), true);
		}
		
		public void run() {
			String line = null;
			
			try {
				while (in.hasNextLine()) {  
					line = in.nextLine();
					String[] splited = line.split("#");   // Ŭ���̾�Ʈ�κ��� ���� �޼����� # �������� ����
					String opcode = splited[0];
					
					switch(opcode)
					{
					case "LOGIN":
						ClientName = splited[1];  // ������ Ŭ���̾�Ʈ ID
						synchronized(ClientAll) {
							ClientAll.put(this.getClientName(), this);
							System.out.println("[�α��� ����] ID : " + ClientAll.get(this.ClientName).getClientName());							
						}
						break;
						
					case "LOGOUT":
						ClientName = splited[1];
						synchronized(ClientAll) {
							ClientAll.remove(this.getClientName());
							System.out.println("[�α׾ƿ�] ID : " + this.getClientName());
						}
						break;
					}
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public String getClientName() {
			return this.ClientName;
		}
		
		public void SendMsg(String msg) {
			out.println(msg);
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println("ä�� ���� ���� ����!");
			new ChatServer().RunServer();
		}
		catch(Exception e) {
			System.out.println("Server Running Error : " + e.getMessage());
		}
	}
	
}
