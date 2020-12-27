package com.indong.capitalism.Item;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Enum.EAccountType;

public class ItemAccount {
	private int accountNumber = 0;
	private EAccountType accountType;
	private CBCommercial bank;
	private int rightsOfCash = 0;
	
	public ItemAccount(CBCommercial bank , int accountNumber , EAccountType type)
	{
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
	}
	
	public CBCommercial getBank()
	{
		return bank;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public EAccountType getAccountType()
	{
		return accountType;
	}

	public int getRightsOfCash() {
		return rightsOfCash;
	}

	public void addRightsOfCash(int rightsOfCash) {
		this.rightsOfCash += rightsOfCash;
	}
}
