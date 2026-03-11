package kapitalism.javaorigin.Processor.Compiler;

import java.util.LinkedList;

public class CHSRemove extends CompilerHelpString {

	public CHSRemove(LinkedList<String> commandList) {
		super(commandList);
		// TODO Auto-generated constructor stub
	}

	//rm
	//p , c
	//country
	//plist , clist -> select name
	
	@Override
	public String[] getHelpString(int level) {
		// TODO Auto-generated method stub
		String[] result = null;
		if(level > 3)
			level = 3;
		
		switch(level)
		{
		case 1:
			result = new String[] {"'p' - people" , "'c' - company"};
			break;
		case 2:
			result = getCountryNameResult();
			break;
		case 3:
			//2에서 선택한 country의 1에서선택한 type list
			String type = commandList.get(1);
			if(type.equalsIgnoreCase("p"))
			{
				result = getPeopleList();
			}
			else if(type.equalsIgnoreCase("c"))
			{
				result = getCompanyList();
			}
			break;
		}
		return result;
	}

}
