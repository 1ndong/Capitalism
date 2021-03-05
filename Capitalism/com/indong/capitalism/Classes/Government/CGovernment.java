package com.indong.capitalism.Classes.Government;

import com.indong.capitalism.Classes.CBelong;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearch;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CGovernment extends CBelong implements ITime , ISearch {
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

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Government;
	}
}
