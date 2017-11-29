import java.io.*;
import java.net.*;

class Global {
	public static final String CLOSE_FLAG = "Bye";
	public static final int PORT = 2333, START_ID = 1000;
	public static ServerSocket server;
}

class multiServer extends Thread {
	public static int count = Global.START_ID;
	private int id;

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

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
		try {
			String str = in.readLine();
			out.println(
				"Hello " + str + ", your client ID is " + id
			);

			System.out.println(
				"\nEstablished connection with client " +
				id + "(" + str + ")\n"
			);

			while (true) {
				str = in.readLine();
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

			if (count == Global.START_ID) {
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
