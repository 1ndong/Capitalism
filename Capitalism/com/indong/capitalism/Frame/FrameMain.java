package com.indong.capitalism.Frame;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Processor.ProcessorMain;

public class FrameMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -109834300546271564L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrameMain(double width , double height) 
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
		
		Rectangle scrollPaneRect = new Rectangle(rect);
		scrollPaneRect.y = dashboardRect.height;
		scrollPaneRect.height = rect.height - dashboardRect.height;
		
		CPanelTableModel compModel = new CPanelTableModel();
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
		table.setFillsViewportHeight(true);
		table.setBounds(scrollPane.getBounds());
		compModel.addRow("name",1000);
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
}
