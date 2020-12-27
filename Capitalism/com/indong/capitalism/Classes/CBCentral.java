package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.Frame.FrameLog;

public class CBCentral extends CBank{
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private float baseInterestRate = 0.5f;
	
	public CBCentral()
	{
		
	}
	
	public void makeMoney(int amount)
	{
		balance.addBalance(amount);
	}
	
	public void releaseMoney(CBank bank , int amount)
	{
		if(amount > balance.getBalance())
		{
			FrameLog.getInstance().addLog("centralbank", "잔액 부족");
			return;
		}
		bank.addBalance(amount);
		balance.addBalance(-amount);
	}
	
	public void addNewBank(CBank newbank)
	{
		bankList.add(newbank);
	}
	
	public float getBaseInterestRate()
	{
		return baseInterestRate;
	}
}
