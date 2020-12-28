package com.indong.capitalism.Processor;

import java.util.LinkedList;
import java.util.StringTokenizer;

import com.indong.capitalism.Frame.FrameLog;

public class ProcessorCommand {
	private static ProcessorCommand instance = new ProcessorCommand();
	public static ProcessorCommand getInstance()
	{
		return instance;
	}
	
	public String[] getHelpString(String command)
	{
		int level = 0;
		for(int i = 0 ; i < command.length(); i++)
		{
			char temp = command.charAt(i);
			if(temp == ' ')
				level++;
		}
		
		if(level == 0)
		{
			String[] result = new String[] {"'mk' - make" , "'rm' - remove"};
			return result;
		}
		else if(level == 1)
		{
			String[] result = new String[] {"'p' - people","'c' - company"};
			return result;
		}
		else if(level == 2)
		{
			String[] result = new String[] {"'yyyymmdd' - birth"};
			return result;
		}
		else if(level == 3)
		{
			String[] result = new String[] {"'name' - name"};
			return result;
		}
		
		return null;
	}
	
	private LinkedList<String> parsingCommand(String command)
	{
		// command type args1 args2 args3....
		StringTokenizer st = new StringTokenizer(command, " ");
		
		LinkedList<String> commandList = new LinkedList<String>();
		while(st.hasMoreTokens())
		{
			commandList.add(st.nextToken());
		}
		return commandList;
	}
	
	public boolean compile(String command)
	{
		LinkedList<String> commandList = parsingCommand(command);
		
		if(commandList.size() < 2)
		{
			FrameLog.getInstance().addLog("compile", "fail");
			return false;
		}
		
		String action = commandList.removeFirst();
		String target = commandList.removeFirst();
		
		boolean result = false;
		
		if(action.equalsIgnoreCase("mk"))
		{
			if(target.equalsIgnoreCase("people"))
			{
				
			}
			else if(target.equalsIgnoreCase("company"))
			{
				
			}
		}
		else if(action.equalsIgnoreCase("rm"))
		{
			if(target.equalsIgnoreCase("people"))
			{
				
			}
			else if(target.equalsIgnoreCase("company"))
			{
				
			}
		}
				
		if(result)
			FrameLog.getInstance().addLog("compile", "success compile");
		else
			FrameLog.getInstance().addLog("compile", "fail compile");
		
		return result;
	}
}
