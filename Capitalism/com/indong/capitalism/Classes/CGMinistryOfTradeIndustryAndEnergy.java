package com.indong.capitalism.Classes;

import java.util.HashMap;

import com.indong.capitalism.Frame.FrameLog;

public class CGMinistryOfTradeIndustryAndEnergy extends CGovernment{

	private HashMap<Integer,CCompany> companyMap = new HashMap<Integer,CCompany>();
	
	public CGMinistryOfTradeIndustryAndEnergy(CCountry country) {
		super(country);
		// TODO Auto-generated constructor stub
		name="산업통산자원부";
	}

	public void registerCompany(CCompany company)
	{
		int ID = makeUniqueID();
		company.getCompanyData().setID(ID);
		companyMap.put(ID,company);
		FrameLog.getInstance().addLog("registerPeople", "신규등록 완료 이름 = "+company.getCompanyData().getName());
	}
	
	private int makeUniqueID()
	{
		int result = -1;
		
		int tempKey = 0;
		while(result == -1)
		{
			CCompany isExist = companyMap.get(tempKey);
			if(isExist == null)
			{
				result = tempKey;
			}
			else
			{
				tempKey++;
			}
		}
		
		return result;
	}

	public HashMap<Integer,CCompany> getCompanyMap() {
		return companyMap;
	}
}