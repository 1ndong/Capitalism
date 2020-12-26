package com.indong.capitalism.Frame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Processor.ProcessorMain;

public class FrameMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -109834300546271564L;
	private JPanel contentPane;
	private CPanelTableModel compModel;
	private static FrameMain instance;
	/**
	 * Create the frame.
	 */
	public static void MakeMainFrame(double width , double height)
	{
		instance = new FrameMain(width , height);
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
		
		float RATIO_DASHBOARD = 0.3f;
		Rectangle dashboardRect = new Rectangle(rect);
		dashboardRect.height *= RATIO_DASHBOARD;
		
		JPanel dashboard = new JPanel();
		dashboard.setBackground(Color.blue);
		dashboard.setBounds(dashboardRect);
		
		JButton testCreatePeopleBtn = new JButton("사람 생성");
		testCreatePeopleBtn.setBounds(10,10,10,10);
		dashboard.add(testCreatePeopleBtn);
		//test area
		testCreatePeopleBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().equals(testCreatePeopleBtn))
				{
					int size = compModel.getRowCount();
					FrameMain.getInstance().addRow("테스터"+size, 3000);
					//테스트로 넣은거고 실제로는 humanresource의 peoplemap이 늘어날때마다 리스트 업데이트 해줘야됨
				}
			}
		});
		//
		Rectangle scrollPaneRect = new Rectangle(rect);
		scrollPaneRect.y = dashboardRect.height;
		scrollPaneRect.height = rect.height - dashboardRect.height;
		
		compModel = new CPanelTableModel();
		JTable table = new JTable(compModel);
		table.setRowHeight(new CCompCellPanel("",0).getPreferredSize().height);
        table.setTableHeader(null);
        table.setRowHeight(100);
        CPanelCellEditorRenderer PanelCellEditorRenderer = new CPanelCellEditorRenderer();
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
	}
	
	public synchronized void addRow(String name , int balance)
	{
		compModel.addRow(name,balance);
	}
}
