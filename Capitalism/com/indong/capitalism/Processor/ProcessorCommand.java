package com.indong.capitalism.Processor;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EGovernmentType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Processor.Compiler.*;
import com.indong.capitalism.Processor.Compiler2.C2CBankService;
import com.indong.capitalism.Processor.Compiler2.C2CBuyClothing;
import com.indong.capitalism.Processor.Compiler2.C2CEatFood;
import com.indong.capitalism.Processor.Compiler2.C2Command;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ProcessorCommand {
	private static final ProcessorCommand instance = new ProcessorCommand();
	public static ProcessorCommand getInstance()
	{
		return instance;
	}

	private LinkedList<C2Command> commandList = new LinkedList<C2Command>();

	public ProcessorCommand()
	{
		initCommandList();
	}

	private void initCommandList()
	{
		commandList.add(new C2CBankService());
		commandList.add(new C2CBuyClothing());
		commandList.add(new C2CEatFood());
	}

	public void changeCountry(CCountry country)
	{
		for(int i = 0 ; i < commandList.size(); i++)
		{
			C2Command command = commandList.get(i);
			command.setCountry(country);
		}
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
			String[] result = new String[CWorld.getInstance().getCountryList().size()];
			for(int i = 0 ;i < CWorld.getInstance().getCountryList().size() ; i++)
			{
				result[i] = "'"+CWorld.getInstance().getCountryList().get(i).getCountryName()+"'";
			}
			return result;
		}
		else if(level == 3)
		{
			String[] result = new String[] {"'yyyymmdd' - birth"};
			return result;
		}
		else if(level == 4)
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
								gmohaw = (CGMinistryOfHealthAndWelfare)country.getGovernmentMap().get(EGovernmentType.EHealthAndWelfare);
								
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
								gmotiae = (CGMinistryOfTradeIndustryAndEnergy)country.getGovernmentMap().get(EGovernmentType.ETradeIndustryAndEnergy);
								
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
	
	public boolean newcompile(String command)
	{
		boolean result = false;
		LinkedList<String> commandList = parsingCommand(command);
		
		if(commandList.size() == 0)
			return false;
		
		CompilerCommand cc = null;
		
		switch(commandList.get(0))
		{
		case "mk":
			cc = new CCMake(commandList);
			break;
		case "rm":
			cc = new CCRemove(commandList);
			break;
		case "grep":
			cc = new CCGrep(commandList);
			break;
		}
		
		if(cc != null)
		{
			result = cc.compile();
		}
		
		return result;
	}
	
	public String[] newgetHelpString(String command)
	{
		int level = 0;
		for(int i = 0 ; i < command.length(); i++)
		{
			char temp = command.charAt(i);
			if(temp == ' ')
				level++;
		}
		
		if(level == 0)
			return getFirstCommandHelpString();
		
		LinkedList<String> commandlist = parsingCommand(command);
		String firstCommand = commandlist.get(0);
		
		CompilerHelpString chs= null;//글자 칠때마다 새로만드는데 좀 더 효율적인 방법을 생각해봐야
		
		if(firstCommand.equalsIgnoreCase("mk"))
		{
			chs = new CHSMake(commandlist);
		}
		else if(firstCommand.equalsIgnoreCase("rm"))
		{
			chs = new CHSRemove(commandlist);
		}
		else if(firstCommand.contentEquals("grep"))
		{
			chs = new CHSGrep(commandlist);
		}
		else
		{
			String[] result = new String[] {"invalid first command"};
			return result;
		}	
		
		return chs.getHelpString(level);
	}
	
	private String[] getFirstCommandHelpString()
	{
		//all command
		String[] result = new String[] {"'mk' - make" , "'rm' - remove" , "'grep' - select object"};
		return result;
	}

	//////////below Compiler 2.0

}
