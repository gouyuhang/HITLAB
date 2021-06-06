package EntryState;

/**
 * 
 * �ѷ�����Դ״̬��ADT
 *
 */
//immutable
public class Allocated implements State {
	private String name = "Allocated";
	static State instance = new Allocated();

	// AF
	// name����״̬������
	// RI
	// name!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert name.equals("Allocated");
	}

	/**
	 * ��ȡ״̬ʵ��
	 */
	private Allocated() {
	};

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
		case "Cancelled":
			return Cancelled.instance;
		}
		checkRep();
		return null;
	}
}
