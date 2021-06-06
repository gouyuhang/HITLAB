package Board;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Planning.FlightEntry;

/**
 * 
 * 飞机时刻表的ADT
 *
 */
public class FlightBoard {
	private List<FlightEntry> list = new ArrayList<>();
	private JFrame jf;
	//AF
	//list是所有已经安排时间的计划项的列表
	//jf是计划表的JFrame窗体
	//RI
	//list!=null
	//Safety from rep exposure
	//没有返回内部的rep
	private void checkRep() {
		assert list!=null;
	}
	/**
	 * 新建一个航班计划表对象
	 * @param list 由航班计划项组成的列表
	 */
	public FlightBoard(List<FlightEntry> list) {

		for (FlightEntry c : list) {
			if (c.timeIsSet() && c.locIsSet()) {
				this.list.add(c);
			}
		}
		checkRep();
	}
	/**
	 * flightboard的迭代器功能
	 * @return 航班计划表的迭代器
	 */
	public Iterator<FlightEntry> iterator() {
		Collections.sort(this.list, new Comparator<FlightEntry>() {
			@Override
			public int compare(FlightEntry o1, FlightEntry o2) {
				return o1.time().start().compareTo(o2.time().start());
			}
		});
		checkRep();
		return this.list.iterator();
	}
	/**
	 * 生成始发站的航班计划表
	 * @param location 始发地
	 * @param ca 当前时间
	 */
	public void visualize(String location, Calendar ca) {
		Collections.sort(this.list, new Comparator<FlightEntry>() {
			@Override
			public int compare(FlightEntry o1, FlightEntry o2) {
				return o1.time().start().compareTo(o2.time().start());
			}
		});
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (ca == null) {
			jf = new JFrame(
					dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + "机场 出港表");
		} else {
			jf = new JFrame(dateformat.format(new Date(ca.getTimeInMillis())) + " " + location + "机场 出港表");
		}
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("时间");
		columnNames.add("航班号");
		columnNames.add("起点-终点");
		columnNames.add("状态");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<FlightEntry> iterator = this.list.iterator();

		while (iterator.hasNext()) {
			Vector<String> v = new Vector<String>();
			FlightEntry c = iterator.next();
			Calendar calendar = Calendar.getInstance();
			if (ca != null) {
				calendar = ca;
			}

			Calendar startTime = c.time().start();
			int minute1=(int)(calendar.getTimeInMillis()-startTime.getTimeInMillis())/60000;
				if (c.getLocation().get(0).getName().equals(location)) {
					if (Math.abs(minute1)<=60) {
						v.add(startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE));
						v.add(c.getName());
						v.add(c.getLocation().get(0).getName() + "->"
								+ c.getLocation().get(c.getLocation().size() - 1).getName());
						v.add(c.currentState());
						rowData.add(v);
					}
				}
		}
		JTable jt = new JTable(rowData, columnNames);
		jt.setBounds(150, 150, 150, 150);
		jt.getTableHeader().setFont(new Font("宋体", Font.PLAIN, 60));
		jt.setFont(new Font("宋体", Font.PLAIN, 60));
		jt.setRowHeight(100);
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
	/**
	 * 生成终点站的航班计划表
	 * @param location 终点站
	 * @param ca 当前时间
	 */
	public void visualize2(String location, Calendar ca) {
		Collections.sort(this.list, new Comparator<FlightEntry>() {
			@Override
			public int compare(FlightEntry o1, FlightEntry o2) {
				return o1.time().end().compareTo(o2.time().end());
			}
		});
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (ca == null) {
			jf = new JFrame(
					dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + "机场 出港表");
		} else {
			jf = new JFrame(dateformat.format(new Date(ca.getTimeInMillis())) + " " + location + "机场 出港表");
		}
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("时间");
		columnNames.add("航班号");
		columnNames.add("起点-终点");
		columnNames.add("状态");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<FlightEntry> iterator = this.list.iterator();

		while (iterator.hasNext()) {
			Vector<String> v = new Vector<String>();
			FlightEntry c = iterator.next();
			Calendar calendar = Calendar.getInstance();
			if (ca != null) {
				calendar = ca;
			}
			Calendar endTime=c.time().end();
			int minute1=(int)(int)(calendar.getTimeInMillis()-endTime.getTimeInMillis())/60000;
				if (c.getLocation().get(c.getLocation().size() - 1).getName().equals(location)) {
					if (Math.abs(minute1)<=60) {
						v.add(endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE));
						v.add(c.getName());
						v.add(c.getLocation().get(0).getName() + "->"
								+ c.getLocation().get(c.getLocation().size() - 1).getName());
						v.add(c.currentState());
						rowData.add(v);
					}
				}


		}
		JTable jt = new JTable(rowData, columnNames);
		jt.setBounds(150, 150, 150, 150);
		jt.getTableHeader().setFont(new Font("宋体", Font.PLAIN, 60));
		jt.setFont(new Font("宋体", Font.PLAIN, 60));
		jt.setRowHeight(100);
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
}
