package Model;
import java.sql.Timestamp;

public class Msg {
	public int id;
	public String nickname;
	public String message;
	public Timestamp postTime;

	public Msg(
		int id,
		String nickname,
		String message,
		Timestamp postTime
	) {
		this.id = id;
		this.nickname = nickname;
		this.message = message;
		this.postTime = postTime;
	}

	public int getId() { return id; }
	public String getNickname() { return nickname; }
	public String getMessage() { return message; }
	public Timestamp getPostTime() { return postTime; }
}
