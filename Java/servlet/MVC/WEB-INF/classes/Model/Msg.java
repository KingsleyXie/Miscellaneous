package Model;
import java.util.Date;

public class Msg {
	public int id;
	public Date postTime;
	public String nickname, message;

	public Msg(
		int id, Date postTime,
		String nickname, String message
	) {
		this.id = id; this.postTime = postTime;
		this.nickname = nickname; this.message = message;
	}

	public int getId() { return id; }
	public Date getPostTime() { return postTime; }
	public String getNickname() { return nickname; }
	public String getMessage() { return message; }
}
