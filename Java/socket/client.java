import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
	public static void main(String[] args) throws IOException {
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
	}
}
