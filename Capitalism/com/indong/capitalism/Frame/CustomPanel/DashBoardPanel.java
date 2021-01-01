package com.indong.capitalism.Frame.CustomPanel;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.indong.capitalism.Classes.CWorld;

public class DashBoardPanel extends JPanel {

	private JComboBox<String> combobox;
	
	public DashBoardPanel(Rectangle dashboardRect) {
		// TODO Auto-generated constructor stub
		this.setBounds(dashboardRect);
		this.setBackground(Color.blue);
		this.setLayout(null);
		
		combobox = new JComboBox<String>();
		combobox.setBounds(0, 0, 100, 100);
		this.add(combobox);
		combobox.setSelectedIndex(-1);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void updateComponent()
	{
		combobox.removeAllItems();
		for(int i = 0 ; i < CWorld.getInstance().getCountryList().size() ; i++)
		{
			combobox.addItem(CWorld.getInstance().getCountryList().get(i).getCountryName());
		}
		
	}
}
