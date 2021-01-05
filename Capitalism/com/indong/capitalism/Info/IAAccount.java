package com.indong.capitalism.Info;

import com.indong.capitalism.Classes.CBank;
import com.indong.capitalism.Enum.EAccountType;

public class IAAccount {
	private CBank bank;
	private int accountNumber;
	private EAccountType accountType;
	
	public IAAccount(CBank bank , int accountNumber , EAccountType type)
	{
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
	}
	
	public CBank getBank() {
		return bank;
	}
	public void setBank(CBank bank) {
		this.bank = bank;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public EAccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(EAccountType accountType) {
		this.accountType = accountType;
	}
}
