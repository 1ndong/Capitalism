package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CGovernment extends CBelong implements ITime{
	private String name;
	
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
	ministry of health and welfare 보건복지부
	ministry of trade industry and energy 산업통산자원부
}
