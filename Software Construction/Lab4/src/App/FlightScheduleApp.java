package App;

import java.util.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
import javax.swing.*;
import APIs.PlanningEntryAPIs;
import APIs.Strategy;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Planning.*;
import Resource.*;
import Location.*;
import Timeslot.*;
import Board.*;
import Exception.*;

/**
 * 
 * FlightScheduleApp主程序
 *
 */
public class FlightScheduleApp {
	private List<FlightResource> resource = new ArrayList<>();
	private List<Location> location = new ArrayList<>();
	private List<FlightEntry> list = new ArrayList<>();
	private JFrame planningUi;
	private JFrame locationUi;
	private JFrame resourceUi;
	private JFrame mainFrame;
	private JFrame checkUi;
	private JFrame boardUi;
	private JFrame allocateRUi;
	private JFrame allocateLUi;
	private JFrame timeUi;
	private JFrame txtUi;
	private JFrame logUi;
	private final static Logger logger = Logger.getLogger("Flight Schedule Log");

	// AF
	// resource代表所有飞机的列表
	// location代表所有飞机场的列表
	// list代表所有飞机计划项的列表
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
					FlightScheduleApp window = new FlightScheduleApp();
					Locale.setDefault(new Locale("en", "EN"));
					logger.setLevel(Level.INFO);
					FileHandler fileHandler = new FileHandler("src/log/FlightScheduleLog.txt", true);
					fileHandler.setFormatter(new SimpleFormatter());
					logger.addHandler(fileHandler);
					window.mainFrame.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FlightScheduleApp() {
		initialize();
	}

	/**
	 * 检查读入的文件是否合法
	 * 
	 * @return 文件合法性
	 * @throws Exception
	 */
	public boolean check() throws NotSameAirportException, SameFlightException, ResourceConflictException {
		boolean flag = true;
		int i, j = 0;
		for (i = 0; i < this.list.size(); ++i) {
			if (flag == false) {
				break;
			}
			for (j = i + 1; j < this.list.size(); ++j) {
				if (this.list.get(i).getResource().planeNumber().equals(this.list.get(j).getResource().planeNumber())) {
					if (!this.list.get(i).getResource().equals(this.list.get(j).getResource())) {
						throw new ResourceConflictException("Resource Conflict");
					}
				}
				Pattern pattern = Pattern.compile("([A-Z]{2})([0-9]{2,4}),(.*?)");
				Matcher m1 = pattern.matcher(this.list.get(i).getName());
				Matcher m2 = pattern.matcher(this.list.get(j).getName());
				if (m1.matches() && m2.matches()) {
					int a = Integer.parseInt(m1.group(2));
					int b = Integer.parseInt(m2.group(2));
					if (m1.group(1).equals(m2.group(1)) && a == b) {
						if (m1.group(3).equals(m2.group(3))) {
							throw new SameFlightException("Same Flight");
						}
						if (!this.list.get(i).getLocation().get(0).equals(this.list.get(j).getLocation().get(0))) {
							flag = false;
							break;

						}
						if (!this.list.get(i).getLocation().get(this.list.get(i).getLocation().size() - 1).equals(
								this.list.get(j).getLocation().get(this.list.get(j).getLocation().size() - 1))) {
							flag = false;
							break;
						}
						if (this.list.get(i).time().start().get(Calendar.HOUR_OF_DAY) != this.list.get(j).time().start()
								.get(Calendar.HOUR_OF_DAY)) {
							flag = false;
							break;
						}
						if (this.list.get(i).time().start().get(Calendar.MINUTE) != this.list.get(j).time().start()
								.get(Calendar.MINUTE)) {
							flag = false;
							break;
						}
						if (this.list.get(i).time().end().get(Calendar.HOUR_OF_DAY) != this.list.get(j).time().end()
								.get(Calendar.HOUR_OF_DAY)) {
							flag = false;
							break;
						}
						if (this.list.get(i).time().end().get(Calendar.MINUTE) != this.list.get(j).time().end()
								.get(Calendar.MINUTE)) {
							flag = false;
							break;
						}
					}
				}
			}
		}
		if (flag == false) {
			throw new NotSameAirportException("Same Flight not match");
		}
		return true;
	}

