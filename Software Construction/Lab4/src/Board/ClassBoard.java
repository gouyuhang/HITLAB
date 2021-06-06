package Board;

import java.util.*;
import java.text.*;
import java.util.List;
import Planning.*;
import javax.swing.*;
import java.awt.*;

/**
 * 
 * 课程表ADT
 *
 */
//mutable
public class ClassBoard {
	private List<CourseEntry> list = new ArrayList<>();

	// AF
	// list代表所有的已经设定好位置和时间的计划项
	// RI
	// list!=null
	// Safety from rep exposure
	// 没有返回任何的rep
	private void checkRep() {
		assert list != null;
	}

	/**
	 * 新建一个课程表对象
	 * 
	 * @param list 由一些列计划项构成的集合
	 */
	public ClassBoard(List<CourseEntry> list) {
		for (CourseEntry c : list) {
			if (c.timeIsSet() && c.locIsSet()) {
				this.list.add(c);
			}
		}
		checkRep();
	}

	/**
	 * classboard类的迭代器功能
	 * 
	 * @return 该类的迭代器
	 */
	public Iterator<CourseEntry> iterator() {
		Collections.sort(this.list, new Comparator<CourseEntry>() {
			public int compare(CourseEntry o1, CourseEntry o2) {
				return o1.time().start().compareTo(o2.time().start());
			}
		});
		checkRep();
		return this.list.iterator();
	}

	/**
	 * 某教室的课程表的可视化
	 * 
	 * @param location 需要获取的课程表的教室信息
	 */
	public void visualize(String location) {
		Collections.sort(this.list, new Comparator<CourseEntry>() {
			public int compare(CourseEntry o1, CourseEntry o2) {
				return o1.time().start().compareTo(o2.time().start());
			}
		});
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JFrame jf = new JFrame(
				dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + " 课程表");
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("时间");
		columnNames.add("课程");
		columnNames.add("教师");
		columnNames.add("状态");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<CourseEntry> iterator = this.list.iterator();

		while (iterator.hasNext()) {
			Vector<String> v = new Vector<String>();
			CourseEntry c = iterator.next();
			Calendar calendar = Calendar.getInstance();

			Calendar startTime = c.time().start();
			Calendar endTime = c.time().end();
			int minute1 = (int) (calendar.getTimeInMillis() - startTime.getTimeInMillis()) / 60000;
			if (c.getLocation().getName().equals(location)) {
				if (Math.abs(minute1) <= 60) {
					v.add(startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE) + "-"
							+ endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE));
					v.add(c.getName());
					if (c.getResource() == null) {
						v.add("empty");
					} else {
						v.add(c.getResource().name());
					}
					v.add(c.currentState());
					rowData.add(v);
				}
			}

		}
		JTable jt = new JTable(rowData, columnNames);
		jt.setBounds(150, 150, 150, 150);
		jt.getTableHeader().setFont(new Font("宋体", Font.PLAIN, 60));
		jt.setRowHeight(100);
		jt.setFont(new Font("宋体", Font.PLAIN, 60));
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
}
