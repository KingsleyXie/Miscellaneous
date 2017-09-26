import java.awt.BorderLayout;
import javax.swing.*;

public class Array2D extends JFrame {
	private JButton north = new JButton("N");
	private JButton south = new JButton("S");
	private JButton east = new JButton("E");
	private JButton west = new JButton("W");
	private JButton center = new JButton("C");

	public Array2D() {
		setSize(300, 650);
		setLocation(300, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
	}
	public static void main(String[] args) {
		Array2D frame = new Array2D();
		frame.setVisible(true);
	}
}
