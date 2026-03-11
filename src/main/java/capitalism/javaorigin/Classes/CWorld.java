package capitalism.javaorigin.Classes;

import java.util.LinkedList;

import capitalism.javaorigin.DataCenter.DataCenter;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.ESearchType;
import capitalism.javaorigin.UI.FrameLog;
import capitalism.javaorigin.Interface.ISearchable;
import capitalism.javaorigin.Interface.ITime;
import capitalism.javaorigin.Interface.ITimeKeeper;
import capitalism.javaorigin.Processor.ProcessorDay;

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
