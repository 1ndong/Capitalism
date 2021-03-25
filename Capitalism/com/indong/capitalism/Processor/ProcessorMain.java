package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.Bank.CBCommercial;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ICompanyService;
import com.indong.capitalism.Property.PAAccount;
import com.indong.capitalism.Property.PCompanyData;
import com.indong.capitalism.UI.FrameLog;

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
		rok.getGovernmentMap().put(EGovernmentType.HealthAndWelfare, gmohaw);
		rok.getGovernmentMap().put(EGovernmentType.TradeIndustryAndEnergy, gmotiae);
		
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

		//samsung
		CCompany samsung = new CCompany(rok,new DTime(1955,2,12),"삼성");
		gmotiae.registerCompany(samsung);
		samsung.setSalaryDay(25);
		//

		//lg
		CCompany lg = new CCompany(rok,new DTime(1958,3,21),"LG");
		gmotiae.registerCompany(lg);
		lg.setSalaryDay(25);
		//

		//skt
		CCompany skt = new CCompany(rok,new DTime(1970,5,4),"SK텔레콤");
		gmotiae.registerCompany(skt);
		skt.setSalaryDay(25);
		//

		//cj
		CCompany cj = new CCompany(rok,new DTime(1984,2,3),"CJ");
		gmotiae.registerCompany(cj);
		cj.setSalaryDay(25);
		//

		//giordano
		CCompany giordano = new CCompany(rok, new DTime(1988 , 6,7),"지오다노");
		gmotiae.registerCompany(giordano);
		giordano.setSalaryDay(25);
		//

		//indong
		CPeople indong = new CPeople(rok, new DTime(1987,2,12), "김인동");
		gmohaw.registerPeople(indong);
		//

		//ronaldo
		CPeople ronaldo = new CPeople(rok , new DTime(1985,6,20) , "효날도");
		gmohaw.registerPeople(ronaldo);
		//

		//jasonkim
		CPeople jasonkim = new CPeople(rok, new DTime(1985,10,20),"자손킴");
		gmohaw.registerPeople(jasonkim);
		//

		//beknim
		CPeople beknim = new CPeople(rok , new DTime(1986,2,28),"박상뱀");
		gmohaw.registerPeople(beknim);
		//

		//scwind
		CPeople scwind = new CPeople(rok , new DTime(1986,10,28),"최형택");
		gmohaw.registerPeople(scwind);
		//

		//yds0903
		CPeople yds0903 = new CPeople(rok, new DTime(1986,9,3),"윤동식디스크");
		gmohaw.registerPeople(yds0903);
		//
		//////////////////////////////
		
		DTime interestDay = new DTime(0,0,7);
		
		IBankService shbankservice = (IBankService)shinhanbank;
		CBAccount samsungdepositaccount = shbankservice.makeNewAccount(samsung, EAccountType.Deposit,interestDay);
		CBAccount samsungloanaccount = shbankservice.makeNewAccount(samsung, EAccountType.Loan,interestDay);
		shbankservice.raiseLoan(samsungloanaccount, 50000000000L , 120 , 7);//500억
		shbankservice.sendMoney(samsung.getBasicData().getPropertyAsset().findAccountInfo(samsungloanaccount)
				, samsung.getBasicData().getPropertyAsset().findAccountInfo(samsungdepositaccount), 3000000000L);
		if(samsung.getBasicData() instanceof PCompanyData)
		{
			PCompanyData cd = (PCompanyData) samsung.getBasicData();
			cd.setMainDepositAccount(samsung.getBasicData().getPropertyAsset().findAccountInfo(samsungdepositaccount));
		}

/////////////////////
		IBankService kmbankservice = (IBankService)kookminbank;
		CBAccount lgdepositaccount = kmbankservice.makeNewAccount(lg, EAccountType.Deposit,interestDay);
		CBAccount lgloanaccount = kmbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		kmbankservice.raiseLoan(lgloanaccount, 50000000000L, 120, 7);
		kmbankservice.sendMoney(lg.getBasicData().getPropertyAsset().findAccountInfo(lgloanaccount)
				, lg.getBasicData().getPropertyAsset().findAccountInfo(lgdepositaccount), 2000000000L);
		if(lg.getBasicData() instanceof PCompanyData)
		{
			PCompanyData cd = (PCompanyData) lg.getBasicData();
			cd.setMainDepositAccount(lg.getBasicData().getPropertyAsset().findAccountInfo(lgdepositaccount));
		}

		CBAccount giordanodepositaccount = kmbankservice.makeNewAccount(giordano, EAccountType.Deposit,interestDay);
		CBAccount giordanoloanaccount = kmbankservice.makeNewAccount(giordano, EAccountType.Loan,interestDay);
		kmbankservice.raiseLoan(giordanoloanaccount, 30000000000L, 120, 7);
		kmbankservice.sendMoney(giordano.getBasicData().getPropertyAsset().findAccountInfo(giordanoloanaccount)
				, giordano.getBasicData().getPropertyAsset().findAccountInfo(giordanodepositaccount), 2000000000L);
		if(giordano.getBasicData() instanceof PCompanyData)
		{
			PCompanyData cd = (PCompanyData) giordano.getBasicData();
			cd.setMainDepositAccount(giordano.getBasicData().getPropertyAsset().findAccountInfo(giordanodepositaccount));
		}
