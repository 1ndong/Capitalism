package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Info.InfoPersonalData;
import com.indong.capitalism.Interface.ISearch;

public class CPeople extends CBeing implements ISearch
{
	public CPeople(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Personal;
		basicData = new InfoPersonalData(birth , name);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		repaymentDebt(newTime);
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.People;
	}
}
