package Board;

import java.util.*;
import java.text.*;
import java.util.List;
import Planning.*;
import javax.swing.*;
import java.awt.*;

/**
 * 
 * �γ̱�ADT
 *
 */
//mutable
public class ClassBoard {
	private List<CourseEntry> list = new ArrayList<>();

	// AF
	// list�������е��Ѿ��趨��λ�ú�ʱ��ļƻ���
	// RI
	// list!=null
	// Safety from rep exposure
	// û�з����κε�rep
	private void checkRep() {
		assert list != null;
	}

	/**
	 * �½�һ���γ̱����
	 * 
	 * @param list ��һЩ�мƻ���ɵļ���
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
	 * classboard��ĵ���������
	 * 
	 * @return ����ĵ�����
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
	 * ĳ���ҵĿγ̱�Ŀ��ӻ�
	 * 
	 * @param location ��Ҫ��ȡ�Ŀγ̱�Ľ�����Ϣ
	 */
	public void visualize(String location) {
		Collections.sort(this.list, new Comparator<CourseEntry>() {
			public int compare(CourseEntry o1, CourseEntry o2) {
				return o1.time().start().compareTo(o2.time().start());
			}
		});
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JFrame jf = new JFrame(
				dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + " �γ̱�");
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("ʱ��");
		columnNames.add("�γ�");
		columnNames.add("��ʦ");
		columnNames.add("״̬");
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
		jt.getTableHeader().setFont(new Font("����", Font.PLAIN, 60));
		jt.setRowHeight(100);
		jt.setFont(new Font("����", Font.PLAIN, 60));
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
}
