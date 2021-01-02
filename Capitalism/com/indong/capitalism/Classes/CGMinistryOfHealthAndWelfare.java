package com.indong.capitalism.Classes;

import java.util.HashMap;

import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Frame.FrameMain;

public class CGMinistryOfHealthAndWelfare extends CGovernment 
{
	private HashMap<Integer, CPeople> peopleMap = new HashMap<Integer,CPeople>();
	
	public CGMinistryOfHealthAndWelfare(CCountry country)
	{
		super(country);
		name = "보건복지부";
	}
	
	public void registerPeople(CPeople people)
	{
		int ID = makeUniqueID();
		people.getPersonaldata().setID(ID);
		peopleMap.put(ID,people);
		FrameLog.getInstance().addLog("registerPeople", "신규등록 완료 이름 = "+people.getPersonaldata().getName());
		FrameMain.getInstance().addPeople(people);
	}
	
	private int makeUniqueID()
	{
		int result = -1;
		
		int tempKey = 0;
		while(result == -1)
		{
			CPeople isExist = peopleMap.get(tempKey);
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

	public HashMap<Integer, CPeople> getPeopleMap() {
		return peopleMap;
	}
}
