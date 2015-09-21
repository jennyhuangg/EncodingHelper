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
	
	@Test
	public void illegalCharConstructorShouldThrowWhenCodepointIsNegative() {
		 try {
				EncodingHelperChar c = new EncodingHelperChar(-1);
		        fail("My constructor didn't throw when the codepoint was negative");
		 } catch(IllegalArgumentException expectedException) {
		        // No action needed.
		 }
	}
	
	@Test
	public void illegalCharConstructorShouldThrowWhenCodepointIsTooLarge() {
		try {
				EncodingHelperChar c = new EncodingHelperChar(0x110000);
				fail("My constructor didn't throw when the codepoint was too large");
		} catch (IllegalArgumentException e) {
				
		}
	}
	

}
