package solvingDebuger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.annotation.processing.Processor;
import javax.print.attribute.AttributeSet;
import javax.swing.BoxLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class MainBoard extends JFrame {
	private static JFrame MyFrame;
	private JPanel contentPane;
	private JPanel exeSelectPane;
	private JPanel mainPane;
	private JPanel btnPane;
	private JButton settingBtn;
	private JButton runBtn;
	private JButton resetBtn;
	private JButton cancelBtn;
	private JLabel exeFileShow;
	private JButton exeFileBtn;
	private JPanel inputPane;
	private JPanel outputPane;
	private JPanel inputSelector;
	private JButton inputSelectorBtn;
	private JScrollPane scrollPane;
	private JPanel output;
	private JPanel userOutput;
	private JTextPane inputTxt;
	private JPanel outputSelector;
	private JButton outputSelectorBtn;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JTextArea outputTxt;
	private JTextPane userOutputTxt;
	private FileWriter fw;
	private BufferedReader fr;
	private BufferedReader reader;
	private BufferedWriter writer;
	public settingDialog dialog;
	/**
	 * Launch the application.
	 */
 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			MainBoard frame;
			public void run() {
				try {
					 frame= new MainBoard();
					frame.btnSetting();
					frame.fileSetting();
					frame.setVisible(true);
					MyFrame=frame;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainBoard() {
		setTitle("debuger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		exeSelectPane = new JPanel();
		contentPane.add(exeSelectPane, BorderLayout.NORTH);
		exeSelectPane.setLayout(null);
		
		exeFileShow = new JLabel("file://");
		exeFileShow.setBounds(17, 9, 416, 21);
		exeSelectPane.add(exeFileShow);
		exeSelectPane.setPreferredSize(new Dimension(100,40));
		
		exeFileBtn = new JButton("EXE선택");
		exeFileBtn.setBounds(450, 5, 101, 29);
		exeSelectPane.add(exeFileBtn);
		
		mainPane = new JPanel();
		contentPane.add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		inputPane = new JPanel();
		mainPane.add(inputPane);
		inputPane.setLayout(new BorderLayout(0, 0));
		
		inputSelector = new JPanel();
		inputPane.add(inputSelector, BorderLayout.SOUTH);
		
		inputSelectorBtn = new JButton("입력 파일");
		inputSelectorBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		inputSelector.add(inputSelectorBtn);
		

		inputTxt = new JTextPane();
		
		scrollPane = new JScrollPane(inputTxt);
		inputPane.add(scrollPane, BorderLayout.CENTER);
		
		
		outputPane = new JPanel();
		mainPane.add(outputPane);
		outputPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		output = new JPanel();
		outputPane.add(output);
		output.setLayout(new BorderLayout(0, 0));
		
		outputSelector = new JPanel();
		output.add(outputSelector, BorderLayout.SOUTH);
		
		outputSelectorBtn = new JButton("출력 파일");
		outputSelector.add(outputSelectorBtn);
		

		outputTxt = new JTextArea();
		scrollPane2 = new JScrollPane(outputTxt);
		output.add(scrollPane2, BorderLayout.CENTER);
		
		
		userOutput = new JPanel();
		outputPane.add(userOutput);
		userOutput.setLayout(new BorderLayout(0, 0));

		userOutputTxt = new JTextPane();
		scrollPane3 = new JScrollPane(userOutputTxt);
		userOutput.add(scrollPane3, BorderLayout.CENTER);
		
		
		btnPane = new JPanel();
		contentPane.add(btnPane, BorderLayout.SOUTH);
		btnPane.setLayout(new GridLayout(1, 4, 0, 0));
		
		settingBtn = new JButton("설정");
		btnPane.add(settingBtn);
		
		runBtn = new JButton("실행");
		btnPane.add(runBtn);
		
		resetBtn = new JButton("초기화");
		btnPane.add(resetBtn);
		
		cancelBtn = new JButton("종료");
		btnPane.add(cancelBtn);
	
		dialog=new settingDialog();
	}
	public void btnSetting(){
		exeFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(exeFileBtn);
				File exeFile=fc.getSelectedFile();
				try{
					exeFileShow.setText(exeFile.getPath());
					fw=new FileWriter(new File("path1.txt"));
					fw.write(exeFile.getPath());
					fw.close();
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		});
		inputSelectorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(inputSelectorBtn);
				File exeFile=fc.getSelectedFile();
				try{
					BufferedReader in=new BufferedReader(new FileReader(exeFile.getPath()));
					String s;
					while((s=in.readLine())!=null){
						appendToPane(inputTxt, s+"\n", Color.black);
					}
					fw=new FileWriter(new File("path2.txt"));
					fw.write(exeFile.getPath());
					fw.close();
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		});
		outputSelectorBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(inputSelectorBtn);
				File exeFile=fc.getSelectedFile();
				try{
					BufferedReader in=new BufferedReader(new FileReader(exeFile.getPath()));
					String s;
					while((s=in.readLine())!=null){
						outputTxt.append(s);
						outputTxt.append("\n");
					}
					fw=new FileWriter(new File("path3.txt"));
					fw.write(exeFile.getPath());
					fw.close();
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyFrame.dispose();
			}
		});
		resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				outputTxt.setText("");
				inputTxt.setText("");
				userOutputTxt.setText("");
				fileSetting();
			}
		});
		runBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ProcessBuilder pcBuilder=new ProcessBuilder(exeFileShow.getText());
				pcBuilder.redirectErrorStream(true);
				try {
					Process process=pcBuilder.start();
					if(outputTxt.getText().equals(""))throw new Exception();
					if(inputTxt.getText().equals(""))throw new Exception();
					OutputStream stdin = process.getOutputStream ();
					InputStream stderr = process.getErrorStream ();
					InputStream stdout = process.getInputStream ();
					reader = new BufferedReader(new InputStreamReader(stdout));
					writer = new BufferedWriter(new OutputStreamWriter(stdin));
					runThread();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(MyFrame,"실행파일 또는 입출력 파일을 설정해 주세요");
				}
			}
		});
		settingBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
	}
	public void fileSetting(){
		try {
			fr=new BufferedReader(new FileReader("path1.txt"));
			String line="";
			if((line=fr.readLine())!=null){
				exeFileShow.setText(line);
			}
			fr=new BufferedReader(new FileReader("path2.txt"));
			line="";
			if((line=fr.readLine())!=null){
				try{
					BufferedReader in=new BufferedReader(new FileReader(line));
					String s;
					while((s=in.readLine())!=null){

						appendToPane(inputTxt, s+"\n", Color.black);
					}
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
			fr=new BufferedReader(new FileReader("path3.txt"));
			if((line=fr.readLine())!=null){
				try{
					BufferedReader in=new BufferedReader(new FileReader(line));
					String s;
					while((s=in.readLine())!=null){
						outputTxt.append(s);
						outputTxt.append("\n");
					}
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void runThread(){
		Runnable input=new Runnable(){
			@Override
			public void run(){
				String s[]=inputTxt.getText().split("\n");
				for(int i=0;i<s.length;i++){
					try {
						writer.write(s[i]+"\n");
						writer.flush();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		Runnable output=new Runnable(){
			@Override
			public void run(){
				String line="";
				String s[]=outputTxt.getText().split("\n");
				ArrayList<Integer> arr=new ArrayList<Integer>();
				int cnt=0;
				try {
					while((line=reader.readLine())!=null){
						if(s[cnt].equals(line))
							appendToPane(userOutputTxt,line+"\n",Color.BLACK);
						else{
							appendToPane(userOutputTxt,line+"\n",Color.RED);
							arr.add(cnt+1);
						}
						cnt++;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reWriteInput(arr);
			}
		};
		Thread a=new Thread(input);
		Thread b=new Thread(output);
		a.start(); b.start();
	}
	public void reWriteInput(ArrayList<Integer> integerInput){
		inputTxt.setText("");
		int testLine=0;
		int inputNumofLine=0;
		try {
			fr=new BufferedReader(new FileReader("setting.txt"));
			testLine=Integer.parseInt(fr.readLine());
			inputNumofLine=Integer.parseInt(fr.readLine());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		try {
			fr=new BufferedReader(new FileReader("path2.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line="";
		try {
			if((line=fr.readLine())!=null){
				try{
					BufferedReader in=new BufferedReader(new FileReader(line));
					String s;
					int cnt=1;
					int indexCnt=0;
					int test=0;
					while((s=in.readLine())!=null){
						if(cnt<=testLine){
							appendToPane(inputTxt, s+"\n", Color.black);
						}
						else{
							if(indexCnt<integerInput.size()&&
									(integerInput.get(indexCnt)-1)*inputNumofLine+testLine< cnt+1 &&
									cnt+1 >=integerInput.get(indexCnt)*inputNumofLine+testLine ){
								appendToPane(inputTxt, s+"\n", Color.RED);
								test++;
								if(test==inputNumofLine){
									test=0;
									indexCnt++;
								}
							}
							else{
								appendToPane(inputTxt, s+"\n", Color.black);
							}
						}
						cnt++;
					}
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        javax.swing.text.AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
