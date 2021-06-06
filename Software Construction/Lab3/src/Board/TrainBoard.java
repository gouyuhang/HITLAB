package Board;

import java.awt.BorderLayout;
import java.awt.Font;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.*;
import Planning.TrainEntry;
/**
 * 
 * �г��ƻ����ADT
 *
 */
//mutable
public class TrainBoard {
	private List<TrainEntry> list=new ArrayList<>();
	//AF
	//list��������ʱ���λ���Ѿ��趨�õ��г��ƻ�����б�
	//RI
	//list!=null
	//Safety from rep exposure
	//û�з����κ΃Ȳ���rep
	private void checkRep() {
		assert list!=null;
	}
	/**
	 * �½�һ��trainboard��
	 * @param list ��һ���г��ƻ����γɵ��б�
	 */
	public TrainBoard(List<TrainEntry> list) {
		
		for(TrainEntry c:list) {
			if(c.timeIsSet()&&c.locIsSet()) {
				this.list.add(c);
			}
		}
	}
	/**
	 * trainboard�ĵ�����
	 * @return trainboard��iterator
	 */
	public Iterator<TrainEntry> iterator() {
		Collections.sort(this.list, new Comparator<TrainEntry>() {
	        @Override
	        public int compare(TrainEntry o1, TrainEntry o2) {
	        return o1.getTime().get(0).start().compareTo(o2.getTime().get(0).start());
	        }
	    });
		checkRep();
		return this.list.iterator();
	}
	/**
	 * ʼ��վ���г���
	 * @param location ʼ��վ
	 */
	public void visualize(String location) {
		Collections.sort(this.list, new Comparator<TrainEntry>() {
	        @Override
	        public int compare(TrainEntry o1, TrainEntry o2) {
	        return o1.getTime().get(0).start().compareTo(o2.getTime().get(0).start());
	        }
	    });
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JFrame jf=new JFrame(dateformat.format(new Date(Calendar.getInstance().getTimeInMillis()))
				+" "+location+"��վ ��վ��");
		Vector<Object> columnNames=new Vector<>() ;
		columnNames.add("ʱ��");
		columnNames.add("�г���");
		columnNames.add("���-�յ�");
		columnNames.add("״̬");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<TrainEntry> iterator=this.list.iterator();
		
		while(iterator.hasNext()) {
			Vector<String> v=new Vector<String>();
			TrainEntry c=iterator.next();
			Calendar calendar=Calendar.getInstance();
			Calendar startTime=c.getTime().get(0).start();
			int minute1=(int)(calendar.getTimeInMillis()-startTime.getTimeInMillis())/60000;
			//Calendar endTime=c.getTime().get(c.getTime().size()-1).end();			
				if(c.getLocation().get(0).getName().equals(location)) {
					if(Math.abs(minute1)<=60) {
						v.add(startTime.get(Calendar.HOUR_OF_DAY)+":"+startTime.get(Calendar.MINUTE));
						v.add(c.getName());
						v.add(c.getLocation().get(0).getName()+"->"+
								c.getLocation().get(c.getLocation().size()-1).getName());
						v.add(c.currentState());
						rowData.add(v);
					}
				}
			
		}
		JTable jt=new JTable(rowData,columnNames);
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
	/**
	 * �յ�վ���г���
	 * @param location �յ�վ
	 */
	public void visualize2(String location) {
		Collections.sort(this.list, new Comparator<TrainEntry>() {
	        @Override
	        public int compare(TrainEntry o1, TrainEntry o2) {
	        return o1.getTime().get(o1.getTime().size()-1).end().
	        		compareTo(o2.getTime().get(o2.getTime().size()-1).end());
	        }
	    });
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JFrame jf=new JFrame(dateformat.format(new Date(Calendar.getInstance().getTimeInMillis()))
				+" "+location+"��վ ��վ��");
		Vector<Object> columnNames=new Vector<>() ;
		columnNames.add("ʱ��");
		columnNames.add("�г���");
		columnNames.add("���-�յ�");
		columnNames.add("״̬");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<TrainEntry> iterator=this.list.iterator();
		
		while(iterator.hasNext()) {
			Vector<String> v=new Vector<String>();
			TrainEntry c=iterator.next();
			Calendar calendar=Calendar.getInstance();
			Calendar endTime=c.getTime().get(c.getTime().size()-1).end();
			int minute1=(int)(calendar.getTimeInMillis()-endTime.getTimeInMillis())/60000;
				if(c.getLocation().get(c.getLocation().size()-1).getName().equals(location)) {
					if(Math.abs(minute1)<=60) {
						v.add(endTime.get(Calendar.HOUR_OF_DAY)+":"+endTime.get(Calendar.MINUTE));
						v.add(c.getName());
						v.add(c.getLocation().get(0).getName()+"->"+
								c.getLocation().get(c.getLocation().size()-1).getName());
						v.add(c.currentState());
						rowData.add(v);
					}
				}		
		}
		JTable jt=new JTable(rowData,columnNames);
		jt.setBounds(150, 150, 150, 150);
		jt.setRowHeight(100);
		jt.getTableHeader().setFont(new Font("����", Font.PLAIN, 60));
		jt.setFont(new Font("����", Font.PLAIN, 60));
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
	/**
	 * ��ͣվ���г���
	 * @param location ��ͣվ
	 */
	public void visualize3(String location) {
		Collections.sort(this.list, new Comparator<TrainEntry>() {
	        @Override
	        public int compare(TrainEntry o1, TrainEntry o2) {
	        return o1.getTime().get(o1.getTime().size()-1).end().
	        		compareTo(o2.getTime().get(o2.getTime().size()-1).end());
	        }
	    });
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		JFrame jf=new JFrame(dateformat.format(new Date(Calendar.getInstance().getTimeInMillis()))
				+" "+location+"��վ ��ͣ��");
		Vector<Object> columnNames=new Vector<>() ;
		columnNames.add("��ͣʱ��");
		columnNames.add("�г���");
		columnNames.add("���-�յ�");
		columnNames.add("״̬");
		Vector<Vector<String>> rowData = new Vector<Vector<String>>();
		Iterator<TrainEntry> iterator=this.list.iterator();
		
		while(iterator.hasNext()) {
			Vector<String> v=new Vector<String>();
			TrainEntry c=iterator.next();
			Calendar calendar=Calendar.getInstance();
			for(int i=1;i<c.getLocation().size()-1;++i) {
				if(c.getLocation().get(i).getName().equals(location)) {
					Calendar start=c.getTime().get(i-1).end();
					Calendar end=c.getTime().get(i).start();
					int minute1=(int)(calendar.getTimeInMillis()-start.getTimeInMillis())/60000;
					if(Math.abs(minute1)<=60) {
						v.add(start.get(Calendar.HOUR_OF_DAY)+":"+start.get(Calendar.MINUTE)+"-"
						+end.get(Calendar.HOUR_OF_DAY)+":"+end.get(Calendar.MINUTE));
						v.add(c.getName());
						v.add(c.getLocation().get(0).getName()+"->"+
								c.getLocation().get(c.getLocation().size()-1).getName());
						v.add(c.currentState());
						rowData.add(v);
						break;
					}
				}
			}
			
		}
		JTable jt=new JTable(rowData,columnNames);
		jt.setBounds(150, 150, 150, 150);
		jt.setRowHeight(100);
		jt.getTableHeader().setFont(new Font("����", Font.PLAIN, 60));
		jt.setFont(new Font("����", Font.PLAIN, 60));
		JScrollPane scrollPane = new JScrollPane(jt);
		jf.getContentPane().add(scrollPane, BorderLayout.CENTER);
		jf.setVisible(true);
		jf.setSize(1000, 1000);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		checkRep();
	}
}
