package capitalism.javaorigin.Classes.Bank;

import capitalism.javaorigin.Classes.CBelong;
import capitalism.javaorigin.Classes.CCountry;
import capitalism.javaorigin.Classes.Asset.CACCash;
import capitalism.javaorigin.DataCenter.DataCenter;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.ESearchType;
import capitalism.javaorigin.Interface.ISearchable;
import capitalism.javaorigin.Interface.ITime;
import capitalism.javaorigin.Interface.ITimeKeeper;
import capitalism.javaorigin.Processor.ProcessorDay;

public class CBank extends CBelong implements ITime , ISearchable {
	protected CACCash balance = new CACCash();
	protected String name;

	protected ESearchType searchType = ESearchType.Bank;
	public CBank(CCountry country , String name)
	{
		super(country);
		this.name = name;
		
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}
	
	public String getName()
	{
		return name;
	}
	
	public CACCash getBalance()
	{
		return balance;
	}
	
	protected void addBalance(long cash)
	{
		balance.addCash(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Bank;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	@Override
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getSearchableOriginName() {
		return getName();
	}
}
