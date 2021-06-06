package yufafenxi;

import java.util.HashMap;

public class keyword {
	public HashMap<String, String> map1 = new HashMap<>();
	public HashMap<String, String> map2 = new HashMap<>();

	public HashMap define() {
		map1.put("auto", "1");
		map1.put("break", "1");
		map1.put("case", "1");
		map1.put("char", "1");
		map1.put("const", "1");
		map1.put("continue", "1");
		map1.put("default", "1");
		map1.put("do", "1");
		map1.put("double", "1");
		map1.put("else", "1");
		map1.put("enum", "1");
		map1.put("extern", "1");
		map1.put("float", "1");
		map1.put("for", "1");
		map1.put("goto", "1");
		map1.put("if", "1");
		map1.put("int", "1");
		map1.put("long", "1");
		map1.put("register", "1");
		map1.put("return", "1");
		map1.put("short", "1");
		map1.put("signed", "1");
		map1.put("sizeof", "1");
		map1.put("static", "1");
		map1.put("struct", "1");
		map1.put("switch", "1");
		map1.put("typedef", "1");
		map1.put("union", "1");
		map1.put("unsigned", "1");
		map1.put("void", "1");
		map1.put("volatile", "1");
		map1.put("while", "1");
		return map1;

	}

	public HashMap define2() {
		map2.put(".","POINT");
		map2.put("!", "NOT");
		map2.put("&", "BYTE_AND");
		map2.put("~", "COMPLEMENT");
		map2.put("^", "BYTE_XOR");
		map2.put("*", "MUL");
		map2.put("/", "DIV");
		map2.put("%", "MOD");
		map2.put("+", "ADD");
		map2.put("-", "SUB");
		map2.put("<", "LESS_THAN");
		map2.put(">", "GRT_THAN");
		map2.put("=", "ASG");
		map2.put("->", "ARROW");
		map2.put("++", "SELF_ADD");
		map2.put("--", "SELF_SUB");
		map2.put("<<", "LEFT_MOVE");
		map2.put(">>", "RIGHT_MOVE");
		map2.put("<=", "LES_EQUAL");
		map2.put(">=", "GRT_EQUAL");
		map2.put("==", "EQUAL");
		map2.put("!=", "NOT_EQUAL");
		map2.put("&&", "AND");
		map2.put("||", "OR");
		map2.put("+=", "COMPLETE_ADD");
		map2.put("-=", "COMPLETE_SUB");
		map2.put("*=", "COMPLETE_MUL");
		map2.put("/=", "COMPLETE_DIV");
		map2.put("^=", "COMPLETE_BYTE_XOR");
		map2.put("&=", "COMPLETE_BYTE_ADD");
		map2.put("~=", "COMPLETE_COMPLEMENT");
		map2.put("%=", "COMPLETE_MOD");
		map2.put("|", "BYTE_OR");
		map2.put("/*", "注释开始:");
		map2.put("*/", "注释结束");

		// 限界符
		map2.put("(", "LEFT_PER");
		map2.put(")", "RIGHT_PER");
		map2.put("[", "LEFT_INDEX");
		map2.put("]", "RIGHT_INDEX");
		map2.put("{", "LEFT_BOUNDER");
		map2.put("}", "RINGHT_BOUNDER");
		map2.put(",", "COMMA");
		map2.put(";", "SEMI");
		map2.put(":", "COLON");
		return map2;

	}

}
