package App;

import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import APIs.PlanningEntryAPIs;
import APIs.Strategy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Planning.*;
import Resource.*;
import Location.*;
import Timeslot.*;
import Board.*;

/**
 * 
 * TrainScheduleApp������
 *
 */
public class TrainScheduleApp {
	private List<TrainResource> resource = new ArrayList<>();
	private List<Location> location = new ArrayList<>();
	private List<TrainEntry> list = new ArrayList<>();
	private JFrame planningUi;
	private JFrame locationUi;
	private JFrame resourceUi;
	private JFrame mainFrame;
	private JFrame checkUi;
	private JFrame boardUi;
	private JFrame allocateRUi;
	private JFrame allocateLUi;
	private JFrame timeUi;

	// AF
	// resource�������г�����б�
	// location�������л�վ���б�
	// list���������г��ƻ�����б�
	// RI
	// resource list location��=null
	// Safety from rep exposure
	// û�з����κ��ڲ���rep
	private void checkRep() {
		assert resource != null;
		assert location != null;
		assert list != null;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrainScheduleApp window = new TrainScheduleApp();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TrainScheduleApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(1500, 2000));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("�𳵹���ϵͳ");
		Container contentPane = mainFrame.getContentPane();
		contentPane.setPreferredSize(new Dimension(400, 300));
		contentPane.setLayout(new GridLayout(4, 2));
		JButton b1 = new JButton("������");
		b1.addActionListener((e) -> {
			resourceUi();
		});
		b1.setFont(new Font("����", Font.PLAIN, 60));
		JButton b2 = new JButton("�����վ");
		b2.setFont(new Font("����", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			locationUi();
		});
		JButton b9 = new JButton("�½�һ�����г�");
		b9.setFont(new Font("����", Font.PLAIN, 50));
		b9.addActionListener((e) -> {
			timeUi();
		});
		JButton b3 = new JButton("�����г�״̬");
		b3.setFont(new Font("����", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			planningUi();
		});
		JButton b4 = new JButton("��ѯ���ų�ͻ");
		b4.addActionListener((e) -> {
			PlanningEntryAPIs<TrainResource> api = new PlanningEntryAPIs<TrainResource>();
			boolean flag = true;
			if (this.list.size() >= 2) {
				if (api.checkLocationConflict(this.list)) {
					JOptionPane.showMessageDialog(null, "����λ�ó�ͻ");
					flag = false;
				}
				if (api.checkResourceConflict(this.list)) {
					flag = false;
					JOptionPane.showMessageDialog(null, "������Դ��ͻ");
				}
			}
			if (flag == true)
				JOptionPane.showMessageDialog(null, "�����ڳ�ͻ");
		});
		b4.setFont(new Font("����", Font.PLAIN, 60));
		JButton b5 = new JButton("��ѯ���ó�����г�");
		b5.setFont(new Font("����", Font.PLAIN, 40));
		b5.addActionListener((e) -> {
			checkUi();
		});
		JButton b6 = new JButton("Ϊ�г����䳵��");
		b6.setFont(new Font("����", Font.PLAIN, 60));
		b6.addActionListener((e) -> {
			allocateRUi();
		});
		JButton b7 = new JButton("Ϊ�г��趨������վ");
		b7.setFont(new Font("����", Font.PLAIN, 50));
		b7.addActionListener((e) -> {
			allocateLUi();
		});
		JButton b8 = new JButton("��ǰ�г��ƻ���");
		b8.setFont(new Font("����", Font.PLAIN, 60));
		b8.addActionListener((e) -> {
			boardUi();
		});

