package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBeing extends CBelong implements ITime{
	protected EBeingType type;

	public CBeing(CCountry country)
	{
		super(country);
		ITimeKeeper timekeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
	}

	public EBeingType getType() {
		return type;
	}
}
