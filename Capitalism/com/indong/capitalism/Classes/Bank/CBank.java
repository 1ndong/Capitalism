package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.Classes.CBelong;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearch;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBank extends CBelong implements ITime , ISearch {
	protected CACCash balance = new CACCash();
	protected String name;
	
	public CBank(CCountry country , String name)
	{
		super(country);
		this.name = name;
		
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
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

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Bank;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}
}
