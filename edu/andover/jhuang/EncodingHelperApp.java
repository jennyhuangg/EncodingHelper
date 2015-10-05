package edu.andover.jhuang;
/*
 * EncodingHelper Controller
 * 
 * Jenny Huang
 * Project 1.3
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 5 October 2015
 */

import static org.junit.Assert.fail;

import java.util.ArrayList;

public class EncodingHelperApp {
	
	public static String runApp(String[] args) {
		ArrayList<String> data = getDataList(args);
		String inputType = getInputType(args);
		String outputType = getOutputType(args);
		
		//help message
		if (data.get(0).equals("--help") || data.get(0).equals("-h")) {
			//got help from cam about the help message
			return "Usage: java EncodingHelper [-i type] [-o type] <data>\n" +
                "\tInput types: string, utf8, codepoint\n" +
                "\tOutput types: string, utf8, codepoint, summary";
		}
		
		//no data input from user
		else if (data.size() == 0) {
			return "need an input arg: please input a string, "
					+ "utf8 sequence, or codepoint(s)";
		}
		
		//input is string
		else if (inputType.equals("string")) {
			//if input string is more than one arg, print fail message
			if (data.size() > 1)
				return "String input can only be one arg";
			//special case: string is a character
			else if (data.get(0).length() == 1) {
				char c = data.get(0).charAt(0);
				EncodingHelperChar ehc = new EncodingHelperChar(c);
				//string output
				if (outputType.equals("string")){
					String ch = "" + c;
					return ch;
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return ehc.toUtf8String();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehc.toCodepointString();
				}
				//summary output
				else {
					return "Character: " + c + "\n" +
						"Code point: " + ehc.toCodepointString() + "\n" +
						"Name: " + ehc.getCharacterName() + "\n" +
						"UTF-8: " + ehc.toUtf8String();
				}	
			}
			//when string is not one character long
			else {
				String s = data.get(0);
				EncodingHelperString ehs = new 
						EncodingHelperString(s);
				//string output
				if (outputType.equals("string")) {
					return s;
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return ehs.toUtf8String();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehs.toCodepointString();
				}
				// summary output
				else {
					return "String: " + data.get(0) + "\n" +
						"Code points: " + ehs.toCodepointString() + "\n" +
						"UTF-8: " + ehs.toUtf8String();
				}
			}
		}
		//utf8 input
		else if (inputType.equals("utf8")) {
			String utf8String = data.get(0);
			ArrayList<Byte> utf8Bytes = toUtf8BytesFromString(utf8String);
			EncodingHelperString ehs = new 
					EncodingHelperString(utf8Bytes);
			ArrayList<Integer> codepoints = ehs.getCodepoints();
			
			if (data.size() > 1)
				return "Utf8 input can only be one arg";
			//special case: utf8 sequence represents one character
			else if (codepoints.size() == 1) {
				int codepoint = codepoints.get(0);
				EncodingHelperChar ehc = 
						new EncodingHelperChar(codepoint);
				//string output
				if (outputType.equals("string")){
					//codepoint to string converter from cam
					String s = new String(new int[] {codepoint}, 0, 1);
					return s;
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return utf8String;
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehc.toCodepointString();
				}
				//summary output
				else {
					String s = new String(new int[] {codepoint}, 0, 1);
					return "Character: " + s + "\n" +
						"Code point: " + ehc.toCodepointString() + "\n" +
						"Name: " + ehc.getCharacterName() + "\n" +
						"UTF-8: " + utf8String;
				}	
			}
			//utf8 is not just one character
			else {
				//string output
				if (outputType.equals("string")) {
					String outputString = "";
					for (int i = 0; i < codepoints.size(); i++) {
						char[] ch = Character.toChars(codepoints.get(i));
						outputString += ch[0];
						
					}
					return outputString;
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return utf8String;
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehs.toCodepointString();
				}
				// summary output
				else {
					String outputString = "";
					for (int i = 0; i < codepoints.size(); i++) {
						//from cam
						String s = new String(new int[] {codepoints.get(i)}, 0, 1);
						outputString += s;
					}
					return "String: " + outputString + "\n" +
						"Code point: " + ehs.toCodepointString() + "\n" +
						"UTF-8: " + ehs.toUtf8String();
				}
			}
		}
		//codepoint input
		else if (inputType.equals("codepoint")) {
			String potentialCodepoints = data.get(0);
			String[] split = potentialCodepoints.split("\\s+");
			int[] codepoints;
			if (split.length == 1) {
				codepoints = new int[data.size()];
				for (int i = 0; i < data.size(); i++) {
					codepoints[i] = toIntCodepointFromString(data.get(i));
				}
			}
			else {
				codepoints = new int[split.length];
				for (int k = 0; k < split.length; k++) {
					codepoints[k] = toIntCodepointFromString(split[k]);
				}
			}
			//special case: only one codepoint (represents one character)
			if (codepoints.length == 1) {
				int codepoint = codepoints[0];
				EncodingHelperChar ehc = 
						new EncodingHelperChar(codepoint);
				//string output
				if (outputType.equals("string")){
					//codepoint to string converter from cam
					String s = new String(new int[] {codepoint}, 0, 1);
					return s;
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return ehc.toUtf8String();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehc.toCodepointString();
				}
				//summary output
				else {
					String s = new String(new int[] {codepoint}, 0, 1);
					return "Character: " + s + "\n" +
						"Code point: " + ehc.toCodepointString() + "\n" +
						"Name: " + ehc.getCharacterName() + "\n" +
						"UTF-8: " + ehc.toUtf8String();
				}	
			}
			//more than one codepoint (not just one character)
			else {
				EncodingHelperString ehs = new EncodingHelperString(codepoints);
				//string output
				if (outputType.equals("string")) {
					String outputString = "";
					for (int i = 0; i < codepoints.length; i++) {
						//from cam
						String s = new String(new int[] {codepoints[i]}, 0, 1);
						outputString += s;
					}
					return outputString;
					
				}
				//utf8 output
				else if (outputType.equals("utf8")) {
					return ehs.toUtf8String();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehs.toCodepointString();
				}
				//summary output
				else {
					String outputString = "";
					for (int i = 0; i < codepoints.length; i++) {
						//from cam
						String s = new String(new int[] {codepoints[i]}, 0, 1);
						outputString += s;
					}
					return "String: " + outputString + "\n" +
						"Code point: " + ehs.toCodepointString() + "\n" +
						"UTF-8: " + ehs.toUtf8String();
				}
			}
		}
		return "";
	}
	
