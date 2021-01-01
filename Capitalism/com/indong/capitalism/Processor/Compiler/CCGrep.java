package com.indong.capitalism.Processor.Compiler;

import java.util.LinkedList;

import com.indong.capitalism.Frame.FrameLog;

public class CCGrep extends CompilerCommand {

	public CCGrep(LinkedList<String> list) {
		super(list);
		// TODO Auto-generated constructor stub
	}
	//grep
	//p,c,b,g
	//list
	//pfn,cfn,bfn,gfn
	
	public boolean compile()
	{
		boolean result = false;
		
		if(commandList.size() < 2)
		{
			FrameLog.getInstance().addLog("compile", "fail");
			return false;
		}
		
		return result;
	}
}
