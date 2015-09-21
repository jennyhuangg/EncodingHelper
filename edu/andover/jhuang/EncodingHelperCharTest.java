package edu.andover.jhuang;

import static org.junit.Assert.*;

import org.junit.Test;

public class EncodingHelperCharTest {

	@Test
	public void testCharConstructorShouldRepresentCorrectCodepoint() {
		EncodingHelperChar c = new EncodingHelperChar(10295); //choose trickier number
		int outputCodePoint = c.getCodepoint();
		assertEquals("Did not make codepoint correctly", 10295, outputCodePoint);
	}
	
	/*
	 * checks that the constructor throws when the input codepoint is
	 * out of range - negative
	 */
	@Test
	public void illegalCharConstructorShouldThrowWhenCodepointIsNegative() {
		 try {
				EncodingHelperChar c = new EncodingHelperChar(-1);
		        fail("My constructor didn't throw when the codepoint wasm out of range - negative");
		 } catch(IllegalArgumentException expectedException) {
		        // No action needed.
		 }
	}
	
	/*
	 * checks that the constructor throws when the input codepoint is 
	 * out of range - too large
	 */
	@Test 
	public void illegalCharConstructorShouldThrowWhenCodepointIsTooLarge() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x110000);
				EncodingHelperChar c2 = new EncodingHelperChar(0x111111);
				fail("My constructor didn't throw when the codepoint was out of range - too large");
		} catch (IllegalArgumentException e) {
				//No action needed.
		}
	}

}
