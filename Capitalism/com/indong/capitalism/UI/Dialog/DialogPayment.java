package com.indong.capitalism.UI.Dialog;

import com.indong.capitalism.UI.CustomPanel.ControlPanel.ControlPanel;
import com.indong.capitalism.UI.CustomPanel.ControlPanel.SubmitClickListener;

import javax.swing.*;

public class DialogPayment extends JDialog {

    private SubmitClickListener parent;

    public DialogPayment(SubmitClickListener parent)
    {
        setModal(true);
        this.parent = parent;

        setLayout(null);
        setSize(500,500);
        setVisible(true);
    }
}
