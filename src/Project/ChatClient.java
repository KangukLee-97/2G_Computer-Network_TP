package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static String SERVER_IP;   // 채팅 서버 IP주소
	private static int SERVER_PORT;   // 채팅 서버 포트넘버
	
	public static Socket socket;	// 서버랑 연결을 하기 위한 소켓
	public static Scanner in;   // 서버로부터 데이터를 받기 위한 InputStream
	public static PrintWriter out;   // 서버에게 데이터를 보내기 위한 OutputStream
	
	private static String ClientName;   // 사용자 ID
	
	// 파일에서 서버 IP주소와 포트 넘버 받아오기
	public static void getServerInfo(String fileName) {
		try {
			Scanner in = new Scanner(new File(fileName));
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] info = line.split(" ");
				SERVER_IP = info[0];   // 127.0.0.1
				SERVER_PORT = Integer.parseInt(info[1]);   // 1863
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File Open Error : " + e.getMessage());
		}
	}
	
	public ChatClient() {
		/*
		 * LoginGUI에서 클라이언트가 ID랑 PW를 입력하고 LOGIN 버튼을 누른다.
		 * LOGIN이 성사가 되면은 RunClient를 실행시킨다.
		 */
		
		new LoginGUI();
	}
	
	public static void RunClient() {
		// 서버의 소켓과 연결
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT); 
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(IOException e) {
			System.out.println("Connection Error : " + e.getMessage());
		}
		
		try {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// 로그인 했다고 Server에게 알려줌
					out.println("LOGIN#" + getClientName());
				}
			}).start();
		}
		catch(Exception e) {
			System.out.println("Running Exception : " + e.getMessage());
			System.exit(1);
		}
	}
	
	public static void setClientName(String name) {
		ClientName = name;
	}
	public static String getClientName() {   // 로그인한 ID를 return
		return ClientName;
	}
	public static void sendMsg(String msg) {   // 서버에게 상태 메세지를 전달해줌
		out.println(msg);
	}
	
	public static void main(String[] args) {
		getServerInfo("C:\\config.txt");
		new ChatClient();
	}
	
}
