package capitalism.javaorigin.Classes.Government;

import capitalism.javaorigin.Classes.CBelong;
import capitalism.javaorigin.Classes.CCountry;
import capitalism.javaorigin.DataCenter.DataCenter;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.ESearchType;
import capitalism.javaorigin.Interface.ISearchable;
import capitalism.javaorigin.Interface.ITime;
import capitalism.javaorigin.Interface.ITimeKeeper;
import capitalism.javaorigin.Processor.ProcessorDay;

public class CGovernment extends CBelong implements ITime , ISearchable {
	protected String name;

	protected ESearchType searchType = ESearchType.Government;
	public CGovernment(CCountry country)
	{
		super(country);
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Government;
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
