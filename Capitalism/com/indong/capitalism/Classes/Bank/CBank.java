package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.Classes.CBelong;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBank extends CBelong implements ITime{
	protected CACCash balance = new CACCash();
	protected String name;
	
	public CBank(CCountry country , String name)
	{
		super(country);
		this.name = name;
		
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);
	}
	
	public String getName()
	{
		return name;
	}
	
	public CACCash getBalance()
	{
		return balance;
	}
	
	protected void addBalance(long cash)
	{
		balance.addCash(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}
}
