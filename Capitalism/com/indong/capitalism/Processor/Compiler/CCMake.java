package com.indong.capitalism.Processor.Compiler;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameLog;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CCMake extends CompilerCommand{

	public CCMake(LinkedList<String> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	//mk
	//p,c
	//country
	//birth
	//name
	
	public boolean compile()
	{
		boolean result = false;
		
		if(commandList.size() < 2)
		{
			FrameLog.getInstance().addLog("compile", "fail");
			return false;
		}
		
		String action = commandList.removeFirst();
		String target = commandList.removeFirst();
		
		if(action.equalsIgnoreCase("mk"))
		{
			String countryName = commandList.removeFirst();
			CCountry country = null;
			for(int i = 0 ; i < CWorld.getInstance().getCountryList().size() ; i++)
			{
				CCountry tempcountry = CWorld.getInstance().getCountryList().get(i); 
				String temp = tempcountry.getCountryName();
				if(countryName.equalsIgnoreCase(temp))
				{
					country = tempcountry;
					break;
				}
			}
			
			if(country != null)
			{
				//yyyymmdd , name
				String birthStr = commandList.removeFirst();
				if(birthStr.length() == 8)
				{
					try
					{
						String yearstr = birthStr.substring(0, 3);
						String monthstr = birthStr.substring(4, 5);
						String daystr = birthStr.substring(6, 7);
						int year = Integer.parseInt(yearstr);
						int month = Integer.parseInt(monthstr);
						int day = Integer.parseInt(daystr);

						if(target.equalsIgnoreCase("p"))
						{
							CGMinistryOfHealthAndWelfare gmohaw = null;
							gmohaw = (CGMinistryOfHealthAndWelfare)country.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);
							
							if(gmohaw != null)
							{
								CPeople newpeople = new CPeople(country,new DTime(year,month,day),commandList.removeFirst());
								gmohaw.registerPeople(newpeople);
								result = true;
							}
						}
						else if(target.equalsIgnoreCase("c"));
						{
							CGMinistryOfTradeIndustryAndEnergy gmotiae = null;
							gmotiae = (CGMinistryOfTradeIndustryAndEnergy)country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
							
							if(gmotiae != null)
							{
								CCompany newcompany = new CCompany(country,new DTime(year,month,day),commandList.removeFirst());
								gmotiae.registerCompany(newcompany);
								result = true;
							}
						}	
					}
					catch(NumberFormatException e)
					{
						
					}
					catch(NoSuchElementException ee)
					{
						
					}
				}
			}
		}
		
		return result;
	}
}
