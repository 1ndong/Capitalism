package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.DataStructure.DCareTaker;
import com.indong.capitalism.DataStructure.DHCentralBank;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;
import com.indong.capitalism.Processor.ProcessorDay;
import com.indong.capitalism.Util.UCurrency;

import java.util.LinkedList;

public class CBCentral extends CBank implements IInterestChanger{
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private float baseInterestRate = 0.5f;
	private DCareTaker careTaker = new DCareTaker();
	private LinkedList<IInterestRate> ibankList = new LinkedList<IInterestRate>();
	private CBAccount nationalAccount = new CBAccount("국고",this,1111, EAccountType.NationalAccount,0.0f,new DTime(0,0,0));
	
	public CBCentral(CCountry country , String name)
	{
		super(country , name);
	}
	
	public void makeMoney(long amount)
	{
		balance.addCash(amount);
		FrameLog.getInstance().addLog("makeMoney", "중앙은행 현금 발행 " + UCurrency.getInstance().toString(amount, ECurrency.Won));
		
		DHCentralBank date = new DHCentralBank(ProcessorDay.GetInstance().getToday(), amount);
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
		FrameLog.getInstance().addLog("releaseMoney", "중앙은행에서 " + bank.getName() + "은행으로 " + UCurrency.getInstance().toString(amount,ECurrency.Won) + " 지급");
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

	public CBAccount getNationalAccount() {
		return nationalAccount;
	}

	public void setNationalAccount(CBAccount nationalAccount) {
		this.nationalAccount = nationalAccount;
	}
}
