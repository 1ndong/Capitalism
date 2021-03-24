package com.indong.capitalism.UI.CustomPanel.ControlPanel;

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
