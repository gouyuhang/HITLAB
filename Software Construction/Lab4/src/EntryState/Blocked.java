package EntryState;

/**
 * 
 * ����״̬��ADT
 */
//immutable
public class Blocked implements State {
	private String name = "Blocked";
	static State instance = new Blocked();

	private Blocked() {
	};

	// AF
	// name����״̬������
	// RI
	// name!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert name.equals("Blocked");
	}

	/**
	 * ��ȡ״̬ʵ��
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
