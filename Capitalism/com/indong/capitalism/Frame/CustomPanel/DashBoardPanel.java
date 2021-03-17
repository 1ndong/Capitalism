package com.indong.capitalism.Frame.CustomPanel;

import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.Bank.CBCommercial;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DBankMember;
import com.indong.capitalism.DataStructure.DCareTaker;
import com.indong.capitalism.DataStructure.DHCentralBank;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameMain;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Util.UCurrency;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

public class DashBoardPanel extends JPanel implements ITime{
	
	private JPanel upperPanel;
	private JPanel centerPanel;
	private JComboBox<String> combobox;
	private JTextField dayField;
	private boolean bInit = false;
	private DefaultTableModel model;
	private JTable table;
	
	public DashBoardPanel(Rectangle dashboardRect) {
		// TODO Auto-generated constructor stub
		this.setBounds(dashboardRect);
		this.setLayout(null);
		
		Font font = new Font("맑은 고딕",Font.BOLD,20);
		
		Rectangle rect = getBounds();
		final float upperRatio = 0.1f;
		final float combowidthRatio = 0.3f;
		
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
		int daw = ((upperRect.width / 3)*2) - comboboxRect.width;
		int dah = upperRect.height;
		dayField.setBounds(dax , day , daw , dah);
		dayField.setEditable(false);
		dayField.setForeground(Color.white);
		dayField.setHorizontalAlignment(JTextField.CENTER);
		dayField.setBackground(new Color(119,25,170));
		dayField.setFont(font);
		
		JTextField amicField = new JTextField();
		int ax = dayField.getX() + dayField.getWidth();
		int ay = 0;
		int aw = upperRect.width - comboboxRect.width - dayField.getWidth();
		int ah = upperRect.height;
		amicField.setBounds(ax, ay, aw, ah);
		amicField.setEditable(false);
		amicField.setForeground(Color.white);
		amicField.setHorizontalAlignment(JTextField.CENTER);
		amicField.setFont(font);
		amicField.setBackground(new Color(45,194,216));
		amicField.setText("All money is credit");
		
		upperPanel.add(combobox);
		upperPanel.add(dayField);
		upperPanel.add(amicField);
		
		/////////////////////////////
		
		centerPanel = new JPanel();
		centerPanel.setBounds(0,upperRect.height , rect.width , (int)(rect.height * (1.0f - upperRatio)));
		centerPanel.setBackground(Color.gray);
		centerPanel.setLayout(null);
		/////////////report//////////
		
		String[] colName = new String[] {"[information]"};
		model = new DefaultTableModel(colName,0);
		
		table = new JTable(model);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(0,0,centerPanel.getWidth() , centerPanel.getHeight());
		table.setBounds(scrollPane.getBounds());
		table.setRowHeight(40);
		table.setBackground(new Color(44,34,85));
		table.setForeground(Color.white);
		
		centerPanel.add(scrollPane);
		
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
		
		model.setRowCount(0);
		
		if(combobox.getSelectedIndex() == -1)
			return;
		
		String countryName = combobox.getSelectedItem().toString();
		
		CCountry country = null;
		for(int i = 0 ; i < CWorld.getInstance().getCountryList().size() ; i++)
		{
			if(CWorld.getInstance().getCountryList().get(i).getCountryName().equalsIgnoreCase(countryName))
			{
				country = CWorld.getInstance().getCountryList().get(i);
				break;
			}
		}
		
		if(country != null)
		{
			DCareTaker careTaker = country.getCentralBank().getCareTaker();
			long allMadeMoney = 0;
			for(int i = 0 ; i < careTaker.getList().size() ; i++)
			{
				DHCentralBank history = (DHCentralBank)careTaker.getList().get(i);
				allMadeMoney += history.getMakeMoneyAmount();
			}
			addRowInformationTable("중앙은행 총 통화 발행량 : " + UCurrency.getInstance().toString(allMadeMoney,ECurrency.Won));
			addRowInformationTable("기준금리 : "+ country.getCentralBank().getBaseInterestRate() + "%");
			addRowInformationTable("중앙은행 현금보유량 : " + UCurrency.getInstance().toString(country.getCentralBank().getBalance().getCash(),ECurrency.Won));
			
			for(int i = 0 ; i < country.getBankList().size() ; i++)
			{
				CBCommercial bank = (CBCommercial)country.getBankList().get(i);
				addRowInformationTable(bank.getName() + " , 가산금리 : " + bank.getSpreadInterestRate() + "%");

				long creditCurrency = 0;
				for(int j = 0 ; j < bank.getBankMemberList().size() ; j++)
				{
					DBankMember bankmember = bank.getBankMemberList().get(j);
					for(int k = 0 ; k < bankmember.getAccountList().size() ; k++)
					{
						CBAccount account = bankmember.getAccountList().get(k);
						creditCurrency += account.getRightsOfCash();
					}
				}
				addRowInformationTable(bank.getName() + " , 신용통화 : " + UCurrency.getInstance().toString(creditCurrency,ECurrency.Won));
				addRowInformationTable(bank.getName() + " , 현금보유량 : " + UCurrency.getInstance().toString(bank.getBalance().getCash(),ECurrency.Won));
			}
		}
		
		CGMinistryOfHealthAndWelfare mohaw = (CGMinistryOfHealthAndWelfare) country.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);
		
		Iterator<Integer> mapIter = mohaw.getPeopleMap().keySet().iterator();
		LinkedList<CPeople> peopleList = new LinkedList<CPeople>();
        while(mapIter.hasNext())
        {
            int key = mapIter.next();
            CPeople value = mohaw.getPeopleMap().get(key);
            peopleList.add(value);
        }
        FrameMain.getInstance().addPeopleList(peopleList);
        
        CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy) country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
        
        Iterator<Integer> mapIter2 = motia.getCompanyMap().keySet().iterator();
		LinkedList<CCompany> companyList = new LinkedList<CCompany>();
        while(mapIter2.hasNext())
        {
            int key = mapIter2.next();
            CCompany value = motia.getCompanyMap().get(key);
            companyList.add(value);
        }
        FrameMain.getInstance().addCompanyList(companyList);
	}
	
	private void addRowInformationTable(String content)
	{
		String[] s = new String[] {content};
		model.addRow(s);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		dayField.setText(newTime.getYear() + "/" + newTime.getMonth() + "/" + newTime.getDay() + "/" + newTime.getDayoftheweek() + "요일");
	}
}
