package solvingDebuger;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * settingDialog class
 * 설정 관련한 dialog입니다.
 * 
 * 1.생성자 -> 버튼을 제외한 Component들을 할당해 줍니다.
 * 2.void ileSetting() -> 저장된 setting을 로딩합니다.
 * 3.void buttonSetting() -> 버튼을 할당하고 listener를 연결합니다.
 * */
public class settingDialog extends JDialog {
	/*
	 * GUI를 위한 컴포넌트 선언 부분입니다.
	 * */
	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField testCaseLine;
	private JLabel lblNewLabel_2;
	private JTextField inputLine;
	
	/*
	 * 설정과 관련된 변수를 저장할 변수 입니다.
	 * 
	 * */
	public int testLine; // 테스트 케이스의 입력 line 수 입니다.
	public int inputNumofLine;// 테스트 케이스 한개당 입력 줄 수 입니다.
	
	/*
	 * 파일 입출력을 위한 변수 들입니다.
	 * */
	public JDialog my=this;
	public FileWriter fw;
	public BufferedReader fr;
	/**
	 * Create the dialog.
	 * 생성자 버튼을 제외한 component들을 할당해 줍니다.
	 */
	public settingDialog() {
		//파일 초기화 부분입니다.
		fileSetting();
		//GUI 설정 부분입니다.
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(null);
			
			lblNewLabel = new JLabel("테스트 케이스는 몇줄?");
			lblNewLabel.setBounds(0, 0, 207, 43);
			panel.add(lblNewLabel);
			
			lblNewLabel_1 = new JLabel("(테스트케이스 진입 전 줄 수)");
			lblNewLabel_1.setBounds(0, 38, 254, 59);
			panel.add(lblNewLabel_1);
			
			testCaseLine = new JTextField();
			testCaseLine.setBounds(245, 55, 156, 27);
			panel.add(testCaseLine);
			testCaseLine.setColumns(10);
			testCaseLine.setText(testLine+"");
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(null);
			
			lblNewLabel_2 = new JLabel("테스트 케이스당 인풋 줄 수?\r\n");
			lblNewLabel_2.setBounds(0, 15, 251, 35);
			panel.add(lblNewLabel_2);
			
			inputLine = new JTextField();
			inputLine.setBounds(245, 55, 156, 27);
			panel.add(inputLine);
			inputLine.setColumns(10);
			inputLine.setText(inputNumofLine+"");
		}
		//버튼에 setting을 해줍니다.
		buttonSetting();
	}
	/**
	 * void fileSetting()
	 * 
	 * setting.txt 파일을 할당해 주고 로딩을 해줍니다.
	 * */
	public void fileSetting(){
				try {
				fr=new BufferedReader(new FileReader("setting.txt"));
				 testLine=Integer.parseInt(fr.readLine());
				 inputNumofLine=Integer.parseInt(fr.readLine());
			} catch (Exception e2) {
				e2.printStackTrace();
				testLine=0;
				inputNumofLine=0;
			}
	}
	/**
	 * void buttonSetting()
	 * 
	 * Ok버튼과 Cancel 버튼을 할당해 주고, 리스너를 연결해줍니다.
	 * */
	public void buttonSetting(){
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//OK 버튼 셋팅!
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					//OK 버튼을 누를 경우 입력된 값들을 setting.txt에 적어 줍니다.
					@Override
					public void actionPerformed(ActionEvent e) {
						try{
						testLine=Integer.parseInt(testCaseLine.getText());
						inputNumofLine=Integer.parseInt(inputLine.getText());
						}catch(Exception e1){
							testLine=0;
							inputNumofLine=0;
							
						}
						try {
							fw=new FileWriter(new File("setting.txt"));
							fw.write(testLine+"\n"+inputNumofLine);
							fw.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						my.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				//Cancel 버튼을 누를 시 종료합니다.
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						my.dispose();
					}
				});
				buttonPane.add(cancelButton);
				testLine=0;
				inputNumofLine=0;
			}
		}
		
	}
}
