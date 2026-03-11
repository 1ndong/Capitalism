package kapitalism.javaorigin.Classes.Government;

import java.util.HashMap;

import kapitalism.javaorigin.Classes.CCompany;
import kapitalism.javaorigin.Classes.CCountry;
import kapitalism.javaorigin.UI.FrameLog;

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
		company.getBasicData().setID(ID);
		companyMap.put(ID,company);
		FrameLog.getInstance().addLog("registerCompany", "신규등록 완료 이름 = "+company.getBasicData().getName());
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
