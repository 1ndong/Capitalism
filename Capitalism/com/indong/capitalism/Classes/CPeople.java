package com.indong.capitalism.Classes;

import com.indong.capitalism.Interface.EAccount;
import com.indong.capitalism.Interface.ICompanyMember;
import com.indong.capitalism.Item.ItemAccount;
import com.indong.capitalism.info.InfoAssetData;
import com.indong.capitalism.info.InfoPersonalData;

public class CPeople implements ICompanyMember 
{
	private InfoAssetData m_InfoAssetData;
	private InfoPersonalData m_InfoPersonalData;
	
	public CPeople(int age , String name)
	{
		m_InfoPersonalData = new InfoPersonalData(age, name);
		m_InfoAssetData = new InfoAssetData();
	}

	public InfoAssetData getInfoAssetData() {
		return m_InfoAssetData;
	}

	public InfoPersonalData getInfoPersonalData() {
		return m_InfoPersonalData;
	}

	@Override
	public void receive(int monthlySalary) {
		// TODO Auto-generated method stub
		ItemAccount account = getInfoAssetData().getAccount(EAccount.Salary);
		if(account != null)
		{
			account.depositCash(monthlySalary);
		}
	}
}
