package com.indong.capitalism.CustomComponent;

import javax.swing.*;
import java.awt.*;

public class AutoResizeTextField extends JTextField {

    public AutoResizeTextField(String initString) {
        setHorizontalAlignment(SwingConstants.CENTER);
        setText(initString);
        resize();
    }

    void resize() {
        int i=0;
        while(true) {
            Font before = getFont();
            Font font = new Font(before.getName(), before.getStyle(), i);
            setFont(font);
            if(getPreferredSize().getWidth()>getWidth() || getPreferredSize().getHeight()>getHeight()) {
                font = new Font(before.getName(), before.getStyle(), i-1);
                setFont(font);
                break;
            }
            i++;
        }
    }
}
