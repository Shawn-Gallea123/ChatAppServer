import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ChatAppServer implements Runnable {
	
	private static ArrayList<User> users;
	public static ArrayList<Socket> clientSockets;
	public static ServerSocket serverSocket;
	private Socket serviceSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	
	ChatAppServer(Socket sSocket) throws IOException {
		this.serviceSocket = sSocket;
		this.out = new PrintWriter(serviceSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
	}
	
	public static void main(String[] args) throws IOException, InterruptedException  {
		int SERVER_PORT = -1;

		users = new ArrayList<User>();
		clientSockets = new ArrayList<Socket>();
		
		try {
			
			if (SERVER_PORT == -1) {
				serverSocket = new ServerSocket(60000);
				SERVER_PORT = serverSocket.getLocalPort();
				System.out.println("SERVER_PORT=" + SERVER_PORT);
				System.out.println(InetAddress.getLocalHost().getHostAddress());
				new Thread(new InputReader()).start();
			}
			
			while(true) {
				Socket serviceSocket = serverSocket.accept();
				System.out.println("Connected");
				
				clientSockets.add(serviceSocket);
				new Thread(new ChatAppServer(serviceSocket)).start();
				Thread.sleep(100);
			}
			
		} catch (IOException e) {
			return;
		}
		
	}

	private String buildConvoList() {
		String CL = "CL";
		
		for (User u : users) {
			CL += "/" + u.getUserName();
		}
		
		return CL;
		
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				String receivedMessage = in.readLine();
			
				if (receivedMessage != null && receivedMessage.equals("q")) {
					serviceSocket.close();
					System.out.println("Exiting");
					return;
				} else if (receivedMessage != null && receivedMessage.contains("SU")) {
					// Format of sign up message is SU/USERNAME/PASSWORD
					String[] tokens = receivedMessage.split("/");
					User newUser = new User(tokens[1], tokens[2]);
					System.out.println("Made user: " + tokens[1]);
					System.out.println("With password: " + tokens[2]);
					users.add(newUser);
					out.println("MSG/Sign up successful");
				} else if (receivedMessage != null && receivedMessage.contains("LI")) {
					// Format of log in message is LI/USERNAME/PASSWORD
					String[] tokens = receivedMessage.split("/");
					String userName = tokens[1];
					String passWord = tokens[2];
					boolean found = false;

					for (User user : users) {
						if (user.checkMatch(userName, passWord)) {
							System.out.println(userName + " logged in");
							out.println(buildConvoList());
							found = true;
							break;
						}
					}
				
					if (!found) {
						out.println("Log in unsuccessful");
						System.out.println("Log in unsuccessful");
					}
				} else {
					Thread.sleep(100);
				}
			}
		} catch (IOException e) {
		} catch (InterruptedException e) {
			System.out.println("InterruptEX");
		}
		
	}
}

