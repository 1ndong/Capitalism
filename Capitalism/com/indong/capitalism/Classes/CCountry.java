package com.indong.capitalism.Classes;

import java.util.HashMap;
import java.util.LinkedList;

import com.indong.capitalism.Classes.Bank.CBCentral;
import com.indong.capitalism.Classes.Bank.CBank;
import com.indong.capitalism.Classes.Government.CGovernment;
import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.UI.FrameLog;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Interface.ITimeKeeper;
import com.indong.capitalism.Processor.ProcessorDay;

public class CCountry implements ITime , ISearchable {
	private String name;
	private CBCentral centralBank;
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private LinkedList<CCompany> companyList = new LinkedList<CCompany>();
	private HashMap<EGovernmentType , CGovernment> governmentMap = new HashMap<EGovernmentType,CGovernment>();

	protected ESearchType searchType = ESearchType.Country;

	public CCountry(String name)
	{
		this.name = name;
		FrameLog.getInstance().addLog("CCountry", getCountryName() + " 생성");
		centralBank = new CBCentral(this , "한국은행");
		FrameLog.getInstance().addLog("CCountry", getCountryName() + " 중앙은행 생성");
		
		ITimeKeeper timeKeeper = (ITimeKeeper)ProcessorDay.GetInstance();
		timeKeeper.addTimeSlave(this);

		registerObject();
	}
	
	public String getCountryName()
	{
		return name;
	}

	public LinkedList<CBank> getBankList() {
		return bankList;
	}

	public CBCentral getCentralBank() {
		return centralBank;
	}

	public LinkedList<CCompany> getCompanyList() {
		return companyList;
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		
	}

	public HashMap<EGovernmentType , CGovernment> getGovernmentMap() {
		return governmentMap;
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
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getSearchableOriginName() {
		return getCountryName();
	}
}
