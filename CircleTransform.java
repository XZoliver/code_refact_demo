package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import javax.management.ImmutableDescriptor;

public class CircleTransform {
	Input input = new Input("") ;
	
	public void whileToFor(String out){
		Stack<String> strStack = new Stack<String>() ;
		for(int line = 0 ; line < input.lineCount ; line++ ) {
			String thisLine = input.inputText[line];
			if(thisLine==null) {
				continue ;
			}
			else {
			}
			while(thisLine.startsWith("\t")) {
				thisLine = thisLine.substring(1);
			}
			if(thisLine.length()>=5) {
				if(thisLine.startsWith("while")) {
					int begin = thisLine.indexOf('(') + 1 ;
					int end = thisLine.indexOf("{");
					while(thisLine.charAt(end) != ')') {
						end-- ;
					}
					String str1 = new String();
					str1="for(;"+thisLine.substring(begin, end)+";){";
					print(out, str1);
				}
				else {
					print(out,thisLine);
				}
			}
			else {
				print(out, thisLine);
			}
		}
	}
	public void forToWhile(String out){
		Stack<String> strStack = new Stack<String>() ;
		for(int line = 0 ; line < input.lineCount ; line++ ) {
			String thisLine = input.inputText[line];
			if(thisLine==null) continue ;
			char[] ch =  thisLine.toCharArray();
			int charI = 0 ;
			while(ch[charI]=='\t'||ch[charI]==' ') {
				charI++ ;
			}
			String right = new String() ;
			if(ch[charI]=='}') {
				if(!strStack.empty()) {
					String outString = strStack.pop();
					print(out,outString);
				}
			}
			else {
				right = thisLine.substring(charI, charI+3);
			}
			if(right.equals("for")) {
				int begin = charI + 4 ;
				int end = charI + 4 ;
				String str1 = new String();
				String str2 = new String();
				String str3 = new String();
				//第一句截取
				while(ch[end] != ';') {
					end++ ;
				}
				str1=thisLine.substring(begin, end);
				if(!str1.equals(" ")) {
					str1+=";";
				}
				print(out,str1);
				begin = end++ ;
				//第二句截取
				while(ch[end] != ';') {
					end++ ;
				}
				str2=thisLine.substring(begin+1,end) ;
				if(str2.equals(" ")) {
					str2="while(true){" ;
				}
				else {
					str2="while("+str2+"){" ;
				}
				print(out,str2);
				begin = end++ ;
				//third
				while(ch[end] != '{') {
					end++ ;
				}
				while(ch[end] != ')') {
					end-- ;
				}
				str3=thisLine.substring(begin+1, end);
				if(!str3.equals(" ")) {
					str3+=";";
				}
				strStack.push(str3);
			}
			else {
				print(out,thisLine);
			}
		}
	}
	public void print(String fileName,String str) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName,true);
			//s = this.deleteNULL(s);
			str += System.getProperty("line.separator");
			fos.write(str.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CircleTransform() {
	}	

}

