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
/**
 * MainBoard class
 * 1. 생성자 -> GUI 설정, component들을 배치합니다.
 * 2. void appendToPane(JTextPane tp, String msg, Color c) -> tp에 msg를 c의 색으로 출력해 줍니다.
 * 3. btnSetting() -> 모든 버튼에 리스너를 연결해 줍니다.
 * 4. fileSetting() -> 저장된 경로의 파일을 읽어서 로딩해 줍니다.
 * 5. runThread() -> 프로세스의 입출력의 thread를 생성해줍니다.(유저 출력에서 틀린 부분을 color를 입혀줍니다.)
 * 6. reWriteInput(ArrayList<Integer> integerInput) -> input부분에서 틀린 테스트 케이스를 color를 입혀줍니다.
 * */
public class MainBoard extends JFrame {
	/**
	 * 변수 선언 부분
	 * gui 부분을 위한 셋팅입니다.
	 * */
	private JPanel contentPane;  //전체 판넬을 감싸는 컨테이너
	private JPanel mainPane;//전체 판넬
	
	private JPanel btnPane;//버튼들을 감싸는 판넬
	private JButton settingBtn; //설정 버튼 
	private JButton runBtn; //실행 버튼
	private JButton resetBtn; //초기화 버튼
	private JButton cancelBtn; //종료 버튼
	
	private JPanel exeSelectPane;//exe관련 부분을 감싸는 판넬
	private JLabel exeFileShow; //exe 파일의 경로를 보여주는 부분
	private JButton exeFileBtn; //exe 파일을 선택하는 버튼 -> filechooser로 actionCommand 설정 되어 있음
	
	private JPanel inputPane; //입력 파일을 보여주는 판넬 부분
	private JPanel inputSelector;//input btn을 감싸는 컨테이너
	private JButton inputSelectorBtn;//input btn ->filechooser로 actionCommand 설정 되어 있음
	private JTextPane inputTxt; //input 파일의 내용을 보여주는 부분
	private JScrollPane scrollPane; //inputTXT의 scroll 가능하게 위해서 붙임
	
	private JPanel outputPane; //유저 출력과 output 파일의 출력을 감싸는 컨테이너
	
	private JPanel output; //output파일과 관련된 부분
	private JPanel outputSelector;//output btn을 감싸는 컨테이너
	private JButton outputSelectorBtn;//output btn ->filechooser로 actionCommand 설정 되어 있음
	private JTextArea outputTxt; //output 파일의 내용을 보여주는 부분
	private JScrollPane scrollPane2; //outputTXT의 scroll 가능하게하기 위해서 붙임

	
	private JPanel userOutput; // 유저 출력 부분
	private JTextPane userOutputTxt; // 유저 출력을 보여주준다
	private JScrollPane scrollPane3; // 유저출력의 scroll 가능하게하기 위해서 붙임
	
	/**
	 * GUI를 제외한 다른 부분을 하기 위한 변수 
	 * 
	 * */
	private static JFrame MyFrame; //현재 실행중인 판넬을 구체화 -> actionListner에서 조작을 하기 위함
	private FileWriter fw; //파일 쓰기를 위한 객체
	private BufferedReader fr; //파일 읽기를 위한 객체
	private BufferedReader reader;//프로세스의 입력 버퍼를 연결
	private BufferedWriter writer;//프로세스의 출력 버퍼를 연결
	
	public settingDialog dialog;//설정 btn을 눌렀을 때 이 dialog가 실행됩니다
	
