package com.indong.capitalism.Frame.CustomTable;

import javax.swing.table.DefaultTableModel;

public class CMainPanelTableModel extends DefaultTableModel{
    private static final long serialVersionUID = 1L;

    @Override
    public int getColumnCount() {
        return 1;
    }

    public void addRow(String name,long allAsset , long deposit , long cash , long loan) {
        super.addRow(new Object[]{new CMainTableComp(name,allAsset , deposit , cash , loan)});//이름 총자산 예금 현금 대출
    }
}
