package com.indong.capitalism.Frame.CustomPanel.ControlPanel;

import javax.naming.ldap.Control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CancelClickListener implements ActionListener {

    private ControlPanel cp;

    public CancelClickListener(ControlPanel cp)
    {
        this.cp = cp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cp.ResetPanel();
    }
}
