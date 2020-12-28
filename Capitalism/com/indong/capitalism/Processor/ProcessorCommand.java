package com.indong.capitalism.Processor;

import java.util.LinkedList;
import java.util.StringTokenizer;

public class ProcessorCommand {
	private static ProcessorCommand instance = new ProcessorCommand();
	private boolean lastResult = false;
	public static ProcessorCommand getInstance()
	{
		return instance;
	}
	
	public boolean compile(String command)
	{
		// command type args1 args2 args3....
		StringTokenizer st = new StringTokenizer(command, " ");
		
		LinkedList<String> commandList = new LinkedList<String>();
		while(st.hasMoreTokens())
		{
			commandList.add(st.nextToken());
		}
		
		compileFirstAction(commandList.removeFirst());
		compileSecondTarget(commandList.removeFirst());
		compileArgs(commandList);
		
		return lastResult;
	}
	
	private void compileFirstAction(String action)
	{
		
	}
	
	private void compileSecondTarget(String target)
	{
		
	}
	
	private void compileArgs(LinkedList<String> commandList)
	{
		
	}
}