	/**
	 * 读取文件并判断是否合法
	 * 
	 * @param filename 文件名
	 * @return 读入的文件是否语法正确
	 * @throws Exception 读入异常
	 */
	public boolean readfile(String filename) throws Exception {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
		BufferedReader bReader = new BufferedReader(isr);
		String line = "";
		int cntLine = 0;
		StringBuilder stringInfo = new StringBuilder();
		while ((line = bReader.readLine()) != null) {
			if (line.equals(""))
				continue;
			stringInfo.append(line + "\n");
			cntLine++;
			if (cntLine % 13 == 0) {
				if (regex(stringInfo.toString()) == false) {
					System.out.println(stringInfo);
					bReader.close();
					return false;
				}
				stringInfo = new StringBuilder();
			}
		}
		bReader.close();
		return true;
	}

	/**
	 * 正则表达式解析一个计划项语句集合
	 * 
	 * @param s 一个计划项语句集合
	 * @return 语句是否合法
	 */
	private boolean regex(String s) throws DateConflictException, MoreTimeException, FormatIncorrectException,
			SeatIncorrectException, DateFormatIncorrectException, AgeOutBoundException, MoreZeroException,
			NameIncorrectException, TypeIncorrectException, PlaneIncorrectException {
		Pattern pattern = Pattern
				.compile("Flight:(.*?),(.*?)\n" + "\\{\n" + "DepartureAirport:(.*?)\n" + "ArrivalAirport:(.*?)\n"
						+ "DepatureTime:(.*?)\n" + "ArrivalTime:(.*?)\n" + "Plane:(.*?)\n\\{\n" + "Type:(.*?)\n"
						+ "Seats:(([1-9][0-9]*){1,3})\n" + "Age:(([0-9]*)+(.[0-9]{1})?)\n" + "\\}\n" + "\\}\n");
		Matcher m;
		m = pattern.matcher(s);
		if (!m.matches()) {
			throw new FormatIncorrectException("Format Not Correct");
		}
		if (m.matches()) {
			String time = m.group(1);
			String name = m.group(2);
			//
			Pattern p1 = Pattern.compile("[A-Z]{2}[0-9]{2,4}");
			Matcher m1 = p1.matcher(name);
			if (!m1.matches()) {
				throw new NameIncorrectException("name incorrect");
			}
			String depart = m.group(3);
			String arrival = m.group(4);
			String departTime = m.group(5);
			String arriveTime = m.group(6);
			String plane = m.group(7);
			//
			Pattern p2 = Pattern.compile("[N|B]{1}[0-9]{4}");
			Matcher m2 = p2.matcher(plane);
			if (!m2.matches()) {
				throw new PlaneIncorrectException("plane incorrect");
			}
			String type = m.group(8);
			//
			Pattern p3 = Pattern.compile("[A-Za-z0-9]+");
			Matcher m3 = p3.matcher(type);
			if (!m3.matches()) {
				throw new TypeIncorrectException("type incorrect");
			}
			String seat = m.group(9);
			int seati = Integer.parseInt(seat);
			if (seati < 50 || seati > 600) {
				throw new SeatIncorrectException("Seat Incorrect");
			}
			String age = m.group(11);
			if (age.length() >= 2) {
				if (age.charAt(0) == '0' && age.charAt(1) >= '0' && age.charAt(1) <= '9') {
					throw new MoreZeroException("Age have more zero");
				}
			}
			double agei = Double.valueOf(age);
			if (agei < 0.0 || agei > 30.0) {
				throw new AgeOutBoundException("Age Out of Bound");
			}
			FlightResource r = new FlightResource(plane, type, seat, age);
			Location l1 = Location.getInstance("flight", depart, null, null, true);
			Location l2 = Location.getInstance("flight", arrival, null, null, true);
			FlightEntry f = PlanningEntry.getFlightInstance();
			f.setName(name + "," + time);
			f.setResource(r);
			f.setLocation(l1);
			f.setLocation(l2);
			f.begin();
			Timeslot t = new Timeslot();
			SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Calendar title = Calendar.getInstance();
				title.setTime(f2.parse(time));
				Calendar startT = Calendar.getInstance();
				startT.setTime(f1.parse(departTime));
				if (title.get(Calendar.YEAR) != startT.get(Calendar.YEAR)
						|| title.get(Calendar.MONTH) != startT.get(Calendar.MONTH)
						|| title.get(Calendar.DATE) != startT.get(Calendar.DATE)) {
					throw new DateConflictException("Date Conflict");
				}
				// startT.add(Calendar.MONTH, 1);
				t.setStart(startT.get(Calendar.YEAR), startT.get(Calendar.MONTH), startT.get(Calendar.DATE),
						startT.get(Calendar.HOUR_OF_DAY), startT.get(Calendar.MINUTE));
				Date d2 = f1.parse(arriveTime);
				Calendar endT = Calendar.getInstance();
				endT.setTime(d2);
				// endT.add(Calendar.MONTH, 1);
				t.setEnd(endT.get(Calendar.YEAR), endT.get(Calendar.MONTH), endT.get(Calendar.DATE),
						endT.get(Calendar.HOUR_OF_DAY), endT.get(Calendar.MINUTE));
			} catch (ParseException e1) {
				throw new DateFormatIncorrectException("Date Format Incorect");
			}
			int hours = (int) (t.end().getTimeInMillis() - t.start().getTimeInMillis()) / 3600000;
			if (hours > 24) {
				throw new MoreTimeException("More than 24 hour");
			}
			f.setTime(t);
			this.list.add(f);
			boolean flag = false;
			boolean flag2 = false;
			boolean flag3 = false;
			for (int i = 0; i < this.resource.size(); ++i) {
				if (this.resource.get(i).planeNumber().contentEquals(plane)) {
					flag = true;
					break;
				}
			}
			for (int i = 0; i < this.location.size(); ++i) {
				if (this.location.get(i).getName().contentEquals(depart)) {
					flag2 = true;
					break;
				}
			}
			for (int i = 0; i < this.location.size(); ++i) {
				if (this.location.get(i).getName().contentEquals(arrival)) {
					flag3 = true;
					break;
				}
			}
			if (flag == false) {
				this.resource.add(r);
			}
			if (flag2 == false) {
				this.location.add(l1);
			}
			if (flag3 == false) {
				this.location.add(l2);
			}
			return true;
		}
		return false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(1500, 2000));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle("航班管理系统");
		Container contentPane = mainFrame.getContentPane();
		contentPane.setPreferredSize(new Dimension(400, 300));
		contentPane.setLayout(new GridLayout(6, 2));
		JButton b1 = new JButton("管理飞机");
		b1.addActionListener((e) -> {
			resourceUi();
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b2 = new JButton("管理机场");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			locationUi();
		});
		JButton b9 = new JButton("新建一个新航班");
		b9.setFont(new Font("宋体", Font.PLAIN, 50));
		b9.addActionListener((e) -> {
			timeUi();
		});
		JButton b3 = new JButton("管理航班状态");
		b3.setFont(new Font("宋体", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			planningUi();
		});
		JButton b4 = new JButton("查询安排冲突");
		b4.addActionListener((e) -> {
			PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
			boolean f = true;
			if (this.list.size() >= 2) {
				if (api.checkLocationConflict(this.list)) {
					JOptionPane.showMessageDialog(null, "存在位置冲突");
					f = false;
				}
				if (api.checkResourceConflict(this.list)) {
					JOptionPane.showMessageDialog(null, "存在资源冲突");
					f = false;
				}
			}
			if (f == true)
				JOptionPane.showMessageDialog(null, "不存在冲突");
		});
		b4.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b5 = new JButton("查询共用飞机的航班");
		b5.setFont(new Font("宋体", Font.PLAIN, 40));
		b5.addActionListener((e) -> {
			checkUi();
		});
		JButton b6 = new JButton("为航班分配飞机");
		b6.setFont(new Font("宋体", Font.PLAIN, 60));
		b6.addActionListener((e) -> {
			allocateRUi();
		});
		JButton b7 = new JButton("为航班设定起落地");
		b7.setFont(new Font("宋体", Font.PLAIN, 50));
		b7.addActionListener((e) -> {
			allocateLUi();
		});
		JButton b8 = new JButton("当前航班计划表");
		b8.setFont(new Font("宋体", Font.PLAIN, 60));
		b8.addActionListener((e) -> {
			boardUi();
		});
		JButton b10 = new JButton("读文件");
		b10.setFont(new Font("宋体", Font.PLAIN, 60));
		b10.addActionListener((e) -> {
			txtUi();
		});
		JButton b11 = new JButton("查日志");
		b11.setFont(new Font("宋体", Font.PLAIN, 60));
		b11.addActionListener((e) -> {
			logUi();
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
		contentPane.add(b10);
		contentPane.add(b11);
		mainFrame.pack();
		mainFrame.setVisible(true);

	}

	/**
	 * 资源管理界面
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
		JLabel j1 = new JLabel("Plane Name: ", SwingConstants.LEFT);
		j1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(plane);
		JLabel j2 = new JLabel("Type Name: ", SwingConstants.LEFT);
		j2.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j2);
		northPanel.add(type);
		JLabel j3 = new JLabel("Seat Number: ", SwingConstants.LEFT);
		j3.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j3);
		northPanel.add(seat);
		JLabel j4 = new JLabel("Age Number: ", SwingConstants.LEFT);
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
				FlightResource r = new FlightResource(plane.getText(), type.getText(), seat.getText(), age.getText());
				this.resource.add(r);
				logger.logp(Level.INFO, "Entry," + plane.getText(), "addR", "success");
				JOptionPane.showMessageDialog(null, "添加成功");
			}
		});

		JButton showing = new JButton("显示全部");
		showing.setFont(new Font("宋体", Font.PLAIN, 60));
		showing.addActionListener((e) -> {
			StringBuilder output = new StringBuilder();
			for (int i = 0; i < this.resource.size(); ++i) {
				output.append(this.resource.get(i).planeNumber() + "," + this.resource.get(i).planeType() + ","
						+ this.resource.get(i).seatNumber() + "," + this.resource.get(i).planeAge() + "\n");
			}
			out.setText(output.toString());
		});
		northPanel.add(adding);
		northPanel.add(showing);
		JScrollPane centerPanel = new JScrollPane(out);
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 3));
		JLabel j5 = new JLabel("Type Name: ", SwingConstants.LEFT);
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
				boolean flag2 = true;
				try {
					for (int j = 0; j < this.list.size(); ++j) {
						if (this.list.get(j).resourceIsSet()) {
							if (this.list.get(j).getResource().planeNumber().equals(text.getText())) {
								flag2 = false;
								throw new RNotDelException("Cant Delete Resource");
							}
						}
					}
				} catch (RNotDelException ex) {
					logger.logp(Level.WARNING, "Entry,empty", "deleteR", ex.getMessage());
					JOptionPane.showMessageDialog(null, "还在使用中删除失败");
				}
				if (flag2 == true) {
					for (int i = 0; i < this.resource.size(); ++i) {
						if (this.resource.get(i).planeNumber().equals(text.getText())) {
							this.resource.remove(i);
							flag = true;
						}
					}
					if (flag == true) {
						logger.logp(Level.INFO, "Entry," + text.getText(), "deleteR", "success");
						JOptionPane.showMessageDialog(null, "删除成功");
					}
					if (flag == false) {
						JOptionPane.showMessageDialog(null, "删除资源不存在");
					}
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
		checkRep();
	}

	/**
	 * 位置管理界面
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
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel j1 = new JLabel("Location Name: ", SwingConstants.LEFT);
		j1.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j1);
		northPanel.add(name);
		JLabel j2 = new JLabel("Location Longtitude: ", SwingConstants.LEFT);
		j2.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j2);
		northPanel.add(lng);
		JLabel j3 = new JLabel("Location Latitude: ", SwingConstants.LEFT);
		j3.setFont(new Font("宋体", Font.PLAIN, 60));
		northPanel.add(j3);
		northPanel.add(lat);
		JTextArea out = new JTextArea();
		out.setEditable(false);
		out.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton adding = new JButton("添加");
		adding.setFont(new Font("宋体", Font.PLAIN, 60));
		adding.addActionListener((e) -> {
			if (name.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "输入信息错误");
			} else {
				Location l = Location.getInstance("flight", name.getText(), lng.getText(), lat.getText(), true);
				this.location.add(l);
				logger.logp(Level.INFO, "Entry," + name.getText(), "allocateL", "success");
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
				boolean flag2 = true;
				try {
					for (int j = 0; j < this.list.size(); ++j) {
						if (this.list.get(j).locIsSet()) {
							if (this.list.get(j).getLocation().get(0).getName().equals(text.getText())
									|| this.list.get(j).getLocation().get(1).getName().equals(text.getText())) {
								flag2 = false;
								throw new LNotDelException("Cant Delete Location");
							}
						}
					}
				} catch (LNotDelException ex) {
					logger.logp(Level.WARNING, "Entry,empty", "deleteL", ex.getMessage());
					JOptionPane.showMessageDialog(null, "还在使用中删除失败");
				}
				if (flag2 == true) {
					for (int i = 0; i < this.location.size(); ++i) {
						if (this.location.get(i).getName().equals(text.getText())) {
							this.location.remove(i);
							flag = true;
						}
					}
					if (flag == true) {
						logger.logp(Level.INFO, "Entry," + text.getText(), "deleteL", "success");
						JOptionPane.showMessageDialog(null, "删除成功");
					}
					if (flag == false) {
						JOptionPane.showMessageDialog(null, "删除位置不存在");
					}
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
	 * 计划项管理界面
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
		JLabel label2 = new JLabel("Plane Name: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("Plane Name: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label4 = new JLabel("Plane Name: ", SwingConstants.LEFT);
		label4.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label5 = new JLabel("Plane Name: ", SwingConstants.LEFT);
		label5.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField startPlan = new JTextField();
		startPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField cancelPlan = new JTextField();
		cancelPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField endPlan = new JTextField();
		endPlan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField check = new JTextField();
		check.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b2 = new JButton("启动");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			for (int i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(startPlan.getText())) {
					if (this.list.get(i).stateMove("Running") == false) {
						JOptionPane.showMessageDialog(null, "启动失败");
					} else {
						this.list.get(i).stateMove("Running");
						logger.logp(Level.INFO, "Entry," + startPlan.getText(), "run", "success");
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
						logger.logp(Level.INFO, "Entry," + cancelPlan.getText(), "cancel", "success");
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
						this.list.get(i).stateMove("Ended");
						logger.logp(Level.INFO, "Entry," + endPlan.getText(), "end", "success");
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
	 * 寻找前序计划项的界面
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

		JLabel label2 = new JLabel("飞机编号: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("待查询航班: ", SwingConstants.LEFT);
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
						if (this.list.get(i).getResource().planeNumber().equals(resource.getText())) {
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
				if (this.resource.get(j).planeNumber().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
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
				if (this.resource.get(j).planeNumber().equals(resource.getText())) {
					flag2 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
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
	 * 列车时刻表界面
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
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel label1 = new JLabel("始发站: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("终点站: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("时间: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField startL = new JTextField();
		startL.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField endL = new JTextField();
		endL.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField time = new JTextField();
		time.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("查询");
		b1.addActionListener((e) -> {
			if (startL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				FlightBoard b = new FlightBoard(this.list);
				b.visualize(startL.getText(), null);
			}
		});
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b2 = new JButton("查询");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			if (endL.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				FlightBoard b = new FlightBoard(this.list);
				b.visualize2(endL.getText(), null);
			}
		});
		JButton b3 = new JButton("有时间 始发查询");
		b3.setFont(new Font("宋体", Font.PLAIN, 30));
		b3.addActionListener((e) -> {
			if (startL.getText().trim().contentEquals("") || time.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH-mm");
				try {
					Calendar startT = Calendar.getInstance();
					startT.setTime(f1.parse(time.getText()));
					FlightBoard b = new FlightBoard(this.list);
					b.visualize(startL.getText(), startT);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "查询失败");
				}

			}
		});
		JButton b4 = new JButton("有时间 终点查询");
		b4.setFont(new Font("宋体", Font.PLAIN, 30));
		b4.addActionListener((e) -> {
			if (endL.getText().trim().contentEquals("") || time.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "查询失败");
			} else {
				SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH-mm");
				try {
					Calendar startT = Calendar.getInstance();
					startT.setTime(f1.parse(time.getText()));
					FlightBoard b = new FlightBoard(this.list);
					b.visualize2(endL.getText(), startT);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "查询失败");
				}

			}
		});
		northPanel.add(label1);
		northPanel.add(startL);
		northPanel.add(b1);
		northPanel.add(label2);
		northPanel.add(endL);
		northPanel.add(b2);
		northPanel.add(label3);
		northPanel.add(time);
		northPanel.add(b3);
		northPanel.add(b4);
		boardUi.add(northPanel, BorderLayout.NORTH);
		boardUi.pack();
		boardUi.setVisible(true);
	}

	/**
	 * 为计划项分配资源界面
	 */
	private void allocateRUi() {
		allocateRUi = new JFrame();
		allocateRUi.setPreferredSize(new Dimension(1000, 1000));
		allocateRUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2));
		JLabel label1 = new JLabel("航班: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("飞机: ", SwingConstants.LEFT);
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
			boolean flag3 = true;
			int i, j;
			for (i = 0; i < this.list.size(); ++i) {
				if (this.list.get(i).getName().equals(plan.getText())) {
					flag1 = true;
					break;
				}
			}
			for (j = 0; j < this.resource.size(); ++j) {
				if (this.resource.get(j).planeNumber().equals(plane.getText())) {
					flag2 = true;
					break;
				}
			}
			try {
				this.list.get(i).setResource(this.resource.get(j));
				PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
				if (this.list.size() >= 2) {
					if (api.checkResourceConflict(this.list)) {
						flag3 = false;
						throw new RNotAlloException("Cant Allocate R");
					}
				}
			} catch (RNotAlloException ex) {
				this.list.get(i).resourceRemove();
				logger.logp(Level.WARNING, "Entry," + plan.getText(), "allocateR", ex.getMessage());
				JOptionPane.showMessageDialog(null, "安排失败");
			}
			if (flag1 == false || flag2 == false) {
				JOptionPane.showMessageDialog(null, "安排失败");
			} else {
				if (flag3 == true) {
					this.list.get(i).setResource(this.resource.get(j));
					logger.logp(Level.INFO, "Entry," + plan.getText(), "allocateR", "success");
					JOptionPane.showMessageDialog(null, "安排成功");
				}
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
	 * 为计划项分配位置界面
	 */
	private void allocateLUi() {
		allocateLUi = new JFrame();
		allocateLUi.setPreferredSize(new Dimension(1000, 1000));
		allocateLUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel label1 = new JLabel("航班: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("起点: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("终点: ", SwingConstants.LEFT);
		label3.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField plan = new JTextField();
		plan.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField end = new JTextField();
		end.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("安排");
		b1.addActionListener((e) -> {
			if (plan.getText().trim().contentEquals("") || start.getText().trim().contentEquals("")
					|| start.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "安排失败");
			}
			boolean flag1 = false;
			boolean flag2 = false;
			boolean flag3 = false;
			int i, j, k;
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
			for (k = 0; k < this.location.size(); ++k) {
				if (this.location.get(k).getName().equals(end.getText())) {
					flag3 = true;
					break;
				}
			}
			if (flag1 == false || flag2 == false || flag3 == false || this.list.get(i).locIsSet()) {
				JOptionPane.showMessageDialog(null, "安排失败");
			} else {
				this.list.get(i).setLocation(this.location.get(j));
				this.list.get(i).setLocation(this.location.get(k));
				logger.logp(Level.INFO, "Entry," + plan.getText(), "allocateL", "success");
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
		allocateLUi.add(northPanel, BorderLayout.NORTH);
		allocateLUi.pack();
		allocateLUi.setVisible(true);
	}

	/**
	 * 新建新的计划项并设定时间
	 */
	private void timeUi() {
		timeUi = new JFrame();
		timeUi.setPreferredSize(new Dimension(1000, 1000));
		timeUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(4, 2));
		JLabel label1 = new JLabel("航班: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("起飞时间: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label3 = new JLabel("降落时间: ", SwingConstants.LEFT);
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
			FlightEntry f = PlanningEntry.getFlightInstance();
			f.begin();

			if (f.setTime(t) == false) {
				JOptionPane.showMessageDialog(null, "安排失败");
				success = false;
			}
			if (success == true) {
				f.setName(plan.getText());
				f.setTime(t);
				this.list.add(f);
				logger.logp(Level.INFO, "Entry," + plan.getText(), "NewPlan", "success");
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

	/**
	 * 读取文件界面
	 */
	private void txtUi() {
		txtUi = new JFrame();
		txtUi.setPreferredSize(new Dimension(1000, 1000));
		txtUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 1));
		JLabel label1 = new JLabel("文件名: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField start = new JTextField();
		start.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("读入");
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		b1.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder("src/App/txt/");
			if (start.getText().trim().contentEquals("")) {
				JOptionPane.showMessageDialog(null, "读入失败");
			} else {
				sb.append(start.getText());
				try {
					if (readfile(sb.toString()) == false) {
						this.list.clear();
						this.resource.clear();
						this.location.clear();
						JOptionPane.showMessageDialog(null, "读入失败");
					} else {
						if (check()) {
							logger.logp(Level.INFO, "Entry,empty", "readfile", "success");
							JOptionPane.showMessageDialog(null, "读入成功");
						} else {
							this.list.clear();
							this.resource.clear();
							this.location.clear();
							JOptionPane.showMessageDialog(null, "读入失败");
						}
					}
				} catch (Exception e1) {
					this.list.clear();
					this.resource.clear();
					this.location.clear();
					logger.logp(Level.WARNING, "Entry,empty", "readfile", e1.getMessage());
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		northPanel.add(label1);
		northPanel.add(start);
		northPanel.add(b1);
		txtUi.add(northPanel, BorderLayout.NORTH);
		txtUi.pack();
		txtUi.setVisible(true);
	}

	/**
	 * 日志查询界面
	 */
	private void logUi() {
		logUi = new JFrame();
		logUi.setPreferredSize(new Dimension(1500, 2000));
		logUi.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setFont(new Font("宋体", Font.PLAIN, 60));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 3));
		JLabel label1 = new JLabel("计划项名称: ", SwingConstants.LEFT);
		label1.setFont(new Font("宋体", Font.PLAIN, 60));
		JLabel label2 = new JLabel("操作: ", SwingConstants.LEFT);
		label2.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField field1 = new JTextField();
		field1.setFont(new Font("宋体", Font.PLAIN, 60));
		JTextField field2 = new JTextField();
		field2.setFont(new Font("宋体", Font.PLAIN, 60));
		JButton b1 = new JButton("查询");
		b1.setFont(new Font("宋体", Font.PLAIN, 60));
		b1.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			try {
				String filename = "src/log/FlightScheduleLog.txt";
				InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
				BufferedReader bReader = new BufferedReader(isr);
				String line;
				while ((line = bReader.readLine()) != null) {
					Pattern p1 = Pattern.compile("(.*?) Entry,(.*?) (.*?)");
					Matcher m1 = p1.matcher(line);
					if (m1.matches()) {
						if (m1.group(2).equals(field1.getText())) {
							sb.append(m1.group(1) + "," + m1.group(2) + "," + m1.group(3) + "\n");
						}
					}

				}
				bReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			txt.setText(sb.toString());
		});
		JButton b2 = new JButton("查询");
		b2.setFont(new Font("宋体", Font.PLAIN, 60));
		b2.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			try {
				String filename = "src/log/FlightScheduleLog.txt";
				InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
				BufferedReader bReader = new BufferedReader(isr);
				String line;
				while ((line = bReader.readLine()) != null) {
					Pattern p1 = Pattern.compile("(.*?) Entry,(.*?) (.*?)");
					Matcher m1 = p1.matcher(line);
					if (m1.matches()) {
						if (m1.group(3).equals(field1.getText())) {
							sb.append(m1.group(1) + "," + m1.group(2) + "," + m1.group(3) + "\n");
						}
					}

				}
				bReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			txt.setText(sb.toString());
		});
		JButton b3 = new JButton("查询全部");
		b3.setFont(new Font("宋体", Font.PLAIN, 60));
		b3.addActionListener((e) -> {
			StringBuilder sb = new StringBuilder();
			try {
				String filename = "src/log/FlightScheduleLog.txt";
				InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8");
				BufferedReader bReader = new BufferedReader(isr);
				String line;
				try {
					while ((line = bReader.readLine()) != null) {
						Pattern p1 = Pattern.compile("(.*?) Entry,(.*?) (.*?)");
						Pattern p2 = Pattern.compile("INFO: (.*?)");
						Pattern p3 = Pattern.compile("WARNING: (.*?)");
						Matcher m1 = p1.matcher(line);
						Matcher m2 = p2.matcher(line);
						Matcher m3 = p3.matcher(line);
						if (m1.matches()) {
							sb.append(m1.group(1) + "," + m1.group(2) + "," + m1.group(3));
						}
						if (m2.matches()) {
							sb.append("," + m2.group(1) + "\n");
						}
						if (m3.matches()) {
							sb.append("," + m3.group(1) + "\n");
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				bReader.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e2) {
				e2.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			txt.setText(sb.toString());
		});
		northPanel.add(label1);
		northPanel.add(field1);
		northPanel.add(b1);
		northPanel.add(label2);
		northPanel.add(field2);
		northPanel.add(b2);
		northPanel.add(b3);
		JScrollPane centerPanel = new JScrollPane(txt);
		logUi.add(northPanel, BorderLayout.NORTH);
		logUi.add(centerPanel, BorderLayout.CENTER);
		logUi.pack();
		logUi.setVisible(true);
	}
}
