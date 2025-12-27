package edu.ustb.sei.mde.eecg.tools;

public class CharTool {
	static public char linebreakR = '\r';
	static public char linebreakN = '\n';
	
	static public boolean charEqual(char ch, String s) {
		return ch == s.charAt(0);
	}
}
