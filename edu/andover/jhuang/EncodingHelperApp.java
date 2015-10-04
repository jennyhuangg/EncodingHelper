package edu.andover.jhuang;

public class EncodingHelperApp {

	public String getString()
	{
		EncodingHelperChar c = new EncodingHelperChar('e');
		String result = c.toCodepointString();
		return result;
	}
}
