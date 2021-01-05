package com.indong.capitalism.Info;

import com.indong.capitalism.Classes.CBank;
import com.indong.capitalism.Enum.EAccountType;

public class IAAccount {
	private String ownerName;
	private CBank bank;
	private int accountNumber;
	private EAccountType accountType;
	
	public IAAccount(String name , CBank bank , int accountNumber , EAccountType type)
	{
		this.ownerName = name;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
	}
	
	public CBank getBank() {
		return bank;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public EAccountType getAccountType() {
		return accountType;
	}

	public String getOwnerName() {
		return ownerName;
	}
}
