package capitalism.Classes;

import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EBeingType;
import capitalism.Enum.ESearchType;
import capitalism.Property.PPersonalData;
import capitalism.Interface.ISearchable;
import lombok.Getter;

@Getter
public class CPeople extends CBeing implements ISearchable {
	protected ESearchType searchType = ESearchType.People;

	public CPeople(CCountry country, DTime birth, String name) {
		super(country);
		type = EBeingType.Personal;
		basicData = new PPersonalData(birth, name);
		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
		repaymentDebt(newTime);
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.People;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	@Override
	public String getSearchableOriginName() {
		return getBasicData().getName();
	}
}
