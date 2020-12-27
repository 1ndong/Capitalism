package com.indong.capitalism.Classes;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Item.ItemAccount;

public class CCompanySamsung extends CCompany{
	private ItemAccount salaryPayingAccount;
	
	public CCompanySamsung(DTime birth , String name)
	{
		super(birth, name);
		salaryDay = 25;
	}

}
