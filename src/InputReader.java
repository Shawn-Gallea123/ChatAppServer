import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;

public class InputReader implements Runnable {
	private Scanner input;
	
	public InputReader() {
		input = new Scanner(System.in);
	}
	
	
	@Override
	public void run() {
		String s;
		while (true) {
			s = input.nextLine();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			
			if (s.equals("q")) {
				System.out.println("Killing service sockets");
				
				for (Socket sock : ChatAppServer.clientSockets) {
					try {
						sock.close();
					} catch (IOException e) {
						System.out.println("Failed to close service socket");
					}
				}
					
				System.out.println("Killing server");
				try {
					ChatAppServer.serverSocket.close();
				} catch (IOException e) {
				}
				return;
			}
			
		}
	}
	
}
