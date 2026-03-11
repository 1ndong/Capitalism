package capitalism.Classes.Bank;

import capitalism.Classes.CBelong;
import capitalism.Classes.CCountry;
import capitalism.Classes.Asset.CACCash;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DTime;
import capitalism.Enum.ESearchType;
import capitalism.Interface.ISearchable;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import lombok.Getter;

@Getter
public class CBank extends CBelong implements ITime, ISearchable {
	protected CACCash balance = new CACCash();
	protected String name;
	protected ESearchType searchType = ESearchType.Bank;

	public CBank(CCountry country, String name) {
		super(country);
		this.name = name;

		ITimeKeeper timeKeeper = (ITimeKeeper) ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}

	protected void addBalance(long cash) {
		balance.addCash(cash);
	}

	@Override
	public void dayChange(DTime newTime) {
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
	public String getSearchableOriginName() {
		return getName();
	}
}
