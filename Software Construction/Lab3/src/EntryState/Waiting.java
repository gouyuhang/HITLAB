package EntryState;
/**
 * 
 * �ȴ�״̬��ADT
 *
 */
//immutable
public class Waiting implements State {
	private String name="Waiting";
	public static State instance = new Waiting();
	//AF
	//name����״̬������
	//RI
	//name!=null
	//Safety from rep exposure
	//String�ǲ��ɱ���������
	private void checkRep() {
		assert name=="Waiting";
	}
	/**
	 * ��ȡ״̬ʵ��
	 */
	private Waiting() {} ;
	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		switch(cmd) {
		case "Allocated":
			return Allocated.instance;
		case "Cancelled":
			return Cancelled.instance;
		}
		checkRep();
		return null;
	}
}
