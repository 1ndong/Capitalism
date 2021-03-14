package com.indong.capitalism.Classes.Government;

import com.indong.capitalism.Classes.CBelong;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CGovernment extends CBelong implements ITime , ISearchable {
	protected String name;

	protected ESearchType searchType = ESearchType.Government;
	public CGovernment(CCountry country)
	{
		super(country);
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
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

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	@Override
	public ESearchType getSearchType() {
		return searchType;
	}
}
