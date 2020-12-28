package com.indong.capitalism.Frame.CustomTable;

import javax.swing.table.DefaultTableModel;

public class CMainPanelTableModel extends DefaultTableModel{
    private static final long serialVersionUID = 1L;

    @Override
    public int getColumnCount() {
        return 1;
    }

    public void addRow(String name,int balance) {
        super.addRow(new Object[]{new CMainTableComp(name,balance)});
    }
}
