import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Objects;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Base extends JFrame {
	final int MAX_ARRAY_SIZE = 1000;
	final int TABLE_COLUMN_LENGTH = 4;

	public String statistics[][] = new String[MAX_ARRAY_SIZE][3];
	public int dataLen = 0, statisticsLen = 0, i, j;
	public JTable table;
	public JButton importBtn;

	private String columnName[] = new String[TABLE_COLUMN_LENGTH];
	private String data[][] = new String[MAX_ARRAY_SIZE][TABLE_COLUMN_LENGTH];

	public Base() {
		setSize(900,600);
		setLocation(50,50);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		importPrepare();
	}

	public void importPrepare() {
		importBtn = new JButton("导入统计数据");
		add(importBtn, BorderLayout.CENTER);
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importBtn.setVisible(false);
				readData();
				drawInterface();
			}
		});
	}

	public void readData() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));

			String line = null;

			line = reader.readLine();
			columnName = line.split(",");

			while((line = reader.readLine()) != null) {
				String it[] = line.split(",");

				data[dataLen][0] = it[0];
				data[dataLen][1] = it[1];
				data[dataLen][2] = it[2];
				data[dataLen][3] = it[3];

				dataLen++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawInterface() {
		table = new JTable(new AbstractTableModel() {
			public String getColumnName(int column) {
				return columnName[column];
			}
			public int getColumnCount() {
				return columnName.length;
			}
			public int getRowCount() {
				return dataLen;
			}
			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		});

		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane, BorderLayout.CENTER);
		table.setAutoCreateRowSorter(true);

		JButton exportBtn = new JButton("导出统计数据");
		add(exportBtn, BorderLayout.NORTH);

		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statistics();
				outputData();
			}
		});
	}

	public void statistics() {
		for (i = 0; i < dataLen; i++) {
			j = 0;
			while(j < statisticsLen && !Objects.equals(data[i][3], statistics[j][0]))
				j++;

			int id = Objects.equals(data[i][2], "男") ? 1 : 2;
			if (j < statisticsLen) {
				statistics[j][id] = String.valueOf(Integer.parseInt(statistics[j][id]) + 1);
			} else {
				statistics[statisticsLen][0] = data[i][3];
				statistics[statisticsLen][id] = "1";
				statistics[statisticsLen][3 - id] = "0";

				statisticsLen++;
			}
		}
	}

	public void outputData() {
		System.out.println("If You See This Then The Upcasting Is Failed");
		//This Function Was Rewrote In Extended Class So The Message Won't Show
	}
}

class Extended extends Base {
	public int i = 233;
	public int newi = 233;

	public void uncallable() {
		System.out.println("This Functiton Is Not Callable");
	}

	// Rewrite Output Function
	public void outputData() {
		try {
			String csvFile = "statistics.csv";
			FileWriter writer = new FileWriter(csvFile);
			writer.append("籍贯,男生,女生\n");
			System.out.println("学生信息统计数据如下：");
			for (i = 0; i < statisticsLen; i++) {
				writer.append(
					statistics[i][0] + ',' +
					statistics[i][1] + ',' +
					statistics[i][2] + '\n'
				);

				statistics[i][1] = String.format("%3s", statistics[i][1]);
				statistics[i][2] = String.format("%3s", statistics[i][2]);

				System.out.println(
					"籍贯：" + statistics[i][0] +
					"\t\t男生：" + statistics[i][1] +
					"\t女生：" + statistics[i][2]
				);
			}
			writer.flush();
			writer.close();

			JOptionPane.showMessageDialog(null, "学生籍贯统计数据已成功导出\n学生总数：" + dataLen);
			// Process process = Runtime.getRuntime().exec("cmd /c statistics.csv");
		} catch(IOException err) {
			err.printStackTrace();
		}
	}
}

public class upcasting {
	public static void main(String[] args) {
		//Upcasting Object
		Base frame = new Extended();

		frame.setVisible(true);

		/***************************
		 * EXTRA TEST CASE - START
		 ***************************/

			// frame.uncallable();
			// Function `uncallable` Is Blocked

			System.out.printf("Value Of Variable `i` is: %d\n\n", frame.i);
			// Output Value Of `i` was from Base Class Instead Of Extended Class

			// System.out.println(frame.newi);
			// Variable `newi` Is Blocked Either

		/***************************
		 * EXTRA TEST CASE - END
		 ***************************/
	}
}
