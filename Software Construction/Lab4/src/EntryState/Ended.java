package EntryState;

/**
 * 
 * ��ֹ״̬��ADT
 *
 */
//immutable
public class Ended implements State {
	String name = "Ended";
	static State instance = new Ended();

	// AF
	// name����״̬������
	// RI
	// name!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert name.equals("Ended");
	}

	/**
	 * ��ȡ״̬ʵ��
	 */
	private Ended() {
	};

	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		checkRep();
		return null;
	}

}
