package test;

import java.io.*;

public class StructureRefactor {
	int textlength;
	public StructureRefactor() {
	}

	public void print(String fileName,String[] s) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			for(int i=0 ; i<textlength; i++) {
				if(s[i] != null) {
					for(int j=0 ; j<s[i].length() ; j++) {
						fos.write((String.valueOf(s[i].charAt(j))).getBytes());
					}
				}
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void Recfactor(String[] s) {
		//ɾ��\t�Ϳո�
		int length=0;
		for(int i=0 ; i<s.length ; i++) {
			if(s[i] != null) {
				StringBuffer stb = new StringBuffer(s[i]);
				for(int j=0 ; j<stb.length() ; j++) {
					if(stb.charAt(j) == '\t') {
						stb.delete(j,j+1);
						j--;
					}
				}
				while(stb.length() > 0&&stb.charAt(0) == ' ') {
					stb.delete(0,1);
				}
				while(stb.length() > 0&&stb.charAt(stb.length()-1) == ' ') {
					stb.delete(stb.length()-1,stb.length());
				}
				s[length++] = stb.toString();
			}
		}
		textlength = length;
		int isBracket = 0;//���ڼ�¼�ո���
		boolean isNote2 = false;//���ڱ�ʶ�Ƿ�Ϊ/**/��ע��
		int isString = 0;//���ڱ�ʶ�ַ�
		int isString1 = 0;//���ڱ�ʶ�ַ���
		boolean addBraces = false;//��bracesNumһ���ʶ��������ţ������жϲ�������ŵ�λ��
		//int bracesNum = 0;//��addBracesһ���������ţ����ڼ�¼��������ŵ�����
		//ɾ�������ո�
		length=0;
		for(int i=0 ; i<textlength ; i++) {
			if(s[i] != null) {
				StringBuffer stb = new StringBuffer(s[i]);
				for(int j=0 ; j<stb.length() ; j++) {
					if(isNote2)continue;
					if(isString > 0 && isString1 == 0 && stb.charAt(j) == '\'' ) {
						isString--;
					}
					else if(isString1 > 0 && isString == 0 && stb.charAt(j) == '"' ) {
						isString1--;
					}
					else if((isString == 0 && isString1 == 0) && stb.charAt(j) == '\'') {
						isString++;
					}
					else if((isString == 0 && isString1 == 0) && stb.charAt(j) == '\"') {
						isString1++;
					}
					if((isString == 0 && isString1 == 0 ) && j<stb.length()-1 && stb.charAt(j) == ' ') {
						int k = j + 1;
						while(stb.charAt(k) == ' ') {
							stb.delete(j+1,k+1);
						}
					}
				}
				if(!stb.toString().equals(""))
					s[length++] = stb.toString();
			}
		}
		textlength = length;
		isString = 0;
		isString1 = 0;
		//�ع�
		for(int i=0 ; i<length ; i++) {
			if(s[i] != null) {
				StringBuffer stb = new StringBuffer(s[i]);
				if(stb.charAt(stb.length()-1)!=';' && stb.charAt(stb.length()-1)!='{'
						&& stb.charAt(stb.length()-1)!='}' && stb.charAt(stb.length()-1)!=')'
						&& stb.charAt(stb.length()-1)!=' ') {
					stb.insert(stb.length(),' ');
				}
				for(int j=0 ; j<stb.length() ; j++) {
					
					if(stb.charAt(j) == '\\') {
						j++;
						continue;
					}
					
					if(!isNote2 && isString > 0 && isString1 == 0 && stb.charAt(j) == '\'' ) {
						isString--;
					}
					else if(!isNote2 && isString1 > 0 && isString == 0 && stb.charAt(j) == '"' ) {
						isString1--;
					}
					else if(!isNote2 && (isString == 0 && isString1 == 0) && stb.charAt(j) == '\'') {
						isString++;
					}
					else if(!isNote2 && (isString == 0 && isString1 == 0) && stb.charAt(j) == '\"') {
						isString1++;
					}
					
					if(!isNote2 && (isString == 0 && isString1 == 0) && stb.charAt(j) == '(') {
						isBracket++;
					}
					if(!isNote2 && (isString == 0 && isString1 == 0) && (stb.charAt(j)=='@' || (stb.charAt(j) == '/' && j+1 < stb.length() && stb.charAt(j+1) == '/')) ) {
						stb.insert(stb.length(), System.getProperty("line.separator"));
						break;
					}
					if(!isNote2 && stb.charAt(j) == '/' && j+1 < stb.length() && stb.charAt(j+1) == '*' && (isString == 0 || isString1 == 0)) {
						isNote2 = true;
						j++;
					}
					if(stb.charAt(j) == ';') {
						if(isNote2 || isBracket > 0 || isString1 > 0  || isString>0) {
							if(isBracket > 0) {
								if(j>=1 && stb.charAt(j-1) != ' ') {
									stb.insert(j, ' ');
									j++;
								}
								if(j+1 < stb.length() && stb.charAt(j+1) != ' ') {
									stb.insert(j+1, ' ');
								}
								
							}
						}
						else {
							if(j<stb.length()-1 && stb.charAt(j+1) == '/' && 
								j<stb.length()-2 && stb.charAt(j+2) == '/') {
								
							}
							else if(j<stb.length()-2 && stb.charAt(j+2) == '/' && 
								j<stb.length()-3 && stb.charAt(j+3) == '/') {
									
							}
							else {
								stb.insert(j+1, System.getProperty("line.separator"));
								j += 2;
							}
						}
						
					}
					else if(stb.charAt(j) == '{') {
						if(isNote2 || isBracket>0 || isString>0 || isString1 > 0) {
							
						}
						else {
							if(addBraces)addBraces=false;
							stb.insert(j+1, System.getProperty("line.separator"));
							j += 2;
						}
					}
					else if(stb.charAt(j) == '}') {
						if( isNote2 || isString>0 || isString1 > 0) {
							
						}
						else {
							stb.insert(j+1, System.getProperty("line.separator"));
							j += 2;
						}
					}
					else if(isNote2 && (isString == 0 && isString1 == 0 ) && stb.charAt(j) == '*' && j+1 < stb.length() && stb.charAt(j+1) == '/') {
						isNote2 = false;
						stb.insert(j+2, System.getProperty("line.separator"));
						j += 3;
					}
					else if(!isNote2 && (isString == 0 && isString1 == 0 ) && stb.charAt(j) == ')') {
							isBracket--;
							if(isBracket != 0 || (j<stb.length()-1 && stb.charAt(j+1) == '{') || 
									(i<length-1 && s[i+1].length()>0 && s[i+1].charAt(0) == '{')
									|| ((j<stb.length()-2 && stb.charAt(j+2) == '{'))) {
								
							}
							else {
								if(j+1<stb.length() && stb.charAt(j)==' ')j++;
								if(addBraces && (((j+1<stb.length() && stb.charAt(j+1)!='{')||j+1<=stb.length())
										&&(s[i+1]!=null&&s[i+1].length()>0&&s[i+1].charAt(0)!='{'))){
									stb.insert(j+1, System.getProperty("line.separator"));
									j+=2;
									addBraces=false;
								}
								
							}
					}
					else if(j+1 < stb.length() && (
							(stb.charAt(j)=='-'&&stb.charAt(j+1)=='>') ||
							(stb.charAt(j)=='<'&&stb.charAt(j+1)=='<') ||
							(stb.charAt(j)=='>'&&stb.charAt(j+1)=='>') ||
							(stb.charAt(j)=='^'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='%'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='&'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='|'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='='&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='+'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='-'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='*'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='/'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='!'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='&'&&stb.charAt(j+1)=='&') ||
							(stb.charAt(j)=='|'&&stb.charAt(j+1)=='|') ||
							(stb.charAt(j)=='>'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='<'&&stb.charAt(j+1)=='=') ||
							(stb.charAt(j)=='<'&&stb.charAt(j+1)=='>') ) ) {
						if(j-1 > 0 && stb.charAt(j-1) != ' ') {
							stb.insert(j, ' ');
							j++;
						}
						if(j+2 < stb.length() && stb.charAt(j+2) != ' ') {
							stb.insert(j+2, ' ');
							j++;
						}
						j++;
						continue;
					}
					else if(j+1 < stb.length() && ((stb.charAt(j)=='+'&&stb.charAt(j+1)=='+')
							|| (stb.charAt(j)=='-'&&stb.charAt(j+1)=='-'))) {
						j++;
						continue;
					}
					else if( (isString == 0 && isString1 == 0 ) &&
							((stb.charAt(j) == '='||stb.charAt(j) == '+'||stb.charAt(j)=='-'||
							stb.charAt(j)=='*'||stb.charAt(j)=='/'||stb.charAt(j)=='&'||
							stb.charAt(j)=='|'||stb.charAt(j)=='?'||stb.charAt(j)==':'||
							stb.charAt(j)=='>'||stb.charAt(j)=='<'||stb.charAt(j)=='^'
							||stb.charAt(j)=='~')
							)) {
						if(j-1 > 0 && stb.charAt(j-1) != ' ') {
							stb.insert(j, ' ');
							j++;
						}
						if(j+1 < stb.length() && stb.charAt(j+1) != ' ') {
							stb.insert(j+1, ' ');
						}
					}
					else if((isString == 0 && isString1 == 0 ) &&
							(stb.charAt(j) == 'i'||stb.charAt(j)=='w'||stb.charAt(j)=='f') && !addBraces){
						addBraces = isBraces(stb, j);
					}
					else if((isString == 0 && isString1 == 0 ) && j+4<=stb.length() &&
							stb.charAt(j) == 'e' && "else".equals(stb.subSequence(j, j+4))) {
						if(j+7 <= stb.length() && stb.charAt(j+5)=='i' && "if".equals(stb.subSequence(j+5, j+7))) {
							
						}
						else {
							if((j+4 < stb.length() && stb.charAt(j+4)=='{')||(j+5 < stb.length() && stb.charAt(j+5)=='{')) {
								
							}
							else {
								stb.insert(j+4,  System.getProperty("line.separator"));
								j+=4;
							}
						}
					}
					
				}
				
				if(isNote2) {
					stb.insert(stb.length(), System.getProperty("line.separator"));
				}
				
				if(stb.length() > 0) {
					s[i] = stb.toString();
				}
				
			}
		}
	}

	public boolean isBraces(StringBuffer s,int column){
		int j=column ;
		if((j+3 < s.length() && s.charAt(j)=='f' && "for".equals(s.subSequence(j, j+3))&&((j+3<s.length()&&s.charAt(j+3)=='(')||(j+4<s.length()&&s.charAt(j+4)=='(')))
			||(j+2 < s.length() && s.charAt(j)=='i' && "if".equals(s.subSequence(j, j+2))&&((j+3<s.length()&&s.charAt(j+3)=='(')||(j+2<s.length()&&s.charAt(j+2)=='(')))
			||(j+5 < s.length() && s.charAt(j)=='w' && "while".equals(s.subSequence(j, j+5))&&((j+5<s.length()&&s.charAt(j+5)=='(')||(j+6<s.length()&&s.charAt(j+6)=='(')))
			||(j+4 < s.length() && s.charAt(j)=='e' && "else".equals(s.subSequence(j, j+4)))) {
			return true;
		}
		return false;
	}
	
	public Line[] addTable(String[] s) {
		Line[] l = new Line[s.length];
		for(int i=0 ; i<s.length ; i++) {
			if(s[i]!=null)
				l[i] = new Line(s[i]);
		}
		for(int i=0 ; i<l.length ; i++) {
			if(l[i] != null && l[i].s!=null) {
				StringBuffer stb = new StringBuffer(l[i].s);
				while(stb.length() > 0&&stb.charAt(0) == ' ') {
					stb.delete(0,1);
				}
				while(stb.length() > 0&&stb.charAt(stb.length()-1) == ' ') {
					stb.delete(stb.length()-1,stb.length());
				}
				l[i].s = stb.toString();
				l[i].tNum=0;
				l[i].isNote = false;
				l[i].isNote2 = false;
			}
		}
		for(int i=0 ; i<l.length ; i++) {
			boolean isNote2 = false;
			if(l[i] != null && l[i].s!=null) {
				if(isNote2) {
					l[i].isNote2 = true;
				}
				for(int j=0 ; j<l[i].s.length() ; j++) {
					if( j+1<l[i].s.length()-1 && l[i].s.charAt(j) == '/' && l[i].s.charAt(j+1) == '*') {
						l[i].isNote2 = true;
						isNote2 = true;
					}
					if( j+1<l[i].s.length()-1  && l[i].s.charAt(j) == '*' && l[i].s.charAt(j+1) == '/') {
						isNote2=false;
					}
					if( j+1<l[i].s.length()-1  && l[i].s.charAt(j) == '/'  && l[i].s.charAt(j+1) == '/') {
						l[i].isNote = true;
					}
				}
				l[i].isNote2 = l[i].isNote2 && isNote2;
			}
		}
		
		for(int i=0 ; i<l.length ; i++) {
			if(l[i]!=null && l[i].s!=null) {
				boolean isBraces = false;
				boolean isNeed = false;
				StringBuffer stb = new StringBuffer(l[i].s);
				for(int j=0 ; j<stb.length() ; j++) {
					if(stb.charAt(0)=='i'&&"if".equals(stb.subSequence(0, 0+2))) {
						isNeed=true;
					}
					if(stb.charAt(0)=='f'&&"for".equals(stb.subSequence(0, 0+3))) {
						isNeed=true;
					}
					if(stb.charAt(0)=='w'&&"while".equals(stb.subSequence(0, 0+5))) {
						isNeed=true;
					}
					if(stb.charAt(0)=='e'&&"else".equals(stb.subSequence(0, 0+4))) {
						isNeed=true;
						for(int k=i-1 ; k>=0 ; k--) {
							if(l[k]!=null && l[k].s!=null) {
								if(!l[k].isNote && !l[k].isNote2) {
									if(l[k].s.charAt(0)=='}')
										l[i].tNum=l[k].tNum;
									else
										l[i].tNum = l[k].tNum-1;
									break;
								}
							}
						}
					}
						
					if(isNeed) {
						if(stb.charAt(stb.length()-1)!='{') {
							isBraces = true;
						}
					}
					if(isBraces&&isNeed) {
						if(l[i+1]!=null ) {
							l[i+1].tNum = l[i].tNum+1;
							isNeed = false;
							isBraces = false;
						}
					}
				}
			}
		}
		int tNum=0;//��¼ָ���е�/t����Ŀ
		boolean isNote = false;//���//��ע��
		boolean isNote2 = false;//���/**/��ע��
		for(int i=0 ; i<l.length ; i++) {
			if(l[i]!=null && l[i].s!=null) {
				l[i].tNum = l[i].tNum+tNum;
				for(int j=0 ; j<l[i].s.length() ; j++) {
					if(!isNote && !isNote2 && j+1 < l[i].s.length() && l[i].s.charAt(j) == '/' && l[i].s.charAt(j+1) == '*') {
						isNote2=true;
					}
					if(!isNote && isNote2 && j+1 < l[i].s.length() && l[i].s.charAt(j) == '*'  && l[i].s.charAt(j+1) == '/') {
						isNote2=false;
					}
					if(!isNote && !isNote2 && l[i].s.charAt(j) == '/' && j+1 < l[i].s.length() && l[i].s.charAt(j+1) == '/') {
						isNote=true;
						break;
					}
				}
				if(!isNote && !isNote2) {
					if(l[i].s.length()>0 && l[i].s.charAt(l[i].s.length()-1)=='{') {
						tNum++;
					}
					else if(l[i].s.length()>0 && l[i].s.charAt(l[i].s.length()-1)=='}') {
						l[i].tNum--;
						tNum--;
					}
				}
				isNote = false;
			}
		}
		return l;
	}
	
	public Line[] addBraces(Line[] l,String fileName) {
		String[] s = new String[10000];
		int index = 0;
		int bracesNum=0;
		for(int i=0 ; i<l.length ; i++) {
			if(l[i]!=null && l[i+1]==null) {
				s[index] = l[i].s;
			}
			if(l[i]!=null&&l[i+1]!=null&&l[i].s!=null) {
				if(l[i].tNum < l[i+1].tNum ) {
					if(l[i].s.charAt(l[i].s.length()-1) == '{'	) {
						
					}
					else {
						StringBuffer stb = new  StringBuffer(l[i].s);
						stb.insert(stb.length(), '{');
						bracesNum++;
						l[i].s = stb.toString();
						for(int k=i+1 ; k<l.length-1 ; k++) {
							if(bracesNum>0&&l[k]!=null && l[k+1]!=null && l[k].tNum > l[i].tNum && l[k+1].tNum<=l[i].tNum) {
								stb = new  StringBuffer(l[k].s);
								stb.insert(l[k].s.length(), '}');
								bracesNum--;
								l[k].s = stb.toString();
							}
						}
					}
				}
				s[index++] = l[i].s;
			}
		}
		this.Recfactor(s);
		this.print(fileName,s);
		Input input1 = new Input(fileName);
		s = input1.inputText;
		Line[] line = this.addTable(s);
		return line;
	}
	
	public void anotherPrint(String fileName,Line[] l) {
		try {
			for(int i=0 ; i<l.length ; i++) {
				if(l[i]!=null && l[i].s!=null) {
					StringBuffer stb = new StringBuffer(l[i].s);
					for(int k=0 ; k<l[i].tNum ; k++) {
						stb.insert(0, '\t');
					}
					stb.insert(stb.length(), System.getProperty("line.separator"));
					l[i].s=stb.toString();
				}
			}
			FileOutputStream fos = new FileOutputStream(fileName);
			for(int i=0 ; i<l.length; i++) {
				if(l[i]!=null&&l[i].s != null) {
					for(int j=0 ; j<l[i].s.length() ; j++) {
						fos.write((String.valueOf(l[i].s.charAt(j))).getBytes());
					}
				}
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	public static void main(String[] args) {
//		StructureRefactor sr = new StructureRefactor();
//		Input input = new Input("C:\\Users\\Administrator\\Desktop\\cord.txt");
//		String[] textContent = null;
//		textContent = input.inputText;
//		sr.Recfactor(textContent);
//		sr.print("C:\\Users\\Administrator\\Desktop\\out.txt",textContent);
//		Input input1 = new Input("C:\\Users\\Administrator\\Desktop\\out.txt");
//		textContent = input1.inputText;
//		Line[] l = sr.addTable(textContent);
//		Line[] line = sr.addBraces(l,"C:\\Users\\Administrator\\Desktop\\out.txt");
//		sr.anotherPrint("C:\\Users\\Administrator\\Desktop\\out.txt", line);
//	}
	
}
