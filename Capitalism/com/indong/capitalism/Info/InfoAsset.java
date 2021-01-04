package com.indong.capitalism.Info;

import java.util.LinkedList;

public class InfoAsset {
	private LinkedList<IAAccount> accountList = new LinkedList<IAAccount>();

	public LinkedList<IAAccount> getAccountList() {
		return accountList;
	}

	public void addNewBankInfo(IAAccount newaccount) {
		this.accountList.add(newaccount);
	}
}
