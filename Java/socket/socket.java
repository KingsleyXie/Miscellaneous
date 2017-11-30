import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.Scanner;

class Global {
	public static final String CLOSE_FLAG = "Bye";
	public static final int PORT = 2333, START_ID = 1000;
	public static ServerSocket server;
	public static JFrame frame;
	public static JLabel label;
	public static JScrollPane content;

	public static void append(String html) {
		boolean m = html.indexOf("Received") == 0;
		label.setText(
			label.getText() +
			(m ? "" : "<br>***************************************************") +
			"<p>" + html + "</p>" +
			(m ? "" : "***************************************************<br>")
		);
	}
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

			Global.append(
				"Established connection with client "
				+ id + "(" + str + ")"
			);

			while (true) {
				str = in.readLine();
				if (str.equals(Global.CLOSE_FLAG)) break;

				Global.append("Received \"" + str + "\" from client " + id);
				out.println("I have received your message \"" + str + "\"");
			}

			Global.append("Terminated connection with client " + id);
			socket.close(); count--;
		} catch (Exception e) { e.printStackTrace(); }
	}
}

class server {
	server() throws Exception {
		Global.server = new ServerSocket(Global.PORT);
		Global.label = new JLabel(
			"<html>Socket server started at port " +
			Global.PORT + "<br>",
			SwingConstants.CENTER
		);
		Global.content = new JScrollPane(Global.label);

		Global.frame = new JFrame();
		Global.frame.add(Global.content);
		Global.frame.setTitle("Socket Server");
		Global.frame.setLocation(430,70);
		Global.frame.setSize(475,600);
		Global.frame.setVisible(true);

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

public class socket {
	public static void main(String[] args) {
		try {
			if (args.length == 1 && args[0].equals("-server")) new server();
			if (args.length == 1 && args[0].equals("-client")) new client();
			System.out.println(
				"usage:\n" +
				"    java socket -server      Run as socket server\n" +
				"    java socket -client      Run as socket client"
			);
		} catch (Exception e) {
			switch (e.toString()) {
				case "java.net.ConnectException: Connection refused: connect":
					System.out.println(
						"\n\tError: Socket server not running at port " +
						Global.PORT
					);
					break;

				case "java.net.BindException: Address already in use: JVM_Bind":
					System.out.println(
						"\n\tError: Port " + Global.PORT + " already in use"
					);
					break;

				default:
					e.printStackTrace();
			}
		}
		Global.append(
			"All clients are terminated, closing socket server..."
		);
		try { Global.server.close(); } catch (Exception e) {}
	}
}
