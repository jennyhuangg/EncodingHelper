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

/*
 * Implemented all the methods defined and matched the specifications given
 * in the Javadoc comments in EncodingHelperChar.java
 *
 * Jenny Huang and Roberto Rabines
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 29 September 2015
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
        	throw new IllegalArgumentException ("codepoint out of range"
        			+ " - too large");
        if (codepoint < minCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range"
        			+ " - negative");
        if (codepoint >= 0xD800 && codepoint <= 0xDFFF)
        	throw new IllegalArgumentException ("invalid codepoint - "
        			+ "UTF-16 surrogate");
        this.codepoint = codepoint;
    }
    
    public EncodingHelperChar(byte[] utf8Bytes) {
    	int numBytes = utf8Bytes.length;
    	
    	//empty array
    	if (numBytes == 0) {
    		throw new IllegalArgumentException ("byte array is empty");
    	}
    	//one byte sequence
    	else if (numBytes == 1) {
    		//if first bit is 0
    		boolean isByte1Prefix0 = (byte)(utf8Bytes[0] & 0x80) == (byte)0;
    		if (isByte1Prefix0) { 
    			codepoint = utf8Bytes[0]; 
    		}
    		//if first bit is not 0
    		else {
    			throw new IllegalArgumentException ("one byte sequence does not"
    					+ " begin with 0");
    		}
    	}
    	//two byte sequence
    	else if (numBytes == 2) {
    		//checks that the continuation bytes have correct prefix 10
    		for (int i = 1; i < numBytes; i++) {
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does"
    						+ " not have prefix 10");
    		}
    		boolean isByte1Prefix110 = (byte)(utf8Bytes[0] & 0xE0)
    				== (byte)0xC0;
    		//if first three bits are 110
    		if (isByte1Prefix110) { 
    			//turns 10 prefix into 00 for 2nd byte
    			int a = (utf8Bytes[1] & 0x3F); 
    			//turns 110 prefix into 000 for 1st byte
    			int b = (utf8Bytes[0] & 0x1F); 
    			//codepoint if not illegal argument
    			int potentialCodepoint = (b<<6) + a;
    			int minCodepoint = 0x80;
    			if (potentialCodepoint <= minCodepoint)
    				throw new IllegalArgumentException("overlong sequence");
    			else
    				codepoint = potentialCodepoint; 
    		}
    		//if first three bits are not 110
    		else {
    			throw new IllegalArgumentException ("two byte sequence does not"
    					+ " begin with 110");
    		}
    	}
    	//three byte sequence
    	else if (numBytes == 3) {
    		//checks that the continuation bytes have correct prefix 10
    		for (int i = 1; i < numBytes; i++) {
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does"
    						+ " not have prefix 10");
    		}
    		boolean isByte1Prefix1110 = (byte)(utf8Bytes[0] & 0xF0) == 
    				(byte)0xE0;
    		//if first four bits are 1110
    		if (isByte1Prefix1110) { 
    			//turns 10 prefix into 00 for 3rd byte
    			int a = (utf8Bytes[2] & 0x3F); 
    			//turns 10 prefix into 00 for 2nd byte
    			int b = (utf8Bytes[1] & 0x3F);
    			//turns 1110 prefix into 0000 for 1st byte
    			int c = (utf8Bytes[0] & 0x0F); 
    			//cam helped me understand why I should use parentheses
    			int potentialCodepoint = (c<<12) + (b<<6) + a; 
    			int minCodepoint = 0x800;
    			if (potentialCodepoint <= minCodepoint)
    				throw new IllegalArgumentException("overlong sequence");
    			else
    				codepoint = potentialCodepoint; 
    		}
    		//if first four bits are not 1110
    		else {
    			throw new IllegalArgumentException ("three byte sequence does "
    					+ "not begin with 1110");
    		}
    	}
    	//four byte sequence
    	else if (numBytes == 4) {
    		//checks that continuation bytes have correct prefix 10
    		for (int i = 1; i < numBytes; i++) {
    			if ((byte)(utf8Bytes[i] & 0xC0) != (byte)0x80)
    				throw new IllegalArgumentException("continuation byte does"
    						+ " not have prefix 10");
    		}
    		boolean isByte1Prefix11110 = (byte)(utf8Bytes[0] & 0xF8) 
    				== (byte)0xF0;
    		//if first five bits are 11110
    		if (isByte1Prefix11110) { 
    			//turns 10 prefix into 00 for 4th byte
    			int a = utf8Bytes[3] & 0x3F; 
    			//turns 10 prefix into 00 for 3rd byte
    			int b = utf8Bytes[2] & 0x3F; 
    			//turns 10 prefix into 00 for 2nd byte
    			int c = utf8Bytes[1] & 0x3F; 
    			//turns 11110 prefix into 00000 for 1st byte
    			int d = utf8Bytes[0] & 0x07; 
    			int potentialCodepoint = (d<<18) + (c<<12) + (b<<6) + a;
    			int maxCodepoint = 0x10FFFF;
    			int minCodepoint = 0x10000; 
    			if (potentialCodepoint > maxCodepoint)
    				throw new IllegalArgumentException("codepoint out of range"
    						+ " - too large");
    			if (potentialCodepoint < minCodepoint)
    				throw new IllegalArgumentException("overlong sequence");
    			else
    				codepoint = potentialCodepoint; 
    		}
    		//if first five bits are not 11110
    		else {
    			throw new IllegalArgumentException ("four byte sequence does "
    					+ "not begin with 11110");
    		}
    	}
    	//longer than four byte sequence
    	else
    		throw new IllegalArgumentException ("Byte array is too long");
       
    	if (codepoint >= 0xD800 && codepoint <= 0xDFFF)
        	throw new IllegalArgumentException ("invalid codepoint - "
        			+ "UTF-16 surrogate");
    }
    
    public EncodingHelperChar(char ch) {
        char[] c = {ch};
        codepoint = Character.codePointAt(c, 0);
    }
    
    public int getCodepoint() {
        return codepoint;
    }
    
    public void setCodepoint(int codepoint) {
    	int maxCodepoint = 0x10FFFF;
    	int minCodepoint = 0;
    	if (codepoint > maxCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range"
        			+ " - too large");
        if (codepoint < minCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range"
        			+ " - negative");
        if (codepoint >= 0xD800 && codepoint <= 0xDFFF)
        	throw new IllegalArgumentException ("invalid codepoint - "
        			+ "UTF-16 surrogate");
        this.codepoint = codepoint;
    }

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
    	//1 byte long
    	if (this.codepoint >= 0 && this.codepoint <= 0x7F) {
    		byte[] utf8Bytes = new byte[] {(byte)codepoint};
    		return utf8Bytes;
    	}
    	//2 bytes long
    	else if (this.codepoint >= 0x80 && this.codepoint <= 0x7FF) {
    		int prefix = 0x6; //110 prefix
    		int contPrefix = 0x2; //10 prefix
    		int b1NoPrefix = codepoint & 0x3F; //last six digits
    		int b2NoPrefix = (codepoint & (0x1F << 6)) >> 6; //five digits in first byte
    		byte b1 = (byte)((prefix << 5) + b2NoPrefix); //byte 1 in array
    		byte b2 = (byte)((contPrefix << 6) + b1NoPrefix); //byte 2 in array
    		byte[] utf8Bytes = {b1, b2};
    		return utf8Bytes;
    	}
    	//3 bytes long
    	else if (this.codepoint >= 0x800 && this.codepoint <= 0xFFFF) {
    		int prefix = 0xE; //1110 prefix
    		int contPrefix = 0x2; //10 prefix
    		int b1NoPrefix = codepoint & 0x3F; //last six digits
    		int b2NoPrefix = (codepoint & (0x3F << 6)) >> 6; //next six digits
    		int b3NoPrefix = (codepoint & (0x1F << 12)) >> 12; //first five digits
    		byte b1 = (byte)((prefix << 4) + b3NoPrefix); 
    		byte b2 = (byte)((contPrefix << 6) + b2NoPrefix); 
    		byte b3 = (byte)((contPrefix << 6) + b1NoPrefix);
    		byte[] utf8Bytes = {b1, b2, b3};
    		return utf8Bytes;
    	}
    	//4 bytes long
    	else {
    		int prefix = 0x1E; //11110 prefix
    		int contPrefix = 0x2; //10 prefix
    		int b1NoPrefix = codepoint & 0x3F; //last six digits
    		int b2NoPrefix = (codepoint & (0x3F << 6)) >> 6; //next six digits
    		int b3NoPrefix = (codepoint & (0x3F << 12)) >> 12; //next six digits
    		int b4NoPrefix = (codepoint & (0x1F << 18)) >> 18; //first five digits
    		byte b1 = (byte)((prefix << 3) + b4NoPrefix);
    		byte b2 = (byte)((contPrefix << 6) + b3NoPrefix);
    		byte b3 = (byte)((contPrefix << 6) + b2NoPrefix); 
    		byte b4 = (byte)((contPrefix << 6) + b1NoPrefix); 
    		byte[] utf8Bytes = {b1, b2, b3, b4};
    		return utf8Bytes;
    	}
    }
    
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
    	//one digit hex codepoint
    	if (codepoint <= 0xF)
    		s += "000" + Integer.toHexString(codepoint);
    	//two digit hex codepoint
    	else if (codepoint > 0xF && codepoint <= 0xFF) 
    		s += "00" + Integer.toHexString(codepoint);
    	//three digit hex codepoint
    	else if (codepoint > 0xFF && codepoint <= 0xFFF) 
			s += "0" + Integer.toHexString(codepoint);
    	//four or more digit hex codepoint
    	else 
			s += Integer.toHexString(codepoint);
    	return s.toUpperCase();
    }
 
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
    	for(int i = 0; i < b.length; i++) {   
            utf8String += "\\x" + String.format("%02x", b[i]).toUpperCase();
    	}
        return utf8String;
    }
    
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
	//help from cam's piazza post
    public String getCharacterName() {
    	//removes "U+" in the beginning
    	String fourDigitHexCodepoint = this.toCodepointString().substring(2);
    	try {
    	    Scanner unicodetxt = new Scanner(new File("src/edu/andover/jhuang/"
    	    		+ "UnicodeData.txt"));
    	    while (unicodetxt.hasNextLine()) {
    	    	  String[] data = unicodetxt.nextLine().split(";"); 
    	    	  //if first entry in array equals the 4-digit hex codepoint
    	    	  if(data[0].equals(fourDigitHexCodepoint)) {
    	    		  //if character name is <control>, add the specific name after
    	    		  if (data[1].equals("<control>"))
    	    			  return data[1] + " " + data[10];
    	    		  else
    	    			  return data[1];
    	    	  }
    	    }
    	}
    	catch (IOException e) {	
    	    	System.out.println("File Read Error");
    	}
    	//if not in UnicodeData.txt (undefined)
    	return "<unknown> " + this.toCodepointString();
    }

}