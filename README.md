**CodeRefact**
#简介#
这个软件提供了对java代码的格式结构重构，命名结构重构，循环语句转换的功能。且每项功能可独立进行。代码的结构重构使其程序的设计模式和架构更趋合理，提高软件的扩展性和维护性。
# 快速开始 #
## 使用前提 ##
需要安装java虚拟机JVM
## 打开可执行文件 ##
> java -jar 04016845作品.zip
## 使用流程 ##
1. 拖拽需重构文件至‘输入文件’文本框或输入文件路径，点击确认
2. 拖拽保存文件至‘输出文件’文本框或输入文件路径，点击确认
3. 选择需要需要重构的功能，点击即可
# 测试 #
## 结构重构测试 ##
input：

    import java.util.Scanner;public class text5{
    	public   static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    		int i = 0,j = 0,k = 0;
    	
    	//5.1
    		System.out.println("5.1");
    		int positive = 0,negative = 0;
    	double total = 0;		System.out.print("Enter an integer,the input ends if it is 0:");
    		int integer = input.nextInt();
    		while(integer != 0) {
    			if(integer > 0)
    				positive++;
    			if(integer < 0)
    				negative++;
    			total += integer;
    			integer = input.nextInt();
    		}
    if(i>0)
    if(i<10)
    i++;
    		System.out.println("The number of positives is " + positive);
    		System.out.println("The total is " + total);
    		System.out.println("The average is " + total / (positive + negative));
    	}
    }
output:

    import java.util.Scanner;
    public class text5{
    	public static void main(String[] args) {
    		Scanner input = new Scanner(System.in);
    		int i = 0,j = 0,k = 0;
    		//5.1
    		System.out.println("5.1");
    		int positive = 0,negative = 0;
    		double total = 0;
    		System.out.print("Enter an integer,the input ends if it is 0:");
    		int integer = input.nextInt();
    		while(integer != 0) {
    			if(integer > 0){
    				positive++;
    			}
    			if(integer < 0){
    				negative++;
    			}
    			total += integer;
    			integer = input.nextInt();
    		}
    		if(i > 0){
    			if(i < 10){
    				i++;
    			}
    		}
    		System.out.println("The number of positives is " + positive);
    		System.out.println("The total is " + total);
    		System.out.println("The average is " + total / (positive + negative));
    	}
    }
## 命名重构测试 ##
input：

    package work11;
    
    public class empLoyee 
    	extends Person{
    	private String Office;
    	private double sAlary;
    	private MyDate date = new MyDate();
    	private int isOk =1;
    	public empLoyee (){
    		
    	}	
    	public empLoyee (String namE){
    		super(name);
    	}
    	public empLoyee (String oFfice, double salary, MyDate date) {
    		this.Office = oFfice;
    		this.sAlary = salary;
    		this.date = date;	
    		private int isok =1;
    	}
    	
    	
    	public String toString() {
    		return "Employee : " + this.getName();
    	}
        }
output：

    package work11;
    public class empLoyee extends Person{
    	private String office;
    	private double salary;
    	private MyDate date = new MyDate();
    	private int isok = 1;
    	public empLoyee (){
    	}
    	public empLoyee (String name){
    		super(name);
    	}
    	public empLoyee (String oFfice, double salary, MyDate date) {
    		this.office = oFfice;
    		this.salary = salary;
    		this.date = date;
    		private int isok = 1;
    	}
    	public String tostRing() {
    		return "Employee : " + this.getName();
    	}
    }
    
## 循环转化测试 ##
input：

    import java.util.Scanner;
    public class text5{
    	public static void main(String[] args) {
    		Scanner input = new Scanner(System.in);
    		int positive = 0,negative = 0;
    		double total = 0;
    		System.out.print("Enter an integer,the input ends if it is 0:");
    		int integer = input.nextInt();
    		while(integer != 0) {
    			if(integer > 0){
    				positive++;
    			}
    			if(integer < 0){
    				negative++;
    			}
    			total += integer;
    			integer = input.nextInt();
    		}
    	}
    }

output：

    import java.util.Scanner;
    public class text5{
    	public static void main(String[] args) {
    		Scanner input = new Scanner(System.in);
    		int positive = 0,negative = 0;
    		double total = 0;
    		System.out.print("Enter an integer,the input ends if it is 0:");
    		int integer = input.nextInt();
    		for( ; integer != 0 ; ){
    			if(integer > 0){
    				positive++;
    			}
    			if(integer < 0){
    				negative++;
    			}
    			total += integer;
    			integer = input.nextInt();
    		}
    	}
    }

# 开发者 #
西北大学‘无那’小组。

成员：肖易安，王振华，黄德翔

指导老师：王小凤

# 开发讨论 #
1.经过讨论分析，代码语言的选择结构每一种格式都具有一定得特殊性，它们功能上均有差异，例如if与switch等。若强制规范其代码格式，可能会降低代码的可读性，以及使得部分代码变得臃肿庞大，例如switch向if转换。使得代码逻辑混乱，不满足简洁的结构化程序设计思想。故此，此软件没有加入选择语句之间相互转换的功能。

2.在命名重构方向，无法精确的了解每一位代码书写人员的命名习惯。且部分命名存在简写，复合词语等现象。且同一词语易出现复用，以及在不同命名上使用状况。故此，在命名重构这一功能上我们采用了以读入字典作为规范标准。但由于同名，以及不同位置变量命名规范有所差异，此项功能准确程度无法达到99%。