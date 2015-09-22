package edu.andover.jhuang;

/*
 * Tests behavior for each method and constructor in EncodingHelperChar.java
 *
 * Jenny Huang and Roberto Rabines
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 22 September 2015
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class EncodingHelperCharTest {
	
	/*
	 * tests that getCodepoint() returns the integer codepoint specified when
	 * constructing the object
	 */
	@Test
	public void getCodePoint_ShouldMatchCodepointPassedToConstructorInt() {
		int x = 0x1F1F;
		int y = 0x1AAAA;
		EncodingHelperChar c = new EncodingHelperChar(x);
		EncodingHelperChar d = new EncodingHelperChar(y);
		int xOutputCodepoint = c.getCodepoint();
		int yOutputCodepoint = d.getCodepoint();
		assertEquals("failed to construct correctly", x, xOutputCodepoint);
		assertEquals("failed to construct correctly", y, yOutputCodepoint);
	}
	
	/*
	 * tests that the constructor (for integer parameter) throws when
	 * the codepoint is out of range - negative
	 */
	@Test
	public void constructorIntWithNegativeCodepointShouldThrow() {
		 try {
			 	EncodingHelperChar c = new EncodingHelperChar(-1);
			 	EncodingHelperChar c2 = new EncodingHelperChar(-51020);
		        fail(
		        	"Constructor didn't throw when the "
		        		+ "codepoint was out of range - negative."
		        );
		 } catch(IllegalArgumentException expectedException) {
		        // No action needed.
		 }
	}
	
	/*
	 * tests that the constructor (for integer parameter) throws when 
	 * the codepoint is out of range - too large
	 */
	@Test 
	public void constructorIntWithTooLargeCodepointShouldThrow() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x110000);
				EncodingHelperChar c2 = new EncodingHelperChar(0x111111);
				fail(
					"Constructor didn't throw when the "
					+ "codepoint was out of range - too large."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	/*
	 * tests that getCodepoint() returns the integer codepoint that
	 * corresponds the the byte array specified when constructing
	 * the object.
	 */
	@Test
	public void getCodepoint_ShouldMatchCodepointPassedToConstructorArray() {
		byte[] b = new byte[]{(byte)0xF0,(byte)0x9F,(byte)0xA0,(byte)0x80};
		int x = 0x1F800;
		EncodingHelperChar c = new EncodingHelperChar(b);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly", x, outputCodepoint);	
	}
	
	/*
	 * tests that the binary representation of a 1 byte UTF-8 sequence
	 * does not begin with 1.
	 */
	@Test
	public void constructorArrayWithIncorrectPrefixFor1ByteSequenceShouldThrow() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x80);
				EncodingHelperChar c2 = new EncodingHelperChar(0xFF);
				fail(
					"Constructor didn't throw when the byte sequence "
					+ "was invalid - incorrect prefix for 1 byte sequence."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	//multiple byte sequences should not begin with "10"
	//bytes after the first for multiple byte sequences must begin with 10
	//missing a continuation byte
	//empty array returns null exception
	//subsequent bytes cannot 
	
	//sevenBitCodepointShouldEncodeInOneByte
	//toUtf8BytesShouldReturnNonEmpty
	//

}
