package com.indong.capitalism.Frame;

import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Frame.CustomPanel.DashBoardPanel;
import com.indong.capitalism.Frame.CustomTable.CMainCompCellPanel;
import com.indong.capitalism.Frame.CustomTable.CMainPanelCellEditorRenderer;
import com.indong.capitalism.Frame.CustomTable.CMainPanelTableModel;
import com.indong.capitalism.Info.IAAccount;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Processor.ProcessorMain;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class FrameMain extends JFrame implements ITime{

	/**
	 * 
	 */
	private static final long serialVersionUID = -109834300546271564L;
	private JPanel contentPane;
	private CMainPanelTableModel compModel;
	private CMainPanelTableModel compModel2;
	private static FrameMain instance;
	private DashBoardPanel dashboard;
	/**
	 * Create the frame.
	 */
	public static void MakeMainFrame(double width , double height)
	{
		instance = new FrameMain(width , height);
		instance.updateComponent();
	}
	
	public static FrameMain getInstance()
	{
		return instance;
	}
	
	private FrameMain(double width , double height) 
	{
		setTitle("Capitalism");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)width, (int)height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//
		Rectangle rect = this.getBounds();
		
//		float RATIO_DASHBOARD = 0.6f;
//		Rectangle dashboardRect = new Rectangle(rect);
//		dashboardRect.height *= RATIO_DASHBOARD;
		float RATIO_DASHBOARD_WIDTH = 0.4f;
		Rectangle dashboardRect = new Rectangle(rect);
		dashboardRect.width *= RATIO_DASHBOARD_WIDTH;
		
		dashboard = new DashBoardPanel(dashboardRect);
		
		//peopleList
//		Rectangle scrollPaneRect = new Rectangle(rect);
//		scrollPaneRect.y = dashboardRect.height;
//		scrollPaneRect.height = rect.height - dashboardRect.height;
//		scrollPaneRect.width = rect.width / 2;
		Rectangle scrollPaneRect = new Rectangle(rect);
		scrollPaneRect.x = dashboardRect.width;
		scrollPaneRect.y = 0;
		scrollPaneRect.width = rect.width - dashboardRect.width;
		scrollPaneRect.height = rect.height / 2;
		
		compModel = new CMainPanelTableModel();
		JTable table = new JTable(compModel);
		table.setRowHeight(new CMainCompCellPanel(null ,"",0,0,0,0).getPreferredSize().height);
        table.setTableHeader(null);
        table.setRowHeight(40);
        CMainPanelCellEditorRenderer PanelCellEditorRenderer = new CMainPanelCellEditorRenderer();
        table.setDefaultRenderer(Object.class, PanelCellEditorRenderer);
        table.setDefaultEditor(Object.class, PanelCellEditorRenderer);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.green);
		scrollPane.setBounds(scrollPaneRect);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table.setFillsViewportHeight(true);
		table.setBounds(scrollPane.getBounds());
		
		//companyList
//		Rectangle scrollPaneRect2 = new Rectangle(rect);
//		scrollPaneRect2.y = dashboardRect.height;
//		scrollPaneRect2.height = rect.height - dashboardRect.height;
//		scrollPaneRect2.width = rect.width / 2;
//		scrollPaneRect2.x = scrollPaneRect.width;
		Rectangle scrollPaneRect2 = new Rectangle(rect);
		scrollPaneRect2.x = dashboardRect.width;
		scrollPaneRect2.y = scrollPaneRect.height;
		scrollPaneRect2.width = rect.width - dashboardRect.width;
		scrollPaneRect2.height = rect.height / 2;
		
		compModel2 = new CMainPanelTableModel();
		JTable table2 = new JTable(compModel2);
		table2.setRowHeight(new CMainCompCellPanel(null ,"",0,0,0,0).getPreferredSize().height);
		table2.setTableHeader(null);
		table2.setRowHeight(40);
        CMainPanelCellEditorRenderer PanelCellEditorRenderer2 = new CMainPanelCellEditorRenderer();
        table2.setDefaultRenderer(Object.class, PanelCellEditorRenderer2);
        table2.setDefaultEditor(Object.class, PanelCellEditorRenderer2);
		
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBackground(Color.green);
		scrollPane2.setBounds(scrollPaneRect2);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		table2.setFillsViewportHeight(true);
		table2.setBounds(scrollPane2.getBounds());
		
		contentPane.add(dashboard);
		contentPane.add(scrollPane);
		contentPane.add(scrollPane2);
		//
		new ProcessorMain();

		table.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				ProcessorDay.GetInstance().Process();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ITimeKeeper timekeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		updateComponent();
	}
	
	public void updateComponent()
	{
		dashboard.updateComponent();
	}
	
	public void addPeopleList(LinkedList<CPeople> peopleList)
	{
		compModel.setNumRows(0);
		for(int i = 0 ; i < peopleList.size() ; i++)
		{
			CPeople people = peopleList.get(i);
			String name = people.getBasicData().getName();
			
			long cash = people.getCash().getCash();
			long deposit = 0;
			long loan = 0;
			for(int j = 0 ; j < people.getBasicData().getInfoAsset().getAccountList().size() ; j++)
			{
				IAAccount infoaccount = people.getBasicData().getInfoAsset().getAccountList().get(j);
				IBankService bankservice = (IBankService)infoaccount.getBank();
				CBAccount account = bankservice.findAccount(people.getBasicData().getName(), infoaccount.getAccountNumber());
				if(account.getAccountType() == EAccountType.Deposit)
				{
					deposit += account.getRightsOfCash();
				}
				else if(account.getAccountType() == EAccountType.Loan)
				{
					loan += account.getRightsOfCash();
				}
			}
			
			long allAsset = deposit + cash + loan;
			compModel.addRow(people , name,allAsset,deposit,cash,loan);
		}
	}
	
	public void addCompanyList(LinkedList<CCompany> companyList)
	{
		compModel2.setNumRows(0);
		for(int i = 0 ; i < companyList.size() ; i++)
		{
			CCompany company = companyList.get(i);
			String name = company.getBasicData().getName();

			long cash = company.getCash().getCash();
			long deposit = 0;
			long loan = 0;
			for(int j = 0 ; j < company.getBasicData().getInfoAsset().getAccountList().size() ; j++)
			{
				IAAccount infoaccount = company.getBasicData().getInfoAsset().getAccountList().get(j);
				IBankService bankservice = (IBankService)infoaccount.getBank();
				CBAccount account = bankservice.findAccount(company.getBasicData().getName(), infoaccount.getAccountNumber());
				if(account.getAccountType() == EAccountType.Deposit)
				{
					deposit += account.getRightsOfCash();
				}
				else if(account.getAccountType() == EAccountType.Loan)
				{
					loan += account.getRightsOfCash();
				}
			}
			long allAsset = deposit + cash + loan;
			compModel2.addRow(company , name,allAsset,deposit,cash,loan);
		}
	}
}
