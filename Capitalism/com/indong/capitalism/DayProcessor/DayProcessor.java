package com.indong.capitalism.DayProcessor;

import com.indong.capitalism.Frame.LogFrame;

public class DayProcessor {
	private int[] LASTDAY= {31,29,31,30,31,30,31,31,30,31,30,31};
	private int INIT_DAY = 23;
	private int INIT_MONTH = 12;
	private int INIT_YEAR = 2020;
	
	private int m_Day = 0;
	private int m_Month = 0;
	private int m_Year = 0;
	
	private static DayProcessor instance = new DayProcessor();

	public static DayProcessor GetInstance()
	{
		return instance;
	}
	
	private DayProcessor()
	{
		m_Day = INIT_DAY;
		m_Month = INIT_MONTH;
		m_Year = INIT_YEAR;
	}
	
	public void Process()
	{
		LogFrame.GetInstance().addLog("DayProcessor,Process", "today is " + m_Year + "/" + m_Month +"/" + m_Day);
		
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
}
