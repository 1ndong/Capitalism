package com.indong.capitalism.UI.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Stuff.CStuff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    private DefaultTableModel model;
    private JTable stuffTable;

    private DefaultTableModel serviceModel;
    private JTable serviceTable;

    private JTextField tf;
    private SubmitClickListener submitClickListener = new SubmitClickListener(this);

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
        int sph = dashboardRect.height;
        panel.setBounds(spx, spy, spw, sph);
        panel.setBackground(new Color(0, 43, 54));

        tf = new JTextField();
        int tx = 0;
        int ty = 0;
        int tw = dashboardRect.width;
        int th = (int)(dashboardRect.height * 0.1f);
        tf.setBounds(tx, ty, tw, th);

        panel.add(tf);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBackground(new Color(31, 138, 209));
        btnSubmit.addActionListener(submitClickListener);
        int bsx = 0;
        int bsy = tf.getHeight();
        int bsw = spw / 2;
        int bsh = tf.getHeight();
        btnSubmit.setBounds(bsx, bsy, bsw, bsh);

        JButton btnUnselect = new JButton("Cancel");
        btnUnselect.setForeground(Color.white);
        btnUnselect.setBackground(new Color(210, 51, 85));
        btnUnselect.addActionListener(new CancelClickListener(this));
        int bux = bsw;
        int buy = tf.getHeight();
        int buw = bsw;
        int buh = tf.getHeight();
        btnUnselect.setBounds(bux, buy, buw, buh);

        //service list
        String[] servicecolName = new String[] {"[service list]"};
        serviceModel = new DefaultTableModel(servicecolName,0);

        serviceTable = new JTable(serviceModel);

        JScrollPane servicescrollPane = new JScrollPane(serviceTable);
        serviceTable.setFillsViewportHeight(true);

        int servicetablex = 0;
        int servicetabley = buy + buh;
        int servicetablew = dashboardRect.width / 2;
        int servicetableh = dashboardRect.height - ty;
        servicescrollPane.setBounds(servicetablex, servicetabley, servicetablew, servicetableh);
        serviceTable.setBounds(servicescrollPane.getBounds());

        add(servicescrollPane);
        //
        //stuff list
        String[] colName = new String[] {"[stuff list]"};
        model = new DefaultTableModel(colName,0);

        stuffTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(stuffTable);
        stuffTable.setFillsViewportHeight(true);

        int tablex = dashboardRect.width / 2;
        int tabley = buy + buh;
        int tablew = dashboardRect.width / 2;
        int tableh = dashboardRect.height - ty;
        scrollPane.setBounds(tablex, tabley, tablew, tableh);
        stuffTable.setBounds(scrollPane.getBounds());

        add(scrollPane);
        //
        add(btnSubmit);
        add(btnUnselect);
        add(panel);
    }

    public DefaultTableModel getModel()
    {
        return model;
    }

    public JTextField getTextField() {
        return tf;
    }

    private CBeing targetBeing = null;

    public CBeing getTargetBeing() {
        return targetBeing;
    }

    public void startCommandProcess(CBeing being) {
        ResetPanel();

        tf.setText("");
        targetBeing = being;
        {
            if(serviceTable.isEditing())
                serviceTable.getCellEditor().stopCellEditing();
            if(stuffTable.isEditing())
                stuffTable.getCellEditor().stopCellEditing();

            getTextField().setText(targetBeing.getBasicData().getName());
            model.setRowCount(0);
            ArrayList<CStuff> stuffList = targetBeing.getStuffList();

            if(stuffList.size() > 0)
            {
                String[] str = new String[stuffList.size()];
                for(int i = 0 ; i < stuffList.size() ; i++)
                {
                    str[i] = stuffList.get(i).getName();
                }
                model.addRow(str);
            }
        }

        submitClickListener.setStage(0);
        setStage(submitClickListener.getStage());
    }

    private void setStage(int stage)
    {
        switch(stage)
        {
            case 0:
            {
                
            }
            break;
        }
    }

    public void ResetPanel() {

    }

    public void initPanel()
    {
        targetBeing = null;
    }
}
