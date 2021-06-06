package Planning;

//mutable
/**
 * 
 * �����ӿڵ�ʵ��
 *
 */
public class BlockableEntryImpl implements BlockableEntry {
	private int times = 0;

	// AF
	// times���������Ĵ���
	// RI
	// times>=0
	// Safety from rep exposure
	// int�ǲ��ɱ���������
	private void checkRep() {
		assert times >= 0;
	}

	@Override
	public int blockTime() {
		checkRep();
		return this.times;
	}

	@Override
	public void block() {
		checkRep();
		this.times += 1;
	}

}
