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
        	throw new IllegalArgumentException ("codepoint out of range - too large.");
        if (codepoint < minCodepoint)
        	throw new IllegalArgumentException ("codepoint out of range - negative");
        this.codepoint = codepoint;
    }
    
    //jenny
    public EncodingHelperChar(byte[] utf8Bytes) {
    	int l = utf8Bytes.length;
    	
    	if (l == 1) {
    		if (utf8Bytes & )
    	}
        //illegal arguments
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
    }
    
    //jenny
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
        // Not yet implemented.
        return null;
    }
    
    //jenny
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
    	byte[] b = this.toUtf8Bytes();
    	int length = b.length;
    	
    	for (int i = 0; i < length; i++) {
        	String.format(0x%02X, b[i]);
    	}
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
    //use the previous method to do this
    public String toUtf8String() {
        // Not yet implemented.
        return "";
    }
    
    /**
     * Generates the official Unicode name for this character.
     *   For example, if this character is a lower-case letter e with an acute
     * accent, then this method returns the string "LATIN SMALL LETTER E WITH
     * ACUTE" (without quotation marks).
     *
     * @return this character's Unicode name
     */
    public String getCharacterName() {
        // Not yet implemented.
    	//Cameron's Piazza Help
    	//figure out how to go from int codepoint to four/five character
    	//string codepoint
    	String stringCodepoint = codepoint; //insert conversion here
    	try {
    	    Scanner unicodetxt = new Scanner(new File("UnicodeData.txt"));
    	    int i = 0;
    	    while (unicodetxt.hasNextLine()) {
    	    	  String[] data = unicodetxt.nextLine().split(";");
    	    	  // do something with this
    	    	  if(data[i].equals("0000"))
    	    		  	return(data[i+1]);	
    	    	        i++;   	
    	    	  }
    	    }
    	    catch (IOException e) {	    
    	    }
    		
        return "";
    }
    
    //test
    public static void main(String[] args)
    {
    	EncodingHelperChar c = new EncodingHelperChar(0);
    	System.out.println(c.getCharacterName());
    }
}