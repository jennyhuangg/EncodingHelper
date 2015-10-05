package edu.andover.jhuang;

public class EncodingHelperString {

private int codepoints[];

	public EncodingHelperString(String inputString) {
		for (int i = 0; i < inputString.length(); i++)
		{
			char ch = inputString.charAt(i);
	        char[] c = {ch};
	        codepoints[i] = Character.codePointAt(c, 0);
		}		
	}
	
	public String toCodepointString() {
		String s = "";
		for (int i = 0; i < codepoints.length; i++) {
			EncodingHelperChar c = new EncodingHelperChar(codepoints[i]);
			s += c.toCodepointString() + " ";
		}
		return s;
	}
	
	public String toUtf8String() {
		String s = "";
		for (int i = 0; i < codepoints.length; i++) {
			EncodingHelperChar c = new EncodingHelperChar(codepoints[i]);
			s += c.toUtf8String();
		}
		return s;
	}
}
