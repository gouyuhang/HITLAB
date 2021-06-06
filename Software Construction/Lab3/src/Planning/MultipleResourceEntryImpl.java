package Planning;

import java.util.*;
/**
 * 
 *�����Դ���Žӿڵ�ʵ��
 *
 * @param <R>��Դ������
 */
public class MultipleResourceEntryImpl<R> implements MultipleResourceEntry<R>{
	private List<R> list=new ArrayList<>();
	//AF
	//list�����ŵ���Դ���б�
	//RI
	//list!=null
	//Safety from rep exposure
	//unmodified
	private void checkRep() {
		assert list!=null;
	}
	@Override
	public boolean setResource(R resource) {
		if(resource.equals(null)) {
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
		if(this.list.isEmpty()) {
			return false;
		}
		checkRep();
		return true;
	}

}
