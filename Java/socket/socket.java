import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.util.Scanner;

class Global {
	public static final String CLOSE_FLAG = "Bye";
	public static final int PORT = 2333, START_ID = 1000;
	public static ServerSocket server;
}

class listen implements Runnable {
	private chatFrame frm;
	private BufferedReader in;
	private int id;
	public listen(chatFrame frm, BufferedReader in, int id) {
		this.frm = frm; this.in = in; this.id = id;
		new Thread(this).start();
	}

	public void run() {
		try {
			while (true) {
				String str = in.readLine();
				if (str.equals(Global.CLOSE_FLAG)) break;
				frm.append(id + ": " + str, chatFrame.msgType.INCOME);
			}
		} catch (Exception e) {}
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

		server.frame.btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea t = server.frame.textArea;
				out.println(t.getText());
				t.setText("");
			}
		});
	}

	public void run() {
		try {
			String str = in.readLine();

			server.frame.append(
				"成功与客户端 " + id + "（" + str + "）建立连接",
				chatFrame.msgType.SYSTEM
			);

			out.println(
				str + "，你已成功与服务端建立连接，你的 ID 是 " + id
			);
			server.frame.append(
				str + "，你已成功与服务端建立连接，你的 ID 是 " + id,
				chatFrame.msgType.OUTCOME
			);

			new listen(server.frame, in, id);
			while (true) {
				str = in.readLine();
				if (str.equals(Global.CLOSE_FLAG)) break;
				server.frame.append(id + "： " + str, chatFrame.msgType.INCOME);
				out.println("已收到信息：" + str);
				server.frame.append("已收到信息：" + str, chatFrame.msgType.OUTCOME);
			}

			server.frame.append("结束与客户端 " + id + " 的会话", chatFrame.msgType.SYSTEM);
			socket.close(); count--;
		} catch (Exception e) { e.printStackTrace(); }
	}
}

class server {
	public static chatFrame frame;
	server() throws Exception {
		frame = new chatFrame("Socket Server");

		Global.server = new ServerSocket(Global.PORT);
		frame.append("服务端正在运行中，端口号：" + Global.PORT, chatFrame.msgType.SYSTEM);

		while (true) {
			Socket socket = Global.server.accept();
			new multiServer(socket).start();
		}
	}
}

class client {
	private JFrame frame;
	private Container pane;

	client() throws Exception {
		InetAddress addr = InetAddress.getByName(null);
		Socket socket = new Socket(addr, Global.PORT);

		BufferedReader in = new BufferedReader(
			new InputStreamReader(socket.getInputStream())
		);

		PrintWriter out = new PrintWriter(new BufferedWriter(
			new OutputStreamWriter(socket.getOutputStream())
		), true);

		Scanner scanner = new Scanner(System.in);

		System.out.printf("请输入你的用户名 > ");
		String msg= scanner.nextLine();
		out.println(msg);
		System.out.println("\t" + in.readLine() + "\n");

		while (true) {
			System.out.printf("向服务端发送信息 > ");
			msg= scanner.nextLine();
			out.println(msg);

			if (msg.equals(Global.CLOSE_FLAG)) break;
			// System.out.println("\t收到来自服务端的消息：\t" + in.readLine() + "\n");
		}

		socket.close();
		System.out.println("会话结束");
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
		server.frame.append(
			"All clients are terminated, closing socket server...", chatFrame.msgType.SYSTEM
		);
		try { Global.server.close(); } catch (Exception e) {}
	}
}



class chatFrame extends JFrame {
	public Container pane;
	public JTextArea textArea;
	public JButton btn;

	public static enum msgType {
		SYSTEM, INCOME, OUTCOME
	};

	chatFrame(String tit) {
		setTitle(tit);
		setLocation(50, 10);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JScrollPane chatContent = new JScrollPane(pane);
		chatContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(chatContent, BorderLayout.CENTER);

		textArea = new JTextArea(2, 50);
		btn = new JButton("发送");
		btn.setBackground(new Color(3, 155, 229));
		btn.setForeground(Color.WHITE);

		JPanel bottom = new JPanel();
		bottom.add(new JScrollPane(textArea));
		bottom.add(btn);

		add(bottom, BorderLayout.SOUTH);
		setVisible(true);
	}

	public void append(String text, msgType mt) {
		JTextPane output = new JTextPane();
		output.setEditable(false);

		SimpleAttributeSet attribs = new SimpleAttributeSet();
		switch (mt) {
			case SYSTEM:
				text = "【系统消息】 " + text;
				output.setBorder(new TextBubbleBorder(
					new Color(66, 66, 66), 2, 10, 6, false)
				);
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
				break;

			case INCOME:
				output.setBorder(new TextBubbleBorder(
					new Color(255, 145, 0), 2, 10, 6, false)
				);
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_LEFT);
				break;

			case OUTCOME:
				output.setBorder(new TextBubbleBorder(
					new Color(0, 176, 255), 2, 10, 6, true)
				);
				StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_RIGHT);
				break;
		}

		output.setText(text);
		output.setParagraphAttributes(attribs, false);
		pane.add(output);

		JTextPane gap = new JTextPane();
		gap.setPreferredSize(
			new Dimension(
				getWidth() < 500 ? getWidth() : 500, 9
			)
		);
		pane.add(gap);

		pack();
		setSize(
			getWidth() > 500 ? getWidth() : 500,
			getHeight() < 700 ? getHeight() : 700
		);
	}
}





// Class Modified From:
//   https://stackoverflow.com/questions/15025092/border-with-rounded-corners-transparency
class TextBubbleBorder extends AbstractBorder {
	private Color color;
	private int thickness;
	private int radii;
	private int pointerSize;
	private boolean rightPointer;
	private Insets insets = null;
	private BasicStroke stroke = null;
	private int strokePad;
	private int pointerPad;
	RenderingHints hints;

	TextBubbleBorder(
		Color color,
		int thickness, int radii, int pointerSize,
		boolean rightPointer
	) {
		this.thickness = thickness;
		this.radii = radii;
		this.pointerSize = pointerSize;
		this.color = color;
		this.rightPointer = rightPointer;

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
		if (this.rightPointer) {
			int offset =
				server.frame.getWidth() -
				2 * ((Integer)UIManager.get("ScrollBar.width")).intValue();

			pointer.addPoint(
					offset - (strokePad + radii + pointerPad),
					bottomLineY);

			pointer.addPoint(
					offset - (strokePad + radii + pointerPad + pointerSize),
					bottomLineY);

			pointer.addPoint(
					offset - (strokePad + radii + pointerPad + (pointerSize / 2)),
					height - strokePad);
		} else {
			pointer.addPoint(
					strokePad + radii + pointerPad,
					bottomLineY);

			pointer.addPoint(
					strokePad + radii + pointerPad + pointerSize,
					bottomLineY);

			pointer.addPoint(
					strokePad + radii + pointerPad + (pointerSize / 2),
					height - strokePad);
		}

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
