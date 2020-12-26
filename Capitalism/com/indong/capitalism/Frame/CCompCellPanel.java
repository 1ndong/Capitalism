package com.indong.capitalism.Frame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CCompCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel name = new JLabel();
	private JLabel balance = new JLabel();
	private JButton detailButton = new JButton("detail");

	public CCompCellPanel(String name, int balance) {
		setLayout(new GridLayout(1,3));
		this.name.setText("name = "+name);
		this.balance.setText("balance = "+balance);
		detailButton.addActionListener(new CDetailButtonClickListener());
		add(this.name);
		add(this.balance);
		add(detailButton);
	}

	public void setComp(CTableComp comp) {
		name.setText("name = "+comp.name);
		balance.setText("balance = "+comp.balance);
	}

	public CTableComp getComp() {
		String balancetext = balance.getText();
		int lastspace = balancetext.lastIndexOf(" ");
		int onlybalance = Integer.parseInt(balancetext.substring(lastspace+1));
		return new CTableComp(name.getText(), onlybalance);
	}
}
