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
	 * The following three methods test that getCodepoint() returns the integer 
	 * codepoint specified when constructing the object
	 */
	
	//for arbitrary middle codepoint int
	@Test
	public void getCodepoint_ShouldMatchCodepointPassedToConstructorInt() {
		int x = 0x1F1F;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly", x, outputCodepoint);
	}
	//for codepoint of 0 (lower bound of valid codepoints)
	@Test
	public void codepoint0ShouldBe0() {
		int x = 0;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly", x, outputCodepoint);
	}
	//for codepoint of 0x10FFFF (upper bound of valid codepoints)
	@Test
	public void codepoint0x10FFFFShouldBe0x10FFFF() {
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly", x, outputCodepoint);
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
	 * corresponds to the byte array specified when constructing
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
	 * tests that the constructor (for UTF-8 byte sequence) throws when
	 * the binary representation of an input 1 byte sequence begins with 1.
	 * 
	 */
	@Test
	public void constructorArrayWithIncorrectPrefixFor1ByteSequenceShouldThrow() {
		try {
				byte[] b1 = {(byte)0x80}; //lower bound for invalid 1 byte
				byte[] b2 = {(byte)0x9A}; //arbitrary middle invalid 1 byte
				byte[] b3 = {(byte)0xFF}; //upper bound for invalid 1 byte
				EncodingHelperChar c1 = new EncodingHelperChar(b1);
				EncodingHelperChar c2 = new EncodingHelperChar(b2);
				EncodingHelperChar c3 = new EncodingHelperChar(b3);
				fail(
					"Constructor didn't throw when the byte sequence "
					+ "was invalid - incorrect prefix for 1 byte sequence."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	/*
	 * tests that 
	 */
	//multiple byte sequences should not begin with "10"
	//bytes after the first for multiple byte sequences must begin with 10
	//missing a continuation byte
	//empty array returns null exception
	//subsequent bytes cannot 
	
	//sevenBitCodepointShouldEncodeInOneByte
	//toUtf8BytesShouldReturnNonEmpty
	//

}
