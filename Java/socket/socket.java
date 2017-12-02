import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.imageio.*;
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

			server.append(
				"Hello " + str + ", your client ID is " +
				id, server.msgType.OUTCOME
			);
			out.println(
				"Hello " + str + ", your client ID is " + id
			);

			server.append(
				"Established connection with client "
				+ id + "(" + str + ")", server.msgType.SYSTEM
			);

			while (true) {
				str = in.readLine();
				if (str.equals(Global.CLOSE_FLAG)) break;
				server.append("Received \"" + str + "\" from client " + id, server.msgType.INCOME);
				out.println("I have received your message \"" + str + "\"");
				server.append("I have received your message \"" + str + "\"", server.msgType.OUTCOME);
			}

			server.append("Terminated connection with client " + id, server.msgType.SYSTEM);
			socket.close(); count--;
		} catch (Exception e) { e.printStackTrace(); }
	}
}

class server {
	public static JFrame frame;
	public static Container pane;
	public static JScrollPane content;
	public static enum msgType {
		SYSTEM, INCOME, OUTCOME
	};

	public static void append(String text, msgType mt) {
		JTextPane output = new JTextPane();
		output.setText(text);
		output.setEditable(false);

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		switch (mt) {
			case SYSTEM:
				output.setBorder(new TextBubbleBorder(new Color(0, 176, 255),2,16,0));
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
				break;

			case INCOME:
				output.setBorder(new TextBubbleBorder(new Color(255, 145, 0),2,16,0));
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_LEFT);
				break;

			case OUTCOME:
				output.setBorder(new TextBubbleBorder(new Color(24, 255, 255),2,16,0));
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
				break;
		}

		output.setParagraphAttributes(attribs, false);
		pane.add(output);

		JLabel gap = new JLabel();
		gap.setPreferredSize(new Dimension(500, 10));
		pane.add(gap);

		frame.pack();
		if (frame.getHeight() >= 700) frame.setSize(500, 700);
	}

	server() throws Exception {
		frame = new JFrame();
		frame.setTitle("Socket Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(450,10);

		pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		Global.server = new ServerSocket(Global.PORT);
		append("Socket server started at port " + Global.PORT, msgType.SYSTEM);

		frame.setVisible(true);

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
		server.append(
			"All clients are terminated, closing socket server...", server.msgType.SYSTEM
		);
		try { Global.server.close(); } catch (Exception e) {}
	}
}





// Class Modified From:
//   https://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency
class TextBubbleBorder extends AbstractBorder {
	private Color color;
	private int thickness;
	private int radii;
	private int pointerSize;
	private Insets insets = null;
	private BasicStroke stroke = null;
	private int strokePad;
	private int pointerPad;
	RenderingHints hints;

	TextBubbleBorder(Color color, int thickness, int radii, int pointerSize) {
		this.thickness = thickness;
		this.radii = radii;
		this.pointerSize = pointerSize;
		this.color = color;

		stroke = new BasicStroke(thickness);
		strokePad = thickness / 2;

		hints = new RenderingHints(
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON
		);

		int pad = radii + strokePad;
		int bottomPad = pad + pointerSize + strokePad;
		insets = new Insets(pad, pad, bottomPad, pad);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return insets;
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		return getBorderInsets(c);
	}

	@Override
	public void paintBorder(
			Component c,
			Graphics g,
			int x, int y,
			int width, int height) {

		Graphics2D g2 = (Graphics2D) g;

		int bottomLineY = height - thickness - pointerSize;

		RoundRectangle2D.Double bubble =
			new RoundRectangle2D.Double(
				0 + strokePad,
				0 + strokePad,
				width - thickness,
				bottomLineY,
				radii,
				radii
			);

		Polygon pointer = new Polygon();

		pointer.addPoint(
				strokePad + radii + pointerPad,
				bottomLineY);

		pointer.addPoint(
				strokePad + radii + pointerPad + pointerSize,
				bottomLineY);

		pointer.addPoint(
				strokePad + radii + pointerPad + (pointerSize / 2),
				height - strokePad);

		Area area = new Area(bubble);
		area.add(new Area(pointer));

		g2.setRenderingHints(hints);

		Area spareSpace = new Area(new Rectangle(0, 0, width, height));
		spareSpace.subtract(area);
		g2.setClip(spareSpace);
		g2.clearRect(0, 0, width, height);
		g2.setClip(null);

		g2.setColor(color);
		g2.setStroke(stroke);
		g2.draw(area);
	}
}
