import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Objects;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Array2D extends JFrame {
	final int MAX_ARRAY_SIZE = 1000;
	public int dataLen = 0;
	
	public Array2D() {
		setSize(500,600);
		setLocation(50,50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[][] data = new String[MAX_ARRAY_SIZE][4];
		
		try { 
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
			reader.readLine();
			String line = null;
			while((line = reader.readLine()) != null){ 
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

		String[] columnName = {"学号","姓名","性别","籍贯"};
		
		JTable table = new JTable(new AbstractTableModel() {
			
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

		JButton btn = new JButton("导出统计数据");
		add(btn, BorderLayout.NORTH);

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String statistics[][] = new String[MAX_ARRAY_SIZE][3];
				int i, j, index = 0;
				for (i = 0; i < dataLen; i++) {
					j = 0;
					while(j < index && !Objects.equals(data[i][3], statistics[j][0])) {
						j++;
					}

					int id = Objects.equals(data[i][2], "男") ? 1 : 2;
					if (j < index) {
						statistics[j][id] = String.valueOf(Integer.parseInt(statistics[j][id]) + 1);
					} else {
						statistics[index][0] = data[i][3];
						statistics[index][id] = "1";
						statistics[index][3 - id] = "0";
						index++;
					}
				}

				try {
					String csvFile = "statistics.csv";
					FileWriter writer = new FileWriter(csvFile);
					writer.append("籍贯,男生,女生\n");
					System.out.println("学生信息统计数据如下：");
					for (i = 0; i < index; i++) {
						writer.append(statistics[i][0] + ',' + statistics[i][1] + ',' + statistics[i][2] + '\n');
						statistics[i][1] = String.format("%3s", statistics[i][1]);
						statistics[i][2] = String.format("%3s", statistics[i][2]);
						System.out.println("籍贯：" + statistics[i][0] + "\t\t男生：" + statistics[i][1] + "\t女生：" + statistics[i][2]);
					}
					writer.flush();
					writer.close();

					JOptionPane.showMessageDialog(null, "学生籍贯统计数据已成功导出\n学生总数：" + dataLen);
					Process process = Runtime.getRuntime().exec("cmd /c statistics.csv");
				} catch(IOException err) {
					err.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		Array2D frame = new Array2D();
		frame.setVisible(true);
	}
}
