package com.indong.capitalism.Frame.CustomPanel.ControlPanel;

import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.Enum.ESectorType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubmitClickListener implements ActionListener {

    private ControlPanel cp;
    private int stage = -1;
    private ArrayList<DService> currentService = null;

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

    /*
    stage 0 소비 금
    * */

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(cp.getCombobox().getSelectedIndex() == -1)
            return;

        JComboBox<String> cb = cp.getCombobox();
        String selItem = cb.getSelectedItem().toString();

        switch (getStage())
        {
            case 0:
            {
                ESectorType[] types = ESectorType.values();
                ESectorType result = null;
                for(int i = 0 ; i < types.length ; i++)
                {
                    if(types[i].toString().equals(selItem) == true)
                    {
                        result = types[i];
                        break;
                    }
                }
                if(result != null)
                {

                }
            }
            break;
        }
        setStage(getStage()+1);
    }
}
