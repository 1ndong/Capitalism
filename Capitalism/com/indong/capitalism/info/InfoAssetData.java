package com.indong.capitalism.info;

import java.util.HashMap;

import com.indong.capitalism.Interface.EAccount;
import com.indong.capitalism.Item.ItemAccount;

public class InfoAssetData {
	private int m_Cash = 0;
	private HashMap<EAccount , ItemAccount> m_Account = new HashMap<EAccount,ItemAccount>();
	//통장이름 , 계좌
	private int m_Debt = 0;
	
	public InfoAssetData()
	{
		
	}

	public int getCash() {
		return m_Cash;
	}

	public void setCash(int cash) {
		this.m_Cash = cash;
	}

	public int getDebt() {
		return m_Debt;
	}

	public void setDebt(int debt) {
		this.m_Debt = debt;
	}
	
	public void addNewAccount(EAccount type , ItemAccount account)
	{
		m_Account.put(type, account);
	}
	
	public ItemAccount getAccount(EAccount account)
	{
		ItemAccount result = m_Account.get(account);
		return result;
	}
}
