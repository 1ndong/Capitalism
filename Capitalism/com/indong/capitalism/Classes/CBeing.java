package com.indong.capitalism.Classes;

import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Classes.Stuff.CStuff;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Property.PAAccount;
import com.indong.capitalism.Property.PBasicData;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

import java.util.ArrayList;

public class CBeing extends CBelong implements ITime {
	protected EBeingType type;
	protected PBasicData basicData;
	private final CACCash wallet;

	protected ArrayList<CStuff> stuffList = new ArrayList<CStuff>();

	public CBeing(CCountry country)
	{
		super(country);
		wallet = new CACCash();
		ITimeKeeper timekeeper = ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
	}
	
	protected void repaymentDebt(DTime today)
	{
		for(int i = 0 ; i < basicData.getInfoAsset().getAccountList().size() ; i++)
		{
			PAAccount account = basicData.getInfoAsset().getAccountList().get(i);
			if(account.getAccountType() == EAccountType.Loan)
			{
				IBankService bankservice = ((IBankService)account.getBank()); 
				CBAccount realaccount = bankservice.findAccount(account.getOwnerName(), account.getAccountNumber());
				if(realaccount.getInterestDay().getDay() == today.getDay())
				{
					realaccount.repaymentDebt();
				}
			}
		}
	}

	public EBeingType getType() {
		return type;
	}
	
	public PBasicData getBasicData() {
		return basicData;
	}
	
	public CACCash getCash() {
		return wallet;
	}

	public void addCash(long cash) {
		this.wallet.addCash(cash);
	}

	public ArrayList<CStuff> getStuffList()
	{
		return stuffList;
	}
}
