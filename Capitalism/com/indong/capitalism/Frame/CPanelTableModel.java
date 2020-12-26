package com.indong.capitalism.Frame;

import javax.swing.table.DefaultTableModel;

public class CPanelTableModel extends DefaultTableModel{
    private static final long serialVersionUID = 1L;

    @Override
    public int getColumnCount() {
        return 1;
    }

    public void addRow(String name,int balance) {
        super.addRow(new Object[]{new CTableComp(name,balance)});
    }
}
