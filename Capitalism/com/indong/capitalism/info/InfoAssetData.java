package com.indong.capitalism.info;

import java.util.HashMap;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Enum.EAccount;
import com.indong.capitalism.Item.ItemAccount;

public class InfoAssetData {
	private CACCash m_Cash = new CACCash();
	private HashMap<EAccount , ItemAccount> m_Account = new HashMap<EAccount,ItemAccount>();
	
	public InfoAssetData()
	{
		
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

	public CACCash getCash() {
		return m_Cash;
	}
}
