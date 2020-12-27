package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoPersonalData;

public class CPeople 
{
	private InfoBasicData personaldata;
	private CACCash cash;
	
	public CPeople(DTime birth , String name)
	{
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
}
