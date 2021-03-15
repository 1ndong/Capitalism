package com.indong.capitalism.Frame.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.DataStructure.DService;
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
        int sph = (int) (dashboardRect.height * 0.8f);
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
        int bsy = sph;
        int bsw = spw / 2;
        //int bsh = (int)(dashboardRect.height * 0.2f);
        int bsh = 100;
        btnSubmit.setBounds(bsx, bsy, bsw, bsh);

        JButton btnUnselect = new JButton("Cancel");
        btnUnselect.setForeground(Color.white);
        btnUnselect.setBackground(new Color(210, 51, 85));
        btnUnselect.addActionListener(new CancelClickListener(this));
        int bux = bsw;
        int buy = sph;
        int buw = bsw;
        int buh = 100;
        btnUnselect.setBounds(bux, buy, buw, buh);

        //stuff list
        String[] colName = new String[] {"[stuff list]"};
        model = new DefaultTableModel(colName,0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        int tx = 0;
        int ty = 50;
        int tw = dashboardRect.width;
        int th = dashboardRect.height - ty - 100 - 50;
        scrollPane.setBounds(tx, ty, tw, th);
        table.setBounds(scrollPane.getBounds());

        add(scrollPane);
        //
        add(panel);
        add(btnSubmit);
        add(btnUnselect);
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
    private ArrayList<ISearchable> zeroStageList = null;
    private ArrayList<DService> currentStageList = null;

    public ArrayList<ISearchable> getZeroStageList() {
        return zeroStageList;
    }

    public ArrayList<DService> getCurrentStageList() {
        return currentStageList;
    }

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

    public void setZeroStage(ArrayList<ISearchable> zeroStageList)
    {
        ResetPanel();

        cb.setEnabled(true);

        this.zeroStageList = zeroStageList;

        for (int i = 0; i < zeroStageList.size(); i++) {
            ISearchable temp = zeroStageList.get(i);
            cb.addItem(temp.getSearchableOriginName());
        }

        cb.setSelectedIndex(-1);
    }

    public void setNextStage(ArrayList<DService> curStageList)
    {
        ResetPanel();
        cb.setEnabled(true);

        this.currentStageList = curStageList;

        JComboBox<String> cb = getCombobox();
        for(int i = 0 ; i < curStageList.size() ; i++)
        {
            DService temp = curStageList.get(i);
            cb.addItem(temp.getName());
        }

        cb.setSelectedIndex(-1);
    }

    public void ResetPanel() {
        cb.removeAllItems();
        cb.setEnabled(false);
    }

    public void initPanel()
    {
        zeroStageList = null;
        currentStageList = null;
        targetBeing = null;
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
