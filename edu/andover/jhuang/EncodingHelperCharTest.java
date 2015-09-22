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
	
	//For arbitrary middle codepoint int
	@Test
	public void getCodepoint_ShouldMatchCodepointPassedToConstructorInt() {
		int x = 0x1F1F;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly", x, outputCodepoint);
	}
	//For codepoint of 0 (lower bound of valid codepoints)
	@Test
	public void testMinCodepointShouldBeAccepted() {
		int x = 0;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly - 0 should be 0", x, outputCodepoint);
	}
	//For codepoint of 0x10FFFF (upper bound of valid codepoints)
	@Test
	public void testMaxCodepointShouldBeAccepted () {
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("failed to construct correctly - 0x10FFFF should be 0x10FFFFF", x, outputCodepoint);
	}
	
	/*
	 * Tests that the constructor (for integer parameter) throws when
	 * the codepoint is out of range - negative
	 */
	@Test
	public void constructorIntWithNegativeCodepointShouldThrow() {
		 try {
			 	EncodingHelperChar c = new EncodingHelperChar(-1);
		        fail(
		        	"Constructor didn't throw when the "
		        		+ "codepoint was out of range - negative."
		        );
		 } catch(IllegalArgumentException expectedException) {
		        // No action needed.
		 }
	}
	/*
	 * Tests that the constructor (for integer parameter) throws when 
	 * the codepoint is out of range - too large
	 */
	@Test 
	public void constructorIntWithTooLargeCodepointShouldThrow() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x110000);
				fail(
					"Constructor didn't throw when the "
					+ "codepoint was out of range - too large."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	/*
	 * Tests that getCodepoint() returns the integer codepoint that
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
	 * Tests that the constructor (for UTF-8 byte sequence) throws when
	 * the binary representation of an input 1 byte sequence begins with 1.
	 * 
	 * These invalid 1 byte sequences are larger than 0x75.
	 */
	@Test
	public void constructorArrayWithIncorrectPrefixFor1ByteSequenceShouldThrow() {
		 //For arbitrary middle invalid 1 byte
		try {
			byte[] b1 = {(byte)0x9A};
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when the 1 byte sequence "
				+ "was invalid - prefix of 1."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		//For lower bound for invalid 1 byte
		try {
				byte[] b2 = {(byte)0x80}; 
				EncodingHelperChar c2 = new EncodingHelperChar(b2);
				fail(
					"Constructor didn't throw when the 1 byte sequence "
					+ "was invalid - prefix of 1."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
		//For upper bound for invalid 1 byte
		try {
			byte[] b3 = {(byte)0xFF}; 
			EncodingHelperChar c3 = new EncodingHelperChar(b3);
			fail(
				"Constructor didn't throw when the 1 byte sequence "
				+ "was invalid - prefix of 1."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}
	
	//Tests that toUtf8Bytes() does not return null
	@Test
	public void toUTFBytesShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertFalse(c.toUtf8String().isEmpty());
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
