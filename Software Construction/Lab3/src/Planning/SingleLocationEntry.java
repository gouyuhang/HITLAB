package Planning;
import Location.*;
/**
 * 
 * 单个位置安排的接口
 *
 */
public interface SingleLocationEntry {
	/**
	 * 设定位置
	 * @param l 位置对象
	 * @return 是否设定成功
	 */
	public boolean setLocation(Location l);
	/**
	 * 获取当前设定的位置
	 * @return 当前位置
	 */
	public Location getLocation();
	/**
	 * 位置是否已经设定
	 * @return 是/否
	 */
	public boolean locIsSet();
}
