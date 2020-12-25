package com.indong.capitalism.Item;

import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.EBank;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Frame.FrameLog;

public class ItemAccount {
	//계좌는 은행으로 들어가는 통로이지 돈이 보관되는곳이 아님 캐쉬 현물이 보관되는곳은 내주머니 아니면 은행임
	private int accountNumber = 0;
	private EAccountType accountType;
	private EBeingType type;
	private EBank bank;
	
	
	public ItemAccount(EBank bank , int accountNumber)
	{
		this.bank = bank;
		this.accountNumber = accountNumber;
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
