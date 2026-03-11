package kapitalism.javaorigin.Classes;

import kapitalism.javaorigin.Classes.Asset.CACCash;
import kapitalism.javaorigin.Classes.Bank.CBAccount;
import kapitalism.javaorigin.Classes.Stuff.CStuff;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.EAccountType;
import kapitalism.javaorigin.Enum.EBeingType;
import kapitalism.javaorigin.Property.PAAccount;
import kapitalism.javaorigin.Property.PBasicData;
import kapitalism.javaorigin.Interface.IBankService;
import kapitalism.javaorigin.Interface.ITime;
import kapitalism.javaorigin.Interface.ITimeKeeper;
import kapitalism.javaorigin.Processor.ProcessorDay;

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
