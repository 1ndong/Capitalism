package kapitalism.javaorigin.Classes.Bank;

import kapitalism.javaorigin.Classes.CBelong;
import kapitalism.javaorigin.Classes.CCountry;
import kapitalism.javaorigin.Classes.Asset.CACCash;
import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.ESearchType;
import kapitalism.javaorigin.Interface.ISearchable;
import kapitalism.javaorigin.Interface.ITime;
import kapitalism.javaorigin.Interface.ITimeKeeper;
import kapitalism.javaorigin.Processor.ProcessorDay;

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
