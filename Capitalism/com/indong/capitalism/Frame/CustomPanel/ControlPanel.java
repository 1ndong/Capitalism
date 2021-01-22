package com.indong.capitalism.Frame.CustomPanel;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {

    public ControlPanel(Rectangle dashboardRect) {
        // TODO Auto-generated constructor stub
        this.setBounds(dashboardRect);
        this.setLayout(new GridLayout(5,3));
        this.setBackground(new Color(0,43,54));
    }

    public void setCustomLayout()
    {

    }
}
