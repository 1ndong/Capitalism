package com.indong.capitalism.Item;

import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.EBank;

public class ItemAccount {
	private int balance = 0;
	private EBank bank;
	private int accountNumber = 0;
	
	public ItemAccount(EBank bank , int accountNumber)
	{
		this.bank = bank;
		this.accountNumber = accountNumber;
	}
	
	public void depositCash(int cash)
	{
		balance += cash;
	}
	
	public int withdrawCash(int cash)
	{
		if(balance < cash)
		{
			FrameLog.GetInstance().addLog("account", "잔액부족");
			return 0;
		}
		
		balance -= cash;
		return cash;//인출한 금액
	}
	
	public int getBalance()
	{
		return balance;
	}
	
	public EBank getBankType()
	{
		return bank;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
}
