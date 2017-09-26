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

		String[][] data = {
			{"201633322332", "董建华", "香港"},
			{"201633322332", "张宝华", "香港"},
			{"201633322332", "董富强", "香港"},
			{"201633322332", "董民主", "香港"},
			{"201633322332", "董文明", "香港"},
			{"201633322332", "董和谐", "香港"},
			{"201633322332", "董诚信", "香港"},
			{"201633322332", "董友善", "香港"},
		};
		JTable table = new JTable(new AbstractTableModel() {
			private String columnName[] = {"学号", "姓名", "籍贯"};
			
			public String getColumnName(int column) {
				return columnName[column];
			}
			public int getColumnCount() {
				return 3;
			}
			public int getRowCount() {
				return data.length;
			}
			public Object getValueAt(int row, int col) {
				// return new Integer(row * col);
				return data[row][col];
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
