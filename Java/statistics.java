import java.util.concurrent.*;
import java.util.Objects;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

class Global {
	public static final int
		MAX_DATA_SIZE = 1079999,
		MAX_NAME_SIZE = 100,
		MAX_THREAD_SIZE = 3000,
		TABLE_COLUMN_LENGTH = 4,
		PRE_PROCESS_NUM = 600,
		STATISTICS_STEP = 100000;

	public static int
		dataLen = 0, statisticsLen = 0, statisticsStart = PRE_PROCESS_NUM;

	public static int subStat[][][] = new int[MAX_THREAD_SIZE][MAX_NAME_SIZE][3];

	public static boolean
		concise = false,
		mt4statistics = false,
		mt4detailinfo = false;

	public static String
		detailContent[][] = new String[MAX_NAME_SIZE][2],
		data[][] = new String[MAX_DATA_SIZE][TABLE_COLUMN_LENGTH],
		statistics[][] = new String[MAX_NAME_SIZE][3];

	public static final Font
		FZJL_L = new Font("方正静蕾简体", Font.PLAIN, 76),
		FZJL_M = new Font("方正静蕾简体", Font.PLAIN, 21),
		MSYH_S = new Font("微软雅黑", Font.PLAIN, 13);
}

class Stat implements Runnable {
	public static int started = 0, finished = 0;
	public int start, end, pos;

	public void run() {
		try {
			start = Global.statisticsStart;
			end = start + Global.STATISTICS_STEP;
			pos = started; started++;

			Global.statisticsStart += Global.STATISTICS_STEP;

			if (end > Global.dataLen) end = Global.dataLen;

			for (int i = start; i < end; i++) {
				int j = 0;
				while(!Objects.equals(Character.toString(Global.data[i][1].charAt(0)),
					Global.statistics[j][0])) j++;

				Global.subStat[pos][j][Objects.equals(Global.data[i][2], "男") ? 1 : 2]++;
			}
		} finally { finished++; }
	}
}

class Detail implements Runnable {
	public static int started = 0, finished = 0;
	private int p;

	public Detail(int p) { this.p = p; }

	public void run() {
		try {
			Global.detailContent[p][0] =
			"<html><div>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"姓氏：" + Global.statistics[p][0] +

				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"总人数：" + String.valueOf(Integer.parseInt(Global.statistics[p][1]) +
					Integer.parseInt(Global.statistics[p][2])) +

				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"男生：" + Global.statistics[p][1] +

				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"女生：" + Global.statistics[p][2] +

				"<hr>" +
				"<table style='width:300px;'>" +
					"<thead>" +
						"<tr>" +
							"<td> 学号 </td>" +
							"<td> 姓名 </td>" +
							"<td> 性别 </td>" +
							"<td> 籍贯 </td>" +
						"</tr>" +
					"</thead>" +
					"<tbody>";

			Global.detailContent[p][1] =
				"姓氏：" + Global.statistics[p][0] +
				",总人数：" + String.valueOf(Integer.parseInt(Global.statistics[p][1]) +
					Integer.parseInt(Global.statistics[p][2])) +
				",男生：" + Global.statistics[p][1] +
				",女生：" + Global.statistics[p][2] +
				"\n\n" + "学号,姓名,性别,籍贯\n";

			for (int j = 0; j < Global.dataLen; j++) {
				if (Objects.equals(
						Character.toString(Global.data[j][1].charAt(0)),
						Global.statistics[p][0]
					)) {
					Global.detailContent[p][0] +=
					"<tr>" +
						"<td>" + Global.data[j][0] + "</td>" +
						"<td>" + Global.data[j][1] + "</td>" +
						"<td>" + Global.data[j][2] + "</td>" +
						"<td>" + Global.data[j][3] + "</td>" +
					"</tr>";

					Global.detailContent[p][1] +=
						Global.data[j][0] + "," +
						Global.data[j][1] + "," +
						Global.data[j][2] + "," +
						Global.data[j][3] + "\n";
				}
			}
			Global.detailContent[p][0] += "</tbody>" + "</table>" + "</div>" + "<br><br><br>";
			Global.detailContent[p][1] += "\n\n\n";
		} finally { finished++; }
	}
}

class detailFrame extends JFrame {
	public detailFrame(String html) {
		JLabel content = new JLabel(html, SwingConstants.CENTER);
		JScrollPane detail = new JScrollPane(content);
		content.setFont(Global.MSYH_S);

		add(detail);
		setTitle("姓氏统计具体数据");
		setLocation(430,70);
		setSize(475,600);
	}
}

