package test;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class Interface extends JFrame {
	private JTextField inputTextField ;
	private JTextField outputTextField ;
	public JTextField testTextField ;
	public String inText = "" ;
	public String outText = "" ;
	public String result = "" ;
	
	public Interface() {
		
		this.setTitle("CodeRefact");
		this.setSize(800,800);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		testTextField = new JTextField();
		testTextField.setFont(new Font("宋体",1,18));
		testTextField.setBounds(0, 700, 800, 100);
		//input 
	    JLabel inputJLabel = new JLabel("输入文件:");
	    inputJLabel.setFont(new Font("宋体",1,18));
	    inputJLabel.setBounds(50, 50, 100, 40);
	    
		inputTextField = new JTextField();
		inputTextField.setBounds(150, 50, 500, 40);
		
		JButton inputButton = new JButton("确认");
		inputButton.setFont(new Font("宋体",1,18));
		inputButton.setBounds(650, 50, 100, 40);
		inputButton.setActionCommand("in");
        inputButton.addMouseListener(new MyListener());
		//output
		JLabel outputJLabel = new JLabel("输出文件:");
		outputJLabel.setFont(new Font("宋体",1,18));
		outputJLabel.setBounds(50, 150, 100, 40);
		
		outputTextField = new JTextField();
		outputTextField.setBounds(150, 150, 500, 40);
		
		JButton outputButton = new JButton("确认");
		outputButton.setFont(new Font("宋体",1,18));
		outputButton.setBounds(650, 150, 100, 40);
		outputButton.setActionCommand("out");
		outputButton.addMouseListener(new MyListener());
		//function button
		JButton structureButton = new JButton("结构重构");
		structureButton.setFont(new Font("宋体",1,18));
		structureButton.setBounds(300, 250, 200, 40);
		structureButton.setActionCommand("structure");
		structureButton.addMouseListener(new MyListener());
		
		JButton circleButton1 = new JButton("while->for");
		circleButton1.setFont(new Font("宋体",1,18));
		circleButton1.setBounds(100, 350, 200, 40);
		circleButton1.setActionCommand("whileToFor");
		circleButton1.addMouseListener(new MyListener());
		
		JButton circleButton2 = new JButton("for->while");
		circleButton2.setFont(new Font("宋体",1,18));
		circleButton2.setBounds(500, 350, 200, 40);
		circleButton2.setActionCommand("forToWhile");
		circleButton2.addMouseListener(new MyListener());
		
		JButton nameButton = new JButton("命名重构");
		nameButton.setFont(new Font("宋体",1,18));
		nameButton.setBounds(300, 550, 200, 40);
		nameButton.setActionCommand("name");
		nameButton.addMouseListener(new MyListener());
		//find input address
		inputTextField.setTransferHandler(new TransferHandler(){
			private static final long serialVersionUID = 1L ;
			@Override
			public boolean importData(JComponent comp, Transferable trans){
				try {
					Object object = trans.getTransferData(DataFlavor.javaFileListFlavor);
					String filepath = object.toString();
					if(filepath.startsWith("[")) {
						filepath = filepath.substring(1);
					}
					if(filepath.endsWith("]")) {
						filepath = filepath.substring(0,filepath.length() - 1);
					}
					inputTextField.setText(filepath);
					return true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return false ;
			}
			@Override
			public boolean canImport(JComponent comp, DataFlavor[] flavors) {
				for(int i=0 ; i<flavors.length ; i++ ) {
					if(DataFlavor.javaFileListFlavor.equals(flavors[i])) {
						return true ;
					}
				}
				return false ;
			}
		});
		//find output address
		outputTextField.setTransferHandler(new TransferHandler(){
			private static final long serialVersionUID = 1L ;
			@Override
			public boolean importData(JComponent comp, Transferable trans){
				try {
					Object object = trans.getTransferData(DataFlavor.javaFileListFlavor);
					String filepath = object.toString();
					if(filepath.startsWith("[")) {
						filepath = filepath.substring(1);
					}
					if(filepath.endsWith("]")) {
						filepath = filepath.substring(0,filepath.length() - 1);
					}
					outputTextField.setText(filepath);
					return true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return false ;
			}
			@Override
			public boolean canImport(JComponent comp, DataFlavor[] flavors) {
				for(int i=0 ; i<flavors.length ; i++ ) {
					if(DataFlavor.javaFileListFlavor.equals(flavors[i])) {
						return true ;
					}
				}
				return false ;
			}
		});
		
		this.add(inputTextField);
		this.add(inputJLabel);
		this.add(inputButton);
		this.add(outputTextField);
		this.add(outputJLabel);
		this.add(outputButton);
		this.add(structureButton);
		this.add(circleButton1);
		this.add(circleButton2);
		this.add(nameButton);
		this.add(testTextField);
		
		this.setVisible(true);
	}
	
	class MyListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			JButton jbt = (JButton)e.getSource();
			String str=jbt.getActionCommand();
			if(str=="in") {
				inText = inputTextField.getText().toString();
				result = "输入地址添加成功" ;
				testTextField.setText(result);
			}
			if(str=="out") {
				outText = outputTextField.getText().toString();
				result = "输出地址添加成功" ;
				testTextField.setText(result);
			}
			if(str=="structure") {
				structure(inText, outText);
				result = "结构重构成功" ;
				testTextField.setText(result);
			}
			if(str=="name") {
				try {
					structure(inText, outText);
					NameRefactor nameRefactor = new NameRefactor(outText,outText);
					structure(outText, outText);
					result = "命名重构成功" ;
					testTextField.setText(result);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(str=="whileToFor") {
				structure(inText, outText);
				Input in = new Input(outText);
				CircleTransform circleTransform = new CircleTransform();
				circleTransform.input=in;
				clear(outText);
				circleTransform.whileToFor(outText);
				structure(outText, outText);
				result = "循环重构成功" ;
				testTextField.setText(result);
			}
			if(str=="forToWhile") {
				structure(inText, outText);
				Input in = new Input(outText);
				CircleTransform circleTransform = new CircleTransform();
				circleTransform.input=in;
				clear(outText);
				circleTransform.forToWhile(outText);
				structure(outText, outText);
				result = "循环重构成功" ;
				testTextField.setText(result);
			}
		}
	}
	public void clear(String file) {
		try {
			FileOutputStream fos = new FileOutputStream(file,false);
			fos.write("".getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void structure(String in, String out) {
		StructureRefactor sr = new StructureRefactor();
		Input input = new Input(in);
		String[] textContent = null;
		textContent = input.inputText;
		sr.Recfactor(textContent);
		sr.print(out,textContent);
		Input input1 = new Input(out);
		textContent = input1.inputText;
		Line[] line = sr.addBraces(sr.addTable(textContent),out);
		sr.anotherPrint(out, line);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Interface() ;
	}
}
