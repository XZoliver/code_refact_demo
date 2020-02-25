package test;

import java.io.*;
import java.util.*;


public class NameRefactor {
	private Set<String> dict = new HashSet<String>();	//单词字典，以hash set 优化存储
	private Set<String> identifier = new HashSet<String>();	//标识符存储字典
	private Map<String,String> dataMap= new HashMap<String,String>();	//变量名映射集
	private ArrayList<String> fileArray = new ArrayList<String>();	//文件内容字符串数组，获取所有单词
	private String fileAll = "";
	private String fileDirection;
	private String outputDirection;
	
	NameRefactor(String file,String out) throws IOException {
		fileDirection = file;
		outputDirection = out;
		getDict();
	
		if(!getFile(fileDirection)) {
			return;
		};
		getIdentifier();
		deal(0);
		int sizee = fileArray.size();
		fileAll = "";
		for(String temp : fileArray) {
			fileAll +=temp;
		}
		output();
	}
	private void output() throws IOException {
		File file = new File(outputDirection);
		Writer out = new FileWriter(file);
		out.write(fileAll);
		out.close();
	}
	private void getDict() {
		//获取单词字典
		dict.add("number");
		dict.add("of");
		dict.add("student");
		readFileByLines("enDict.txt");
	}
	/*
    private void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        
        BufferedReader reader = null;  
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	dict.add(tempString); 
                line++;  
            }  
            reader.close();  
        } catch (IOException e) {  
            //e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  
	*/
    private void readFileByLines(String fileName) {  
    	
        try (FileReader reader = new FileReader(fileName);
                BufferedReader br = new BufferedReader(reader) 
           ) {
               String line;
               while ((line = br.readLine()) != null) {
                 dict.add(line);
               }
           } catch (IOException e) {
              // e.printStackTrace();
        }
   }
    
    private boolean readFileByChars(String fileName) {  
        File file = new File(fileName);  
        if(file==null) {
        	return false;
        }
        Reader reader = null;  
        try {   
            // 一次读一个字符  
            reader = new InputStreamReader(new FileInputStream(file));  
            int tempchar;  
            while ((tempchar = reader.read()) != -1) {  
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                // 但如果这两个字符分开显示时，会换两次行。  
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
                if (((char) tempchar) != '\r') {  
                	fileAll += (char) tempchar;
                }  
            }  
            reader.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
        return true;
    }
    
	private boolean getFile(String fileDirection) {
		
		if(!this.readFileByChars(fileDirection)) {
			return false;
		};
		int length = fileAll.length();
		int left = 0, right = 0;
		for(left = 0; left < length; left++) {
			
			if(!Character.isLetterOrDigit(fileAll.charAt(left))) {
				fileArray.add(fileAll.substring(left, left+1));
			}
			else {
				for(right = left; right < length; right++) {
					if(!Character.isLetterOrDigit(fileAll.charAt(right))) {
						break;
					}
				}
				fileArray.add(fileAll.substring(left,right));
				left = right-1;
			}
			
			
		}
		//获取文件内容
		return true;
	}
	private void getIdentifier() {
		//获取常用标识符
		identifier.add("int");
		identifier.add("double");
		identifier.add("float");
		identifier.add("boolean");
		identifier.add("String");
		identifier.add("class");
	}
	private String standardName(String name) {
		//标准化
		String newName = name;

		if(name.contains("_")) {
			newName = underlineToCamelCaseFor(name);
			if(!dataMap.containsKey(name)&&dataMap.containsValue(newName)) {
				return name;
			}
			return newName;
		}
		else {
			newName = normalToCamelCaseFor(name);
		}
		if(!dataMap.containsKey(name)&&dataMap.containsValue(newName)) {
			return name;
		}
		return newName;
	}
	private String underlineToCamelCaseFor(String string) {
		String targetString = "";
		String tempString[] = string.split("_");
		for(int i = 1; i < tempString.length; i++) {
			tempString[i] = tempString[i].substring(0,1).toUpperCase() + tempString[i].substring(1).toLowerCase();	
		}
		for(int i = 0; i < tempString.length; i++) {
			targetString += tempString[i];
		}
		return targetString;
	}
	private  String normalToCamelCaseFor(String string) {
		string = string.toLowerCase();
		boolean first = true;
		String targetString = "";
		int left=0,right=string.length();
		for(left = 0; left < string.length(); ) {
			for(right = string.length(); right>left+1; ) {
				if(dict.contains(string.subSequence(left, right))) {
					
					break;
				}
				else {
					right--;
				}
			}
			//System.out.println( string.subSequence(left, right));
			if(first) {
				targetString += string.substring(left, right);
				first = false;
			}
			else if(left+1>=right) {
				targetString += string.substring(left,left+1).toLowerCase();
			}
			else {
				targetString += (string.substring(left,left+1).toUpperCase() + string.substring(left+1, right));
			}
			//System.out.println( targetString);
			left=right;
		}
		return targetString;
	}
	private void deal(int now) {
		while(now<fileArray.size()) {
			if(now == fileArray.size() - 1) {
				//全部判断完成
				break;
			}
			boolean isClass = false;
			
			if(fileArray.get(now).equals("class")) {
				isClass = true;
			}
			
			if(identifier.contains(fileArray.get(now))) {
				//发现标识符
				now++;
				while(!fileArray.get(now).equals(";")) {
					//依次加入dataMap
					String temp1 = fileArray.get(now);
					if(temp1.equals(" ")||temp1.equals(",")) {
						now++;
						continue;
					}
					if(!Character.isLetter(temp1.charAt(0))) {
						
						break;
					}
					if(!isClass&&Character.isLetter(temp1.charAt(0))&&fileArray.get(now+1).charAt(0)==' '&&Character.isLetter(fileArray.get(now+2).charAt(0))) {
						break;
					}

					String temp2 = standardName(temp1);
					if(isClass) {
						temp2 = temp2.substring(0, 1).toUpperCase() + temp2.substring(1);
						isClass=false;
					}
					fileArray.set(now, temp2);
					dataMap.put(temp1, temp2);
					now++;
				}
			}
			if(dataMap.containsKey(fileArray.get(now))) {
				fileArray.set(now, dataMap.get(fileArray.get(now)));	
			}
			now++;
		}
	}
}

