package com.indong.capitalism.Frame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CCompCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel namelabel = new JLabel("name = ");
	private JLabel name = new JLabel();
	private JLabel balancelabel = new JLabel("balance = ");
	private JLabel balance = new JLabel();
	private JButton detailButton = new JButton("detail");

	public CCompCellPanel(String name, int balance) {
		setLayout(new GridLayout(1,3));
		this.name.setText(name);
		this.balance.setText(String.valueOf(balance));
		detailButton.addActionListener(new CDetailButtonClickListener());
		add(this.namelabel);
		add(this.name);
		add(this.balancelabel);
		add(this.balance);
		add(detailButton);
	}

	public void setComp(CTableComp comp) {
		name.setText(comp.name);
		balance.setText(""+comp.balance);
	}

	public CTableComp getComp() {
		return new CTableComp(name.getText(), Integer.parseInt(balance.getText()));
	}
}
