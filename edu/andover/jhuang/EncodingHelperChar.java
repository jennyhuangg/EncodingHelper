package edu.andover.jhuang;

/**
 * The main model class for the EncodingHelper project in Software Design,
 * Fall 2015, Phillips Academy.
 * 
 * Each object of this class represents a single Unicode character.  The class's
 * methods generate various representations of the character.
 * 
 * Every method that takes an argument may throw an IllegalArgumentException if
 * given an illegal input.  This includes codepoints that are out of the legal
 * range for Unicode, byte arrays that are invalid encodings for a single
 * character, etc.
 * 
 * @author Jadrian Miles
 */

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class EncodingHelperChar {
    private int codepoint;
    
    public EncodingHelperChar(int codepoint) {
    	int maxCodepoint = 0x10FFFF;
    	int minCodepoint = 0;
    	if (codepoint > maxCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range - too large");
        if (codepoint < minCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range - negative");
        this.codepoint = codepoint;
    }
    
    //jenny HALP
    //overlong sequence??? max gives out wrong value??
    public EncodingHelperChar(byte[] utf8Bytes) {
    	int numBytes = utf8Bytes.length;
    	
    	if (numBytes == 0) {
    		throw new IllegalArgumentException ("byte array is empty");
    	}
    	else if (numBytes == 1) {
    		//if first bit is 0 and succeeding bits have prefix 10
    		boolean isByte1Prefix0 = (byte)(utf8Bytes[0] & 0x80) == (byte)0;
    		if (isByte1Prefix0) { 
    			codepoint = utf8Bytes[0]; 
    		}
    		else {
    			throw new IllegalArgumentException ("one byte sequence does not begin with 0");
    		}
    	}
    	else if (numBytes == 2) {
    		boolean isByte1Prefix110 = (byte)(utf8Bytes[0] & 0xE0) == (byte)0xC0;
    		for (int i = 1; i < numBytes; i++)
    		{
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does not have prefix 10");
    		}
    		if (isByte1Prefix110) { //if first three bits are 110
    			int a = (byte)(utf8Bytes[1] & 0x3F); //turns 10 prefix into 0 for 2nd byte
    			int b = (byte)(utf8Bytes[0] & 0x1F); //turns 110 prefix into 000 for 1st byte
    			if (b<<6 + a <= 0x10FFFF)
    				codepoint = b<<6 + a; 
    			else
    				throw new IllegalArgumentException("codepoint out of range  - too large");
    		}
    		else {
    			throw new IllegalArgumentException ("two byte sequence does not begin with 110");
    		}
    	}
    	else if (numBytes == 3) {
    		boolean isByte1Prefix1110 = (byte)(utf8Bytes[0] & 0xF0) == (byte)0xE0;
    		for (int i = 1; i < numBytes; i++)
    		{
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does not have prefix 10");
    		}
    		if (isByte1Prefix1110) { //if first four bits are 1110
    			int a = (byte)(utf8Bytes[2] & 0x3F); //turns 10 prefix into 00 for 3rd byte
    			int b = (byte)(utf8Bytes[1] & 0x3F); //turns 10 prefix into 00 for 2nd byte
    			int c = (byte)(utf8Bytes[0] & 0x0F); //turns 1110 prefix into 0000 for 1st byte
    			if (c<<12 + b<<6 + a <= 0x10FFFF)
    				codepoint = c<<12 + b<<6 + a; 
    			else
    				throw new IllegalArgumentException("codepoint out of range  - too large");
    		}
    		else {
    			throw new IllegalArgumentException ("two byte sequence does not begin with 110");
    		}
    	}
    	else if (numBytes == 4) {
    		for (int i = 1; i < numBytes; i++)
    		{
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does not have prefix 10");
    		}
    		boolean isByte1Prefix11110 = (byte)(utf8Bytes[0] & 0xF8) == (byte)0xF0;
    		if (isByte1Prefix11110) { //if first five bits are 11110
    			int a = (byte)(utf8Bytes[3] & 0x3F); //turns 10 prefix into 00 for 4th byte
    			int b = (byte)(utf8Bytes[2] & 0x3F); //turns 10 prefix into 00 for 3rd byte
    			int c = (byte)(utf8Bytes[1] & 0x3F); //turns 10 prefix into 00 for 2nd byte
    			int d = (byte)(utf8Bytes[0] & 0x07); //turns 11110 prefix into 00000 for 1st byte
    			if (d<<18 + c<<12 + b<<6 + a > 0x10FFFF)
    				throw new IllegalArgumentException("codepoint out of range  - too large");
    			else
    				codepoint = d<<18 + c<<12 + b<<6 + a;

    		}
    		else {
    			throw new IllegalArgumentException ("two byte sequence does not begin with 110");
    		}
    	}
    	else
    		throw new IllegalArgumentException ("Byte array is too long");
    }
    
    //roberto
    public EncodingHelperChar(char ch) {
        codepoint = (int) ch;
        //throw illegal arguments
    }
    
    //DONE
    public int getCodepoint() {
        return codepoint;
    }
    
    //roberto
    public void setCodepoint(int codepoint) {
        this.codepoint = codepoint;
        //throw illegal arguments
        /*if()
        {	
        	throw new IllegalArguementException("")	
        }*/
    }
    
    //jenny HAL
    /**
     * Converts this character into an array of the bytes in its UTF-8
     * representation.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, whose UTF-8 form consists of the two-byte sequence C3 A9, then
     * this method returns a byte[] of length 2 containing C3 and A9.
     * 
     * @return the UTF-8 byte array for this character
     */
    public byte[] toUtf8Bytes() {
    	//go from codepoint to utf8 bytes
    	//use stress test numbers
    	if (this.codepoint >= 0 && this.codepoint <= 0x7F) //1 byte long
    	{
    		byte[] b = new byte[] {(byte)codepoint};
    		return b;
    	}
    	else if (this.codepoint >= 0x80 && this.codepoint <= 0x7FF) //2 bytes long HALP
    	{
    		byte[] b = new byte[2];
    		byte b1 = 5;
    		return b;
    	}
        return null;
    }
    
    //jenny DONE
    /**
     * Generates the conventional 4-digit hexadecimal code point notation for
     * this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string U+00E9 (no quotation marks in
     * the returned String).
     *
     * @return the U+ string for this character
     */
    public String toCodepointString() {
    	String s = "U+";
    	if (codepoint <= 0xF) //one digit hex
    		s += "000" + Integer.toHexString(codepoint);
    	else if (codepoint > 0xF && codepoint <= 0xFF) //two digit hex
    		s += "00" + Integer.toHexString(codepoint);
    	else if (codepoint > 0xFF && codepoint <= 0xFFF) //three digit hex
			s += "0" + Integer.toHexString(codepoint);
    	else //four or more digit hex
			s += Integer.toHexString(codepoint);
    	return s.toUpperCase();
    }
    
    //roberto
    /**
     * Generates a hexadecimal representation of this character suitable for
     * pasting into a string literal in languages that support hexadecimal byte
     * escape sequences in string literals (e.g. C, C++, and Python).
     *   For example, if this character is a lower-case letter e with an acute
     * accent (U+00E9), then this method returns the string \xC3\xA9. Note that
     * quotation marks should not be included in the returned String.
     *
     * @return the escaped hexadecimal byte string
     */

    public String toUtf8String() {
    	String utf8String = "";
    	byte[] b = this.toUtf8Bytes();
    	for(int i = 0;i < b.length;i++)
    	{   
    	    utf8String += "\\x" + String.format("0x%02x", b[i]).toUpperCase(); //bytetohexstring
            //utf8String += "\\x" + String.valueOf(b[i]);?
    	}
        return "";
    }
    
    //jenny HALP
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
    	//Cameron's Piazza Help
    	String a = this.toCodepointString().substring(2);
    	try {
    	    Scanner unicodetxt = new Scanner(new File("UnicodeData.txt"));
    	    int i = 0;
    	    while (unicodetxt.hasNextLine()) {
    	    	  String[] data = unicodetxt.nextLine().split(";"); //numbers repeat in file and what am i doing wrong
    	    	  if(data[i].equals(a))
    	    		  	return data[i+1];	
    	    	  else
    	    	        i++;   	
    	    	  }
    	    }
    	    catch (IOException e) {	
    	    	return ""; //what to do hereeeee
    	    }
    	return "";
    }
    
    public static void main(String[] args)
    {
    	byte[] a = new byte[] {(byte)0xF4,(byte)0x9F,(byte)0xBF,(byte)0xBF};
		//corresponding codepoint is U+110000
		EncodingHelperChar d = new EncodingHelperChar(a);
		System.out.println(d.getCodepoint());
    	byte[] b = new byte[]{(byte)0xF4,(byte)0x8F,(byte)0xBF,(byte)0xBF};
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(b);
		System.out.println(c.getCodepoint());
		EncodingHelperChar w = new EncodingHelperChar(0xE4);
		System.out.println(w.toCodepointString().substring(2));
    }
}