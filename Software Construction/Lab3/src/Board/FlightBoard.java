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
 * �ɻ�ʱ�̱��ADT
 *
 */
public class FlightBoard {
	private List<FlightEntry> list = new ArrayList<>();
	private JFrame jf;
	//AF
	//list�������Ѿ�����ʱ��ļƻ�����б�
	//jf�Ǽƻ����JFrame����
	//RI
	//list!=null
	//Safety from rep exposure
	//û�з����ڲ���rep
	private void checkRep() {
		assert list!=null;
	}
	/**
	 * �½�һ������ƻ������
	 * @param list �ɺ���ƻ�����ɵ��б�
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
	 * flightboard�ĵ���������
	 * @return ����ƻ���ĵ�����
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
	 * ����ʼ��վ�ĺ���ƻ���
	 * @param location ʼ����
	 * @param ca ��ǰʱ��
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
					dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + "���� ���۱�");
		} else {
			jf = new JFrame(dateformat.format(new Date(ca.getTimeInMillis())) + " " + location + "���� ���۱�");
		}
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("ʱ��");
		columnNames.add("�����");
		columnNames.add("���-�յ�");
		columnNames.add("״̬");
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
		jt.getTableHeader().setFont(new Font("����", Font.PLAIN, 60));
		jt.setFont(new Font("����", Font.PLAIN, 60));
		jt.setRowHeight(100);
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
	/**
	 * �����յ�վ�ĺ���ƻ���
	 * @param location �յ�վ
	 * @param ca ��ǰʱ��
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
					dateformat.format(new Date(Calendar.getInstance().getTimeInMillis())) + " " + location + "���� ���۱�");
		} else {
			jf = new JFrame(dateformat.format(new Date(ca.getTimeInMillis())) + " " + location + "���� ���۱�");
		}
		Vector<Object> columnNames = new Vector<>();
		columnNames.add("ʱ��");
		columnNames.add("�����");
		columnNames.add("���-�յ�");
		columnNames.add("״̬");
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
		jt.getTableHeader().setFont(new Font("����", Font.PLAIN, 60));
		jt.setFont(new Font("����", Font.PLAIN, 60));
		jt.setRowHeight(100);
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
}
