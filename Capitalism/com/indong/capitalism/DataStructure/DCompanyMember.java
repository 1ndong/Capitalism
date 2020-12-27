package com.indong.capitalism.DataStructure;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Item.ItemAccount;

public class DCompanyMember {
	private CPeople member;
	private ItemAccount salaryAccount;
	
	public DCompanyMember(CPeople people , ItemAccount account)
	{
		this.member = people;
		this.salaryAccount = account;
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
}
