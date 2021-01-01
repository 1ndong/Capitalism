package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CWorld;

public abstract class CompilerCommand {
	protected LinkedList<String> commandList;
	
	public CompilerCommand(LinkedList<String> list)
	{
		commandList = list;
	}
	
	abstract public boolean compile();
	
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
}
