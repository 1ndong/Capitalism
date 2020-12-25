package com.indong.capitalism.Classes;

import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoPersonalData;

public class CPeople 
{
	private InfoPersonalData m_InfoPersonalData;
	private CACCash cash;
	private EBeingType type = EBeingType.Personal;
	
	public CPeople(int age , String name)
	{
		m_InfoPersonalData = new InfoPersonalData(age, name);
		cash = new CACCash();
	}

	public InfoPersonalData getInfoPersonalData() {
		return m_InfoPersonalData;
	}

	public CACCash getCash()
	{
		return cash;
	}
	
	public EBeingType getType()
	{
		return type;
	}
}
