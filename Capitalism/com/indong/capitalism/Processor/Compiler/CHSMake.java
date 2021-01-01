package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CWorld;

public class CHSMake extends CompilerHelpString {

	public CHSMake(LinkedList<String> commandList) {
		super(commandList);
		// TODO Auto-generated constructor stub
	}

	//mk
	//p , c
	//country list -> select country name
	//birth
	//name
	@Override
	public String[] getHelpString(int level) {
		// TODO Auto-generated method stub
		String[] result = null;
		if(level > 4)
			level = 4;
		
		switch(level)
		{
		case 1:
			result = new String[] {"'p' - people" , "'c' - company"};
			break;
		case 2:
			result = getCountryNameResult();
			break;
		case 3:
			result = new String[] {"'yyyymmdd' - birth"};
			break;
		case 4:
			result = new String[] {"'name' - name"};
			break;
		}
		return result;
	}

}
