package com.indong.capitalism.DataStructure;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Item.ItemAccount;

public class DCompanyMember {
	private CPeople member;
	private ItemAccount salaryAccount;
	private ECompanyPosition companyPosition;
	
	public DCompanyMember(CPeople people , ItemAccount account , ECompanyPosition position)
	{
		this.member = people;
		this.salaryAccount = account;
		this.companyPosition = position;
	}
	
	public CPeople getMember() {
		return member;
	}
	public void setMember(CPeople member) {
		this.member = member;
	}
	
	public ItemAccount getSalaryAccount() {
		return salaryAccount;
	}
	public void setSalaryAccount(ItemAccount salaryAccount) {
		this.salaryAccount = salaryAccount;
	}

	public ECompanyPosition getCompanyPosition() {
		return companyPosition;
	}

	public void setCompanyPosition(ECompanyPosition companyPosition) {
		this.companyPosition = companyPosition;
	}
}
