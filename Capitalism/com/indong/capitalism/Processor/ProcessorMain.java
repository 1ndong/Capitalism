package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.Bank.CBCommercial;
import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Enum.ESectorType;
import com.indong.capitalism.UI.FrameLog;
import com.indong.capitalism.Property.PAAccount;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ICompanyService;
import com.indong.capitalism.Interface.ISector;

import java.util.ArrayList;

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
		shinhanbank.setSector(ESectorType.Finance.getValue());
		CBCommercial kookminbank = new CBCommercial(rok , "국민");
		kookminbank.setSector(ESectorType.Finance.getValue());
		CBCommercial wooribank = new CBCommercial(rok , "우리");
		wooribank.setSector(ESectorType.Finance.getValue());
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
		samsung.setSector(ESectorType.Consumption.getValue());
		gmotiae.registerCompany(samsung);
		samsung.setSalaryDay(25);
		{
			DService s1first = new DService("휴대폰",0);
				DService s1fs2first = new DService("갤럭시S21",1000000L);
				DService s1fs2second = new DService("갤럭시S21울트라",1300000L);

			DService s1second = new DService("냉장고",0);
				DService s1ss2first = new DService("비스포크",3500000L);
				DService s1ss2second = new DService("디오스",2000000L);

			DService s1third = new DService("TV",0);
				DService s1ts2first = new DService("4kUHD" , 0);
					DService s1ts2fs3first = new DService("75'" , 3000000L);
					DService s1ts2fs3second = new DService("82'" , 4500000L);
				DService s1ts2second = new DService("8kUHD",0);
					DService s1ts2ss3first = new DService("82'",6000000L);
					DService s1ts2ss3second = new DService("101'" , 20000000L);

			ArrayList<DService> list = new ArrayList<DService>();
			list.add(s1first);
			{
				ArrayList<DService> step2 = new ArrayList<DService>();
				step2.add(s1fs2first);
				step2.add(s1fs2second);
				s1first.setNextList(step2);
			}
			list.add(s1second);
			{
				ArrayList<DService> step2 = new ArrayList<DService>();
				step2.add(s1ss2first);
				step2.add(s1ss2second);
				s1second.setNextList(step2);
			}
			list.add(s1third);
			{
				ArrayList<DService> step2 = new ArrayList<DService>();
				step2.add(s1ts2first);
				{
					ArrayList<DService> step3 = new ArrayList<DService>();
					step3.add(s1ts2fs3first);
					step3.add(s1ts2fs3second);
					s1ts2first.setNextList(step3);
				}
				step2.add(s1ts2second);
				{
					ArrayList<DService> step3 = new ArrayList<DService>();
					step3.add(s1ts2ss3first);
					step3.add(s1ts2ss3second);
					s1ts2second.setNextList(step3);
				}
				s1third.setNextList(step2);
			}

			((ISector)samsung).setServiceList(list);
		}
		//

		//lg
		CCompany lg = new CCompany(rok,new DTime(1958,3,21),"LG");
		lg.setSector(ESectorType.Consumption.getValue());
		gmotiae.registerCompany(lg);
		lg.setSalaryDay(25);
		//

		//skt
		CCompany skt = new CCompany(rok,new DTime(1970,5,4),"SK텔레콤");
		skt.setSector(ESectorType.Consumption.getValue());
		gmotiae.registerCompany(skt);
		skt.setSalaryDay(25);
		//

		//cj
		CCompany cj = new CCompany(rok,new DTime(1984,2,3),"CJ");
		cj.setSector(ESectorType.Consumption.getValue());
		gmotiae.registerCompany(cj);
		cj.setSalaryDay(25);
		//

		//giordano
		CCompany giordano = new CCompany(rok, new DTime(1988 , 6,7),"지오다노");
		giordano.setSector(ESectorType.Consumption.getValue());
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
		shbankservice.sendMoney(samsung.getBasicData().getInfoAsset().findAccountInfo(samsungloanaccount)
				, samsung.getBasicData().getInfoAsset().findAccountInfo(samsungdepositaccount), 3000000000L);

/////////////////////
		IBankService kmbankservice = (IBankService)kookminbank;
		CBAccount lgdepositaccount = kmbankservice.makeNewAccount(lg, EAccountType.Deposit,interestDay);
		CBAccount lgloanaccount = kmbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		kmbankservice.raiseLoan(lgloanaccount, 50000000000L, 120, 7);
		kmbankservice.sendMoney(lg.getBasicData().getInfoAsset().findAccountInfo(lgloanaccount)
				, lg.getBasicData().getInfoAsset().findAccountInfo(lgdepositaccount), 2000000000L);

		CBAccount giordanodepositaccount = kmbankservice.makeNewAccount(giordano, EAccountType.Deposit,interestDay);
		CBAccount giordanoloanaccount = kmbankservice.makeNewAccount(giordano, EAccountType.Loan,interestDay);
		kmbankservice.raiseLoan(giordanoloanaccount, 30000000000L, 120, 7);
		kmbankservice.sendMoney(giordano.getBasicData().getInfoAsset().findAccountInfo(giordanoloanaccount)
				, giordano.getBasicData().getInfoAsset().findAccountInfo(giordanodepositaccount), 2000000000L);
