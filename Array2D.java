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

		String[][] data = new String[MAX_ARRAY_SIZE][3];
		
		try { 
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));
			reader.readLine();
			String line = null;
			while((line = reader.readLine()) != null){ 
				String it[] = line.split(",");
				data[dataLen][0] = it[0];
				data[dataLen][1] = it[1];
				data[dataLen][2] = it[2];
				dataLen++;
			} 
			reader.close();
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 

		String[] columnName = {"学号", "姓名", "籍贯"};
		
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
				String statistics[][] = new String[MAX_ARRAY_SIZE][2];
				int i, j, index = 0;
				for (i = 0; i < dataLen; i++) {
					j = 0;
					while(j < index && !Objects.equals(data[i][2], statistics[j][1])) {
						j++;
					}

					if (j < index) {
						statistics[j][0] = String.valueOf(Integer.parseInt(statistics[j][0]) + 1);
					} else {
						statistics[index][0] = "1";
						statistics[index][1] = data[i][2];
						index++;
					}
				}

				try {
					String csvFile = "statistics.csv";
					FileWriter writer = new FileWriter(csvFile);
					writer.append("籍贯,人数\n");
					System.out.println("学生信息统计数据如下：");
					for (i = 0; i < index; i++) {
						writer.append(statistics[i][1] + ',' + statistics[i][0] + '\n');
						System.out.println("籍贯：" + statistics[i][1] + "\t人数：" + statistics[i][0]);
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
