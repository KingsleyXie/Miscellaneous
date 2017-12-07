import java.sql.*;
import java.io.File;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class init {
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document config = db.parse(new File("config.xml"));

			String database = config.getElementsByTagName("database").item(0).getTextContent();
			String username = config.getElementsByTagName("username").item(0).getTextContent();
			String password = config.getElementsByTagName("password").item(0).getTextContent();

			Class.forName("com.mysql.jdbc.Driver");
			String conURL =
				"jdbc:mysql://localhost:3306/" + database +
				"?useUnicode=true&characterEncoding=UTF-8";
			Connection con = DriverManager.getConnection(conURL, username, password);

			Statement s = con.createStatement();
			String query =
				"CREATE TABLE `forum`(" +
					"`ID` INTEGER NOT NULL AUTO_INCREMENT," +
					"`nickname` VARCHAR(30) NOT NULL," +
					"`message` VARCHAR(200) NOT NULL," +
					"`postTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
					"PRIMARY KEY(`ID`)" +
				") ENGINE = InnoDB DEFAULT CHARSET = utf8;";
			s.executeUpdate(query);

			query =
				"INSERT INTO " +
					"`forum` (`nickname`, `message`)" +
				"VALUES" +
					"('香港记者', '江主席，你觉得董先生连任，好不好啊？')," +
					"('香港记者', '中央也资辞他吗？')," +
					"('江主席', '当然啦！')," +
					"('香港记者', '那为什么这么早就决定了，而不考虑别的人选了？')," +
					"('江主席', '[正在落座，没有回答]')," +
					"('江主席', '没听说过')," +
					"('香港记者', '是彭定康说的。')," +
					"('江主席', '你们媒体千万要注意了，不要见着风是得雨')," +
					"('江主席', '假使这些完全无中生有的东西，你再帮他说一遍，你等于——你也有责任吧？')," +
					"('香港记者', '现在那么早，你们就说支持董先生，会不会给人一种感觉——就是内定呀——是钦点董先生呀？')," +
					"('江主席', '没有，没有任何这个意思！')," +
					"('江主席', '还是按照香港的、按照基本法、按照选举法——去产生……')," +
					"('香港记者', '但是你们能不能……')," +
					"('江主席', '刚才你们问我呀，我可回答你说“无可奉告”。')," +
					"('江主席', '但是你们又不高兴，那怎么办？！')," +
					"('香港记者', '但董先生……')," +
					"('江主席', '我讲的意思，不是钦点他当下一任。')," +
					"('江主席', '你问我资辞不资辞，我说资辞。我就明确可以告诉你。')," +
					"('江主席', '但是你们啊，你们——我感觉你们新闻界，还要学习一个')," +
					"('江主席', '你们非常熟悉西方的那一套的理论，但是你们毕竟还图样!')," +
					"('江主席', '明白这意思吗？')," +
					"('江主席', '我告诉你们，我是身经百战了！ 见得多了！')," +
					"('江主席', '西方的哪个国家我没去过？')," +
					"('江主席', '你们要知道，美国的华莱士')," +
					"('江主席', '那比你们不知要高到哪里去了！')," +
					"('江主席', '嗯，我跟他谈笑风声。')," +
					"('江主席', '就是说媒体呀，还是要提高自己的姿势水平。')," +
					"('江主席', '识得唔识得啊？')," +
					"('江主席', '唉，我也替你们着急呀，真的。')," +
					"('江主席', '但是问来问去的问题呀，都图森破~ sometimes 拿衣服！')," +
					"('江主席', '我很抱歉，我今天是作为一个长者，跟你们讲的！ 我不是新闻工作者，但是我见得太多啦')," +
					"('江主席', '刚才我很想啊，就我每次碰到你们，我就想到——中国有句话叫“闷声发大财”。');";
			s.executeUpdate(query);

			s.close(); con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
