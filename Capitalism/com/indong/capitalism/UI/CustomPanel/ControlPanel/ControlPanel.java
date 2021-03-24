package com.indong.capitalism.UI.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.Enum.ESectorType;
import com.indong.capitalism.Interface.ISearchable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    private DefaultTableModel model;
    private JComboBox<String> cb;
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
        {
            cb = new JComboBox<String>();
            int cx = 0;
            int cy = 0;
            int cw = dashboardRect.width / 2;
            int ch = 50;
            cb.setBounds(cx, cy, cw, ch);
            cb.setEnabled(false);
            panel.add(cb);

            tf = new JTextField();
            int tx = cw;
            int ty = cy;
            int tw = cw;
            int th = ch;
            tf.setBounds(tx, ty, tw, th);

            panel.add(tf);
        }

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setForeground(Color.white);
        btnSubmit.setBackground(new Color(31, 138, 209));
        btnSubmit.addActionListener(submitClickListener);
        int bsx = 0;
        int bsy = 50;
        int bsw = spw / 2;
        //int bsh = (int)(dashboardRect.height * 0.2f);
        int bsh = 50;
        btnSubmit.setBounds(bsx, bsy, bsw, bsh);

        JButton btnUnselect = new JButton("Cancel");
        btnUnselect.setForeground(Color.white);
        btnUnselect.setBackground(new Color(210, 51, 85));
        btnUnselect.addActionListener(new CancelClickListener(this));
        int bux = bsw;
        int buy = 50;
        int buw = bsw;
        int buh = 50;
        btnUnselect.setBounds(bux, buy, buw, buh);

        //stuff list
        String[] colName = new String[] {"[stuff list]"};
        model = new DefaultTableModel(colName,0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        int tx = 0;
        int ty = 100;
        int tw = dashboardRect.width;
        int th = dashboardRect.height - ty;
        scrollPane.setBounds(tx, ty, tw, th);
        table.setBounds(scrollPane.getBounds());

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

    public JComboBox<String> getCombobox() {
        return cb;
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

        cb.setEnabled(true);
        submitClickListener.setStage(0);

        ESectorType[] types = ESectorType.values();
        for (int i = 0; i < types.length; i++)
            cb.addItem(types[i].getLocaleValue(types[i]));

        cb.setSelectedIndex(-1);
    }

    public void ResetPanel() {
        cb.removeAllItems();
        cb.setEnabled(false);
    }

    public void initPanel()
    {
        targetBeing = null;
    }
}
