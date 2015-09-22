package edu.andover.jhuang;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncodingHelperCharTest {
	
	/*
	 * checks that the constructor throws when the input codepoint is
	 * out of range - negative
	 */
	@Test
	public void testIllegalCharConstructorShouldThrowWhenCodepointIsNegative() {
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
	 * checks that the constructor throws when the input codepoint is 
	 * out of range - too large
	 */
	@Test 
	public void testIllegalCharConstructorIntegerArgumentShouldThrowWhenCodepointIsTooLarge() {
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
	 * checks that getCodepoint() returns the integer codepoint specified in
	 * constructing the object
	 */
	@Test
	public void testGetCodePoint_MatchesValuePassedToConstructor() {
		int x = 0x1F1F;
		int y = 0x1AAAA;
		EncodingHelperChar c = new EncodingHelperChar(x);
		EncodingHelperChar c2 = new EncodingHelperChar(y);
		int outputCodePoint = c.getCodepoint();
		assertEquals("Constructor has incorrect codepoint", x, outputCodePoint);
	}
	
	@Test
	public void testCharConstructorArrayArgumentShouldThrowWhen() {
		int[] b = new int[]{1,2,3,4,5};
	}

}
