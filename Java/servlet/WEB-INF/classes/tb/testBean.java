package tb;

public class testBean {
	private String name = null;

	public testBean(String name) {
		this.name = name;
	}

	public void set(String name) {
		this.name = name;
	}

	public String get() {
		return name;
	}
}