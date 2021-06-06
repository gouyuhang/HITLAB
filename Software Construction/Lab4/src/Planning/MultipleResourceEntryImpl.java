package Planning;

import java.util.*;

/**
 * 
 * �����Դ���Žӿڵ�ʵ��
 *
 * @param <R>��Դ������
 */
public class MultipleResourceEntryImpl<R> implements MultipleResourceEntry<R> {
	private List<R> list = new ArrayList<>();

	// AF
	// list�����ŵ���Դ���б�
	// RI
	// list!=null
	// Safety from rep exposure
	// unmodified
	private void checkRep() {
		assert list != null;
		if (!this.list.isEmpty()) {
			for (int i = 0; i < this.list.size(); ++i) {
				for (int j = i + 1; j < this.list.size(); ++j) {
					assert !this.list.get(i).equals(this.list.get(j));
				}
			}
		}
	}

	@Override
	public boolean setResource(R resource) {
		if (resource == null) {
			return false;
		}
		this.list.add(resource);
		checkRep();
		return true;
	}

	@Override
	public List<R> getSortedResourceList() {
		checkRep();
		return Collections.unmodifiableList(this.list);

	}

	@Override
	public boolean resourceIsSet() {
		if (this.list.isEmpty()) {
			return false;
		}
		checkRep();
		return true;
	}

}
