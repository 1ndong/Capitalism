package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoPersonalData;
import com.indong.capitalism.Interface.ITime;

public class CPeople extends CBeing implements ITime
{
	private InfoBasicData personaldata;
	private CACCash cash;
	
	public CPeople(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Personal;
		cash = new CACCash();
		personaldata = new InfoPersonalData(birth , name);
	}

	public InfoBasicData getPersonaldata() {
		return personaldata;
	}

	public CACCash getCash() {
		return cash;
	}

	public void addCash(int cash) {
		this.cash.addBalance(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
	}
}
