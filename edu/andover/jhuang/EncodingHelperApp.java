package edu.andover.jhuang;

import java.util.ArrayList;

public class EncodingHelperApp {
	
	public static String runApp(String[] args) {
		ArrayList<String> data = getDataList(args);
		String inputType = getInputType(args);
		String outputType = getOutputType(args);
		
		//if character
		if (data.size() == 1 && data.get(0).length() == 1 && 
				inputType.equals("string")) {
			 char c = data.get(0).charAt(0);
			 EncodingHelperChar ehc = new EncodingHelperChar(c);
			 String s = "";
			 s += "Character: " + c + "\n";
			 s += "Codepoint: " + ehc.toCodepointString() + "\n";
			 s += "Name: " + ehc.getCharacterName() + "\n";
			 s += "UTF-8: " + ehc.toUtf8String();
			 return s;
		}
		//if string
		else if (data.size() == 1 && data.get(0).length() > 1 && 
				inputType.equals("string")) {
			 for (int i = 0; i < args.length; i++) {
				 EncodingHelperString ehs = new EncodingHelperString(data.get(0));
				 String s = "";
				 s += "String: " + data.get(0) + "\n";
				 s += "Codepoints: " + ehs.toCodepointString() + "\n";
				 s += "UTF-8: " + ehs.toUtf8String();
				 return s; 
			 }
		}
		//if string is more than one arg
		else if (inputType.equals("string") && data.size() > 1) {
			System.out.println("Input cannot be longer than one string");
		}
return "";
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
		String[] data = {};
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
		String[] data = {};
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
				}
				else {
					System.out.println("Incorrect output type: can only enter "
							+ "'string' 'utf8' 'codepoint' or 'summary'");
					System.exit(1);
				}	
		}
		return "summary";
	}
}
