import java.io.*;
import java.net.*;

class multiServer extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public static final String closeFlag = "Bye";

	public multiServer(Socket s) throws IOException {
		socket = s;
		in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);
	}

	public void run() {
		System.out.println("Established connection with client\n");
		try {
			while (true) {
					String str = in.readLine();
					if (str.equals(closeFlag)) break;

					System.out.println("\tReceived: " + str + "\n");
					out.println("I have received your message: " + str);
			}
			System.out.println("Terminated connection with client\n");
			socket.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
}

public class server {
	public static final int PORT = 2333;

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Socket server started at port " + PORT);

		while (true) {
			Socket socket = s.accept();
			try {
				new multiServer(socket).start();
			} catch (Exception e) {
				e.printStackTrace(); break;
			}
		}
		s.close();
		System.out.println("Socket server closed");
	}
}
