package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CBeing extends CBelong implements ITime{
	protected EBeingType type;
	protected InfoBasicData basicData;
	private CACCash wallet;
	
	public CBeing(CCountry country)
	{
		super(country);
		wallet = new CACCash();
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
	
	public InfoBasicData getBasicData() {
		return basicData;
	}
	
	public CACCash getCash() {
		return wallet;
	}

	public void addCash(int cash) {
		this.wallet.addCash(cash);
	}
}
