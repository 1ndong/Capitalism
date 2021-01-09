package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.Bank.CBCommercial;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Info.IAAccount;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ICompanyService;

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
		shinhanbank.setSpreadInterestRate(2.1f);
		rok.getBankList().add(kookminbank);
		kookminbank.setSpreadInterestRate(2.4f);
		rok.getBankList().add(wooribank);
		wooribank.setSpreadInterestRate(2.2f);
		
		rok.getCentralBank().makeMoney(1000000000000L);//1조
		rok.getCentralBank().releaseMoney(shinhanbank, 100000000000L);//1000억
		rok.getCentralBank().releaseMoney(kookminbank, 100000000000L);
		rok.getCentralBank().releaseMoney(wooribank, 100000000000L);
		
		CCompany samsung = new CCompany(rok,new DTime(1955,2,12,""),"삼성");
		CCompany lg = new CCompany(rok,new DTime(1958,3,21,""),"LG");
		
		gmotiae.registerCompany(samsung);
		samsung.setSalaryDay(25);
		gmotiae.registerCompany(lg);
		lg.setSalaryDay(5);
		
		CPeople indong = new CPeople(rok, new DTime(1987,2,12,""), "김인동");
		CPeople ronaldo = new CPeople(rok , new DTime(1985,6,20,"") , "호나우도");
		CPeople jasonkim = new CPeople(rok, new DTime(1985,10,20,""),"제이슨킴");
		gmohaw.registerPeople(indong);
		gmohaw.registerPeople(ronaldo);
		gmohaw.registerPeople(jasonkim);
		
		//////////////////////////////
		
		DTime interestDay = new DTime(0,0,7,"");
		
		IBankService shbankservice = (IBankService)shinhanbank;
		CBAccount samsungdepositaccount = shbankservice.makeNewAccount(samsung, EAccountType.Deposit,interestDay);
		CBAccount samsungloanaccount = shbankservice.makeNewAccount(samsung, EAccountType.Loan,interestDay);
		shbankservice.raiseLoan(samsungloanaccount, 50000000000L , 120 , 7);//500억
		shbankservice.sendMoney(samsung.getBasicData().getInfoAsset().findAccountInfo(samsungloanaccount)
				, samsung.getBasicData().getInfoAsset().findAccountInfo(samsungdepositaccount), 30000000);
		
		IBankService kmbankservice = (IBankService)kookminbank;
		CBAccount lgdepositaccount = kmbankservice.makeNewAccount(lg, EAccountType.Deposit,interestDay);
		CBAccount lgloanaccount = kmbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		kmbankservice.raiseLoan(lgloanaccount, 50000000000L, 120, 7);
		kmbankservice.sendMoney(lg.getBasicData().getInfoAsset().findAccountInfo(lgloanaccount)
				, lg.getBasicData().getInfoAsset().findAccountInfo(lgdepositaccount), 20000000);
		
		IBankService wrbankservice = (IBankService)wooribank;
		CBAccount samsungwrloanaccount = wrbankservice.makeNewAccount(samsung, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(samsungwrloanaccount, 10000000000L, 120, 7);//100억
		CBAccount lgwrloanaccount = wrbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(lgwrloanaccount, 40000000000L, 120, 7);//400억
		
		ICompanyService samsungservice = (ICompanyService)samsung;
		CBAccount indongsalaryaccount = shbankservice.makeNewAccount(indong, EAccountType.Deposit,interestDay);
		IAAccount indongaccountinfo = indong.getBasicData().getInfoAsset().findAccountInfo(indongsalaryaccount);
		samsungservice.joinCompany(indong, indongaccountinfo, ECompanyPosition.Clerk);
		
		ICompanyService lgservice = (ICompanyService)lg;
		CBAccount jasonkimsalaryaccount = kmbankservice.makeNewAccount(jasonkim, EAccountType.Deposit,interestDay);
		IAAccount jasonkimaccountinfo = jasonkim.getBasicData().getInfoAsset().findAccountInfo(jasonkimsalaryaccount);
		lgservice.joinCompany(jasonkim, jasonkimaccountinfo, ECompanyPosition.Clerk);
	}
}