	//cam helped me come up with the idea of a data list
	public static ArrayList<String> getDataList(String[] args) {
		ArrayList<String> data = new ArrayList<>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-i") || args[i].equals("--input")) {
				boolean isInputType = args[i+1].equalsIgnoreCase("string") || 
						args[i+1].equalsIgnoreCase("utf8") ||
						args[i+1].equalsIgnoreCase("codepoint");
				if (isInputType) {
					i++;
				}
			}
			else if (args[i].equals("-o") || args[i].equals("--output")) {
				boolean isOutputType = args[i+1].equalsIgnoreCase("string") || 
						args[i+1].equalsIgnoreCase("utf8") ||
						args[i+1].equalsIgnoreCase("codepoint") ||
						args[i+1].equalsIgnoreCase("summary");
				if (isOutputType) {
					i++;
				}
			}
			else
				data.add(args[i]);
		}
		return data;
	}
	
	public static String getInputType(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-i") || args[i].equals("--input")) {
				if (args[i+1].equalsIgnoreCase("string"))
					return "string";
				else if (args[i+1].equalsIgnoreCase("utf8"))
					return "utf8";
				else if (args[i+1].equalsIgnoreCase("codepoint"))
					return "codepoint";
				else {
					System.out.println("Incorrect input type: can only enter "
							+ "'string' 'utf8' or 'codepoint'");
					System.exit(1);
				}
			}
		}
		return "string";
	}
	
	public static String getOutputType(String[] args) {
		for (int i = 0; i+1 < args.length; i++) {
			if (args[i].equals("-o") || args[i].equals("--output")) {
				if (args[i+1].equalsIgnoreCase("string"))
					return "string";
				else if (args[i+1].equalsIgnoreCase("utf8"))
					return "utf8";
				else if (args[i+1].equalsIgnoreCase("codepoint"))
					return "codepoint";
				else if (args[i+1].equalsIgnoreCase("summary"))
					return "summary";
				else {
					System.out.println("Incorrect output type: can only enter "
							+ "'string' 'utf8' 'codepoint' or 'summary'");
					System.exit(1);
				}	
			}
		}
		return "summary";
	}
	
	//convert from utf8 string to utf8 byte ArrayList
	//more flexible input formatting for utf8 inputs (special implementation)
	//got a bit of help from cam
	public static ArrayList<Byte> toUtf8BytesFromString(String utf8String) {
		ArrayList<Byte> utf8Bytes = new ArrayList<Byte>();
		for (int i = 0; i < utf8String.length(); i++) {
			// backslash is found
			if (utf8String.charAt(i) == '\\') {
				if (utf8String.charAt(i+1) == 'x') {
					 try {
						String nextByteString = utf8String.substring(i+2, i+4);
						int nextByteInt = Integer.parseInt(nextByteString, 16);
						utf8Bytes.add((byte)nextByteInt);
					 } catch(IllegalArgumentException e) {
						 System.out.println("Invalid utf8 format");
					 }
					 i += 3;
				}
				else {
					System.out.println("Invalid utf8 format");
				}
			}
			// x is found
			else if (utf8String.charAt(i) == 'x') {
				 try {
					String nextByteString = utf8String.substring(i + 1, i + 3);
					int nextByteInt = Integer.parseInt(nextByteString, 16);
					utf8Bytes.add((byte)nextByteInt);
				 } catch(IllegalArgumentException e) {
					 System.out.println("Invalid utf8 format");
				 }
				 i +=2;
			}
			else { 
				 try {
					String nextByteString = utf8String.substring(i, i + 2);
					int nextByteInt = Integer.parseInt(nextByteString, 16);
					utf8Bytes.add((byte)nextByteInt);
				 } catch(IllegalArgumentException e) {
					 System.out.println("Invalid utf8 format");
				 }
				 i++;
			}
			
		}
		return utf8Bytes;
	}
	
	//convert from string codepoint to int codepoint
	//with more flexible input formatting for codepoint inputs
	public static int toIntCodepointFromString(String inputCodepointString) {
		int codepoint = 0;
		//begins with U+ like U+00E9
		if (inputCodepointString.startsWith("U+")) {
			String codepointString = inputCodepointString.substring(2);
				try {
					codepoint = Integer.parseInt(codepointString, 16);
				} catch(IllegalArgumentException e) {
					System.out.println("Invalid codepoint format");
				}
		}
		//begins with \\u like \u00EA
		else if (inputCodepointString.startsWith("\\u")) {
			String codepointString = inputCodepointString.substring(3);
			try {
				codepoint = Integer.parseInt(codepointString, 16);
			} catch(IllegalArgumentException e) {
				System.out.println("Invalid codepoint format");
			}
		}
		//begins with u like u00EA
		else if (inputCodepointString.startsWith("u")) {
			String codepointString = inputCodepointString.substring(1);
			try {
				codepoint = Integer.parseInt(codepointString, 16);
			} catch(IllegalArgumentException e) {
				System.out.println("Invalid codepoint format");
			}
		}
		//no other formatted beginning
		else {
			String codepointString = inputCodepointString;
			try {
				codepoint = Integer.parseInt(codepointString, 16);
			} catch(IllegalArgumentException e) {
				System.out.println("Invalid codepoint format");
			}
		}
		return codepoint;
	}
}
