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
	public void getCodepointShouldCorrespondToByteArrayPassedToConstructorArray() {
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
	
	/*
	 * Tests that the constructor (for array parameter) throws when
	 * the input UTF-8 byte array contains more than 4 bytes
	 */
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
	
	/*
	 * Tests that the constructor (with array parameter) throws when the first 
	 * byte of a multi-byte sequence has a prefix of 0.
	 */
	@Test
	public void constructorArrayWith0PrefixForByteSequenceShouldThrow() {
		//for arbitrary utf8 byte sequence with 0  prefix in bounds
		try {
			byte[] b1 = new byte[]{(byte)0x74,(byte)0x8F,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
		//for utf8 byte sequence of 0's (lower bound for those with 0 prefix)
		try {
			byte[] b2 = new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
			EncodingHelperChar c2 = new EncodingHelperChar(b2);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		//for utf8 byte sequence of 0x7F 0xBF 0xBF 0xBF (higher bound for those with 0 prefix)
		try {
			byte[] b3 = new byte[]{(byte)0x7F,(byte)0xBF,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c3 = new EncodingHelperChar(b3);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte of multi-byte sequence has prefix of O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
	}
	
	/*
	 * Tests that the constructor (with array parameter) throws when the first 
	 * byte has a prefix of 10.
	 */
	@Test
	public void constructorArrayWith10PrefixForByteSequenceShouldThrow() {
		try {
			byte[] b1 = new byte[]{(byte)0x94,(byte)0x8F,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte has a prefix of 1O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
		try {
			byte[] b2 = new byte[]{(byte)0x80,(byte)0x00,(byte)0x00,(byte)0x00};
			EncodingHelperChar c2 = new EncodingHelperChar(b2);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte has prefix of 1O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		try {
			byte[] b3 = new byte[]{(byte)0xBF,(byte)0xBF,(byte)0xBF,(byte)0xBF};
			EncodingHelperChar c3 = new EncodingHelperChar(b3);
			fail(
				"Constructor didn't throw when byte sequence had incorrect"
				+ "prefix - first byte has prefix of 1O."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}	
	}
	
	/*
	 * Tests that the constructor (with array parameter) throws when the input
	 * byte sequence includes an invalid continuation byte, or one not within 
	 * the bounds of 0x80 and 0xbf
	 */
	@Test
	public void constructorArrayWithInvalidContinuationBytesShouldThrow() {
		try {
			byte[] b1 = {(byte)0xDF, (byte)0x7F}; //second byte prefix of 01
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when the byte sequence's "
				+ "continuation byte was invalid - prefix of not 10."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		try {
			byte[] b2 = {(byte)0xDF, (byte)0x3F}; //second byte prefix of 00
			EncodingHelperChar c2 = new EncodingHelperChar(b2);
			fail(
				"Constructor didn't throw when the byte sequence's "
				+ "continuation byte was invalid - prefix not 10."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		try {
			byte[] b3 = {(byte)0xDF, (byte)0xF1}; //second byte prefix of 11
			EncodingHelperChar c3 = new EncodingHelperChar(b3);
			fail(
				"Constructor didn't throw when the byte sequence's "
				+ "continuation byte was invalid - prefix not 10."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}

	/*
	 * Tests that the constructor (for array parameter) throws when any byte
	 * has the value 0xFF or 0xFE because these bytes cannot appear in a 
	 * correct UTF-8 byte sequence
	 */
	@Test
	public void constructorArrayWithImpossibleValuesShouldThrow() {
		//for 0xFF
		try {
			byte[] b1 = {(byte)0xFF, (byte)0xBF}; 
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when one of the bytes"
				+ "was impossible - 0xFF."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
		//for 0xFE
		try {
			byte[] b2 = {(byte)0x2F, (byte)0xFE}; 
			EncodingHelperChar c2 = new EncodingHelperChar(b2);
			fail(
				"Constructor didn't throw when one of the bytes"
				+ "was impossible - 0xFE."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}
	
	/*
	 * Tests that the constructor (with array parameter) throws when the input
	 * byte sequence is missing a continuation byte
	 */
	@Test
	public void constructorArrayByteArrayWithMissingByteShouldThrow() {
		//prefix indicates three bytes but only has two bytes
		try {
			byte[] b1 = {(byte)0xEF, (byte)0xBF}; 
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when the byte sequence "
				+ "was missing a continuation byte."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}
	
	/*
	 * Tests that the constructor throws when the input byte sequence has 
	 * an extra continuation byte
	 */
	@Test
	public void constructorArrayBiteArrayWithExtraByteShouldThrow() {
		//prefix indicates two bytes but has three bytes
		try {
			byte[] b1 = {(byte)0xDF, (byte)0xBF, (byte)0xBF}; 
			EncodingHelperChar c1 = new EncodingHelperChar(b1);
			fail(
				"Constructor didn't throw when the byte sequence "
				+ "was missing a continuation byte."
			);
		} catch (IllegalArgumentException e) {
			//No action needed.
		}
	}

	/*
	 * Tests that the constructor (with array parameter) throws when the
	 * byte array represents an overlong sequence (sequence is encoded in
	 * more bytes than necessary)
	 */
	@Test
	public void constructorArrayOverlongSequenceCodepointShouldThrow() {
		try {
				byte[] b = new byte[] {(byte)0xE0,(byte)0x91,(byte)0xB8};
				EncodingHelperChar c = new EncodingHelperChar(b);
				fail(
					"Constructor didn't throw when the byte array's"
					+ "utf8 byte sequence was an overlong sequence."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	//utf 16 surrogates
	
	/*
	 * Tests that getCodepoint() returns the corresponding codepoint for the
	 * char specified when constructing the object
	 */
	@Test
	public void getCodepointShouldCorrespondToCharPassedToConstructorChar() {
		char ch = 'e';
		int charCodepoint = 0x0065;
		EncodingHelperChar c = new EncodingHelperChar(ch);
		assertEquals("Failed to construct correctly - 'e' should have codepoint"
				+ "0x0065", charCodepoint, c.getCodepoint());
		//if you have time, do lower and upper bounds too!!!
	}

	/*
	 * The following three methods test that setCodepoint(int codepoint) 
	 * changes the object's codepoint to the input integer.
	 */
	
	//For arbitrary codepoint in bounds
	@Test
	public void setCodepointShouldMatchCodepointPassedToMethod() {
		int x = 0x1F1E;
		EncodingHelperChar c = new EncodingHelperChar(x);
		c.setCodepoint(0x1F1F);
		assertEquals("Failed to change codepoint correctly - 0x1F1E should become 0x1F1F",
				x, c.getCodepoint());
	}
	//For codepoint of 0 (lower bound of valid codepoints)
	@Test
	
	public void minCodepointShouldSetCorrectly() {
		int x = 0x1F1E;
		EncodingHelperChar c = new EncodingHelperChar(x);
		c.setCodepoint(0);
		assertEquals("Failed to change codepoint correctly - 0x1F1E should be 0",
				x, c.getCodepoint());
	}
	//For codepoint of 0x10FFFF (upper bound of valid codepoints)
	@Test
	public void maxCodepointShouldSetCorrectly() {
		int x = 0x10FFFF;
		EncodingHelperChar c = new EncodingHelperChar(x);
		c.setCodepoint(0x10FFFF);
		assertEquals("Failed to change codepoint correctly - 0x1F1E should be 10FFFF",
				x, c.getCodepoint());
	}
	
	/*
	 * Tests that setCodepoint(int codepoint) throws when
	 * the codepoint is out of range - negative
	 */
	@Test
	public void setCodepointWithNegativeCodepointShouldThrow() {
		 try {
			 	EncodingHelperChar c = new EncodingHelperChar(0x1AAA);
			 	c.setCodepoint(-1);
		        fail(
		        	"Set method didn't throw when the "
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
	public void setCodepointWithTooLargeCodepointShouldThrow() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x1AAA);
				c.setCodepoint(0x110000);
				fail(
					"Set method didn't throw when the "
					+ "codepoint was out of range - too large."
				);
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}
	
	/////////////codepoint to utf8 byte array
	
	//Tests that toUtf8Bytes() does not return null
	@Test
	public void toUtf8BytesShouldReturnNonNull() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertNotNull("Failed to generate non-null string", c.toUtf8Bytes());
	}
	//Tests that toUtf8Bytes() does not return empty
	@Test
	public void toUTF8BytesShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertFalse("Failed to generate valid UTF-8 Byte Sequence - empty", 
				c.toUtf8String().isEmpty());
	}
	//Tests that toUtf8Bytes() works for edge case of 8 bits
	@Test
	public void toUTF8BytesByteArrayShouldMatchCodepoint() {
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		byte[] expected = {(byte)0xC3, (byte)0xA4};
		assertArrayEquals("Failed to represent character as a UTF-8 Byte Array", 
				expected, c.toUtf8Bytes());
		EncodingHelperChar c2 = new EncodingHelperChar(0x1AAAA);
		byte[] expected2 = {(byte)0xF0, (byte)0x9A, (byte)0xAA, (byte)0xAA};
		assertArrayEquals("Failed to represent character as a UTF-8 Byte Array", 
				expected2, c2.toUtf8Bytes());
	}
	//8, 12, 17 bit edge cases
	
	////////////codepoint to codepoint string (U+???)
	
	//Tests that toCodepointString() does not return null
	@Test 
	public void toCodepointStringShouldReturnNonNull() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertNotNull("Failed to generate non-null string", c.toCodepointString());
	}
	//Tests that toCodepointString() does not return empty
	@Test
	public void toCodepointStringShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertFalse("Failed to generate valid U+ string - empty", 
				c.toUtf8String().isEmpty());	
	}
	//Tests that toCodepointString() correctly converts character to U+ string for edge cases
	@Test
	public void toCodepointStringShouldMatchCodepoint() {
		//test a two digit hex codepoint
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		assertEquals("Failed to represent character as a U+ string", 
				"U+00E4", c.toCodepointString());
		//test a five digit hex codepoint
		EncodingHelperChar c2 = new EncodingHelperChar(0x1AAAA);
		assertEquals("Failed to represent character as a U+ string", 
				"U+1AAAAA", c2.toCodepointString());
	}
	//Tests that toCodepointString() returns string that starts with U+
	@Test
	public void toCodepointStringShouldContainUPlus() {
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		boolean startsWithUplus = c.toCodepointString().startsWith("U+");
		
		assertTrue("Failed to return string that starts with U+",
				startsWithUplus);
	}
	//Tests that toCodepointString() returns string without quotation marks
	@Test
	public void toCodepointStringShouldNotHaveQuotes() {
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		boolean startsWithQuote = c.toCodepointString().startsWith("\"");
		boolean endWithQuote = c.toCodepointString().endsWith("\"");
		assertFalse("Failed to return string without quotation marks",
				startsWithQuote || endWithQuote);
	}

	///////////codepoint to utf byte escape sequence
	
	//Tests that toUtf8String() does not return null
	@Test 
	public void toUtf8StringShouldReturnNonNull() {
		EncodingHelperChar c = new EncodingHelperChar(0x7E53);
		assertNotNull("Failed to generate non-null string", c.toUtf8String());
	}
	//Tests that toUtf8String() does not return empty
	@Test
	public void toUtf8StringShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x44);
		assertFalse("Failed to generate non empty string", 
				c.toUtf8String().isEmpty());	
	}
	//Tests that toUtf8String() correctly converts character to the escaped hexadecimal byte string
	@Test
	public void toUtf8StringShouldMatchCodepoint() {
		//test a two digit hex codepoint
		EncodingHelperChar c = new EncodingHelperChar(0xE9);
		String expected = "\\xC3\\xA9";
		assertEquals("Failed to represent character as an escaped hexadecimal"
				+ "byte string", expected, c.toUtf8String());
		//test a five digit hex codepoint
		EncodingHelperChar c2 = new EncodingHelperChar(0x1AAAA);
		String expected2 = "\\0xF0\\0x9A\\0xAA\\0xAA";
		assertEquals("Failed to represent character as an escaped hexadecimal"
				+ "byte string", expected2, c2.toCodepointString());
	}
	//Tests that toUtf8String() returns string without quotation marks
	public void toUtf8StringShouldNotHaveQuotes() {
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		boolean startsWithQuote = c.toUtf8String().startsWith("\"");
		boolean endWithQuote = c.toUtf8String().endsWith("\"");
		assertFalse("Failed to return string without quotation marks",
				startsWithQuote || endWithQuote);
	}
	
	//////////codepoint to official name of character string
	
	//Tests that getCharacterName() does not return null
	@Test 
	public void getCharacterNameShouldReturnNonNull() {
		EncodingHelperChar c = new EncodingHelperChar(0x7E53);
		assertNotNull("Failed to generate non-null string", c.getCharacterName());
	}
	//Tests that getCharacterName() does not return empty
	@Test
	public void getCharacterNameShouldReturnNonEmpty() {
		EncodingHelperChar c = new EncodingHelperChar(0x4453);
		assertFalse("Failed to generate non empty string", 
				c.getCharacterName().isEmpty());	
	}
	//Tests that getCharacterName() correctly converts character to the character's
	//official Unicode name.
	@Test
	public void getCharacterNameShouldMatchCodepoint() {
		//test a two digit hex codepoint
		EncodingHelperChar c = new EncodingHelperChar(0xE9);
		String expected = "LATIN SMALL LETTER E WITH ACUTE";
		assertEquals("Failed to return character's Unicode name",
				expected, c.toUtf8String());
		//test a five digit hex codepoint
		EncodingHelperChar c2 = new EncodingHelperChar(0x103A2);
		String expected2 = "OLD PERSIAN SIGN U";
		assertEquals("Failed to return character's Unicode name,"
				+ expected2, c2.toCodepointString());
	}
	//Tests that getCharacterName() returns string without quotation marks
	public void getCharacterNameShouldNotHaveQuotes() {
		EncodingHelperChar c = new EncodingHelperChar(0xE4);
		boolean startsWithQuote = c.getCharacterName().startsWith("\"");
		boolean endWithQuote = c.getCharacterName().endsWith("\"");
		assertFalse("Failed to return string without quotation marks",
				startsWithQuote || endWithQuote);
	}
	//U+2C1F7 special instructions
	
}
