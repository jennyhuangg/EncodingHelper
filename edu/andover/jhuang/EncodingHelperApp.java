package edu.andover.jhuang;
/*
 * EncodingHelper Controller
 * 
 * Jenny Huang
 * Project 1.3
 * COMP-630: Software Design, Instructor: Dr. Miles
 * 5 October 2015
 */

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
				else if (outputType.equals("string")) {
					return ehc.toCodepointString();
				}
				//summary output
				else {
					return "Character: " + c + "\n" +
						"Codepoint: " + ehc.toCodepointString() + "\n" +
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
					return ehs.toCodepointString();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehs.toUtf8String();
				}
				// summary output
				else {
					return "String: " + data.get(0) + "\n" +
						"Codepoint: " + ehs.toCodepointString() + "\n" +
						"UTF-8: " + ehs.toUtf8String();
				}
			}
		}
		//utf8 input
		else if (inputType.equals("utf8")) {
			if (data.size() > 1)
				return "Utf8 input can only be one arg";
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
					return ehs.toCodepointString();
				}
				//codepoint output
				else if (outputType.equals("codepoint")) {
					return ehs.toUtf8String();
				}
				// summary output
				else {
					return "String: " + data.get(0) + "\n" +
						"Codepoint: " + ehs.toCodepointString() + "\n" +
						"UTF-8: " + ehs.toUtf8String();
				}
			}
			

	}
			return "";
		}
	}
	
	//cam helped me come up with the idea of a data list
	public static ArrayList<String> getDataList(String[] args) {
		ArrayList<String> data = new ArrayList<>();
		String inputType = getInputType(args);
		String outputType = getOutputType(args);
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
		for (int i = 0; i < args.length; i++) {
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
}
