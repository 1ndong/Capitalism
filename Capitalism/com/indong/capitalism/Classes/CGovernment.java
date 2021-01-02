package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CGovernment extends CBelong implements ITime{
	protected String name;
	
	public CGovernment(CCountry country)
	{
		super(country);
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}
}
