package edu.andover.jhuang;
import java.util.ArrayList;

/*
 * EncodingHelper Model for String Input
 * 
 * Jenny Huang
 * Project 1.3
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 5 October 2015
 */

public class EncodingHelperString {

	private ArrayList<Integer> codepoints = new ArrayList<Integer>();

	public EncodingHelperString(int[] inputCodepoints) {
		for (int i = 0; i < inputCodepoints.length; i++) {
	        codepoints.add(inputCodepoints[i]);
		}
	}
	public EncodingHelperString(String inputString) {
		for (int i = 0; i < inputString.length(); i++) {
			char ch = inputString.charAt(i);
			EncodingHelperChar ehc = new EncodingHelperChar(ch);
	        codepoints.add(ehc.getCodepoint());
		}
	}
	
	public EncodingHelperString(ArrayList<Byte> utf8Bytes) {
		for (int i = 0; i < utf8Bytes.size(); i++) {
			//if first bit is 0
			boolean isByte1Prefix0 = (byte)(utf8Bytes.get(i) & 0x80) == (byte)0;
			//if first three bits are 110
			boolean isByte1Prefix110 = (byte)(utf8Bytes.get(i) & 0xE0)
    				== (byte)0xC0;
    		//if first four bits are 1110
			boolean isByte1Prefix1110 = (byte)(utf8Bytes.get(i) & 0xF0) == 
    				(byte)0xE0;
    		//if first five bits are 11110
    		boolean isByte1Prefix11110 = (byte)(utf8Bytes.get(i) & 0xF8) 
    				== (byte)0xF0;
    		
			//check if first byte of one byte sequence
			if (isByte1Prefix0) { 
				byte[] b = {utf8Bytes.get(i)};
    			EncodingHelperChar ehc = new EncodingHelperChar(b);
    			codepoints.add(ehc.getCodepoint());
    		}
			//check if first byte of two byte sequence
			else if (isByte1Prefix110) { 
    			byte[] b = {utf8Bytes.get(i), utf8Bytes.get(i+1)};
    			EncodingHelperChar ehc = new EncodingHelperChar(b);
    			codepoints.add(ehc.getCodepoint());
    		}
    		//check if first byte of three byte sequence
			else if (isByte1Prefix1110) { 
				byte[] b = {utf8Bytes.get(i), utf8Bytes.get(i+1), 
						utf8Bytes.get(i+2)};
    			EncodingHelperChar ehc = new EncodingHelperChar(b);
    			codepoints.add(ehc.getCodepoint());
			}
    		//check if first byte of four byte sequence
			else {
				byte[] b = {utf8Bytes.get(i), utf8Bytes.get(i+1), 
						utf8Bytes.get(i+2), utf8Bytes.get(i+3)};
    			EncodingHelperChar ehc = new EncodingHelperChar(b);
    			codepoints.add(ehc.getCodepoint());
			}
			
		}
	}
	
	public ArrayList<Integer> getCodepoint() {
		return codepoints;
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
