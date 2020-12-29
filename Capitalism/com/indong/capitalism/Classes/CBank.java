package com.indong.capitalism.Classes;

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
	
	protected CACCash getBalance()
	{
		return balance;
	}
	
	protected void addBalance(int cash)
	{
		balance.addBalance(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}
}
