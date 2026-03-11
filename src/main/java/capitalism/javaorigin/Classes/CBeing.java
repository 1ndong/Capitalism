package capitalism.javaorigin.Classes;

import capitalism.javaorigin.Classes.Asset.CACCash;
import capitalism.javaorigin.Classes.Bank.CBAccount;
import capitalism.javaorigin.Classes.Stuff.CStuff;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.EAccountType;
import capitalism.javaorigin.Enum.EBeingType;
import capitalism.javaorigin.Property.PAAccount;
import capitalism.javaorigin.Property.PBasicData;
import capitalism.javaorigin.Interface.IBankService;
import capitalism.javaorigin.Interface.ITime;
import capitalism.javaorigin.Interface.ITimeKeeper;
import capitalism.javaorigin.Processor.ProcessorDay;

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
		for(int i = 0; i < basicData.getPropertyAsset().getAccountList().size() ; i++)
		{
			PAAccount account = basicData.getPropertyAsset().getAccountList().get(i);
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
	
	public CACCash getWallet() {
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