/////////////////////
		IBankService wrbankservice = (IBankService)wooribank;
		CBAccount samsungwrloanaccount = wrbankservice.makeNewAccount(samsung, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(samsungwrloanaccount, 10000000000L, 120, 7);//100억
		CBAccount lgwrloanaccount = wrbankservice.makeNewAccount(lg, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(lgwrloanaccount, 30000000000L, 120, 7);//400억

		CBAccount sktdepositaccount = wrbankservice.makeNewAccount(skt, EAccountType.Deposit,interestDay);
		CBAccount sktloanaccount = wrbankservice.makeNewAccount(skt, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(sktloanaccount, 30000000000L, 120, 7);
		wrbankservice.sendMoney(skt.getBasicData().getInfoAsset().findAccountInfo(sktloanaccount)
				, skt.getBasicData().getInfoAsset().findAccountInfo(sktdepositaccount), 2000000000L);

		CBAccount cjdepositaccount = wrbankservice.makeNewAccount(cj, EAccountType.Deposit,interestDay);
		CBAccount cjloanaccount = wrbankservice.makeNewAccount(cj, EAccountType.Loan,interestDay);
		wrbankservice.raiseLoan(cjloanaccount, 20000000000L, 120, 7);
		wrbankservice.sendMoney(cj.getBasicData().getInfoAsset().findAccountInfo(cjloanaccount)
				, cj.getBasicData().getInfoAsset().findAccountInfo(cjdepositaccount), 2000000000L);


	///////////////////
		ICompanyService samsungservice = (ICompanyService)samsung;
		ICompanyService lgservice = (ICompanyService)lg;
		ICompanyService sktservice = (ICompanyService)skt;
		ICompanyService cjservice = (ICompanyService)cj;
		ICompanyService giordanoservice = (ICompanyService)giordano;

		CBAccount indongsalaryaccount = shbankservice.makeNewAccount(indong, EAccountType.Deposit,interestDay);
		PAAccount indongaccountinfo = indong.getBasicData().getInfoAsset().findAccountInfo(indongsalaryaccount);
		samsungservice.joinCompany(indong, indongaccountinfo, ECompanyPosition.Clerk);

		CBAccount jasonkimsalaryaccount = kmbankservice.makeNewAccount(jasonkim, EAccountType.Deposit,interestDay);
		PAAccount jasonkimaccountinfo = jasonkim.getBasicData().getInfoAsset().findAccountInfo(jasonkimsalaryaccount);
		lgservice.joinCompany(jasonkim, jasonkimaccountinfo, ECompanyPosition.Clerk);

		CBAccount ronaldosalaryaccount = wrbankservice.makeNewAccount(ronaldo , EAccountType.Deposit,interestDay);
		PAAccount ronaldoaccountinfo = ronaldo.getBasicData().getInfoAsset().findAccountInfo(ronaldosalaryaccount);
		sktservice.joinCompany(ronaldo,ronaldoaccountinfo,ECompanyPosition.Clerk);

		CBAccount beknimsalaryaccount = wrbankservice.makeNewAccount(beknim , EAccountType.Deposit,interestDay);
		PAAccount beknimaccountinfo = beknim.getBasicData().getInfoAsset().findAccountInfo(beknimsalaryaccount);
		cjservice.joinCompany(beknim,beknimaccountinfo,ECompanyPosition.Clerk);

		CBAccount scwindsalaryaccount = shbankservice.makeNewAccount(scwind , EAccountType.Deposit,interestDay);
		PAAccount scwindaccountinfo = scwind.getBasicData().getInfoAsset().findAccountInfo(scwindsalaryaccount);
		samsungservice.joinCompany(scwind,scwindaccountinfo,ECompanyPosition.Clerk);

		CBAccount yds0903salaryaccount = kmbankservice.makeNewAccount(yds0903 , EAccountType.Deposit,interestDay);
		PAAccount yds0903accountinfo = yds0903.getBasicData().getInfoAsset().findAccountInfo(yds0903salaryaccount);
		giordanoservice.joinCompany(yds0903,yds0903accountinfo,ECompanyPosition.Clerk);
	}
}
