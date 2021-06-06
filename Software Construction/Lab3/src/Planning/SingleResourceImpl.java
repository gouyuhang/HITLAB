package Planning;
import java.util.*;
/**
 * 
 * ������Դ�趨�Ľӿ�ʵ��
 *
 * @param <R>
 */
//mutable
public class SingleResourceImpl<R> implements SingleResourceEntry<R>{
	private  List<R> list=new ArrayList<>();
	//AF
	//���а��ŵ���Դ�γɵ��б�,�б����һ��Ԫ��Ϊ��ǰ�趨����Դ
	//RI
	//list��=null
	//Safety from rep exposure
	//Location�ǲ��ɱ���������
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
	public R getResource() {
		if(this.list.isEmpty()) {
			return null;
		}
		checkRep();
		return list.get(list.size()-1);
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
