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
 * CourseCalendarApp主程序
 *
 */
public class CourseCalendarApp {
	private List<CourseResource> resource = new ArrayList<>();
	private List<Location> location = new ArrayList<>();
	private List<CourseEntry> list = new ArrayList<>();
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
	// resource代表所有教师的列表
	// location代表所有教室的列表
	// list代表所有课程计划项的列表
	// RI
	// resource list location！=null
	// Safety from rep exposure
	// 没有返回任何内部的rep
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
					CourseCalendarApp window = new CourseCalendarApp();
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
	public CourseCalendarApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(1500, 2000));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("课程管理系统");
		Container contentPane = mainFrame.getContentPane();
		contentPane.setPreferredSize(new Dimension(400, 300));
		contentPane.setLayout(new GridLayout(4, 2));
		JButton b1 = new JButton("管理教师");
		b1.addActionListener((e) -> {
			resourceUi();
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b2 = new JButton("管理教室");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			locationUi();
		});
		JButton b9 = new JButton("新建一个新课程");
		b9.setFont(new Font("宋体", Font.PLAIN, 50));
		b9.addActionListener((e) -> {
			timeUi();
		});
		JButton b3 = new JButton("管理课程状态");
		b3.setFont(new Font("宋体", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			planningUi();
		});
		JButton b4 = new JButton("查询安排冲突");
		b4.addActionListener((e) -> {
			PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
			boolean flag = true;
			if (this.list.size() >= 2) {
				if (api.checkLocationConflict(this.list)) {
					JOptionPane.showMessageDialog(null, "存在位置冲突");
					flag = false;
				}
				if (api.checkResourceConflict(this.list)) {
					flag = false;
					JOptionPane.showMessageDialog(null, "存在资源冲突");
				}
			}
			if (flag == true)
				JOptionPane.showMessageDialog(null, "不存在冲突");
		});
		b4.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b5 = new JButton("查询共用教师的课程");
		b5.setFont(new Font("宋体", Font.PLAIN, 40));
		b5.addActionListener((e) -> {
			checkUi();
		});
		JButton b6 = new JButton("为课程分配教师");
		b6.setFont(new Font("宋体", Font.PLAIN, 60));
		b6.addActionListener((e) -> {
			allocateRUi();
		});
		JButton b7 = new JButton("为课程设定教室");
		b7.setFont(new Font("宋体", Font.PLAIN, 50));
		b7.addActionListener((e) -> {
			allocateLUi();
		});
		JButton b8 = new JButton("当前课程表");
		b8.setFont(new Font("宋体", Font.PLAIN, 60));
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
	 * 管理资源界面
	 */
	private void resourceUi() {
		resourceUi = new JFrame();
		resourceUi.setPreferredSize(new Dimension(1500, 2000));
		resourceUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextField plane = new JTextField();
		plane.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField type = new JTextField();
		type.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField seat = new JTextField();
		seat.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField age = new JTextField();
		age.setFont(new Font("宋体", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(5, 2));
		JLabel j1 = new JLabel("ID: ", SwingConstants.LEFT);
		j1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(plane);
		JLabel j2 = new JLabel("Teacher Name: ", SwingConstants.LEFT);
		j2.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j2);
		northPanel.add(type);
		JLabel j3 = new JLabel("Sex: ", SwingConstants.LEFT);
		j3.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j3);
		northPanel.add(seat);
		JLabel j4 = new JLabel("Position: ", SwingConstants.LEFT);
		j4.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j4);
		northPanel.add(age);
		JTextArea out = new JTextArea();
		out.setEditable(false);
		out.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton adding = new JButton("添加");
		adding.setFont(new Font("宋体", Font.PLAIN, 60));
		adding.addActionListener((e) -> {
			boolean s1 = true;
			if (plane.getText().trim().equals("") || type.getText().trim().equals("")
					|| seat.getText().trim().equals("") || age.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "输入信息错误");
				s1 = false;
			}
			if (s1 == true) {
				CourseResource r = new CourseResource(plane.getText(), type.getText(), seat.getText(), age.getText());
				this.resource.add(r);
				JOptionPane.showMessageDialog(null, "添加成功");
			}
		});

		JButton showing = new JButton("显示全部");
		showing.setFont(new Font("宋体", Font.PLAIN, 60));
		showing.addActionListener((e) -> {
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < this.resource.size(); ++i) {
				output.append(this.resource.get(i).id() + "," + this.resource.get(i).name() + ","
						+ this.resource.get(i).sex() + "," + this.resource.get(i).position() + "\n");
			}
			out.setText(output.toString());
		});
		northPanel.add(adding);
		northPanel.add(showing);
		JScrollPane centerPanel = new JScrollPane(out);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));
		JLabel j5 = new JLabel("Teacher ID: ", SwingConstants.LEFT);
		j5.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField text = new JTextField();
		text.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton deleting = new JButton("删除");
		deleting.setFont(new Font("宋体", Font.PLAIN, 60));
		deleting.addActionListener((e) -> {
			if (text.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "不能删除空资源");
			} else {
				boolean flag = false;
				for (int i = 0; i < this.resource.size(); ++i) {
					if (this.resource.get(i).id().equals(text.getText())) {
						this.resource.remove(i);
						flag = true;
					}
				}
				if (flag == true) {
					JOptionPane.showMessageDialog(null, "删除成功");
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(null, "删除资源不存在");
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
	 * 管理位置界面
	 */
	private void locationUi() {
		locationUi = new JFrame();
		locationUi.setPreferredSize(new Dimension(1500, 2000));
		locationUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextField name = new JTextField();
		name.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField lng = new JTextField();
		lng.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField lat = new JTextField();
		lat.setFont(new Font("宋体", Font.PLAIN, 60));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 2));
		JLabel j1 = new JLabel("Location Name: ", SwingConstants.LEFT);
		j1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(name);
		JTextArea out = new JTextArea();
		out.setEditable(false);
		out.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton adding = new JButton("添加");
		adding.setFont(new Font("宋体", Font.PLAIN, 60));
		adding.addActionListener((e) -> {
			if (name.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "输入信息错误");
			} else {
				Location l = Location.getInstance("course", name.getText(), null, null, false);
				this.location.add(l);
				JOptionPane.showMessageDialog(null, "添加成功");
			}
		});

		JButton showing = new JButton("显示全部");
		showing.setFont(new Font("宋体", Font.PLAIN, 60));
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
		j5.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField text = new JTextField();
		text.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton deleting = new JButton("删除");
		deleting.setFont(new Font("宋体", Font.PLAIN, 60));
		deleting.addActionListener((e) -> {
			if (text.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "不能删除空资源");
			} else {
				boolean flag = false;
				for (int i = 0; i < this.location.size(); ++i) {
					if (this.location.get(i).getName().equals(text.getText())) {
						this.location.remove(i);
						flag = true;
					}
				}
				if (flag == true) {
					JOptionPane.showMessageDialog(null, "删除成功");
				}
				if (flag == false) {
					JOptionPane.showMessageDialog(null, "删除位置不存在");
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
	 * 管理计划项界面
	 */
	private void planningUi() {
		for (int i = 0; i < this.list.size(); ++i) {
			if (this.list.get(i).locIsSet() && this.list.get(i).timeIsSet() && this.list.get(i).resourceIsSet()
					&& this.list.get(i).currentState().contentEquals("Waiting")) {
				this.list.get(i).stateMove("Allocated");
			}
		}
		planningUi = new JFrame();
		planningUi.setPreferredSize(new Dimension(1500, 2000));
		planningUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setFont(new Font("宋体", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(5, 3));
		JLabel label5 = new JLabel("Course Name: ", SwingConstants.LEFT);
		label5.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField check = new JTextField();
		check.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("Course Name: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("Course Name: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label4 = new JLabel("Course Name: ", SwingConstants.LEFT);
		label4.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField startPlan = new JTextField();
		startPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField cancelPlan = new JTextField();
		cancelPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField endPlan = new JTextField();
		endPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b2 = new JButton("启动");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(startPlan.getText())) {
					if (this.list.get(i).stateMove("Running") == false) {
						JOptionPane.showMessageDialog(null, "启动失败");
					} else {
						this.list.get(i).stateMove("Running");
						JOptionPane.showMessageDialog(null, "启动成功");
						break;
					}
				}
			}
		});
		JButton b3 = new JButton("取消");
		b3.setFont(new Font("宋体", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(cancelPlan.getText())) {
					if (this.list.get(i).stateMove("Cancelled") == false) {
						JOptionPane.showMessageDialog(null, "取消失败");
					} else {
						this.list.get(i).stateMove("Cancelled");
						JOptionPane.showMessageDialog(null, "取消成功");
						break;
					}
				}
			}
		});
		JButton b4 = new JButton("结束");
		b4.setFont(new Font("宋体", Font.PLAIN, 60));
		b4.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(endPlan.getText())) {
					if (this.list.get(i).stateMove("Ended") == false) {
						JOptionPane.showMessageDialog(null, "结束失败");
					} else {
						this.list.get(i).stateMove("Cancelled");
						JOptionPane.showMessageDialog(null, "结束成功");
						break;
					}
				}
			}
		});
		JButton b5 = new JButton("显示全部");
		b5.setFont(new Font("宋体", Font.PLAIN, 60));
		b5.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.list.size(); ++i) {
				sb.append(this.list.get(i).getName() + "->" + this.list.get(i).currentState() + "\n");
			}
			txt.setText(sb.toString());
		});
		JButton b6 = new JButton("查询");
		b6.setFont(new Font("宋体", Font.PLAIN, 60));
		b6.addActionListener((e) -> {
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
		northPanel.add(label5);
		northPanel.add(check);
		northPanel.add(b6);
		northPanel.add(b5);
		JScrollPane centerPanel = new JScrollPane(txt);
		planningUi.add(northPanel, BorderLayout.NORTH);
		planningUi.add(centerPanel, BorderLayout.CENTER);
		planningUi.pack();
		planningUi.setVisible(true);
	}

	/**
	 * 管理前序计划项界面
	 */
	private void checkUi() {
		checkUi = new JFrame();
		checkUi.setPreferredSize(new Dimension(1500, 2000));
		checkUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setFont(new Font("宋体", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 3));

		JLabel label2 = new JLabel("教师名称: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("待查询课程: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField resource = new JTextField();
		resource.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("宋体", Font.PLAIN, 60));

		JButton b1 = new JButton("查询");
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		b1.addActionListener((e) -> {
			StringBuilder sb1 = new StringBuilder();
			if (resource.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				for (int i = 0; i < this.list.size(); ++i) {
					if (this.list.get(i).resourceIsSet() == false) {
						continue;
					} else {
						if (this.list.get(i).getResource().id().equals(resource.getText())) {
							sb1.append(this.list.get(i).getName() + "\n");
						}
					}

				}
				txt.setText(sb1.toString());
			}
		});
		JButton b2 = new JButton("查前序随机");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			StringBuilder sb2 = new StringBuilder();
			if (resource.getText().trim().equals("") || plan.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
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
				if (this.resource.get(j).id().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
				PlanningEntry c3 = api.findPreEntryPerResource(Strategy.getInstance("FindFast"), this.resource.get(j),
						this.list.get(i), this.list);
				sb2.append(c3.getName());
				txt.setText(sb2.toString());
			}
		});
		JButton b3 = new JButton("查前序最近");
		b3.setFont(new Font("宋体", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			StringBuilder sb3 = new StringBuilder();
			if (resource.getText().trim().equals("") || plan.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
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
				if (this.resource.get(j).id().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
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

	/**
	 * 管理可视化界面
	 */
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
		northPanel.setLayout(new GridLayout(1, 3));
		JLabel label1 = new JLabel("教室: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField startL = new JTextField();
		startL.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("查询");
		b1.addActionListener((e) -> {
			if (startL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				ClassBoard b = new ClassBoard(this.list);
				b.visualize(startL.getText());
			}
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(label1);
		northPanel.add(startL);
		northPanel.add(b1);
		boardUi.add(northPanel, BorderLayout.NORTH);
		boardUi.pack();
		boardUi.setVisible(true);
	}

	/**
	 * 分配资源界面
	 */
	private void allocateRUi() {
		allocateRUi = new JFrame();
		allocateRUi.setPreferredSize(new Dimension(1000, 1000));
		allocateRUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("课程: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("教师id: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plane = new JTextField();
		plane.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("安排");
		b1.addActionListener((e) -> {

			if (plan.getText().trim().contentEquals("") || plane.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "安排失败");
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
				if (this.resource.get(j).id().equals(plane.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "安排失败");
			} else {
				this.list.get(i).setResource(this.resource.get(j));
				JOptionPane.showMessageDialog(null, "安排成功");
			}
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
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
	 * 分配位置界面
	 */
	private void allocateLUi() {
		allocateLUi = new JFrame();
		allocateLUi.setPreferredSize(new Dimension(1000, 1000));
		allocateLUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("课程: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("教室: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("安排");
		b1.addActionListener((e) -> {
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| start.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "安排失败");
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
				if (this.location.get(i).getName().equals(start.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "安排失败");
			} else {
				this.list.get(i).setLocation(this.location.get(j));
				JOptionPane.showMessageDialog(null, "安排成功");
			}
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
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
	 * 新建计划项界面和设定时间
	 */
	private void timeUi() {
		timeUi = new JFrame();
		timeUi.setPreferredSize(new Dimension(1000, 1000));
		timeUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel label1 = new JLabel("课程: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("上课时间: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("下课时间: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField end = new JTextField();
		end.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("安排");
		b1.addActionListener((e) -> {
			boolean success = true;
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| end.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "安排失败");
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
				JOptionPane.showMessageDialog(null, "安排失败");
				success = false;
			}
			try {
				Date d2 = f1.parse(end.getText());
				Calendar endT = Calendar.getInstance();
				endT.setTime(d2);
				t.setEnd(endT.get(Calendar.YEAR), endT.get(Calendar.MONTH), endT.get(Calendar.DATE),
						endT.get(Calendar.HOUR_OF_DAY), endT.get(Calendar.MINUTE));
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "安排失败");
				success = false;
			}
			CourseEntry f = PlanningEntry.getCourseInstance();
			f.begin();

			if (f.setTime(t) == false) {
				JOptionPane.showMessageDialog(null, "安排失败");
				success = false;
			}
			if (success == true) {
				f.setName(plan.getText());
				f.setTime(t);
				this.list.add(f);
				JOptionPane.showMessageDialog(null, "安排成功");
			}
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(label1);
		northPanel.add(plan);
		northPanel.add(label2);
		northPanel.add(start);
		northPanel.add(label3);
		northPanel.add(end);
		northPanel.add(b1);
		timeUi.add(northPanel, BorderLayout.NORTH);
		timeUi.pack();
		timeUi.setVisible(true);
	}

}
