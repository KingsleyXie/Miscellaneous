import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
	public static void main(String[] args) throws IOException {
		InetAddress addr = InetAddress.getByName(null);
		System.out.println("Socket client started");

		Socket socket = new Socket(addr, server.PORT);
		System.out.println("Established connection with server\n");

		BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.printf("Input your data to server >\t");
			String msg= scanner.nextLine();
			out.println(msg);

			if (msg.equals(server.closeFlag)) break;
			System.out.println("\tReceived from server:\t" + in.readLine() + "\n");
		}

		socket.close();
		System.out.println("Socket Closed");
	}
}
