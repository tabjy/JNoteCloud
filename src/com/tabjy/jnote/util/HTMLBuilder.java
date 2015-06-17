package com.tabjy.jnote.util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.markdown4j.Markdown4jProcessor;

public class HTMLBuilder {
	
	public static String renderMarkdown(String markdownString) throws IOException{
		String htmlString = "Oops~ Something worng with the Markdown processor.";
		htmlString = new Markdown4jProcessor().process(markdownString);
		String htmlTemplate = "Oops~ Something worng with the Markdown processor.";
		/**File template = new File("./template.html");
		Scanner scan = new Scanner(template);
		while (scan.hasNextLine()){
			htmlTemplate +=scan.nextLine();
		}*/
		htmlTemplate = "<html> <head> <title>JNote Cloud</title> <style>body{padding: 0px; margin: 10px; color: rgb(63, 63, 63); background-color: #f6f6f6;}a{transition: background-color ease-in-out .15s, color ease-in-out .15s, border-color ease-in-out .15s; text-decoration: none; color: #4fa1db;}a:hover, a:focus{color: #216da3; text-decoration: underline;}h1, h2, h3, h4, h5, h6{font-family: Verdana, 'Helvetica Neue', 'Microsoft Yahei', 'Hiragino Sans GB', 'Microsoft Sans Serif', 'WenQuanYi Micro Hei', sans-serif; font-weight: 100; color: rgb(63, 63, 63); line-height: 1.35;}body, button, input, select, textarea{font: 400 1em/1.8 Lantinghei SC, Microsoft Yahei, Hiragino Sans GB, Microsoft Sans Serif, WenQuanYi Micro Hei, sans-serif; font-family: 'Lantinghei SC', 'Microsoft Yahei', 'Hiragino Sans GB', 'Microsoft Sans Serif', 'WenQuanYi Micro Hei', sans-serif; font-size: 1em; font-style: normal; font-variant: normal; font-weight: 400; font-stretch: normal; line-height: 1.8;}blockquote{padding: 10px 20px; margin: 0 20px; background-color: #efefef; border-left: 5px solid #e6e6e6;}</style> <script></script> </head> <body>";
		htmlTemplate +=htmlString;
		htmlTemplate +="</body></html>";
		return htmlTemplate;
	}

}
