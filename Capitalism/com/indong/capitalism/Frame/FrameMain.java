package com.indong.capitalism.Frame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Frame.CustomPanel.DashBoardPanel;
import com.indong.capitalism.Frame.CustomTable.CMainCompCellPanel;
import com.indong.capitalism.Frame.CustomTable.CMainPanelCellEditorRenderer;
import com.indong.capitalism.Frame.CustomTable.CMainPanelTableModel;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Processor.ProcessorMain;

public class FrameMain extends JFrame implements ITime{

	/**
	 * 
	 */
	private static final long serialVersionUID = -109834300546271564L;
	private JPanel contentPane;
	private CMainPanelTableModel compModel;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, (int)width, (int)height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//
		Rectangle rect = this.getBounds();
		
		float RATIO_DASHBOARD = 0.6f;
		Rectangle dashboardRect = new Rectangle(rect);
		dashboardRect.height *= RATIO_DASHBOARD;
		
		dashboard = new DashBoardPanel(dashboardRect);
		
		Rectangle scrollPaneRect = new Rectangle(rect);
		scrollPaneRect.y = dashboardRect.height;
		scrollPaneRect.height = rect.height - dashboardRect.height;
		
		compModel = new CMainPanelTableModel();
		JTable table = new JTable(compModel);
		table.setRowHeight(new CMainCompCellPanel("",0,0,0,0).getPreferredSize().height);
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
		contentPane.add(dashboard);
		contentPane.add(scrollPane);
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
	
	public void addPeople(CPeople people)
	{
		String name = people.getPersonaldata().getName();
		compModel.addRow(name,0,0,0,0);
	}
}
