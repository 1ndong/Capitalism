package com.indong.capitalism.Frame.CustomTable;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CMainCompCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel namelabel = new JLabel("이름 = ");
	private JLabel name = new JLabel();
	private JLabel allAssetLabel = new JLabel("총자산 = ");
	private JLabel allAsset = new JLabel();
	private JLabel depositLabel = new JLabel("예금 = ");
	private JLabel deposit = new JLabel();
	private JLabel cashLabel = new JLabel("현금 = ");
	private JLabel cash = new JLabel();
	private JLabel loanLabel = new JLabel("대출 = ");
	private JLabel loan = new JLabel();
	
	private JButton detailButton = new JButton("detail");

	public CMainCompCellPanel(String name, long allAsset , long deposit , long cash , long loan) {
		setLayout(new GridLayout(1,11));
		this.name.setText(name);
		this.allAsset.setText(String.valueOf(allAsset));
		this.deposit.setText(String.valueOf(deposit));
		this.cash.setText(String.valueOf(cash));
		this.loan.setText(String.valueOf(loan));
		detailButton.addActionListener(new CMainDetailButtonClickListener());
		add(this.namelabel);
		add(this.name);
		add(this.allAssetLabel);
		add(this.allAsset);
		add(this.depositLabel);
		add(this.deposit);
		add(this.cashLabel);
		add(this.cash);
		add(this.loanLabel);
		add(this.loan);
		add(detailButton);
	}

	public void setComp(CMainTableComp comp) {
		name.setText(comp.name);
		allAsset.setText(""+comp.allAsset);
		deposit.setText(""+comp.deposit);
		cash.setText(""+comp.cash);
		loan.setText(""+comp.loan);
	}

	public CMainTableComp getComp() {
		return new CMainTableComp(name.getText(), Long.parseLong(allAsset.getText()),Long.parseLong(deposit.getText())
				,Long.parseLong(cash.getText()),Long.parseLong(loan.getText()));
	}
}
