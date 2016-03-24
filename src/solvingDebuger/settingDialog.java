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

public class settingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField testCaseLine;
	private JLabel lblNewLabel_2;
	private JTextField inputLine;
	public static int testLine;
	public static int inputNumofLine;	
	public JDialog my=this;
	public FileWriter fw;
	public BufferedReader fr;
	/**
	 * Create the dialog.
	 */
	public settingDialog() {
		try {
		fr=new BufferedReader(new FileReader("setting.txt"));
		 testLine=Integer.parseInt(fr.readLine());
		 inputNumofLine=Integer.parseInt(fr.readLine());
	} catch (Exception e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
		testLine=0;
		inputNumofLine=0;
	}
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
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try{
						testLine=Integer.parseInt(testCaseLine.getText());
						inputNumofLine=Integer.parseInt(inputLine.getText());

						System.out.println(testLine+"/"+inputNumofLine);
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
