package com.indong.capitalism.Frame.CustomPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.DataStructure.DBankMember;
import com.indong.capitalism.DataStructure.DCareTaker;
import com.indong.capitalism.DataStructure.DHCentralBank;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameMain;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Item.ItemAccount;
import com.indong.capitalism.Processor.ProcessorDay;

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
			int allMadeMoney = 0;
			for(int i = 0 ; i < careTaker.getList().size() ; i++)
			{
				DHCentralBank history = (DHCentralBank)careTaker.getList().get(i);
				allMadeMoney += history.getMakeMoneyAmount();
			}
			addRowInformationTable("중앙은행 총 통화 발행량 : " + allMadeMoney + "원");
			addRowInformationTable("기준금리 : "+ country.getCentralBank().getBaseInterestRate() + "%");
			addRowInformationTable("중앙은행 현금보유량 : " + country.getCentralBank().getBalance().getCash() + "원");
			
			for(int i = 0 ; i < country.getBankList().size() ; i++)
			{
				CBCommercial bank = (CBCommercial)country.getBankList().get(i);
				addRowInformationTable(bank.getName() + " , 가산금리 : " + bank.getSpreadInterestRate() + "%");

				int creditCurrency = 0;
				for(int j = 0 ; j < bank.getBankMemberList().size() ; j++)
				{
					DBankMember bankmember = bank.getBankMemberList().get(i);
					for(int k = 0 ; k < bankmember.getAccountList().size() ; k++)
					{
						ItemAccount account = bankmember.getAccountList().get(i);
						creditCurrency += account.getRightsOfCash();
					}
				}
				addRowInformationTable(bank.getName() + " , 신용통화 : " + creditCurrency + "원");
				addRowInformationTable(bank.getName() + " , 현금보유량 : " + bank.getBalance().getCash() + "원");
			}
		}
		
		CGMinistryOfHealthAndWelfare mohaw = (CGMinistryOfHealthAndWelfare) country.getGovernmentMap().get(EGovernmentType.EHealthAndWelfare);
		
		Iterator<Integer> mapIter = mohaw.getPeopleMap().keySet().iterator();
		LinkedList<CPeople> peopleList = new LinkedList<CPeople>();
        while(mapIter.hasNext())
        {
            int key = mapIter.next();
            CPeople value = mohaw.getPeopleMap().get(key);
            peopleList.add(value);
        }
        FrameMain.getInstance().addPeopleList(peopleList);
        
        CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy) country.getGovernmentMap().get(EGovernmentType.ETradeIndustryAndEnergy);
        
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
