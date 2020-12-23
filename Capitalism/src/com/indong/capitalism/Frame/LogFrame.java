package com.indong.capitalism;

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

public class LogFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1988465590986325069L;
	private static JPanel contentPane;
	private static JTable table;
	private static DefaultTableModel model;
	private static JScrollPane scrollPane;
	private static LogFrame instance = new LogFrame();

	public static LogFrame GetInstance()
	{
		return instance;
	}
	
	public synchronized static void addLog(String tag , String log)
	{
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String format_time = format.format (System.currentTimeMillis());
		
		String[] row = new String[]{format_time,tag,log,""};

		model.addRow(row); //테이블에 넣기
		
		table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
	}
	/**
	 * Create the frame.
	 */
	private LogFrame() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1400,100,700,700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		String[] colName = new String[] {"time","tag","log","reserved"};
		model = new DefaultTableModel(colName,0); //테이블 데이터
		
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(490);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
			
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setBounds(12, 10, 960, 641);
		table.setBounds(scrollPane.getBounds());
		contentPane.add(scrollPane,BorderLayout.CENTER);
		
		table.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
		    }
		});
	}
}
