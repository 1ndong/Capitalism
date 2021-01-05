package com.indong.capitalism.DataStructure;

public class DHCentralBank extends DHistory {
	private long makeMoneyAmount;
	
	public DHCentralBank(DTime time , long amount) {
		super(time);
		// TODO Auto-generated constructor stub
		makeMoneyAmount = amount;
	}

	public long getMakeMoneyAmount() {
		return makeMoneyAmount;
	}
}
