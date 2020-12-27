package com.indong.capitalism.Processor;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;

public class ProcessorDay implements ITimeKeeper{
	private LinkedList<ITime> timeSlaveList = new LinkedList<ITime>();
	
	private int[] LASTDAY= {31,29,31,30,31,30,31,31,30,31,30,31};
	private String[] DAYOFTHEWEEK = {"월","화","수","목","금","토","일"};
	
	private int INIT_DAY = 23;
	private int INIT_MONTH = 12;
	private int INIT_YEAR = 2020;
	private int INIT_INDEX = 2;
	private String INIT_DAYOFTHEWEEK = DAYOFTHEWEEK[INIT_INDEX];
	
	private int m_Day = 0;
	private int m_Month = 0;
	private int m_Year = 0;
	private String m_Dayoftheweek = "";
	private int m_Index = 0;
	
	private static ProcessorDay instance = new ProcessorDay();

	public static ProcessorDay GetInstance()
	{
		return instance;
	}
	
	private ProcessorDay()
	{
		m_Day = INIT_DAY;
		m_Month = INIT_MONTH;
		m_Year = INIT_YEAR;
		m_Dayoftheweek = INIT_DAYOFTHEWEEK;
		m_Index = INIT_INDEX;
	}
	
	public void Process()
	{
		FrameLog.getInstance().addLog("DayProcessor,Process"
				, "today is " + m_Year + "/" + m_Month +"/" + m_Day + " , " + m_Dayoftheweek + "요일");
		
		m_Day++;
		if(m_Day > LASTDAY[m_Month-1])
		{
			m_Day = 1;
			m_Month++;
			if(m_Month > 12)
			{
				m_Month = 1;
				m_Year++;
				if(m_Year%4 == 0)
				{
					LASTDAY[1] = 29;//leap year
				}
				else
				{
					LASTDAY[1] = 28;
				}
			}
		}
		m_Index++;
		m_Dayoftheweek = DAYOFTHEWEEK[m_Index%7];
		
		updateTime();
	}
	
	public String getDate()
	{
		return ""+m_Year+m_Month+m_Day+m_Dayoftheweek;
	}

	@Override
	public void addTimeSlave(ITime newslave) {
		// TODO Auto-generated method stub
		timeSlaveList.add(newslave);
	}

	@Override
	public void updateTime() {
		// TODO Auto-generated method stub
		DTime newTime = new DTime(m_Year,m_Month,m_Day,m_Dayoftheweek);
		for(int i = 0 ; i < timeSlaveList.size() ; i++)
		{
			ITime temp = timeSlaveList.get(i);
			temp.dayChange(newTime);
		}
	}
}
