package kapitalism.javaorigin.Classes.Government;

import kapitalism.javaorigin.Classes.CBelong;
import kapitalism.javaorigin.Classes.CCountry;
import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.ESearchType;
import kapitalism.javaorigin.Interface.ISearchable;
import kapitalism.javaorigin.Interface.ITime;
import kapitalism.javaorigin.Interface.ITimeKeeper;
import kapitalism.javaorigin.Processor.ProcessorDay;

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
