package kapitalism.javaorigin.Classes;

import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.EBeingType;
import kapitalism.javaorigin.Enum.ESearchType;
import kapitalism.javaorigin.Property.PPersonalData;
import kapitalism.javaorigin.Interface.ISearchable;

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
