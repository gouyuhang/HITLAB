package Planning;

/**
 * ���λ�ð��Žӿ�
 */
import java.util.List;
import Location.*;

public interface MultipleLocationEntry {
	/**
	 * ��ȡ�ƻ����λ���Ƿ��Ѿ��趨
	 * 
	 * @return ��/��
	 */
	public boolean locIsSet();

	/**
	 * ���λ��
	 * 
	 * @param l ��վ/����/�ɻ���
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean setLocation(Location l);

	/**
	 * ��ȡ���е��Ѱ��ŵĳ�վ
	 * 
	 * @return �Ѱ��ŵĳ�վ�б�
	 */
	public List<Location> getLocation();
}
