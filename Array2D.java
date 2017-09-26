import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class Array2D extends JFrame {
	public Array2D() {
		setSize(300,600);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JTable table = new JTable(new AbstractTableModel() {
			private String columnName[] = {"学号", "姓名", "籍贯"};
			
			public String getColumnName(int column) {
				return columnName[column];
			}
			public int getColumnCount() {
				return 3;
			}
			public int getRowCount() {
				return 100;
			}
			public Object getValueAt(int row, int col) {
				return new Integer(row * col);
			}
		});

		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		Array2D frame = new Array2D();
		frame.setVisible(true);
	}
}