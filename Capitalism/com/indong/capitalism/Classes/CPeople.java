package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoPersonalData;
import com.indong.capitalism.Interface.ITime;

public class CPeople extends CBeing implements ITime
{
	private CACCash wallet;
	
	public CPeople(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Personal;
		wallet = new CACCash();
		basicData = new InfoPersonalData(birth , name);
	}

	public CACCash getCash() {
		return wallet;
	}

	public void addCash(int cash) {
		this.wallet.addBalance(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
	}
}
