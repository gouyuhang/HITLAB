package EntryState;

/**
 * 
 * 运行状态的ADT
 *
 */
//immutable
public class Running implements State {
	private String name = "Running";
	static State instance = new Running();

	// AF
	// name代表状态的名称
	// RI
	// name!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert name.equals("Running");
	}

	/**
	 * 获取状态实例
	 */
	private Running() {
	};

	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		switch (cmd) {
		case "Blocked":
			return Blocked.instance;
		case "Ended":
			return Ended.instance;
		}
		checkRep();
		return null;
	}
}
