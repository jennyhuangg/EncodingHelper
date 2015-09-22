package edu.andover.jhuang;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncodingHelperCharTest {
	
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
	 * tests that getCodepoint() returns the integer codepoint specified when
	 * constructing the object
	 */
	@Test
	public void getCodePoint_ShouldMatchValuePassedToConstructor() {
		int x = 0x1F1F;
		int y = 0x1AAAA;
		EncodingHelperChar c = new EncodingHelperChar(x);
		EncodingHelperChar c2 = new EncodingHelperChar(y);
		int outputCodePoint = c.getCodepoint();
		assertEquals("Constructor has incorrect codepoint", x, outputCodePoint);
	}
	
	/*
	 * tests that the constructor (for byte array parameter) throws when
	 * the codepoint is invalid for a single character
	 */
	@Test
	public void constructorArrayShouldThrow() {
		int[] b = new int[]{1,2,3,4,5};
	}

}
