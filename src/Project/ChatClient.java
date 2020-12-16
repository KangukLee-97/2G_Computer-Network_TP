package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private static String SERVER_IP;   // ä�� ���� IP�ּ�
	private static int SERVER_PORT;   // ä�� ���� ��Ʈ�ѹ�
	
	public static Socket socket;	// ������ ������ �ϱ� ���� ����
	public static Scanner in;   // �����κ��� �����͸� �ޱ� ���� InputStream
	public static PrintWriter out;   // �������� �����͸� ������ ���� OutputStream
	
	private static String ClientName;   // ����� ID
	
	// ���Ͽ��� ���� IP�ּҿ� ��Ʈ �ѹ� �޾ƿ���
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
		 * LoginGUI���� Ŭ���̾�Ʈ�� ID�� PW�� �Է��ϰ� LOGIN ��ư�� ������.
		 * LOGIN�� ���簡 �Ǹ��� RunClient�� �����Ų��.
		 */
		
		new LoginGUI();
	}
	
	public static void RunClient() {
		// ������ ���ϰ� ����
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
					// �α��� �ߴٰ� Server���� �˷���
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
	public static String getClientName() {   // �α����� ID�� return
		return ClientName;
	}
	public static void sendMsg(String msg) {   // �������� ���� �޼����� ��������
		out.println(msg);
	}
	
	public static void main(String[] args) {
		getServerInfo("C:\\config.txt");
		new ChatClient();
	}
	
}
