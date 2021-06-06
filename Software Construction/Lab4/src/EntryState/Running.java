package EntryState;

/**
 * 
 * ����״̬��ADT
 *
 */
//immutable
public class Running implements State {
	private String name = "Running";
	static State instance = new Running();

	// AF
	// name����״̬������
	// RI
	// name!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert name.equals("Running");
	}

	/**
	 * ��ȡ״̬ʵ��
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
