package EntryState;

/**
 * 
 * 阻塞状态的ADT
 */
//immutable
public class Blocked implements State {
	private String name = "Blocked";
	static State instance = new Blocked();

	private Blocked() {
	};

	// AF
	// name代表状态的名称
	// RI
	// name!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert name.equals("Blocked");
	}

	/**
	 * 获取状态实例
	 */
	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		switch (cmd) {
		case "Running":
			return Running.instance;
		}
		checkRep();
		return null;
	}
}
