package test;

import java.io.*;

public class Input {
	public int size = 10000 ; //行数
	public String[] inputText = new String[size];
	public int lineCount ;
	private int bufferSize = 20 * 1024 * 1024; //缓存大小
	 
	public Input(String textName){
		try {
			//创造缓冲读入
			File file = new File(textName);  
	        FileInputStream fileInputStream = new FileInputStream(file);  
	        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);  
	        InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);  
	        BufferedReader input = new BufferedReader(inputStreamReader, bufferSize);
			//写入内存
			String line = null ;
			while((line = input.readLine()) != null) {
				if(line.isEmpty()) {
					continue ;
				}
				inputText[lineCount] = line ;
				lineCount++ ;
			}
			input.close();  
		}catch(IOException e) {
		}
	}
}