		contentPane.add(b1);
		contentPane.add(b2);
		contentPane.add(b9);
		contentPane.add(b3);
		contentPane.add(b4);
		contentPane.add(b5);
		contentPane.add(b6);
		contentPane.add(b7);
		contentPane.add(b8);
		mainFrame.pack();
		mainFrame.setVisible(true);
		checkRep();
	}

	/**
	 * ������Դ�Ľ���
	 */
	private void resourceUi() {
		resourceUi = new JFrame();
		resourceUi.setPreferredSize(new Dimension(1500, 2000));
		resourceUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextField plane = new JTextField();
		plane.setFont(new Font("����", Font.PLAIN, 60));
		JTextField type = new JTextField();
		type.setFont(new Font("����", Font.PLAIN, 60));
		JTextField seat = new JTextField();
		seat.setFont(new Font("����", Font.PLAIN, 60));
		JTextField age = new JTextField();
		age.setFont(new Font("����", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(5, 2));
		JLabel j1 = new JLabel("Train number: ", SwingConstants.LEFT);
		j1.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(plane);
		JLabel j2 = new JLabel("Type Name: ", SwingConstants.LEFT);
		j2.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j2);
		northPanel.add(type);
		JLabel j3 = new JLabel("Seat Number: ", SwingConstants.LEFT);
		j3.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j3);
		northPanel.add(seat);
		JLabel j4 = new JLabel("Year Number: ", SwingConstants.LEFT);
		j4.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j4);
		northPanel.add(age);
		JTextArea out = new JTextArea();
		out.setEditable(false);
		out.setFont(new Font("����", Font.PLAIN, 60));
		JButton adding = new JButton("���");
		adding.setFont(new Font("����", Font.PLAIN, 60));
		adding.addActionListener((e) -> {
			boolean s1 = true;
			if (plane.getText().trim().equals("") || type.getText().trim().equals("")
					|| seat.getText().trim().equals("") || age.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "������Ϣ����");
				s1 = false;
			}
			if (s1 == true) {
				TrainResource r = new TrainResource(plane.getText(), type.getText(), seat.getText(), age.getText());
				this.resource.add(r);
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
			}
		});

		JButton showing = new JButton("��ʾȫ��");
		showing.setFont(new Font("����", Font.PLAIN, 60));
		showing.addActionListener((e) -> {
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < this.resource.size(); ++i) {
				output.append(this.resource.get(i).trainNumber() + "," + this.resource.get(i).type() + ","
						+ this.resource.get(i).passengerNumber() + "," + this.resource.get(i).year() + "\n");
			}
			out.setText(output.toString());
		});
		northPanel.add(adding);
		northPanel.add(showing);
		JScrollPane centerPanel = new JScrollPane(out);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));
		JLabel j5 = new JLabel("Type Name: ", SwingConstants.LEFT);
		j5.setFont(new Font("����", Font.PLAIN, 60));
		JTextField text = new JTextField();
		text.setFont(new Font("����", Font.PLAIN, 60));
		JButton deleting = new JButton("ɾ��");
		deleting.setFont(new Font("����", Font.PLAIN, 60));
		deleting.addActionListener((e) -> {
			if (text.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "����ɾ������Դ");
			} else {
				boolean flag = false;
				for (int i = 0; i < this.resource.size(); ++i) {
					if (this.resource.get(i).trainNumber().equals(text.getText())) {
						this.resource.remove(i);
						flag = true;
					}
				}
				if (flag == true) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(null, "ɾ����Դ������");
				}
			}
		});
		southPanel.add(j5);
		southPanel.add(text);
		southPanel.add(deleting);
		resourceUi.add(northPanel, BorderLayout.NORTH);
		resourceUi.add(centerPanel, BorderLayout.CENTER);
		resourceUi.add(southPanel, BorderLayout.SOUTH);
		resourceUi.pack();
		resourceUi.setVisible(true);

	}

	/**
	 * �����վ�Ľ���
	 */
	private void locationUi() {
		locationUi = new JFrame();
		locationUi.setPreferredSize(new Dimension(1500, 2000));
		locationUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextField name = new JTextField();
		name.setFont(new Font("����", Font.PLAIN, 60));
		JTextField lng = new JTextField();
		lng.setFont(new Font("����", Font.PLAIN, 60));
		JTextField lat = new JTextField();
		lat.setFont(new Font("����", Font.PLAIN, 60));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel j1 = new JLabel("Location Name: ", SwingConstants.LEFT);
		j1.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(name);
		JLabel j2 = new JLabel("Location Longtitude: ", SwingConstants.LEFT);
		j2.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j2);
		northPanel.add(lng);
		JLabel j3 = new JLabel("Location Latitude: ", SwingConstants.LEFT);
		j3.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(j3);
		northPanel.add(lat);
		JTextArea out = new JTextArea();
		out.setEditable(false);
		out.setFont(new Font("����", Font.PLAIN, 60));
		JButton adding = new JButton("���");
		adding.setFont(new Font("����", Font.PLAIN, 60));
		adding.addActionListener((e) -> {
			if (name.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "������Ϣ����");
			} else {
				Location l = Location.getInstance("train", name.getText(), lng.getText(), lat.getText(), true);
				this.location.add(l);
				JOptionPane.showMessageDialog(null, "��ӳɹ�");
			}
		});

		JButton showing = new JButton("��ʾȫ��");
		showing.setFont(new Font("����", Font.PLAIN, 60));
		showing.addActionListener((e) -> {
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < this.location.size(); ++i) {
				output.append(this.location.get(i).getName() + "\n");
			}
			out.setText(output.toString());
		});
		northPanel.add(adding);
		northPanel.add(showing);
		JScrollPane centerPanel = new JScrollPane(out);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));
		JLabel j5 = new JLabel("Location Name: ", SwingConstants.LEFT);
		j5.setFont(new Font("����", Font.PLAIN, 60));
		JTextField text = new JTextField();
		text.setFont(new Font("����", Font.PLAIN, 60));
		JButton deleting = new JButton("ɾ��");
		deleting.setFont(new Font("����", Font.PLAIN, 60));
		deleting.addActionListener((e) -> {
			if (text.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "����ɾ������Դ");
			} else {
				boolean flag = false;
				for (int i = 0; i < this.location.size(); ++i) {
					if (this.location.get(i).getName().equals(text.getText())) {
						this.location.remove(i);
						flag = true;
					}
				}
				if (flag == true) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(null, "ɾ��λ�ò�����");
				}
			}
		});
		southPanel.add(j5);
		southPanel.add(text);
		southPanel.add(deleting);
		locationUi.add(northPanel, BorderLayout.NORTH);
		locationUi.add(centerPanel, BorderLayout.CENTER);
		locationUi.add(southPanel, BorderLayout.SOUTH);
		locationUi.pack();
		locationUi.setVisible(true);

	}

	/**
	 * ����ƻ���Ľ���
	 */
	private void planningUi() {
		for (int i = 0; i < this.list.size(); ++i) {
			if (this.list.get(i).getLocation().size() == this.list.get(i).getTime().size() + 1
					&& this.list.get(i).resourceIsSet() && this.list.get(i).currentState().contentEquals("Waiting")) {
				this.list.get(i).stateMove("Allocated");
			}
		}
		planningUi = new JFrame();
		planningUi.setPreferredSize(new Dimension(1500, 2000));
		planningUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setFont(new Font("����", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(6, 3));
		JLabel label1 = new JLabel("Train Name: ", SwingConstants.LEFT);
		label1.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label2 = new JLabel("Train Name: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label3 = new JLabel("Train Name: ", SwingConstants.LEFT);
		label3.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label4 = new JLabel("Train Name: ", SwingConstants.LEFT);
		label4.setFont(new Font("����", Font.PLAIN, 60));
		JTextField blockPlan = new JTextField();
		blockPlan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField startPlan = new JTextField();
		startPlan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField cancelPlan = new JTextField();
		cancelPlan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField endPlan = new JTextField();
		endPlan.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label5 = new JLabel("Train Name: ", SwingConstants.LEFT);
		label5.setFont(new Font("����", Font.PLAIN, 60));
		JTextField check = new JTextField();
		check.setFont(new Font("����", Font.PLAIN, 60));
		JButton b2 = new JButton("����");
		b2.setFont(new Font("����", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(startPlan.getText())) {
					if (this.list.get(i).stateMove("Running") == false) {
						JOptionPane.showMessageDialog(null, "����ʧ��");
					} else {
						this.list.get(i).stateMove("Running");
						JOptionPane.showMessageDialog(null, "�����ɹ�");
						break;
					}
				}
			}
		});
		JButton b3 = new JButton("ȡ��");
		b3.setFont(new Font("����", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(cancelPlan.getText())) {
					if (this.list.get(i).stateMove("Cancelled") == false) {
						JOptionPane.showMessageDialog(null, "ȡ��ʧ��");
					} else {
						this.list.get(i).stateMove("Cancelled");
						JOptionPane.showMessageDialog(null, "ȡ���ɹ�");
						break;
					}
				}
			}
		});
		JButton b4 = new JButton("����");
		b4.setFont(new Font("����", Font.PLAIN, 60));
		b4.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(endPlan.getText())) {
					if (this.list.get(i).stateMove("Ended") == false) {
						JOptionPane.showMessageDialog(null, "����ʧ��");
					} else {
						this.list.get(i).stateMove("Ended");
						JOptionPane.showMessageDialog(null, "�����ɹ�");
						break;
					}
				}
			}
		});
		JButton b5 = new JButton("��ʾȫ��");
		b5.setFont(new Font("����", Font.PLAIN, 60));
		b5.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.list.size(); ++i) {
				sb.append(this.list.get(i).getName() + "->" + this.list.get(i).currentState() + "\n");
			}
			txt.setText(sb.toString());
		});
		JButton b6 = new JButton("����");
		b6.setFont(new Font("����", Font.PLAIN, 60));
		b6.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(blockPlan.getText())) {
					if (this.list.get(i).stateMove("Blocked") == false) {
						JOptionPane.showMessageDialog(null, "����ʧ��");
					} else {
						this.list.get(i).stateMove("Blocked");
						JOptionPane.showMessageDialog(null, "����ɹ�");
						break;
					}
				}
			}
		});
		JButton b7 = new JButton("��ѯ");
		b7.setFont(new Font("����", Font.PLAIN, 60));
		b7.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(check.getText()))
					sb.append(this.list.get(i).getName() + "->" + this.list.get(i).currentState() + "\n");
			}
			txt.setText(sb.toString());
		});
		northPanel.add(label2);
		northPanel.add(startPlan);
		northPanel.add(b2);
		northPanel.add(label3);
		northPanel.add(cancelPlan);
		northPanel.add(b3);
		northPanel.add(label4);
		northPanel.add(endPlan);
		northPanel.add(b4);
		northPanel.add(label1);
		northPanel.add(blockPlan);
		northPanel.add(b6);
		northPanel.add(label5);
		northPanel.add(check);
		northPanel.add(b7);
		northPanel.add(b5);
		JScrollPane centerPanel = new JScrollPane(txt);
		planningUi.add(northPanel, BorderLayout.NORTH);
		planningUi.add(centerPanel, BorderLayout.CENTER);
		planningUi.pack();
		planningUi.setVisible(true);
	}

	/**
	 * ��ѯǰ��ƻ���͹�����Դ�ƻ���Ľ���
	 */
	private void checkUi() {
		checkUi = new JFrame();
		checkUi.setPreferredSize(new Dimension(1500, 2000));
		checkUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setFont(new Font("����", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 3));

		JLabel label2 = new JLabel("������: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label3 = new JLabel("����ѯ�г�: ", SwingConstants.LEFT);
		label3.setFont(new Font("����", Font.PLAIN, 60));
		JTextField resource = new JTextField();
		resource.setFont(new Font("����", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("����", Font.PLAIN, 60));

		JButton b1 = new JButton("��ѯ");
		b1.setFont(new Font("����", Font.PLAIN, 60));
		b1.addActionListener((e) -> {
			StringBuilder sb1 = new StringBuilder();
			if (resource.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				for (int i = 0; i < this.list.size(); ++i) {
					if (this.list.get(i).resourceIsSet() == false) {
						continue;
					} else {
						for (TrainResource rr : this.list.get(i).getSortedResourceList()) {
							if (rr.trainNumber().equals(resource.getText())) {
								sb1.append(this.list.get(i).getName() + "\n");
							}
						}
					}

				}
				txt.setText(sb1.toString());
			}
		});
		JButton b2 = new JButton("��ǰ�����");
		b2.setFont(new Font("����", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			StringBuilder sb2 = new StringBuilder();
			if (resource.getText().trim().equals("") || plan.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			}
			boolean flag1 = false;
			boolean flag2 = false;
			int i, j;
			for (i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			for (j = 0; j < this.resource.size(); ++j) {
				if (this.resource.get(j).trainNumber().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				PlanningEntryAPIs<TrainResource> api = new PlanningEntryAPIs<TrainResource>();
				PlanningEntry c3 = api.findPreEntryPerResource(Strategy.getInstance("FindFast"), this.resource.get(j),
						this.list.get(i), this.list);
				sb2.append(c3.getName());
				txt.setText(sb2.toString());
			}
		});
		JButton b3 = new JButton("��ǰ�����");
		b3.setFont(new Font("����", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			StringBuilder sb3 = new StringBuilder();
			if (resource.getText().trim().equals("") || plan.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			}
			boolean flag1 = false;
			boolean flag2 = false;
			int i, j;
			for (i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			for (j = 0; j < this.resource.size(); ++j) {
				if (this.resource.get(j).trainNumber().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				PlanningEntryAPIs<TrainResource> api = new PlanningEntryAPIs<TrainResource>();
				PlanningEntry c3 = api.findPreEntryPerResource(Strategy.getInstance("FindLatest"), this.resource.get(j),
						this.list.get(i), this.list);
				sb3.append(c3.getName());
				txt.setText(sb3.toString());
			}
		});
		northPanel.add(label2);
		northPanel.add(resource);
		northPanel.add(b1);
		northPanel.add(label3);
		northPanel.add(plan);
		northPanel.add(b2);
		northPanel.add(b3);
		JScrollPane centerPanel = new JScrollPane(txt);
		checkUi.add(northPanel, BorderLayout.NORTH);
		checkUi.add(centerPanel, BorderLayout.CENTER);
		checkUi.pack();
		checkUi.setVisible(true);
	}

	// ���ӻ�������
	private void boardUi() {
		boardUi = new JFrame();
		boardUi.setPreferredSize(new Dimension(1000, 1000));
		boardUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		for (int i = 0; i < this.list.size(); ++i) {
			if (this.list.get(i).locIsSet() && this.list.get(i).timeIsSet() && this.list.get(i).resourceIsSet()
					&& this.list.get(i).currentState().contentEquals("Waiting")) {
				this.list.get(i).stateMove("Allocated");
			}
		}
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("ʼ��վ: ", SwingConstants.LEFT);
		label1.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label2 = new JLabel("�յ�վ: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label3 = new JLabel("��ͣվ: ", SwingConstants.LEFT);
		label3.setFont(new Font("����", Font.PLAIN, 60));
		JTextField startL = new JTextField();
		startL.setFont(new Font("����", Font.PLAIN, 60));
		JTextField endL = new JTextField();
		endL.setFont(new Font("����", Font.PLAIN, 60));
		JTextField blockL = new JTextField();
		blockL.setFont(new Font("����", Font.PLAIN, 60));
		JButton b1 = new JButton("��ѯ");
		b1.addActionListener((e) -> {
			if (startL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				TrainBoard b = new TrainBoard(this.list);
				b.visualize(startL.getText());
			}
		});
		b1.setFont(new Font("����", Font.PLAIN, 60));
		JButton b2 = new JButton("��ѯ");
		b2.setFont(new Font("����", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			if (endL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				TrainBoard b = new TrainBoard(this.list);
				b.visualize2(endL.getText());
			}
		});
		JButton b3 = new JButton("��ѯ");
		b3.setFont(new Font("����", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			if (blockL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "��ѯʧ��");
			} else {
				TrainBoard b = new TrainBoard(this.list);
				b.visualize3(blockL.getText());
			}
		});
		northPanel.add(label1);
		northPanel.add(startL);
		northPanel.add(b1);
		northPanel.add(label2);
		northPanel.add(endL);
		northPanel.add(b2);
		northPanel.add(label3);
		northPanel.add(blockL);
		northPanel.add(b3);
		boardUi.add(northPanel, BorderLayout.NORTH);
		boardUi.pack();
		boardUi.setVisible(true);
	}

	/**
	 * ���䳵��Ľ���
	 */
	private void allocateRUi() {
		allocateRUi = new JFrame();
		allocateRUi.setPreferredSize(new Dimension(1000, 1000));
		allocateRUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("�г�: ", SwingConstants.LEFT);
		label1.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label2 = new JLabel("����: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField plane = new JTextField();
		plane.setFont(new Font("����", Font.PLAIN, 60));
		JButton b1 = new JButton("���");
		b1.addActionListener((e) -> {

			if (plan.getText().trim().contentEquals("") || plane.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			}
			boolean flag1 = false;
			boolean flag2 = false;
			int i, j;
			for (i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			for (j = 0; j < this.resource.size(); ++j) {
				if (this.resource.get(j).trainNumber().equals(plane.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			} else {
				this.list.get(i).setResource(this.resource.get(j));
				JOptionPane.showMessageDialog(null, "���ųɹ�");
			}
		});
		b1.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(label1);
		northPanel.add(plan);
		northPanel.add(label2);
		northPanel.add(plane);
		northPanel.add(b1);
		allocateRUi.add(northPanel, BorderLayout.NORTH);
		allocateRUi.pack();
		allocateRUi.setVisible(true);
	}

	/**
	 * �����վ�Ľ���
	 */
	private void allocateLUi() {
		allocateLUi = new JFrame();
		allocateLUi.setPreferredSize(new Dimension(1000, 1000));
		allocateLUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("�г�: ", SwingConstants.LEFT);
		label1.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label2 = new JLabel("������: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("����", Font.PLAIN, 60));
		JButton b1 = new JButton("����");
		b1.addActionListener((e) -> {
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| start.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			}
			boolean flag1 = false;
			boolean flag2 = false;
			int i, j;
			for (i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			for (j = 0; j < this.location.size(); ++j) {
				if (this.location.get(j).getName().equals(start.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false
					|| this.list.get(i).getLocation().size() == this.list.get(i).getTime().size() + 1) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
			} else {
				this.list.get(i).setLocation(this.location.get(j));
				JOptionPane.showMessageDialog(null, "���ųɹ�");
			}
		});
		b1.setFont(new Font("����", Font.PLAIN, 60));
		northPanel.add(label1);
		northPanel.add(plan);
		northPanel.add(label2);
		northPanel.add(start);
		northPanel.add(b1);
		allocateLUi.add(northPanel, BorderLayout.NORTH);
		allocateLUi.pack();
		allocateLUi.setVisible(true);
	}

	/**
	 * �½��ƻ���ͷ���ʱ��Ľ���
	 */
	private void timeUi() {
		timeUi = new JFrame();
		timeUi.setPreferredSize(new Dimension(1000, 1000));
		timeUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel label1 = new JLabel("�г�: ", SwingConstants.LEFT);
		label1.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label2 = new JLabel("����ʱ��: ", SwingConstants.LEFT);
		label2.setFont(new Font("����", Font.PLAIN, 60));
		JLabel label3 = new JLabel("ͣ��ʱ��: ", SwingConstants.LEFT);
		label3.setFont(new Font("����", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("����", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("����", Font.PLAIN, 60));
		JTextField end = new JTextField();
		end.setFont(new Font("����", Font.PLAIN, 60));
		JButton b1 = new JButton("����");
		b1.addActionListener((e) -> {
			boolean success = true;
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| end.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}

			Timeslot t = new Timeslot();
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			try {

				Calendar startT = Calendar.getInstance();
				startT.setTime(f1.parse(start.getText()));
				t.setStart(startT.get(Calendar.YEAR), startT.get(Calendar.MONTH), startT.get(Calendar.DATE),
						startT.get(Calendar.HOUR_OF_DAY), startT.get(Calendar.MINUTE));

			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			try {
				Date d2 = f1.parse(end.getText());
				Calendar endT = Calendar.getInstance();
				endT.setTime(d2);
				t.setEnd(endT.get(Calendar.YEAR), endT.get(Calendar.MONTH), endT.get(Calendar.DATE),
						endT.get(Calendar.HOUR_OF_DAY), endT.get(Calendar.MINUTE));
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			TrainEntry f = PlanningEntry.getTrainInstance();
			f.begin();

			if (f.addTime(t) == false) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			if (success == true) {
				f.setName(plan.getText());
				f.addTime(t);
				this.list.add(f);
				JOptionPane.showMessageDialog(null, "���ųɹ�");
			}
		});
		b1.setFont(new Font("����", Font.PLAIN, 60));
		JButton b2 = new JButton("��ʱ��");
		b2.setFont(new Font("����", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			boolean success = true;
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| end.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}

			Timeslot t = new Timeslot();
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			try {

				Calendar startT = Calendar.getInstance();
				startT.setTime(f1.parse(start.getText()));
				t.setStart(startT.get(Calendar.YEAR), startT.get(Calendar.MONTH), startT.get(Calendar.DATE),
						startT.get(Calendar.HOUR_OF_DAY), startT.get(Calendar.MINUTE));

			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			try {
				Date d2 = f1.parse(end.getText());
				Calendar endT = Calendar.getInstance();
				endT.setTime(d2);
				t.setEnd(endT.get(Calendar.YEAR), endT.get(Calendar.MONTH), endT.get(Calendar.DATE),
						endT.get(Calendar.HOUR_OF_DAY), endT.get(Calendar.MINUTE));
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			boolean flag1 = false;
			int j;
			for (j = 0; j < this.list.size(); ++j) {
				if (this.list.get(j).getName().contentEquals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			if (this.list.get(j).addTime(t) == false || flag1 == false) {
				JOptionPane.showMessageDialog(null, "����ʧ��");
				success = false;
			}
			if (success == true && flag1 == true) {
				this.list.get(j).addTime(t);
				JOptionPane.showMessageDialog(null, "���ųɹ�");
			}
		});
		northPanel.add(label1);
		northPanel.add(plan);
		northPanel.add(label2);
		northPanel.add(start);
		northPanel.add(label3);
		northPanel.add(end);
		northPanel.add(b1);
		northPanel.add(b2);
		timeUi.add(northPanel, BorderLayout.NORTH);
		timeUi.pack();
		timeUi.setVisible(true);
	}

}
