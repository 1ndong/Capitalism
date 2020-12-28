package com.indong.capitalism.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.indong.capitalism.Processor.ProcessorCommand;

public class FrameControl extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FrameControl instance;
	
	public static FrameControl getInstance()
	{
		return instance;
	}
	
	public static void MakeControlFrame(double x, double y , double width , double height)
	{
		instance = new FrameControl(x,y,width , height);
	}
	
	private FrameControl(double x , double y , double width ,double height)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)x,(int)y,(int)width,(int)height);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextField textfield = new JTextField();
		JButton processBtn = new JButton();
		JLabel resultLabel = new JLabel();
		
		Rectangle rect = getBounds();
		
		int tfx = 0;
		int tfy = 0;
		int tfw = (int)(rect.width * 0.8f);
		int tfh = (int)(rect.height * 0.1f);
		
		textfield.setBounds(tfx, tfy, tfw, tfh);
		
		int pbx = tfw;
		int pby = 0;
		int pbw = (int)(rect.width * 0.2f);
		int pbh = (int)(rect.height * 0.1f);
		
		processBtn.setBounds(pbx, pby, pbw, pbh);
		processBtn.setText("process");
		Color idleColor = new Color(90,120,255);
		processBtn.setBackground(idleColor);
		processBtn.setForeground(Color.white);
		
		processBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean result = false;
				result = ProcessorCommand.getInstance().compile(textfield.getText());
				if(result)
				{
					Color passColor = new Color(39,167,104);
					processBtn.setBackground(passColor);
				}
				else
				{
					Color failColor = new Color(223,89,77);
					processBtn.setBackground(failColor);
				}
			}
		});
		
		contentPane.add(textfield);
		contentPane.add(processBtn);
	}
}