class mainFrame extends JFrame {
	public String columnName[] = new String[Global.TABLE_COLUMN_LENGTH];
	public long startTime;

	public JTable table;
	public JButton importBtn, exportBtn;

	public mainFrame() {
		setTitle("姓氏统计小程序");
		setLocation(230,70);
		setSize(900,600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		importPrepare();
	}

	private void resetStartTime() {
		startTime = System.currentTimeMillis();
	}

	private void printDurationTime(String Info) {
		System.out.println(
			Info + ": " +
			((long)System.currentTimeMillis() - startTime) + "ms"
		);
	}

	public void importPrepare() {
		importBtn = new JButton("导入并统计数据");
		importBtn.setFont(Global.FZJL_L);
		importBtn.setFocusPainted(false);

		add(importBtn, BorderLayout.CENTER);

		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importBtn.setVisible(false);
				readAndStat(); drawInterface();
			}
		});
	}

	public void readAndStat() {
		resetStartTime();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data.csv"));

			String line = null;
			int pending = 0;

			line = reader.readLine();
			columnName = line.split(",");

			while((line = reader.readLine()) != null) {
				String it[] = line.split(",");

				Global.data[Global.dataLen][0] = it[0];
				Global.data[Global.dataLen][1] = it[1];
				Global.data[Global.dataLen][2] = it[2];
				Global.data[Global.dataLen][3] = it[3];

				Global.dataLen++; pending++;

				if (Global.dataLen == Global.PRE_PROCESS_NUM) {
					pending = 0;
					for (int i = 0; i < Global.PRE_PROCESS_NUM; i++) {
						int j = 0;
						while(j < Global.statisticsLen
							&& !Objects.equals(Character.toString(Global.data[i][1].charAt(0)), Global.statistics[j][0]))
							j++;

						int id = Objects.equals(Global.data[i][2], "男") ? 1 : 2;
						if (j < Global.statisticsLen) {
							Global.statistics[j][id] = String.valueOf(Integer.parseInt(Global.statistics[j][id]) + 1);
						} else {
							Global.statistics[Global.statisticsLen][0] = Character.toString(Global.data[i][1].charAt(0));
							Global.statistics[Global.statisticsLen][id] = "1";
							Global.statistics[Global.statisticsLen][3 - id] = "0";

							Global.statisticsLen++;
						}
					}
				}

				if (pending > Global.STATISTICS_STEP) {
					pending = 0;
					if (!Global.mt4statistics) {
						for (int i = 0; i < pending; i++) {
							int j = 0;
							while(!Objects.equals(Character.toString(Global.data[i][1].charAt(0)),
								Global.statistics[j][0])) j++;

							Global.subStat[Stat.started][j][Objects.equals(Global.data[i][2], "男") ? 1 : 2]++;
						}
						Stat.started++;
					} else {
						new Thread(new Stat()).start();
					}
				}
			}

			if (!Global.mt4statistics) {
				for (int i = 0; i < pending; i++) {
					int j = 0;
					while(!Objects.equals(Character.toString(Global.data[i][1].charAt(0)),
						Global.statistics[j][0])) j++;

					Global.subStat[Stat.started][j][Objects.equals(Global.data[i][2], "男") ? 1 : 2]++;
				}
				Stat.started++;
			} else {
				new Thread(new Stat()).start();
			}

			reader.close();
		} catch (Exception e) { e.printStackTrace(); }
		printDurationTime("Read & Statistics");
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
				return Global.dataLen;
			}

			public Object getValueAt(int row, int col) {
				return Global.data[row][col];
			}
		});

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);

		table.setAutoCreateRowSorter(true);
		table.setFont(Global.MSYH_S);
		table.setRowHeight(21);

		JScrollPane scrollpane = new JScrollPane(table);
		add(scrollpane, BorderLayout.CENTER);

		exportBtn = new JButton("导出统计数据");
		exportBtn.setFont(Global.FZJL_M);
		exportBtn.setFocusPainted(false);

		add(exportBtn, BorderLayout.NORTH);

		exportBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tot = ((Global.dataLen - Global.PRE_PROCESS_NUM) / Global.STATISTICS_STEP) + 1;

				if (Global.mt4statistics) {
					while(true)
						if (Stat.finished == tot) { break; }
						else {
							try { Thread.sleep(10); }
							catch(Exception err) { err.printStackTrace(); }
						}
				}

				for (int j = 0; j < Global.statisticsLen; j++)
						for (int i = 0; i < tot; i++) {
								Global.statistics[j][1] = String.valueOf(
										Global.subStat[i][j][1] + Integer.parseInt(Global.statistics[j][1])
								);
								Global.statistics[j][2] = String.valueOf(
										Global.subStat[i][j][2] + Integer.parseInt(Global.statistics[j][2])
								);
						}
				outputData();
			}
		});
	}

	public void outputData() {
		String
			statisticsHeader = "数据统计结果",
			statisticsMessage =
			"<html>" +
			"<h2 style='text-align:center;'>总人数： " + Global.dataLen + "</h2>" + "<hr>" +
			"<table style='width:100%;'>" + "<tbody>";

		statisticsMessage += "<tr><td><strong>姓氏</strong></td>";
		for (int i = 0; i < Global.statisticsLen; i++)
			statisticsMessage += "<td>" + Global.statistics[i][0] + "</td>";
		statisticsMessage += "</tr>";

		statisticsMessage += "<tr><td><strong>总人数</strong></td>";
		for (int i = 0; i < Global.statisticsLen; i++)
			statisticsMessage +=
			"<td>" +
				String.format("%3s", Integer.parseInt(Global.statistics[i][1])
					+ Integer.parseInt(Global.statistics[i][2])) +
			"</td>";
		statisticsMessage += "</tr>";

		statisticsMessage += "<tr><td><strong>男生</strong></td>";
		for (int i = 0; i < Global.statisticsLen; i++)
			statisticsMessage += "<td>" + String.format("%3s", Global.statistics[i][1]) + "</td>";
		statisticsMessage += "</tr>";

		statisticsMessage += "<tr><td><strong>女生</strong></td>";
		for (int i = 0; i < Global.statisticsLen; i++)
			statisticsMessage += "<td>" + String.format("%3s", Global.statistics[i][2]) + "</td>";
		statisticsMessage += "</tr>";

		statisticsMessage +=
		"</tbody>" + "</table>" + "<hr>" + "<br>" +
		"<p style='text-align:center;'>点击 【确定】 后将显示具体数据并自动打开数据导出文件</p>" +
		"<br>" + "</html>";

		UIManager.put("OptionPane.messageFont", Global.MSYH_S);
		int choice = JOptionPane.showConfirmDialog(null,
			statisticsMessage,
			statisticsHeader,
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.PLAIN_MESSAGE
		);

		try {
			if (choice == JOptionPane.OK_OPTION) {
				showDetailData();
				if (!Global.concise) {
					Process process = Runtime.getRuntime().exec("cmd /c statistics.csv");
				}
			} else {
				if (!Global.concise) {
					Process process = Runtime.getRuntime().exec("java statistics");
				}
				System.exit(0);
			}
		} catch(IOException err) {
			err.printStackTrace();
		}
	}

	public void showDetailData() throws IOException {
		resetStartTime();

		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < Global.statisticsLen; i++) es.execute(new Detail(i));

		while(true) if (Detail.finished == Global.statisticsLen) {
			printDurationTime("Generate Detailed Data");

			if (!Global.concise) {
				String detailExp = "", detailStr =
				"<html>" + "<h1 style='text-align:center'>具体统计信息</h1>" + "<br>";
				for (int i = 0; i < Global.statisticsLen; i++)
				{
					detailStr += Global.detailContent[i][0];
					detailExp += Global.detailContent[i][1];
				}
				detailStr += "</html>";

				detailFrame dF = new detailFrame(detailStr);
				dF.setVisible(true);

				FileWriter writer = new FileWriter("statistics.csv");
				writer.append(detailExp);
				writer.flush();
				writer.close();
			}
			break;
		} else { try { Thread.sleep(10); } catch(Exception err) { err.printStackTrace(); } }
	}
}

public class statistics {
	public static void main(String[] args) {
		for (String opt : args) {
			if (Objects.equals(opt, "-concise")) Global.concise = true;
			if (Objects.equals(opt, "-mt4statistics")) Global.mt4statistics = true;
			if (Objects.equals(opt, "-mt4detailinfo")) Global.mt4detailinfo = true;
		}
		mainFrame frame = new mainFrame();
		frame.setVisible(true);
	}
}
