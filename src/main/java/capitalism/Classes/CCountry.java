package capitalism.Classes;

import java.util.HashMap;
import java.util.LinkedList;

import capitalism.Classes.Bank.CBCentral;
import capitalism.Classes.Bank.CBank;
import capitalism.Classes.Government.CGovernment;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EGovernmentType;
import capitalism.Enum.ESearchType;
import capitalism.UI.FrameLog;
import capitalism.Interface.ISearchable;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import lombok.Getter;

@Getter
public class CCountry implements ITime, ISearchable {
	private String countryName;
	private CBCentral centralBank;
	private LinkedList<CBank> bankList = new LinkedList<>();
	private LinkedList<CCompany> companyList = new LinkedList<>();
	private HashMap<EGovernmentType, CGovernment> governmentMap = new HashMap<>();
	protected ESearchType searchType = ESearchType.Country;

	public CCountry(String name) {
		this.countryName = name;
		FrameLog.getInstance().addLog("CCountry", getCountryName() + " 생성");
		centralBank = new CBCentral(this, "한국은행");
		FrameLog.getInstance().addLog("CCountry", getCountryName() + " 중앙은행 생성");

		ITimeKeeper timeKeeper = (ITimeKeeper) ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Country;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	@Override
	public String getSearchableOriginName() {
		return getCountryName();
	}
}
