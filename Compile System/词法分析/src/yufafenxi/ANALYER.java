package yufafenxi;

import yufafenxi.keyword;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class ANALYER {
	char a[];

	public void readtext() {
		ArrayList<String> function = new ArrayList<String>();
		HashMap<String, String> map1 = new HashMap<>();
		HashMap<String, String> map2 = new HashMap<>();
		keyword k = new keyword();
		ArrayList<String> result_word = new ArrayList<String>();
		ArrayList<String> result_cato = new ArrayList<String>();
		map1 = k.define();
		map2 = k.define2();
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			int line = 1;
			bufferedReader = new BufferedReader(new FileReader("io/input.txt"));
			bufferedWriter = new BufferedWriter(new FileWriter("io/output.txt"));
			String lString;
			while ((lString = bufferedReader.readLine()) != null) {
				String temp1 = lString;
				temp1.replaceAll("\\s+", "");// 去掉一个以上的空白符，用一个空白代替
				a = temp1.toCharArray();
				analy(map1, map2, result_word, result_cato, line);
				line++;
			}
			//System.out.println(result_word);
			//System.out.println(result_cato);
			for (int i = 0, j = 0; i < result_word.size() && j < result_cato.size(); i++, j++) {
				if (result_cato.get(j).equals("INT") || result_word.get(j).equals("DOUBLE")
						|| result_word.get(j).equals("FLOAT") || result_word.get(j).equals("VOID")) {
					if (result_cato.get(i + 1).equals("ID") && result_cato.get(i + 2).equals("LEFT_PER")) {
						function.add(result_word.get(i + 1));
					}
				}
			}
			boolean zhushi = false;
			for (int i = 0, j = 0; i < result_word.size() && j < result_cato.size(); i++, j++) {
				String pattern = ".*Error.*";
				StringBuffer output = new StringBuffer();
				if (result_cato.get(j).equals("注释开始:")) {
					output.append(result_word.get(i) + " <" + "注释开始" + ",_>");
					bufferedWriter.write(output.toString() + "\n");
					output = new StringBuffer();
					zhushi = true;
				}
				if (result_cato.get(j).equals("注释结束")) {
					output.append(result_word.get(i) + " <" + "注释结束" + ",_>");
					bufferedWriter.write(output.toString() + "\n");
					output = new StringBuffer();
					zhushi = false;
					continue;
				}
				if (zhushi == false) {
					if (result_cato.get(j) == "REAL" || result_cato.get(j) == "ID" || result_cato.get(j) == "CONST"
							|| result_cato.get(j) == "OCTAL" || result_cato.get(j) == "DEX"
							|| result_cato.get(j) == "SCINO" || result_cato.get(j) == "STR_CONST"
							|| result_cato.get(j) == "CHAR_CONST") {
						if (result_cato.get(j) == "ID" && function.contains(result_word.get(i).toString())) {
							output.append(result_word.get(i) + " <" + "FUNCTION" + "," + result_word.get(i) + ">");
						}else if(Pattern.matches(pattern, result_cato.get(j))) {
							output.append(result_word.get(i) + " " +result_cato.get(j));
						}
						else {
							output.append(
									result_word.get(i) + " <" + result_cato.get(j) + "," + result_word.get(i) + ">");
						}
					} else {

						output.append(result_word.get(i) + " <" + result_cato.get(j) + ",_>");
					}
					bufferedWriter.write(output.toString() + "\n");
					output = new StringBuffer();
				}
			}
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void analy(HashMap m1, HashMap m2, ArrayList result_word, ArrayList result_cato, int line) {
		LinkedList<String> list1 = new LinkedList<String>();
		boolean end = false;
		StringBuffer temp1 = new StringBuffer();
		int state = 0;
		int i = 0;
		while (i < a.length) {
			temp1.append(a[i]);
			//System.out.println(state);
			if ((a[i] == ' ' || a[i] == ',' || a[i] == ';' || a[i] == '[' || a[i] == ']' || a[i] == '{' || a[i] == '}'
					|| a[i] == '(' || a[i] == ')'||a[i]==':') && state != 13 && state != 11) {//处理分隔符且分隔符不在字符常数或字符串中
				if (i == a.length - 1) {
					end = true;
				}
				temp1.deleteCharAt(temp1.length() - 1);
				if (!temp1.isEmpty()) {
					if (m1.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(temp1.toString().toUpperCase());
						//System.out.print(temp1.toString() + ' ' + temp1 + '\n');
					} else if (m2.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(m2.get((temp1.toString())));
						//System.out.print(temp1.toString() + ' ' + m2.get((temp1.toString())) + '\n');
					} else {
						result_word.add(temp1.toString());
						if (state == 1) {
							result_cato.add("ID");
						}
						if (state == 2) {
							result_cato.add("CONST");
						}
						if (state == 3&&!temp1.toString().equals("0")) {
							result_cato.add("OCTAL");
						}else if(state == 3&&temp1.toString().equals("0")) {
							result_cato.add("CONST");
						}
						if (state == 5) {
							result_cato.add("DEX");
						}
						if (state == 6) {
							result_cato.add("REAL");
						}
						if (state == 10) {
							result_cato.add("SCINO");
						}
						if (state == 12) {
							result_cato.add("STR_CONST");
						}
						if (state == 15) {
							result_cato.add("CHAR_CONST");
						}
						if(state==31) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Octal_Error");
						}
						if(state==51) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Dex_Error");
						}
						if(state==-1) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" ID_Error");
						}
						//System.out.print(temp1.toString() + ' ' + state + '\n');

					}
				}

				if (a[i] != ' ') {
					result_word.add(String.valueOf(a[i]));
					result_cato.add(m2.get(String.valueOf(a[i])));
					//System.out.print(String.valueOf(a[i]) + ' ' + m2.get(String.valueOf(a[i])) + '\n');
				}

				state = 0;
				temp1 = new StringBuffer();
			}

			if ((a[i] == '.') && state != 13 && state != 8 && state != 11 && state != 2) {
				if(i>=1&&Integer.valueOf(String.valueOf(a[i-1]))!=0) {//处理.的情况
					temp1.deleteCharAt(temp1.length() - 1);
					if (!temp1.isEmpty()) {
						if (m1.containsKey(temp1.toString())) {
							result_word.add(temp1.toString());
							result_cato.add(temp1.toString().toUpperCase());
							//System.out.print(temp1.toString() + ' ' + temp1 + '\n');
						} else if (m2.containsKey(temp1.toString())) {
							result_word.add(temp1.toString());
							result_cato.add(m2.get((temp1.toString())));
							//System.out.print(temp1.toString() + ' ' + m2.get((temp1.toString())) + '\n');
						} else {
							result_word.add(temp1.toString());
							if (state == 1) {
								result_cato.add("ID");
							}
							if (state == 2) {
								result_cato.add("CONST");
							}
							if (state == 3&&!temp1.toString().equals("0")) {
								result_cato.add("OCTAL");
							}else if(state == 3&&temp1.toString().equals("0")) {
								result_cato.add("CONST");
							}
							if (state == 5) {
								result_cato.add("DEX");
							}
							if (state == 6) {
								result_cato.add("REAL");
							}
							if (state == 10) {
								result_cato.add("SCINO");
							}
							if (state == 12) {
								result_cato.add("STR_CONST");
							}
							if (state == 15) {
								result_cato.add("CHAR_CONST");
							}
							if(state==31) {
								result_cato.add("line:"+Integer.valueOf(line).toString()+" Octal_Error");
							}
							if(state==51) {
								result_cato.add("line:"+Integer.valueOf(line).toString()+" Dex_Error");
							}
							if(state==-1) {
								result_cato.add("line:"+Integer.valueOf(line).toString()+" ID_Error");
							}
							//System.out.print(temp1.toString() + ' ' + state + '\n');
						}
					}

					if (a[i] != ' ') {
						result_word.add(String.valueOf(a[i]));
						result_cato.add(m2.get(String.valueOf(a[i])));
						//System.out.print(String.valueOf(a[i]) + ' ' + m2.get(String.valueOf(a[i])) + '\n');
					}

					state = 0;
					temp1 = new StringBuffer();
				}
				

			}

			if ((a[i] == '+' || (a[i] == '-') || a[i] == '*' || a[i] == '/' || a[i] == '^' || a[i] == '!' || a[i] == '%'
					|| a[i] == '&' || a[i] == '|' || a[i] == '>' || a[i] == '<' || a[i] == '=') && state != 13
					&& state != 8 && state != 11) {//运算符处理

				if (i == a.length - 1) {
					end = true;
				}
				temp1.deleteCharAt(temp1.length() - 1);
				if (!temp1.isEmpty()) {
					if (m1.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(temp1.toString().toUpperCase());
						//System.out.print(temp1.toString() + ' ' + temp1 + '\n');
					} else if (m2.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(m2.get((temp1.toString())));
						//System.out.print(temp1.toString() + ' ' + m2.get((temp1.toString())) + '\n');
					} else {
						result_word.add(temp1.toString());
						if (state == 1) {
							result_cato.add("ID");
						}
						if (state == 2) {
							result_cato.add("CONST");
						}
						if (state == 3&&!temp1.toString().equals("0")) {
							result_cato.add("OCTAL");
						}else if(state == 3&&temp1.toString().equals("0")) {
							result_cato.add("CONST");
						}
						if (state == 5) {
							result_cato.add("DEX");
						}
						if (state == 6) {
							result_cato.add("REAL");
						}
						if (state == 10) {
							result_cato.add("SCINO");
						}
						if (state == 12) {
							result_cato.add("STR_CONST");
						}
						if (state == 15) {
							result_cato.add("CHAR_CONST");
						}
						if(state==31) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Octal_Error");
						}
						if(state==51) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Dex_Error");
						}
						if(state==-1) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" ID_Error");
						}
						//System.out.print(temp1.toString() + ' ' + state + '\n');
					}
				}
				state = 0;
				temp1 = new StringBuffer();
				temp1.append(a[i]);
				StringBuffer out = new StringBuffer();
				out.append(a[i]);
				boolean n_gram = false;
				if (i < a.length - 1) {
					if (a[i + 1] == '=' || a[i + 1] == '+' || a[i + 1] == '-' || a[i + 1] == '>' || a[i + 1] == '<'
							|| a[i + 1] == '&' || a[i + 1] == '|' || a[i + 1] == '*' || a[i + 1] == '/') {
						out.append(a[i + 1]);
						n_gram = true;
						if (i + 1 == a.length - 1) {
							end = true;
						}
					}
				}

				if (n_gram) {//复合运算符 例如+=
					if (m2.containsKey(out.toString())) {
						result_word.add(out.toString());
						result_cato.add(m2.get(out.toString()));
						//System.out.print(out.toString() + ' ' + m2.get(out.toString()) + '\n');
						i += 1;
						state = 0;
						temp1 = new StringBuffer();
					} else {
						result_word.add(String.valueOf(a[i]));
						result_cato.add(m2.get(String.valueOf(a[i])));
						result_word.add(String.valueOf(a[i + 1]));
						result_cato.add(m2.get(String.valueOf(a[i + 1])));
						//System.out.print(String.valueOf(a[i]) + ' ' + m2.get(String.valueOf(a[i])) + '\n');
						//System.out.print(String.valueOf(a[i + 1]) + ' ' + m2.get(String.valueOf(a[i + 1])) + '\n');
						i += 1;
						state = 0;
						temp1 = new StringBuffer();
					}
				} else {
					result_word.add(temp1.toString());
					result_cato.add(m2.get(temp1.toString()));
					//System.out.print(temp1.toString() + ' ' + m2.get(temp1.toString()) + '\n');
					state = 0;
					temp1 = new StringBuffer();
				}

				state = 0;
				temp1 = new StringBuffer();
			}

			switch (state) {
			case 0:
				if (Character.isLetter(a[i]))//变量,关键字
					state = 1;
				if(a[i]=='_')
					state=16;
				if (Character.isDigit(a[i])) {//数识别
					if (Integer.valueOf(String.valueOf(a[i])) > 0 && Integer.valueOf(String.valueOf(a[i])) <= 9)
						state = 2;
					if (Integer.valueOf(String.valueOf(a[i])) == 0) //8进制 十六进制
						state = 3;
				}
				if ((int) a[i] == 34) {// " 识别
					state = 11;
				}
				if ((int) a[i] == 39) {// ' 识别
					state = 13;
				}
				break;
			case 1:
				if (Character.isLetter(a[i]) || Character.isDigit(a[i]) || a[i] == '_') {
					state = 1;
				}else {
					state=-1;
				}
				break;
			case 2:
				if (Character.isDigit(a[i])) {
					state = 2;
				}
				if (a[i] == '.') {
					state = 6;
				}
				if (a[i] == 'e' || a[i] == 'E') {
					state = 8;
				}
				break;
			case 3:
				if (a[i] == 'x' || a[i] == 'X')
					state = 4;
				else if (Character.isDigit(a[i])&&Integer.valueOf(String.valueOf(a[i])) >= 0 && Integer.valueOf(String.valueOf(a[i])) <= 7)
					state = 3;
				else if(a[i]=='.')
					state=6;
				else {
					state=31;//8进制错误
				}
				break;
			case 4:
				String pattern = "[A-Fa-f]";
				boolean isMatch = Pattern.matches(pattern, String.valueOf(a[i]));
				Pattern r = Pattern.compile(pattern);
				if (Character.isDigit(a[i]) || isMatch) {
					state = 5;
				}else {
					state=51;
				}
				break;
			case 5:
				String pattern1 = "[A-Fa-f]";
				boolean isMatch1 = Pattern.matches(pattern1, String.valueOf(a[i]));
				Pattern r1 = Pattern.compile(pattern1);
				if (Character.isDigit(a[i]) || isMatch1) {
					state = 5;
				}else {
					state=51;
				}
				break;
			case 6:
				if (Character.isDigit(a[i])) {
					state = 6;
				}
				if (a[i] == 'e' || a[i] == 'E') {
					state = 8;
				}
				break;
			case 8:
				if (Character.isDigit(a[i])) {
					state = 10;
				}
				if (a[i] == '+' || a[i] == '-') {
					state = 9;
				}
				break;
			case 9:
				if (Character.isDigit(a[i])) {
					state = 10;
				}
				break;
			case 10:
				if (Character.isDigit(a[i])) {
					state = 10;
				}
				break;
			case 11:
				if ((int) a[i] == 34) {
					state = 12;
				}
				break;
			case 13:
				if ((int) a[i] >= 32) {
					state = 14;
				}
				break;
			case 14:
				if ((int) a[i] == 39) {
					state = 15;
				}
				break;
			case 16:
				if(a[i]=='_'||Character.isDigit(a[i])||Character.isLetter(a[i])) {
					state=1;
				}
				break;
			}

			if (end == false && i == a.length - 1) {
				if (!temp1.isEmpty()) {
					if (m1.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(temp1.toString().toUpperCase());
						//System.out.print(temp1.toString() + ' ' + temp1 + '\n');
					} else if (m2.containsKey(temp1.toString())) {
						result_word.add(temp1.toString());
						result_cato.add(m2.get((temp1.toString())));
						//System.out.print(temp1.toString() + ' ' + m2.get((temp1.toString())) + '\n');
					} else {
						result_word.add(temp1.toString());
						if (state == 1) {
							result_cato.add("ID");
						}
						if (state == 2) {
							result_cato.add("CONST");
						}
						if (state == 3&&!temp1.toString().equals("0")) {
							result_cato.add("OCTAL");
						}else if(state == 3&&temp1.toString().equals("0")) {
							result_cato.add("CONST");
						}
						if (state == 5) {
							result_cato.add("DEX");
						}
						if (state == 6) {
							result_cato.add("REAL");
						}
						if (state == 10) {
							result_cato.add("SCINO");
						}
						if (state == 12) {
							result_cato.add("STR_CONST");
						}
						if (state == 15) {
							result_cato.add("CHAR_CONST");
						}
						if(state==31) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Octal_Error");
						}
						if(state==51) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" Dex_Error");
						}
						if(state==-1) {
							result_cato.add("line:"+Integer.valueOf(line).toString()+" ID_Error");
						}
						//System.out.print(temp1.toString() + ' ' + state + '\n');
					}
				}
			}

			i += 1;
		}
	}

	public static void main(String[] args) {
		ANALYER analyzer = new ANALYER();
		analyzer.readtext();
	}

}
