package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DCareTaker;
import com.indong.capitalism.DataStructure.DHCentralBank;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBCentral extends CBank{
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private float baseInterestRate = 0.5f;
	private DCareTaker careTaker = new DCareTaker();
	
	public CBCentral(CCountry country , String name)
	{
		super(country , name);
	}
	
	public void makeMoney(int amount)
	{
		balance.addBalance(amount);
		FrameLog.getInstance().addLog("makeMoney", "중앙은행 현금 발행 [" + amount + "]만원");
		
		DHCentralBank date = new DHCentralBank(ProcessorDay.GetInstance().getDate(), amount);
		careTaker.addMemento(date);
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

	public float getBaseInterestRate() {
		return baseInterestRate;
	}

	public void setBaseInterestRate(float baseInterestRate) {
		this.baseInterestRate = baseInterestRate;
	}

	public DCareTaker getCareTaker() {
		return careTaker;
	}
}
