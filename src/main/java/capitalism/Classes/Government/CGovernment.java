package capitalism.Classes.Government;

import capitalism.Classes.CBelong;
import capitalism.Classes.CCountry;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DTime;
import capitalism.Enum.ESearchType;
import capitalism.Interface.ISearchable;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import lombok.Getter;

@Getter
public class CGovernment extends CBelong implements ITime, ISearchable {
	protected String name;
	protected ESearchType searchType = ESearchType.Government;

	public CGovernment(CCountry country) {
		super(country);
		ITimeKeeper timeKeeper = (ITimeKeeper) ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);
		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
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
	public String getSearchableOriginName() {
		return getName();
	}
}
