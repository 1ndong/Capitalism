package com.indong.capitalism.Frame.CustomPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cb;
    private JTextField tf;

    public ControlPanel(Rectangle dashboardRect) {
        // TODO Auto-generated constructor stub
        this.setBounds(dashboardRect);
        this.setLayout(null);
        //this.setBackground(new Color(0,43,54));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        int spx = 0;
        int spy = 0;
        int spw = dashboardRect.width;
        int sph = (int)(dashboardRect.height * 0.8f);
        panel.setBounds(spx,spy,spw,sph);
        panel.setBackground(new Color(0,43,54));
        {
            cb = new JComboBox<String>();
            int cx = 0;
            int cy = 0;
            int cw = dashboardRect.width / 2;
            int ch = 50;
            cb.setBounds(cx,cy,cw,ch);
            cb.setEnabled(false);
            panel.add(cb);

            tf = new JTextField();
            int tx = cw;
            int ty = cy;
            int tw = cw;
            int th = ch;
            tf.setBounds(tx,ty,tw,th);
            tf.setEnabled(false);
            panel.add(tf);
        }

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBackground(new Color(31,138,209));
        int bsx = 0;
        int bsy = sph;
        int bsw = spw / 2;
        //int bsh = (int)(dashboardRect.height * 0.2f);
        int bsh = 100;
        btnSubmit.setBounds(bsx,bsy,bsw,bsh);

        JButton btnUnselect = new JButton("Cancel");
        btnUnselect.setForeground(Color.white);
        btnUnselect.setBackground(new Color(210,51,85));
        int bux = bsw;
        int buy = sph;
        int buw = bsw;
        int buh = 100;
        btnUnselect.setBounds(bux,buy,buw,buh);

        add(panel);
        add(btnSubmit);
        add(btnUnselect);
    }

    public void setCustomLayout(String[] result)
    {
        cb.setEnabled(true);
        tf.setEnabled(true);

        for(int i = 0 ; i < result.length ; i++)
        {
            cb.addItem(result[i]);
        }

        cb.setSelectedIndex(-1);
    }

    /*
    public ControlPanel(Rectangle dashboardRect) {
        // TODO Auto-generated constructor stub
        this.setBounds(dashboardRect);
        this.setLayout(null);
        this.setBackground(new Color(0,43,54));

        String[] colName = new String[] {"CommandList"};
        model = new DefaultTableModel(colName,0);

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        int spx = 0;
        int spy = 0;
        int spw = dashboardRect.width;
        int sph = (int)(dashboardRect.height * 0.8f);
        scrollPane.setBounds(spx,spy,spw,sph);
        table.setBounds(scrollPane.getBounds());
        table.setRowHeight(50);
        table.getColumn("CommandList").setCellRenderer(new ControlPanelTableCellRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBackground(new Color(31,138,209));
        int bsx = 0;
        int bsy = sph;
        int bsw = spw / 2;
        //int bsh = (int)(dashboardRect.height * 0.2f);
        int bsh = 100;
        btnSubmit.setBounds(bsx,bsy,bsw,bsh);

        JButton btnUnselect = new JButton("Cancel");
        btnUnselect.setForeground(Color.white);
        btnUnselect.setBackground(new Color(210,51,85));
        int bux = bsw;
        int buy = sph;
        int buw = bsw;
        int buh = 100;
        btnUnselect.setBounds(bux,buy,buw,buh);

        add(scrollPane);
        add(btnSubmit);
        add(btnUnselect);

        model.addRow(new String[]{"a"});
        model.addRow(new String[]{"b"});
        model.addRow(new String[]{"c"});
        model.addRow(new String[]{"d"});
        model.addRow(new String[]{"a"});
        model.addRow(new String[]{"b"});
        model.addRow(new String[]{"c"});
        model.addRow(new String[]{"d"});
        model.addRow(new String[]{"a"});
        model.addRow(new String[]{"b"});
        model.addRow(new String[]{"c"});
        model.addRow(new String[]{"d"});
        model.addRow(new String[]{"a"});
        model.addRow(new String[]{"b"});
        model.addRow(new String[]{"c"});
        model.addRow(new String[]{"d"});

    }
*/
}
