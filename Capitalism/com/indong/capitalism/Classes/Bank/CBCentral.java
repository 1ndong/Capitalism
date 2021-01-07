package com.indong.capitalism.Classes.Bank;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.DataStructure.DCareTaker;
import com.indong.capitalism.DataStructure.DHCentralBank;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBCentral extends CBank implements IInterestChanger{
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private float baseInterestRate = 0.5f;
	private DCareTaker careTaker = new DCareTaker();
	private LinkedList<IInterestRate> ibankList = new LinkedList<IInterestRate>();
	
	public CBCentral(CCountry country , String name)
	{
		super(country , name);
	}
	
	public void makeMoney(long amount)
	{
		balance.addCash(amount);
		FrameLog.getInstance().addLog("makeMoney", "중앙은행 현금 발행 [" + amount + "]원");
		
		DHCentralBank date = new DHCentralBank(ProcessorDay.GetInstance().getDate(), amount);
		careTaker.addMemento(date);
	}
	
	public void releaseMoney(CBank bank , long amount)
	{
		if(amount > balance.getCash())
		{
			FrameLog.getInstance().addLog("centralbank", "잔액 부족");
			return;
		}
		bank.addBalance(amount);
		balance.addCash(-amount);
		FrameLog.getInstance().addLog("releaseMoney", "중앙은행에서 " + bank.getName() + "은행으로 " + amount + "원 지급");
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
		FrameLog.getInstance().addLog("setbaseinterestrate", "기준금리 변동 : " + baseInterestRate + "%");
		interestChange();
	}

	public DCareTaker getCareTaker() {
		return careTaker;
	}

	@Override
	public void addAccount(IInterestRate account) {
		// TODO Auto-generated method stub
		ibankList.add(account);//account is bank
	}

	@Override
	public void interestChange() {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < ibankList.size() ; i++)
		{
			ibankList.get(i).interestChange(getBaseInterestRate());
		}
	}
}
