package com.indong.capitalism.Frame.CustomPanel.ControlPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        if(getStage() == 0)
        {
            JComboBox<String> cb = cp.getCombobox();

            String selItem = cb.getSelectedItem().toString();
            System.out.println(selItem);
        }
    }
}
