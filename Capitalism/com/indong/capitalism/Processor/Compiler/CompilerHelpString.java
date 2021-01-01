package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CWorld;

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
}
