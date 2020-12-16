package Project;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/***
 * 클라이언트로 부터 오는 데이터를 처리하고 전달하는 서버 역할
 * Client로 부터 Message를 받는다 (LOGIN, LOGOUT)
 * 받게 되면 해당 메세지 마다 기능들을 구현한다.
 */

public class ChatServer {
	
	private final static int PORT = 1863;   // 채팅서버 포트넘버
	private ServerSocket listener;   // 서버 소켓
	
	Map<String, Connection> ClientAll = new HashMap<>();   // 전체 관리
	
	// 서버소켓 생성 및 초기화
	public ChatServer() throws IOException {
		listener = new ServerSocket(PORT);
	}
	
	// 서버 구동(클라이언트가 들어올 때 까지 기다림)
	public void RunServer() {
		try {
			System.out.println("Waiting for Client...");
			while (true)
			{
				Socket socket = listener.accept();
				Connection conn = new Connection(socket);
				conn.start();   // Thread 시작
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
		
		private String ClientName;   // 입장한 회원의 이름
		
		public Connection(Socket sock) throws IOException {
			in = new Scanner(sock.getInputStream());
			out = new PrintWriter(sock.getOutputStream(), true);
		}
		
		public void run() {
			String line = null;
			
			try {
				while (in.hasNextLine()) {  
					line = in.nextLine();
					String[] splited = line.split("#");   // 클라이언트로부터 들어온 메세지를 # 기준으로 나눔
					String opcode = splited[0];
					
					switch(opcode)
					{
					case "LOGIN":
						ClientName = splited[1];  // 접속한 클라이언트 ID
						synchronized(ClientAll) {
							ClientAll.put(this.getClientName(), this);
							System.out.println("[로그인 성공] ID : " + ClientAll.get(this.ClientName).getClientName());							
						}
						break;
						
					case "LOGOUT":
						ClientName = splited[1];
						synchronized(ClientAll) {
							ClientAll.remove(this.getClientName());
							System.out.println("[로그아웃] ID : " + this.getClientName());
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
			System.out.println("채팅 서버 구동 시작!");
			new ChatServer().RunServer();
		}
		catch(Exception e) {
			System.out.println("Server Running Error : " + e.getMessage());
		}
	}
	
}
