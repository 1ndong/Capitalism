package com.indong.capitalism.Frame.CustomPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class DashBoardPanel extends JPanel implements ITime{
	
	private JPanel upperPanel;
	private JPanel centerPanel;
	private JComboBox<String> combobox;
	private JTextField dayField;
	private boolean bInit = false;
	
	public DashBoardPanel(Rectangle dashboardRect) {
		// TODO Auto-generated constructor stub
		this.setBounds(dashboardRect);
		this.setLayout(null);
		
		Font font = new Font("맑은 고딕",Font.BOLD,30);
		
		Rectangle rect = getBounds();
		final float upperRatio = 0.1f;
		final float combowidthRatio = 0.1f;
		
		upperPanel = new JPanel();
		upperPanel.setBounds(0, 0, rect.width, (int)(rect.height * upperRatio));
		upperPanel.setBackground(Color.blue);
		upperPanel.setLayout(null);
		
		Rectangle upperRect = upperPanel.getBounds();
		
		combobox = new JComboBox<String>();
		combobox.setBounds(0,0,(int)(upperRect.width * combowidthRatio),upperRect.height);
		combobox.setSelectedIndex(-1);
		combobox.setFont(font);
		
		Rectangle comboboxRect = combobox.getBounds();
		
		dayField = new JTextField();
		int dax = comboboxRect.width;
		int day = 0;
		int daw = (upperRect.width / 2) - comboboxRect.width;
		int dah = upperRect.height;
		dayField.setBounds(dax , day , daw , dah);
		dayField.setEditable(false);
		dayField.setForeground(Color.white);
		dayField.setHorizontalAlignment(JTextField.CENTER);
		dayField.setBackground(new Color(119,25,170));
		dayField.setFont(font);
		
		upperPanel.add(combobox);
		upperPanel.add(dayField);
		
		/////////////////////////////
		
		centerPanel = new JPanel();
		centerPanel.setBounds(0,upperRect.height , rect.width , (int)(rect.height * (1.0f - upperRatio)));
		centerPanel.setBackground(Color.gray);
		centerPanel.setLayout(null);
		/////////////report//////////
		
		
		
		
		/////////////////////////////
		this.add(upperPanel);
		this.add(centerPanel);
		
		ITimeKeeper timekeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void initComponent()
	{
		combobox.removeAllItems();
		for(int i = 0 ; i < CWorld.getInstance().getCountryList().size() ; i++)
		{
			combobox.addItem(CWorld.getInstance().getCountryList().get(i).getCountryName());
		}
		combobox.setSelectedIndex(-1);
	}
	
	public void updateComponent()
	{
		if(bInit == false)
		{
			initComponent();
			bInit = true;
		}
		else
		{
			
		}
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		dayField.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
	}
}