/////////////////////
		IBankService wrbankservice = (IBankService)wooribank;
		CBAccount samsungwrloanaccount = wrbankservice.makeNewAccount(samsung, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(samsungwrloanaccount, 10000000000L, 120, 7);//100억
		CBAccount lgwrloanaccount = wrbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(lgwrloanaccount, 30000000000L, 120, 7);//400억

		CBAccount sktdepositaccount = wrbankservice.makeNewAccount(skt, EAccountType.Deposit,interestDay);
		CBAccount sktloanaccount = wrbankservice.makeNewAccount(skt, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(sktloanaccount, 30000000000L, 120, 7);
		wrbankservice.sendMoney(skt.getBasicData().getPropertyAsset().findAccountInfo(sktloanaccount)
				, skt.getBasicData().getPropertyAsset().findAccountInfo(sktdepositaccount), 2000000000L);
		if(skt.getBasicData() instanceof PCompanyData)
		{
			PCompanyData cd = (PCompanyData) skt.getBasicData();
			cd.setMainDepositAccount(skt.getBasicData().getPropertyAsset().findAccountInfo(sktdepositaccount));
		}

		CBAccount cjdepositaccount = wrbankservice.makeNewAccount(cj, EAccountType.Deposit,interestDay);
		CBAccount cjloanaccount = wrbankservice.makeNewAccount(cj, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(cjloanaccount, 20000000000L, 120, 7);
		wrbankservice.sendMoney(cj.getBasicData().getPropertyAsset().findAccountInfo(cjloanaccount)
				, cj.getBasicData().getPropertyAsset().findAccountInfo(cjdepositaccount), 2000000000L);
		if(cj.getBasicData() instanceof PCompanyData)
		{
			PCompanyData cd = (PCompanyData) cj.getBasicData();
			cd.setMainDepositAccount(cj.getBasicData().getPropertyAsset().findAccountInfo(cjdepositaccount));
		}

	///////////////////
		ICompanyService samsungservice = (ICompanyService)samsung;
		ICompanyService lgservice = (ICompanyService)lg;
		ICompanyService sktservice = (ICompanyService)skt;
		ICompanyService cjservice = (ICompanyService)cj;
		ICompanyService giordanoservice = (ICompanyService)giordano;

		CBAccount indongsalaryaccount = shbankservice.makeNewAccount(indong, EAccountType.Deposit,interestDay);
		PAAccount indongaccountinfo = indong.getBasicData().getPropertyAsset().findAccountInfo(indongsalaryaccount);
		samsungservice.joinCompany(indong, indongaccountinfo, ECompanyPosition.Clerk);

		CBAccount jasonkimsalaryaccount = kmbankservice.makeNewAccount(jasonkim, EAccountType.Deposit,interestDay);
		PAAccount jasonkimaccountinfo = jasonkim.getBasicData().getPropertyAsset().findAccountInfo(jasonkimsalaryaccount);
		lgservice.joinCompany(jasonkim, jasonkimaccountinfo, ECompanyPosition.Clerk);

		CBAccount ronaldosalaryaccount = wrbankservice.makeNewAccount(ronaldo , EAccountType.Deposit,interestDay);
		PAAccount ronaldoaccountinfo = ronaldo.getBasicData().getPropertyAsset().findAccountInfo(ronaldosalaryaccount);
		sktservice.joinCompany(ronaldo,ronaldoaccountinfo,ECompanyPosition.Clerk);

		CBAccount beknimsalaryaccount = wrbankservice.makeNewAccount(beknim , EAccountType.Deposit,interestDay);
		PAAccount beknimaccountinfo = beknim.getBasicData().getPropertyAsset().findAccountInfo(beknimsalaryaccount);
		cjservice.joinCompany(beknim,beknimaccountinfo,ECompanyPosition.Clerk);

		CBAccount scwindsalaryaccount = shbankservice.makeNewAccount(scwind , EAccountType.Deposit,interestDay);
		PAAccount scwindaccountinfo = scwind.getBasicData().getPropertyAsset().findAccountInfo(scwindsalaryaccount);
		samsungservice.joinCompany(scwind,scwindaccountinfo,ECompanyPosition.Clerk);

		CBAccount yds0903salaryaccount = kmbankservice.makeNewAccount(yds0903 , EAccountType.Deposit,interestDay);
		PAAccount yds0903accountinfo = yds0903.getBasicData().getPropertyAsset().findAccountInfo(yds0903salaryaccount);
		giordanoservice.joinCompany(yds0903,yds0903accountinfo,ECompanyPosition.Clerk);
	}
}