	/**
	 * Launch the application.
	 */
 	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			MainBoard frame;
			public void run() {
				try {
					 frame= new MainBoard(); //프레임 생성
					frame.btnSetting();//버튼에 actionListener를 연결해 줍니다.
					frame.fileSetting();//파일 입출력을 조작해 save된 파일을 로딩해 줍니다
					frame.setVisible(true);
					MyFrame=frame;//현재 객체를 할당
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 선언된 GUI변수에 객체를 할당하는 역할을 하는 생성자 입니다.
	 */
	public MainBoard() {
		setTitle("CHECKER");
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
	/**
	 * void appendToPane(JTextPane,String,Color)
	 * 지정된 textPane에 String 문자열을 Color로 입력해 줍니다.
	 * 
	 * */
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
	/***
	 * void btnSetting()
	 * 
	 * exeFileBtn -> fileChooser -> 선택된 파일 경로 로딩 및 세이브
	 * inputSelectorBtn ->fileChooser -> 선택된 파일 경로 로딩 및 세이브
	 * outputSelectorBtn->fileChooser -> 선택된 파일 경로 로딩 및 세이브
	 * cancelBtn ->종료
	 * resetBtn ->input,output을 다시 로딩, 유저 출력을 비워줍니다.
	 * runBtn ->프로세스를 생성해 입출력을 하고 결과 값을 유저출력 부분으로 보여줍니다.
	 * settingBtn -> 실행될 파일의 설정을 위한 btn입니다.(settingDialog를 실행합니다.)
	 * 
	 * */
	
	public void btnSetting(){
		exeFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//fileChooser 생성
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(exeFileBtn);
				File exeFile=fc.getSelectedFile();
				try{
					exeFileShow.setText(exeFile.getPath()); //레이블에 파일 경로를 적어준뒤
					//파일 입력으로 그 경로를 적어줍니다.
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
				//fileChooser 생성
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(inputSelectorBtn);
				File exeFile=fc.getSelectedFile();
				try{
					//파일을 읽어와 inputPane에 그려줍니다.
					BufferedReader in=new BufferedReader(new FileReader(exeFile.getPath()));
					String s;
					while((s=in.readLine())!=null){
						appendToPane(inputTxt, s+"\n", Color.black);
					}
					//경로 저장!
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
				//fileChooser 생성
				JFileChooser fc=new JFileChooser();
				fc.showOpenDialog(inputSelectorBtn);
				File exeFile=fc.getSelectedFile();
				try{
					//파일을 읽어와 출력 부분에 적어 줍니다.
					BufferedReader in=new BufferedReader(new FileReader(exeFile.getPath()));
					String s;
					while((s=in.readLine())!=null){
						outputTxt.append(s);
						outputTxt.append("\n");
					}
					//경로 저장!
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
				//프로그램 종료!
				MyFrame.dispose();
			}
		});
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				outputTxt.setText("");
				inputTxt.setText("");
				userOutputTxt.setText("");
				fileSetting();//입출력 부분을 저장한 경로의 파일을 로딩해 줍니다!
			}
		});
		runBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//경로에 지정된 실행파일을 실행할 프로세스를 생성합니다.
				ProcessBuilder pcBuilder=new ProcessBuilder(exeFileShow.getText());
				pcBuilder.redirectErrorStream(true);
				try {
					Process process=pcBuilder.start(); //프로세스 실행
					if(outputTxt.getText().equals(""))throw new Exception(); //출력 부분과
					if(inputTxt.getText().equals(""))throw new Exception();//입력 파일이 없으면 실행하지 않습니다.
					OutputStream stdin = process.getOutputStream (); //프로세스의 입력 스트림을 연결합니다.
					InputStream stderr = process.getErrorStream ();//에러 출력 스트림을 연결합니다.
					InputStream stdout = process.getInputStream ();//프로세스의 출력 스트림을 연결합니다.
					reader = new BufferedReader(new InputStreamReader(stdout));//reader에 할당해 읽기 가능하게 됩니다.
					writer = new BufferedWriter(new OutputStreamWriter(stdin));//writer에 할당해 쓰기 가능하게 됩니다.
					runThread();//reader와 writer에 입출력을 하기 위한 Thread를 생성합니다.
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(MyFrame,"실행파일 또는 입출력 파일을 설정해 주세요");
				}
			}
		});
		settingBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//설정 dialog를 생성합니다.
				dialog.setVisible(true);//보여주기!
			}
		});
	}
	/**
	 * void fileSetting()
	 * 
	 * path1 -> exe 파일 경로를 저장합니다.
	 * path2 -> input 파일의 경로를 저장합니다.
	 * path3 -> output 파일의 경로를 저장합니다.
	 * 
	 * */
	public void fileSetting(){
		try {
			//실행 파일을 로딩합니다.
			fr=new BufferedReader(new FileReader("path1.txt"));
			String line="";
			if((line=fr.readLine())!=null){
				exeFileShow.setText(line);
			}
			
			//입력 파일을 로딩합니다.
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
			
			//출력 파일을 로딩합니다.
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
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * void runtThread()
	 * 
	 * input Thread와 output Thread를 생성해 입출력을 해줍니다.
	 * */
	public void runThread(){
		//input Thread를 implements 합니다.
		Runnable input=new Runnable(){
			@Override
			public void run(){
				String s[]=inputTxt.getText().split("\n"); //입력을 잘라줍니다.
				for(int i=0;i<s.length;i++){
					try {
						writer.write(s[i]+"\n");
						writer.flush();//프로세스에 입력해줍니다.
						try {
							Thread.sleep(10);//입력과 출력의 delay때문에 넣어줬습니다. 상관 없을 수 있습니다.
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		//output Thread를 implements 합니다.
		Runnable output=new Runnable(){
			@Override
			public void run(){
				String line="";
				String s[]=outputTxt.getText().split("\n");//output을 잘라 줍니다. -> 출력과의 확인을 위함입니다.
				ArrayList<Integer> arr=new ArrayList<Integer>(); //틀린 부분이 몇번째 testCase인지를 입력해 줍니다. ->input부분의 색을 변하게 하기 위함.  
				int cnt=0;
				try {
					while((line=reader.readLine())!=null){ //프로세스 출력을 입력받습니다.
						if(s[cnt].equals(line))
							appendToPane(userOutputTxt,line+"\n",Color.BLACK);//맞으면 검은색으로 유저 출력을 보여 줍니다.
						else{
							appendToPane(userOutputTxt,line+"\n",Color.RED);//틀리면 빨간색으로 유저 출력을 보여 줍니다.
							arr.add(cnt+1);//테스트 케이스의 인덱스를 입력합니다.
						}
						cnt++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				//input 부분의 틀린 부분을 빨간색으로 출력해 주는 함수 입니다.
				reWriteInput(arr);
			}
		};
		
		//thread를 실행시킵니다.
		Thread a=new Thread(input);
		Thread b=new Thread(output);
		a.start(); b.start();
	}

	/**
	 * void reWriteINput(ArrayList<int>) - 틀린 부분의 테스트 케이스 순서를 저장한 배열을 넘겨 받아서 출력해 줍니다.
	 * */
	public void reWriteInput(ArrayList<Integer> integerInput){
		int testLine=0; //testCase의 줄 수 입니다.
		int inputNumofLine=0;//testCase당 줄 수 입니다.
		//setting파일을 읽어 와서 testLine과 inputNumOfLine을 셋팅해 줍니다.
		try {
			fr=new BufferedReader(new FileReader("setting.txt"));
			testLine=Integer.parseInt(fr.readLine());
			inputNumofLine=Integer.parseInt(fr.readLine());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int indexCnt=0;
		int test=0;
		String s[]=inputTxt.getText().split("\n"); //입력을 잘라줍니다.
		inputTxt.setText("");//입력 부분을 지워 줍니다.
		for(int i=0; i<s.length; i++){
			if(i+1<=testLine)
				appendToPane(inputTxt, s[i]+"\n", Color.black); //테스트 케이스 입력 부분을 출력해 줍니다.
			else{
				//틀린 테스트 케이스를 테스트 케이스 줄  입력 만큼 그 부분의 줄을 빨간색으로 바꾸어 줍니다.
				if(indexCnt<integerInput.size()&& //틀린 인덱스를 저장한 배열의 범위를 check 해주는 부분입니다.
						(integerInput.get(indexCnt)-1)*inputNumofLine+testLine< i+2 && //테스트 케이스 인덱스의 시작 부분 check 
						i+2 >=integerInput.get(indexCnt)*inputNumofLine+testLine ){//테스트 케이스 인덱스의 끝 부분 check
					appendToPane(inputTxt, s[i]+"\n", Color.RED);
					test++; // 테스트 케이스 당 출력한 줄을 count 합니다.
					if(test==inputNumofLine){ //테스트 케이스의 입력을 다 출력해 주었으면
						test=0;// 0으로 셋팅 해주고
						indexCnt++;//다음 index로 넘어 갑니다.
					}
				}
				else
					appendToPane(inputTxt, s[i]+"\n", Color.black); //맞는 케이스일 경우 검은색으로 출력합니다.
			}
		}
		/*
		 * i+1과 i+2를 하는 이유는 입력 testCase의 인덱스가 0이 아닌 1부터 시작되기 때문입니다.
		 * */
	}
	
}
