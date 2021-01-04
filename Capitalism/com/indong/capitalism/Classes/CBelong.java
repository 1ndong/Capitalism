package com.indong.capitalism.Classes;

import com.indong.capitalism.Info.InfoBasicData;

public class CBelong {
	private CCountry myCountry;
	protected InfoBasicData basicData;
	
	public CBelong(CCountry country)
	{
		myCountry = country;
	}

	public CCountry getMyCountry() {
		return myCountry;
	}

	public InfoBasicData getBasicData() {
		return basicData;
	}
}
