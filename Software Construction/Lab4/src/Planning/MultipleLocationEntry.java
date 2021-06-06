package Planning;

/**
 * 多个位置安排接口
 */
import java.util.List;
import Location.*;

public interface MultipleLocationEntry {
	/**
	 * 获取计划项的位置是否已经设定
	 * 
	 * @return 是/否
	 */
	public boolean locIsSet();

	/**
	 * 添加位置
	 * 
	 * @param l 火车站/教室/飞机场
	 * @return 是否添加成功
	 */
	public boolean setLocation(Location l);

	/**
	 * 获取所有的已安排的车站
	 * 
	 * @return 已安排的车站列表
	 */
	public List<Location> getLocation();
}
