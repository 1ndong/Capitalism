package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.UI.FrameLog;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CWorld implements ITime , ISearchable {
	private LinkedList<CCountry> countryList = new LinkedList<CCountry>();
	private static CWorld instance = new CWorld();

	protected ESearchType searchType = ESearchType.World;
	private CWorld()
	{
		FrameLog.getInstance().addLog("creation", "world 생성");
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}
	
	public static CWorld getInstance()
	{
		return instance;
	}

	public LinkedList<CCountry> getCountryList() {
		return countryList;
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.World;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	@Override
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getSearchableOriginName() {
		return "the earth";
	}
}
