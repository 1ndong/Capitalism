package com.indong.capitalism.UI.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DPayment;
import com.indong.capitalism.DataStructure.DServiceItem;
import com.indong.capitalism.Enum.EServicePropertyType;
import com.indong.capitalism.UI.Dialog.DialogPayment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

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
        serviceTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = serviceTable.getSelectedRow();
                int col = serviceTable.getSelectedColumn();
                String selectedItem = (String)serviceTable.getModel().getValueAt(row,col);

                //
                String asis = getTextField().getText();
                StringBuilder sb= new StringBuilder(asis);

                sb.append(' ');
                sb.append(selectedItem);
                getTextField().setText(sb.toString());
                //

                int nextStage = getStage() + 1;
                setStage(nextStage);
                makeStage(getStage() , selectedItem , row);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        add(servicescrollPane);
        //
        //stuff list
        String[] colName = new String[] {"[stuff list]","[value]"};
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

        btnSubmit.setEnabled(false);
        btnUnselect.setEnabled(false);
    }

    public DefaultTableModel getModel()
    {
        return model;
    }

    public JTextField getTextField() {
        return tf;
    }

    private CBeing targetBeing = null;
    private int stage = 0;
    private Vector<DServiceItem> lastItemList = new Vector<>();

    public Vector<DServiceItem> getLastItemList()
    {
        return lastItemList;
    }

    public CBeing getTargetBeing() {
        return targetBeing;
    }

    public void setStage(int stage)
    {
        this.stage = stage;
    }

    public int getStage()
    {
        return stage;
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
                for(int i = 0 ; i < stuffList.size() ; i++)
                {
                    String[] str = new String[2];
                    str[0] = stuffList.get(i).getName();
                    str[1] = stuffList.get(i).getPrice();
                    model.addRow(str);
                }
            }
        }

        setStage(0);
        makeStage(getStage() , "" , -1);
    }

    //0 sector , 1 major , 2 model + brand : price

    public void makeStage(int stage , String selectedItem , int selectedRow)
    {
        serviceModel.setRowCount(0);
        Vector<DServiceItem> catalog = DataCenter.getInstance().getCatalog();
        Set<String> tableResult = new HashSet<String>();
        switch(stage)
        {
            case 0:
            {
                for(int i = 0 ; i < catalog.size() ; i++)
                {
                    DServiceItem item = catalog.get(i);
                    String sector = item.getProperty(EServicePropertyType.Sector);
                    if(tableResult.contains(sector) == false)
                    {
                        tableResult.add(sector);
                        String[] temp = new String[]{sector};
                        serviceModel.addRow(temp);
                    }
                }
            }
            break;
            case 1:
            {
                for(int i = 0 ; i < catalog.size() ; i++)
                {
                    DServiceItem item = catalog.get(i);
                    if(item.getProperty(EServicePropertyType.Sector).equals(selectedItem) == false)
                        continue;
                    String major = item.getProperty(EServicePropertyType.Major);
                    if(tableResult.contains(major) == false)
                    {
                        tableResult.add(major);
                        String[] temp = new String[]{major};
                        serviceModel.addRow(temp);
                    }
                }
            }
            break;
            case 2:
            {
                for(int i = 0 ; i < catalog.size() ; i++)
                {
                    DServiceItem item = catalog.get(i);
                    if(item.getProperty(EServicePropertyType.Major).equals(selectedItem) == false)
                        continue;
                    String model_price = item.getProperty(EServicePropertyType.Model) + " / " + item.getProperty(EServicePropertyType.Price);
                    String[] temp = new String[]{model_price};
                    serviceModel.addRow(temp);
                    lastItemList.add(item);
                }
            }
            break;
            case 3://go to dialogpayment
            {
                StringTokenizer st = new StringTokenizer(selectedItem,"/");
                String[] temp = new String[2];
                int i = 0;
                while(st.hasMoreTokens())
                {
                    temp[i] = st.nextToken();
                    temp[i] = temp[i].trim();
                    i++;
                }

                DServiceItem item = lastItemList.get(selectedRow);

                DialogPayment dp = new DialogPayment(new DPayment(getTargetBeing(),item));
                if(dp.getResult() == true)
                {
                    String[] resultItem = new String[2];
                    resultItem[0] = item.getProperty(EServicePropertyType.Model);
                    resultItem[1] = item.getProperty(EServicePropertyType.Price);
                    getModel().addRow(resultItem);

                    getTargetBeing().getStuffList().add(new CStuff(item.getProperty(EServicePropertyType.Model)
                            , item.getProperty(EServicePropertyType.Price)));
                }
                lastItemList.removeAllElements();
                getTextField().setText("");
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
