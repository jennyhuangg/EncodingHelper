package edu.andover.jhuang;
import java.util.ArrayList;

public class EncodingHelperString {

private ArrayList<Integer> codepoints = new ArrayList<Integer>();

	public EncodingHelperString(String inputString) {
		for (int i = 0; i < inputString.length(); i++)
		{
			char ch = inputString.charAt(i);
			EncodingHelperChar ehc = new EncodingHelperChar(ch);
	        codepoints.add(ehc.getCodepoint());
		}
	}
	
	public String toCodepointString() {
		String s = "";
		for (int i = 0; i < codepoints.size(); i++) {
			EncodingHelperChar c = new EncodingHelperChar(codepoints.get(i));
			s += c.toCodepointString() + " ";
		}
		return s;
	}
	
	public String toUtf8String() {
		String s = "";
		for (int i = 0; i < codepoints.size(); i++) {
			EncodingHelperChar c = new EncodingHelperChar(codepoints.get(i));
			s += c.toUtf8String();
		}
		return s;
	}
}
