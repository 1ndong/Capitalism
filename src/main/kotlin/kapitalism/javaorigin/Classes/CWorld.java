package kapitalism.javaorigin.Classes;

import java.util.LinkedList;

import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.ESearchType;
import kapitalism.javaorigin.UI.FrameLog;
import kapitalism.javaorigin.Interface.ISearchable;
import kapitalism.javaorigin.Interface.ITime;
import kapitalism.javaorigin.Interface.ITimeKeeper;
import kapitalism.javaorigin.Processor.ProcessorDay;

public class CWorld implements ITime , ISearchable {
	private LinkedList<CCountry> countryList = new LinkedList<CCountry>();
	private static CWorld instance = new CWorld();

	protected ESearchType searchType = ESearchType.World;
	private CWorld()
	{
		FrameLog.getInstance().addLog("creation", "world 생성");
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}
	
	public static CWorld getInstance()
	{
		return instance;
	}

	public LinkedList<CCountry> getCountryList() {
		return countryList;
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.World;
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
		return "the earth";
	}
}
