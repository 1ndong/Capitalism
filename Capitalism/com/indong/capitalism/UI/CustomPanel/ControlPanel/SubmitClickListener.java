package com.indong.capitalism.UI.CustomPanel.ControlPanel;

import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DPayment;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Enum.ESectorType;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.UI.Dialog.DialogPayment;
import com.indong.capitalism.Util.UCurrency;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class SubmitClickListener implements ActionListener {

    private ControlPanel cp;
    private int stage = -1;

    public SubmitClickListener(ControlPanel cp)
    {
        this.cp = cp;
    }

    public int getStage()
    {
        return stage;
    }

    public void setStage(int n)
    {
        stage = n;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(cp.getCombobox().getSelectedIndex() == -1)
            return;

        String selStr = (String)cp.getCombobox().getSelectedItem();

        String asis = cp.getTextField().getText();
        StringBuilder sb= new StringBuilder(asis);

        sb.append(' ');
        sb.append(selStr);
        cp.getTextField().setText(sb.toString());

        setStage(getStage()+1);
    }
}
