package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Frame.FrameLog;

public class ProcessorMain {
	public ProcessorMain()
	{
		FrameLog.getInstance().addLog("tag", "시작");
		
		start();
	}
	
	private void start()
	{
		CPeople aa = new CPeople(new DTime(0,0,0,""),"name");
	}
}
