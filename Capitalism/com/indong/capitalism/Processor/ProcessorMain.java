package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Frame.FrameLog;

public class ProcessorMain {
	public ProcessorMain()
	{
		FrameLog.getInstance().addLog("tag", "시작");
		
		start();
	}
	
	private void start()
	{
		CCountry rok = new CCountry("대한민국");
		CWorld.getInstance().getCountryList().add(rok);
		
		CBCommercial shinhanbank = new CBCommercial("신한");
		CBCommercial kookminbank = new CBCommercial("국민");
		CBCommercial wooribank = new CBCommercial("우리");
		rok.getBankList().add(shinhanbank);
		rok.getBankList().add(kookminbank);
		rok.getBankList().add(wooribank);
	}
}
