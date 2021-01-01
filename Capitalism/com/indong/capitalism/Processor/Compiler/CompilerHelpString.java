package com.indong.capitalism.Processor.Compiler;

import java.util.Iterator;
import java.util.LinkedList;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.Classes.CGovernment;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Enum.EGovernmentType;

public abstract class CompilerHelpString {
	protected LinkedList<String> commandList;
	abstract public String[] getHelpString(int level);
	
	public CompilerHelpString(LinkedList<String> commandList)
	{
		this.commandList = commandList;
	}
	
	protected String[] getCountryNameResult()
	{
		String[] result = new String[CWorld.getInstance().getCountryList().size()];
		for(int i = 0 ;i < CWorld.getInstance().getCountryList().size() ; i++)
		{
			result[i] = "'"+CWorld.getInstance().getCountryList().get(i).getCountryName()+"'";
		}
		return result;
	}
	
	protected CCountry getCountry()
	{
		String countryName = commandList.get(2);
		CCountry country = null;
		for(int i = 0 ; i < CWorld.getInstance().getCountryList().size() ; i++)
		{
			if(countryName.equalsIgnoreCase(CWorld.getInstance().getCountryList().get(i).getCountryName()))
			{
				country = CWorld.getInstance().getCountryList().get(i);
			}
		}
		return country;
	}
	
	protected String[] getPeopleList() 
	{
		CCountry country = getCountry();

		String[] result = null;
		if(country != null)
		{
			CGMinistryOfHealthAndWelfare gov = (CGMinistryOfHealthAndWelfare)country.getGovernmentMap().get(EGovernmentType.EHealthAndWelfare);
			if(gov != null)
			{
				Iterator<Integer> mapIter = gov.getPeopleMap().keySet().iterator();
				result = new String[gov.getPeopleMap().size()];
				int i = 0;
		        while(mapIter.hasNext())
		        {
		            int key = mapIter.next();
		            CPeople value = gov.getPeopleMap().get(key);
		            
		            result[i] = "이름 = " + value.getPersonaldata().getName() + ",주민번호 : " + key;
		            i++;
		        }
			}
		}
		
		return result;
	}
	
	protected String[] getCompanyList()
	{
		CCountry country = getCountry();

		String[] result = null;
		if(country != null)
		{
			CGMinistryOfTradeIndustryAndEnergy gov = (CGMinistryOfTradeIndustryAndEnergy)country.getGovernmentMap().get(EGovernmentType.ETradeIndustryAndEnergy);
			if(gov != null)
			{
				Iterator<Integer> mapIter = gov.getCompanyMap().keySet().iterator();
				result = new String[gov.getCompanyMap().size()];
				int i = 0;
		        while(mapIter.hasNext())
		        {
		            int key = mapIter.next();
		            CCompany value = gov.getCompanyMap().get(key);
		            
		            result[i] = "사명 = " + value.getCompanyData().getName() + ",사업자등록번호 : " + key;
		            i++;
		        }
			}
		}
		return result;
	}
	
	protected String[] getBankList()
	{
		CCountry country = getCountry();
		
		String[] result = null;
		if(country != null)
		{
			result = new String[country.getBankList().size()];
			for(int i = 0 ; i < country.getBankList().size() ; i++)
			{
				result[i] = "은행명 = " + country.getBankList().get(i).getName();
			}
		}
		return result;
	}
	
	protected String[] getGovernmentList()
	{
		CCountry country = getCountry();
		
		String[] result = null;
		if(country != null)
		{
			result = new String[country.getGovernmentMap().size()];
			Iterator<EGovernmentType> mapIter = country.getGovernmentMap().keySet().iterator();
			result = new String[country.getGovernmentMap().size()];
			int i = 0;
	        while(mapIter.hasNext())
	        {
	            EGovernmentType key = mapIter.next();
	            CGovernment value = country.getGovernmentMap().get(key);
	            
	            result[i] = "기관명 = " + value.getName();
	            i++;
	        }
		}
		return result;
	}
}
