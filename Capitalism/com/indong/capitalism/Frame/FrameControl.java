package com.indong.capitalism.Frame;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Frame.CustomPanel.ControlPanel.ControlPanel;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorCommand;
import com.indong.capitalism.Processor.ProcessorDay;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FrameControl extends JFrame implements ITime{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FrameControl instance;
	private DefaultTableModel model;
	private JTextField textfield;
	private JTextField dayField;
	private ControlPanel cp;
	
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
		setTitle("Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)x,(int)y,(int)width,(int)height);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textfield = new JTextField();
		JButton processBtn = new JButton();
		
		Rectangle rect = getBounds();
		
		int tfx = 0;
		int tfy = 0;
		int tfw = (int)(rect.width * 0.8f);
		int tfh = (int)(rect.height * 0.1f);
		
		textfield.setBounds(tfx, tfy, tfw, tfh);
		
		textfield.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					processBtn.doClick();
				}
				updateHelpString();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
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
				//result = ProcessorCommand.getInstance().compile(textfield.getText());
				result = ProcessorCommand.getInstance().newcompile(textfield.getText());
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
				textfield.setText("");
			}
		});
		
		int jbx = tfw;
		int jby = (int)(rect.height * 0.1f);
		int jbw = (int)(rect.width * 0.2f);
		int jbh = (int)(rect.height * 0.1f);
		
		JButton dayBtn = new JButton("1day");
		dayBtn.setBounds(jbx, jby, jbw, jbh);
		dayBtn.setBackground(new Color(125,194,189));
		dayBtn.setForeground(Color.white);
		dayBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ProcessorDay.GetInstance().Process();
			}
		});
		dayField = new JTextField();
		int dax = rect.width / 2;
		int day = (int)(rect.height * 0.1f);
		int daw = (rect.width / 2) - (int)(rect.width * 0.2f);
		int dah = (int)(rect.height * 0.1f);
		dayField.setBounds(dax , day , daw , dah);
		dayField.setEditable(false);
		dayField.setForeground(Color.white);
		dayField.setHorizontalAlignment(JTextField.CENTER);
		dayField.setBackground(new Color(119,25,170));
		Font font = new Font("맑은 고딕",Font.BOLD,20);
		dayField.setFont(font);

		String[] colName = new String[] {"[help]beta use only make"};
		model = new DefaultTableModel(colName,0);
		
		JTable table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		int spx = 0;
		int spy = tfh;
		int spw = rect.width / 2;
		int sph = (int)(rect.height * 0.1f);
		scrollPane.setBounds(spx, spy, spw, sph);
		table.setBounds(scrollPane.getBounds());
		
		updateHelpString();

		//
		Rectangle controlpanelRect = new Rectangle();
		controlpanelRect.x = 0;
		controlpanelRect.y = spy + sph;
		controlpanelRect.width = rect.width;
		controlpanelRect.height = rect.height - pbh - jbh;
		cp = new ControlPanel(controlpanelRect);
		//
		
		contentPane.add(textfield);
		contentPane.add(processBtn);
		contentPane.add(dayField);
		contentPane.add(dayBtn);
		contentPane.add(scrollPane);
		contentPane.add(getControlPanel());
		
		ITimeKeeper timekeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}
	
	private void updateHelpString()
	{
		//String[] helpstring = ProcessorCommand.getInstance().getHelpString(textfield.getText());
		String[] helpstring = ProcessorCommand.getInstance().newgetHelpString(textfield.getText());
		if(helpstring == null)
			return;
		model.setNumRows(0);
		for(int i = 0 ; i < helpstring.length ; i++)
		{
			String[] temp = new String[] {helpstring[i]};
			model.addRow(temp);
		}
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		dayField.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
	}

	public ControlPanel getControlPanel() {
		return cp;
	}
}
