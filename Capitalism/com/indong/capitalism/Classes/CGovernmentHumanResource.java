package com.indong.capitalism.Classes;

import java.util.HashMap;

import com.indong.capitalism.Frame.FrameLog;

public class CGovernmentHumanResource extends CGovernment 
{
	HashMap<Integer, CPeople> m_PeopleList;
	
	public CGovernmentHumanResource()
	{
		m_PeopleList = new HashMap<Integer,CPeople>();
	}
	
	public void RegisterPeople(int ID , CPeople name)
	{
		CPeople isExist = m_PeopleList.get(ID);
		if(isExist == null)
		{
			m_PeopleList.put(ID, name);	
			FrameLog.GetInstance().addLog("����", "ID�߰�����");
		}
		else
		{
			FrameLog.GetInstance().addLog("����", "�����ϴ�ID�Դϴ�");
		}
	}
}
