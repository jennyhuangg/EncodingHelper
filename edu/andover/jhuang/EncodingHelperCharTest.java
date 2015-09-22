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
	
	//For arbitrary codepoint in bounds
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorInt() {
		int x = 0x1F1F;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0x1F1F should be 0x1F1F",
				x, outputCodepoint); //thanks Cam for helping with fail messages
	}
	//For codepoint of 0 (lower bound of valid codepoints)
	@Test
	public void minCodepointShouldBeAccepted() {
		int x = 0;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0 should be 0",
				x, outputCodepoint);
	}
	//For codepoint of 0x10FFFF (upper bound of valid codepoints)
	@Test
	public void maxCodepointShouldBeAccepted () {
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(x);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0x10FFFF should be 0x10FFFF",
				x, outputCodepoint);
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
		 //thanks Cam for explaining try catch
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
	 * The following three methods test that getCodepoint() returns the integer
	 * codepoint that corresponds to the byte array specified when constructing
	 * the object.
	 */
	
	//For arbitrary byte array in bounds
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorArray() {
		byte[] b = new byte[]{(byte)0xF0,(byte)0x9F,(byte)0xA0,(byte)0x80};
		int x = 0x1F800;
		EncodingHelperChar c = new EncodingHelperChar(b);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0xF0 0x9F 0xA0 0x80 "
				+ "should have codepoint 0x1F800", x, outputCodepoint);
	}
	//For byte array with 0s (lower bound of valid byte arrays)
	@Test
	public void minByteArrayShouldBeAccepted() {
		byte[] b = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
		int x = 0;
		EncodingHelperChar c = new EncodingHelperChar(b);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0x00 0x00 0x00 0x00"
				+ "should have codepoint 0", x, outputCodepoint);
	}
	//For byte array with 0xF4 0x8F 0xBF 0xBF (upper bound of valid byte arrays)
	@Test
	public void maxByteArrayShouldBeAccepted() {
		byte[] b = new byte[]{(byte)0xF4,(byte)0x8F,(byte)0xBF,(byte)0xBF};
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(b);
		int outputCodepoint = c.getCodepoint();
		assertEquals("Failed to construct correctly - 0xF4 0x8F 0xBF 0xBF"
				+ "should have codepoint 0x10FFFF", x, outputCodepoint);
	}

	/*
	 * Tests that the constructor (for array parameter) throws when the UTF-8 
	 * byte sequence has a corresponding codepoint out of range - too large
	 */
	@Test 
	public void constructorArrayWithTooLargeCodepointShouldThrow() {
		try {
				byte[] b = new byte[] {(byte)0xF4,(byte)0x9F,(byte)0xBF,(byte)0xBF};
				//corresponding codepoint is U+110000
				EncodingHelperChar c = new EncodingHelperChar(b);
				fail(
					"Constructor didn't throw when the byte array's"
					+ "corresponding codepoint was out of range - too large."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	//input UTF-8 byte array cannot contain more than 4 bytes
	public void constructorArrayIfTooLargeShouldThrow() {
		try {
			byte[] b = new byte[]{(byte)0xF4,(byte)0x8F,(byte)0xBF,(byte)0xBF,
					(byte)0x32};
			EncodingHelperChar c = new EncodingHelperChar(b);
			fail(
				"Constructor didn't throw when byte array was too large - more"
				+ "than 4 bytes."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
	}
	/*
	 * Tests that the constructor (for array parameter) throws when
	 * the array is empty
	 */
	@Test
	public void constructorArrayIfEmptyShouldThrow() {
		try {
			byte[] b = {};
			EncodingHelperChar c = new EncodingHelperChar(b);
			fail(
				"Constructor didn't throw when byte array was empty."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
	}

	/*
	 * Tests that the constructor (for UTF-8 byte sequence) throws when
	 * the binary representation of an input 1 byte sequence begins with 1.
	 */
	@Test
	public void constructorArrayWithIncorrectPrefixFor1ByteSequenceShouldThrow() {
		 //For arbitrary middle invalid 1 byte
		try {
			byte[] b1 = {(byte)0x9A};
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when the 1 byte array "
				+ "was invalid - prefix of 1."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		//For UTF-8 byte of 0x80 (lower bound for invalid 1 byte sequence)
		try {
				byte[] b2 = {(byte)0x80}; 
				EncodingHelperChar c2 = new EncodingHelperChar(b2);
				fail(
					"Constructor didn't throw when the 1 byte array "
					+ "was invalid - prefix of 1."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
		//For UTF-8 byte of 0xFF (upper bound for invalid 1 byte sequence)
		try {
			byte[] b3 = {(byte)0xFF}; 
			EncodingHelperChar c3 = new EncodingHelperChar(b3);
			fail(
				"Constructor didn't throw when the 1 byte array "
				+ "was invalid - prefix of 1."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}
	
	//throws when first byte of multi-byte sequence begins with "0"
	@Test
	public void constructorArrayWithIncorrectPrefixForByteSequenceShouldThrow() {
		//arbitrary utf8 byte sequence beginning with 0 in bounds
		try {
			byte[] b = new byte[]{(byte)0x74,(byte)0x8F,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c = new EncodingHelperChar(b);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
		//lower bound of utf8 byte sequence beginning with 0
		try {
			byte[] b = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
			EncodingHelperChar c = new EncodingHelperChar(b);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		//higher bound of utf8 byte sequence beginning with 0
		try {
			byte[] b = new byte[]{(byte)0x7F,(byte)0xBF,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c = new EncodingHelperChar(b);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
	}

	//first has prefix "10"
	
	//invalid continuation byte (for correct: low bound 80 high bound bf)
	//no ff or fe
	//bytes after the first for multiple byte sequences do not begin with 10
	/////specified prefix should correspond to number of bytes
	//should be 3 bytes but missing a continuation byte
	//should by 3 bytes but has an unexpected continuation byte
	//continuation bytes are not between 0x80 and 0xbf
	//overlong sequence with extra 0s (reject it!)
	//utf 16 surrogates
	
	///////////// all for setCodepoint(int codepoint)
	//makes sure codepoint works like first five tests
	
	/////////////character to utf8 byte array
	//Tests that toUtf8Bytes() does not return null
	//sevenBitCodepointShouldEncodeInOneByte
	//two bytes
	//three bytes
	//four bytes
	//8, 12, 17 bit edge cases
	//tough characters? which are...? 1AAAA?
	@Test
	public void toUTFBytesShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertFalse("Failed to generate valid UTF-8 Byte Sequence - empty", c.toUtf8String().isEmpty());
	}

	
	////////////character to codepoint string (U+
	//does not return null
	//tough characters again?
	//starts with U+
	//no quotation marks
	
	///////////character to utf byte escape sequence
	//does not return null
	
	//////////character to official name of character string
	//U+2C1F7 does not have an official name??
	//
}
