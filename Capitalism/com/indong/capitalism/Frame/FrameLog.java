package com.indong.capitalism.Frame;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FrameLog extends JFrame {
	private static final long serialVersionUID = -1988465590986325069L;
	private JTable table;
	private DefaultTableModel model;
	private static FrameLog instance;

	public static void MakeLogFrame(double x , double width , double height)
	{
		instance = new FrameLog(x,width,height);
	}
	
	public static FrameLog getInstance()
	{
		return instance;
	}
	
	public synchronized void addLog(String tag , String log)
	{
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String format_time = format.format (System.currentTimeMillis());
		
		String[] row = new String[]{format_time,tag,log,""};

		model.addRow(row);
		
		table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
	}
	/**
	 * Create the frame.
	 */
	private FrameLog(double x, double width , double height) 
	{
		setTitle("Log");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)x,0,(int)width,(int)height);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		String[] colName = new String[] {"time","tag","log","reserved"};
		model = new DefaultTableModel(colName,0);
		
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(490);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
			
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(12, 10, 960, 641);
		table.setBounds(scrollPane.getBounds());
		table.setRowHeight(50);
		contentPane.add(scrollPane,BorderLayout.CENTER);
		
		table.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
		    }
		});
	}
}
