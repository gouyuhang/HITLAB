package Planning;
import Location.*;
/**
 * 
 * ����λ�ð��ŵĽӿ�
 *
 */
public interface SingleLocationEntry {
	/**
	 * �趨λ��
	 * @param l λ�ö���
	 * @return �Ƿ��趨�ɹ�
	 */
	public boolean setLocation(Location l);
	/**
	 * ��ȡ��ǰ�趨��λ��
	 * @return ��ǰλ��
	 */
	public Location getLocation();
	/**
	 * λ���Ƿ��Ѿ��趨
	 * @return ��/��
	 */
	public boolean locIsSet();
}
