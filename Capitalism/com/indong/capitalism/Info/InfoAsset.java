package com.indong.capitalism.Info;

import java.util.LinkedList;

import com.indong.capitalism.Classes.Bank.CBAccount;

public class InfoAsset {
	private LinkedList<IAAccount> accountList = new LinkedList<IAAccount>();

	public LinkedList<IAAccount> getAccountList() {
		return accountList;
	}

	public void addNewBankInfo(IAAccount newaccount) {
		this.accountList.add(newaccount);
	}
	
	public IAAccount findAccountInfo(CBAccount account)
	{
		IAAccount result = null;
		
		for(int i = 0 ; i < accountList.size() ; i++)
		{
			IAAccount temp = accountList.get(i);
			if(temp.getAccountNumber() == account.getAccountNumber())
			{
				if(temp.getBank() == account.getBank())
				{
					if(temp.getAccountType() == account.getAccountType())
					{
						result = temp;
						break;
					}
				}
			}
		}
		
		return result;
	}
}
