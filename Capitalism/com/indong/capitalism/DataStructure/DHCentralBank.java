package com.indong.capitalism.DataStructure;

public class DHCentralBank extends DHistory {
	private int makeMoneyAmount;
	
	public DHCentralBank(DTime time , int money) {
		super(time);
		// TODO Auto-generated constructor stub
		makeMoneyAmount = money;
	}

	public int getMakeMoneyAmount() {
		return makeMoneyAmount;
	}
}
