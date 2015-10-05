package edu.andover.jhuang;
/*
 * EncodingHelper JUnit Tests for non-trivial methods
 * 
 * Jenny Huang
 * Project 1.3
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 5 October 2015
 */

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class EncodingHelperTest {

	@Test
	public void getInputTypeShouldReturnCorrectInputType() {
		String[] args = {"--input", "codepoint"};
		assertEquals("Failed to return correct input type - codepoint should"
				+ "return codepoint","codepoint", 
				EncodingHelperApp.getInputType(args));
	}
	
	@Test
	public void getInputTypeShouldReturnStringIfNotSpecified() {
		String[] args = {"hi"};
		assertEquals("Failed to return correct input type - default input type"
				+ "should be string","string", 
				EncodingHelperApp.getInputType(args));
	}
	
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorString() {
		ArrayList<Integer> stringCodepoints = new ArrayList<Integer>();
		stringCodepoints.add(0x68);
		stringCodepoints.add(0x69);
		EncodingHelperString ehs = new EncodingHelperString("hi");
		assertEquals("Failed to construct correctly - 'hi' should have "
				+ "codepoints 0x68 0x69", stringCodepoints, ehs.getCodepoint());
	}
	
	/*@Test
	public void getOutputTypeShouldReturnCorrectInput()
	
	@Test
	public void getOutputTypeShouldReturnStringIfNotSpecified()*/
	


	
}
