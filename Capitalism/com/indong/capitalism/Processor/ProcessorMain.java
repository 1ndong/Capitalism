package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IBankService;

public class ProcessorMain {
	public ProcessorMain()
	{
		FrameLog.getInstance().addLog("tag", "시작");
		
		start();
	}
	
	private void start()
	{
		//sample initialize code
		CCountry rok = new CCountry("대한민국");
		CWorld.getInstance().getCountryList().add(rok);
		
		CGMinistryOfHealthAndWelfare gmohaw = new CGMinistryOfHealthAndWelfare(rok);
		CGMinistryOfTradeIndustryAndEnergy gmotiae = new CGMinistryOfTradeIndustryAndEnergy(rok);
		rok.getGovernmentMap().put(EGovernmentType.EHealthAndWelfare , gmohaw);
		rok.getGovernmentMap().put(EGovernmentType.ETradeIndustryAndEnergy, gmotiae);
		
		CBCommercial shinhanbank = new CBCommercial(rok , "신한");
		CBCommercial kookminbank = new CBCommercial(rok , "국민");
		CBCommercial wooribank = new CBCommercial(rok , "우리");
		rok.getBankList().add(shinhanbank);
		rok.getBankList().add(kookminbank);
		rok.getBankList().add(wooribank);
		
		rok.getCentralBank().makeMoney(1000000000);//10억
		rok.getCentralBank().releaseMoney(shinhanbank, 100000000);
		rok.getCentralBank().releaseMoney(kookminbank, 100000000);
		rok.getCentralBank().releaseMoney(wooribank, 100000000);
		
		CCompany samsung = new CCompany(rok,new DTime(1955,2,12,""),"삼성");
		CCompany lg = new CCompany(rok,new DTime(1958,3,21,""),"LG");
		
		gmotiae.registerCompany(samsung);
		gmotiae.registerCompany(lg);
		
		CPeople indong = new CPeople(rok, new DTime(1987,2,12,""), "김인동");
		CPeople ronaldo = new CPeople(rok , new DTime(1985,6,20,"") , "호나우도");
		CPeople jasonkim = new CPeople(rok, new DTime(1985,10,20,""),"제이슨킴");
		gmohaw.registerPeople(indong);
		gmohaw.registerPeople(ronaldo);
		gmohaw.registerPeople(jasonkim);
		
		//////////////////////////////
		
		IBankService shbankservice = (IBankService)shinhanbank;
		shbankservice.raiseLoan(samsung, 50000000);
	}
}
