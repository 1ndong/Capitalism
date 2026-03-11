package capitalism.javaorigin.Classes;

import capitalism.javaorigin.DataCenter.DataCenter;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.EBeingType;
import capitalism.javaorigin.Enum.ESearchType;
import capitalism.javaorigin.Property.PPersonalData;
import capitalism.javaorigin.Interface.ISearchable;

public class CPeople extends CBeing implements ISearchable
{
	protected ESearchType searchType = ESearchType.People;

	public CPeople(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Personal;
		basicData = new PPersonalData(birth , name);

		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
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
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getSearchableOriginName() {
		return getBasicData().getName();
	}
}
