package Planning;

/**
 * 
 * 计划项共同操作的接口
 *
 */
public interface PlanningEntry {
	/**
	 * 静态工厂方法
	 * 
	 * @return 获取一个新的航班计划项
	 */
	public static FlightEntry getFlightInstance() {
		return new FlightEntry();
	}

	/**
	 * 静态工厂方法
	 * 
	 * @return 获取一个新的课程计划项
	 */
	public static CourseEntry getCourseInstance() {
		return new CourseEntry();
	}

	/**
	 * 静态工厂方法
	 * 
	 * @return 获取一个新的列车计划项
	 */
	public static TrainEntry getTrainInstance() {
		return new TrainEntry();
	}

	/**
	 * 给计划项设定名字
	 * 
	 * @param name 计划项的名字
	 * @return 设定是否成功
	 */
	public boolean setName(String name);

	/**
	 * 获取计划项的名字
	 * 
	 * @return 计划项的名字
	 */
	public String getName();

	/**
	 * 使一个计划项进入初始状态(Waiting)
	 */
	public void begin();

	/**
	 * 计划项的状态转移
	 * 
	 * @param cmd 计划项转移的指令
	 * @return 计划项的状态是否转换成功
	 */
	public boolean stateMove(String cmd);

	/**
	 * 获取计划项的当前状态
	 * 
	 * @return 计划项的状态以字符串显示
	 */
	public String currentState();
}
