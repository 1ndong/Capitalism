package com.indong.capitalism.Frame;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CCompCellPanel extends JPanel {
	 private static final long serialVersionUID = 1L;
	    private JLabel name = new JLabel();
	    private JLabel balance = new JLabel();
	    private JButton detailButton = new JButton("detail");

	   public CCompCellPanel(String name , int balance) {
	        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	        this.name.setText(name);
	        this.balance.setText(String.valueOf(balance));
	        add(this.name);
	        add(this.balance);
	        add(detailButton);
	    }

	    public void setComp(CTableComp comp) {
	        name.setText(comp.name);
	        balance.setText(String.valueOf(comp.balance));
	    }

	    public CTableComp getComp() {
	        return new CTableComp(name.getText(), Integer.parseInt(balance.getText()));
	    }
}
