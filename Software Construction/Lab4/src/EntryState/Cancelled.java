package EntryState;

/**
 * 
 * ȡ��״̬��ADT
 *
 */
//immutable
public class Cancelled implements State {
	private String name = "Cancelled";
	static State instance = new Cancelled();

	// AF
	// name����״̬������
	// RI
	// name!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert name.equals("Cancelled");
	}

	/**
	 * ��ȡ״̬ʵ��
	 */
	private Cancelled() {
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
