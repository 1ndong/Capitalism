package capitalism.javaorigin.Processor.Compiler;

import java.util.Iterator;
import java.util.LinkedList;

import capitalism.javaorigin.Classes.CCompany;
import capitalism.javaorigin.Classes.CCountry;
import capitalism.javaorigin.Classes.CPeople;
import capitalism.javaorigin.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.javaorigin.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.javaorigin.Enum.EGovernmentType;
import capitalism.javaorigin.UI.FrameLog;

public class CCRemove extends CompilerCommand {

	public CCRemove(LinkedList<String> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	//rm
	//p , c
	//country
	//plist , clist -> select name
	
	public boolean compile()
	{
		boolean result = false;
		
		if(commandList.size() < 2)
		{
			FrameLog.getInstance().addLog("compile", "fail");
			return false;
		}
		
		String action = commandList.get(0);
		String target = commandList.get(1);
		String name = commandList.get(3);
		
		if(action.equalsIgnoreCase("rm"))
		{
			CCountry countryobj = getCountry();
			
			if(countryobj != null)
			{
				if(target.equalsIgnoreCase("p"))
				{
					CGMinistryOfHealthAndWelfare gov = (CGMinistryOfHealthAndWelfare)countryobj.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);
					if(gov != null)
					{
						Iterator<Integer> mapIter = gov.getPeopleMap().keySet().iterator();
						int resultkey = -1;
				        while(mapIter.hasNext())
				        {
				            int key = mapIter.next();
				            CPeople value = gov.getPeopleMap().get(key);
				            
				            if(value.getBasicData().getName().equalsIgnoreCase(name))
				            {
				            	resultkey = key;
				            	break;
				            }
				        }
				        
				        if(resultkey != -1)
				        {
				        	gov.getPeopleMap().remove(resultkey);
				        	result = true;
				        	FrameLog.getInstance().addLog("remove compile", name + " 삭제 성공");
				        }
					}
				}
				else if(target.equalsIgnoreCase("c"))
				{
					CGMinistryOfTradeIndustryAndEnergy gov = (CGMinistryOfTradeIndustryAndEnergy)countryobj.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
					if(gov != null)
					{
						Iterator<Integer> mapIter = gov.getCompanyMap().keySet().iterator();
						int resultkey = -1;
				        while(mapIter.hasNext())
				        {
				            int key = mapIter.next();
				            CCompany value = gov.getCompanyMap().get(key);
				            
				            if(value.getBasicData().getName().equalsIgnoreCase(name))
				            {
				            	resultkey = key;
				            	break;
				            }
				        }
				        
				        if(resultkey != -1)
				        {
				        	gov.getCompanyMap().remove(resultkey);
				        	result = true;
				        	FrameLog.getInstance().addLog("remove compile", name + " 삭제 성공");
				        }
					}
				}
			}
		}
		
		return result;
	}
}
