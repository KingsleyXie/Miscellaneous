import java.io.*;
import java.net.*;

public class server {
	public static final int PORT = 2333;
	public static final String closeFlag = "Bye";

	public static void main(String[] args) throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Socket server started at port " + PORT);

		Socket socket = s.accept();
		System.out.println("Established connection with client\n");

		BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);

		while (true) {
			String str = in.readLine();
			if (str.equals(closeFlag)) break;

			System.out.println("\tReceived: " + str + "\n");
			out.println("I have received your message: " + str);
		}

		socket.close();
		System.out.println("Socket Closed");
		s.close();
	}
}
