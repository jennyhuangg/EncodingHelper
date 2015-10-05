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
		assertEquals("Failed to return correct input type - should"
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
	public void getOutputTypeShouldReturnCorrectOutputType() {
		String[] args = {"--output", "codepoint"};
		assertEquals("Failed to return correct input type - should"
				+ "return codepoint","codepoint", 
				EncodingHelperApp.getOutputType(args));
	}
	
	@Test
	public void getOutputTypeShouldReturnSummaryIfNotSpecified() {
		String[] args = {"hi"};
		assertEquals("Failed to return correct input type - default output type"
				+ "should be summary","summary", 
				EncodingHelperApp.getOutputType(args));
	}
	
	@Test
	public void getDataListShouldReturnCorrectListOfData() {
		String[] args = {"-i", "codepoint", "-o", "string", "U+00E9", "U+0068"};
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("U+00E9");
		expected.add("U+0068");
		assertEquals("Failed to create correct DataList- args should be U+00E9"
				+ "and U+0068", expected, EncodingHelperApp.getDataList(args));
	}
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorString() {
		ArrayList<Integer> stringCodepoints = new ArrayList<Integer>();
		stringCodepoints.add(0x68);
		stringCodepoints.add(0x69);
		EncodingHelperString ehs = new EncodingHelperString("hi");
		assertEquals("Failed to construct correctly - 'hi' should have "
			+ "codepoints 0x68 0x69", stringCodepoints, ehs.getCodepoints());
	}
	
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorIntArray() {
		int[] codepoints = {0x68, 0x69};
		ArrayList<Integer> expectedCodepoints = new ArrayList<Integer>();
		expectedCodepoints.add(0x68);
		expectedCodepoints.add(0x69);
		EncodingHelperString ehs = new EncodingHelperString(codepoints);
		assertEquals("Failed to construct correctly - 0x68 0x69 should be "
				+ "0x68 0x69", expectedCodepoints, ehs.getCodepoints());
	}
	
	@Test
	public void getCodepointShouldMatchCodepointPassedToConstructorByteArray() {
		ArrayList<Byte> b = new ArrayList<Byte>();
		b.add((byte)0xC3);
		b.add((byte)0xAA);
		b.add((byte)0x74);
		b.add((byte)0x72);
		b.add((byte)0x65);
		ArrayList<Integer> expectedCodepoints = new ArrayList<Integer>();
		expectedCodepoints.add(0x00EA);
		expectedCodepoints.add(0x0074);
		expectedCodepoints.add(0x0072);
		expectedCodepoints.add(0x0065);
		EncodingHelperString ehs = new EncodingHelperString(b);
		assertEquals("Failed to construct correctly with byte array", 
				expectedCodepoints, ehs.getCodepoints());
	}
	
	@Test
	public void toCodepointStringShouldReturnCorrectString() {
		int[] codepoints = {0x68, 0x69};
		String expected = "U+0068 U+0069 ";
		EncodingHelperString ehs = new EncodingHelperString(codepoints);
		assertEquals("Failed to convert to codepoint string correctly - 0x68 "
				+ "0x69 should be U+0068 U+0069", expected, 
				ehs.toCodepointString());
	}
	
	@Test
	public void toUtf8StringShouldReturnCorrectByteString() {
		String expected = "\\xC3\\xAA\\x74\\x72\\x65";
		int[] codepoints = {0x00EA, 0x0074, 0x0072, 0x0065};
		EncodingHelperString ehs = new EncodingHelperString(codepoints);
		assertEquals("Failed to convert to Utf8 string correctly", expected, 
				ehs.toUtf8String());
	}
	
}
