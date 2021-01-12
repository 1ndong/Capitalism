package com.indong.capitalism.Frame.CustomTable;

import com.indong.capitalism.CustomLayout.WrapLayout;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Util.UCurrency;

import javax.swing.*;

public class CMainCompCellPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel namelabel = new JLabel("이름:");
	private JLabel name = new JLabel();
	private JLabel allAssetLabel = new JLabel("총자산:");
	private JLabel allAsset = new JLabel();
	private JLabel depositLabel = new JLabel("예금:");
	private JLabel deposit = new JLabel();
	private JLabel cashLabel = new JLabel("현금:");
	private JLabel cash = new JLabel();
	private JLabel loanLabel = new JLabel("대출:");
	private JLabel loan = new JLabel();
	
	private JButton detailButton = new JButton("...");

	public CMainCompCellPanel(String name, long allAsset , long deposit , long cash , long loan) {
		//setLayout(new GridLayout(1,11));
		setLayout(new WrapLayout());
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
		allAsset.setText(UCurrency.getInstance().toString(comp.allAsset,ECurrency.Won));
		deposit.setText(UCurrency.getInstance().toString(comp.deposit,ECurrency.Won));
		cash.setText(UCurrency.getInstance().toString(comp.cash,ECurrency.Won));
		loan.setText(UCurrency.getInstance().toString(comp.loan,ECurrency.Won));
	}

	public CMainTableComp getComp() {
		return new CMainTableComp
				(name.getText(),
					UCurrency.getInstance().toOriginValue(allAsset.getText(),ECurrency.Won),
					UCurrency.getInstance().toOriginValue(deposit.getText(),ECurrency.Won),
					UCurrency.getInstance().toOriginValue(cash.getText(),ECurrency.Won),
					UCurrency.getInstance().toOriginValue(loan.getText(),ECurrency.Won)
				);
	}
}
