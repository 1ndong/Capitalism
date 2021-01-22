package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

public class CHSGrep extends CompilerHelpString {

	public CHSGrep(LinkedList<String> commandList) {
		super(commandList);
		// TODO Auto-generated constructor stub
	}

	//grep
	//p , c , b , g
	//country
	//plist , clist , blist , glist -> select name
	//pfnlist , cfnlist , bfnlist , gfnlist -> select function name
	
	@Override
	public String[] getHelpString(int level) {
		// TODO Auto-generated method stub
		String[] result = null;
//		if(level > 4)
//			level = 4;
		
		switch(level)
		{
		case 1:
			result = new String[] {"'p' - people" , "'c' - company" , "'b' - bank" , "'g' - government"};
			break;
		case 2:
			result = getCountryNameResult();
			break;
		case 3:
			String type = commandList.get(1);
			if(type.equalsIgnoreCase("p"))
			{
				result = getPeopleList();
			}
			else if(type.equalsIgnoreCase("c"))
			{
				result = getCompanyList();
			}
			else if(type.equalsIgnoreCase("b"))
			{
				result = getBankList();
			}
			else if(type.equalsIgnoreCase("g"))
			{
				result = getGovernmentList();
			}
			
			break;
		case 4://function type per each type
			result = getFunctionList(commandList.get(1));
			break;
//		case 5:
//			result = getProperFunctionList(commandList.get(4));
//			break;
		}
		
		return result;
	}
	
	private String[] getFunctionList(String type)
	{
		String[] result = null;
		
		if(type.equalsIgnoreCase("p"))
		{
			result = new String[] {"makeAccount","moveMoney","makeLoan"};
		}
		else if(type.equalsIgnoreCase("c"))
		{
			result = new String[] {"makeAccount","moveMoney","makeLoan"};
		}
		else if(type.equalsIgnoreCase("b"))
		{
			result = new String[] {"not yet"};
		}
		else if(type.equalsIgnoreCase("g"))
		{
			result = new String[] {"not yet"};
		}
		
		return result;
	}
}
