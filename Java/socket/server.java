import java.io.*;
import java.net.*;

class Global {
	public static final String CLOSE_FLAG = "Bye";
	public static final int PORT = 2333;
	public static ServerSocket server;
}

class multiServer extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public static int count = 0;
	private int id;


	public multiServer(Socket s) throws IOException {
		socket = s; id = count++;
		in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);
	}

	public void run() {
		System.out.println(
			"\nEstablished connection with client " + id + "\n"
		);

		try {
			while (true) {
				String str = in.readLine();
				if (str.equals(Global.CLOSE_FLAG)) break;

				System.out.println(
					"\tReceived \"" + str + "\" from client " + id
				);
				out.println("I have received your message \"" + str + "\"");
			}

			System.out.println(
				"\nTerminated connection with client " + id + "\n"
			);
			socket.close(); count--;

			if (count == 0) {
				System.out.println(
					"All clients are terminated, closing socket server..."
				);
				Global.server.close();
				System.exit(0);
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
}

public class server {
	public static void main(String[] args) {
		try {
			Global.server = new ServerSocket(Global.PORT);
			System.out.println(
				"Socket server started at port " + Global.PORT
			);

			while (true) {
				Socket socket = Global.server.accept();
				new multiServer(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
