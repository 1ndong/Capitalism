package com.indong.capitalism.Classes;

import java.util.HashMap;

import com.indong.capitalism.Frame.FrameLog;

public class CGovernmentHumanResource extends CGovernment 
{
	HashMap<Integer, CPeople> m_PeopleMap = new HashMap<Integer,CPeople>();
	
	public CGovernmentHumanResource(CCountry country)
	{
		super(country);
	}
	
	public void registerPeople(CPeople people)
	{
		int ID = makeUniqueID();
		people.getPersonaldata().setID(ID);
		m_PeopleMap.put(ID,people);
		FrameLog.getInstance().addLog("registerPeople", "신규등록 완료 이름 = "+people.getPersonaldata().getName());
	}
	
	private int makeUniqueID()
	{
		int result = -1;
		
		int tempKey = 0;
		while(result == -1)
		{
			CPeople isExist = m_PeopleMap.get(tempKey);
			if(isExist == null)
			{
				result = tempKey;
			}
			else
			{
				tempKey++;
			}
		}
		
		return result;
	}
}
