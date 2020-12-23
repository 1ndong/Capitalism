package com.indong.capitalism.Processor;

import com.indong.capitalism.Frame.FrameLog;

public class ProcessorDay {
	private int[] LASTDAY= {31,29,31,30,31,30,31,31,30,31,30,31};
	private int INIT_DAY = 23;
	private int INIT_MONTH = 12;
	private int INIT_YEAR = 2020;
	
	private int m_Day = 0;
	private int m_Month = 0;
	private int m_Year = 0;
	
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
	}
	
	public void Process()
	{
		FrameLog.GetInstance().addLog("DayProcessor,Process", "today is " + m_Year + "/" + m_Month +"/" + m_Day);
		
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
	}
	
	public String getDate()
	{
		return ""+m_Year+m_Month+m_Day;
	}
}
