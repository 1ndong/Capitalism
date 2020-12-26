package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoPersonalData;

public class CPeople 
{
	private InfoBasicData personaldata;
	private CACCash cash;
	private final EBeingType type = EBeingType.Personal;
	
	public CPeople(DTime birth , String name)
	{
		cash = new CACCash();
		personaldata = new InfoPersonalData(birth , name);
	}

	public InfoBasicData getPersonaldata() {
		return personaldata;
	}
}
