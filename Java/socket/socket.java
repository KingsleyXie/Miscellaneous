import java.io.*;
import java.net.*;
import java.util.Scanner;

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

class server {
	server() throws Exception {
		Global.server = new ServerSocket(Global.PORT);
		System.out.println(
			"Socket server started at port " + Global.PORT
		);

		while (true) {
			Socket socket = Global.server.accept();
			new multiServer(socket).start();
		}
	}
}

class client {
	client() throws Exception {
		InetAddress addr = InetAddress.getByName(null);
		System.out.println("Socket client started");

		Socket socket = new Socket(addr, Global.PORT);
		System.out.println("Established connection with server\n");

		BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);

		Scanner scanner = new Scanner(System.in);

		System.out.printf("Please input your username > ");
		String msg= scanner.nextLine();
		out.println(msg);
		System.out.println("\t" + in.readLine() + "\n");

		while (true) {
			System.out.printf("Send some message to server > ");
			msg= scanner.nextLine();
			out.println(msg);

			if (msg.equals(Global.CLOSE_FLAG)) break;
			System.out.println("\tReceived from server:\t" + in.readLine() + "\n");
		}

		socket.close();
		System.out.println("Socket Closed");
		System.exit(0);
	}
}

class socket {
	public static void main(String[] args) {
		try {
			if (args.length == 1 && args[0].equals("-server")) new server();
			if (args.length == 1 && args[0].equals("-client")) new client();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(
			"usage:\n" +
			"    java socket -server      Run as socket server\n" +
			"    java socket -client      Run as socket client"
		);
	}
}
