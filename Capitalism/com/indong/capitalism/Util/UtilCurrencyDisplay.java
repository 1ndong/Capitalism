package com.indong.capitalism.Util;

import com.indong.capitalism.Enum.ECurrency;

public class UtilCurrencyDisplay {
	private static UtilCurrencyDisplay instance = new UtilCurrencyDisplay();
	private String[] han1 = new String[] {
			"","\uC77C","\uC774","\uC0BC","\uC0AC",
		    "\uC624","\uC721","\uCE60","\uD314","\uAD6C"
	};//0,1,2,3,4,5,6,7,8,9
	private String[] han2 = new String[] {
			"","\uC2ED","\uBC31","\uCC9C"
	};//ten , hundred , thousand
	private String[] han3 = new String[] {
			"","\uB9CC","\uC5B5","\uC870"//han3[4]=_T("\uACBD");
	};//ten thousand , one hundred million , trillion
	
	public static UtilCurrencyDisplay getInstance()
	{
		return instance;
	}
	
	public String toCurrencyString(long money , ECurrency currency)
	{
		String str = String.valueOf(money);
		
		String result = "";
		int len = str.length();
		
		final int billlimit = 16;//1000trillion
		boolean bOverflow = false;
		if(len > billlimit)
			bOverflow = true;
		
		String[] billStr = new String[4];//0won ,1man , 2uk ,3jo
		String[] resultBillStr = new String[]
		{
				"","","",""
		};
		
		for(int i = 0 ; i < 4 ; i++)
	    {
	        if(len < 4 || i == 3)
	        {
	            billStr[i] = str;
	            break;
	        }
	        else
	        {
	            billStr[i] = str.substring(len-4);
	            str = str.substring(0, len-4);
	            len = str.length();
	        }
	    }

	    for(int i = 0 ; i < 4 ; i ++)
	    {
	    	if(billStr[i] == null)
	    		continue;
	        int len2 = billStr[i].length();
	        for(int j = len2-1 ; j >=0 ; j--)
	        {
	            char char1 = (billStr[i].charAt(len2 - j - 1));
	            String character = String.valueOf(char1);
	            String temp = han1[Integer.parseInt(character)];
            	resultBillStr[i] += temp;
	            if(bOverflow && i == 3)
	            {
	                if(Integer.parseInt(character) == 0)
	                    resultBillStr[i] += "\uC601";
	            }
	            else
	            {
	                if(Integer.parseInt(character) > 0)
	                    resultBillStr[i] += han2[j%4];
	            }
	        }
	    }

	    for(int i = 3 ; i >= 0 ; i--)
	    {
	        if(resultBillStr[i].length() > 0)
	        {
	            result += resultBillStr[i];
	            result += han3[i];
	        }
	    }
	    
	    if(result.length() > 0)
	    {
	    	if(currency == ECurrency.Won)
	    	{
    			result += "\uC6D0";	
	    	}
	    	else if(currency == ECurrency.Dollar)
	    	{
	    		result += "\uB2EC\uB7EC";
	    	}
	    	else if(currency == ECurrency.Euro)
	    	{
	    		result += "\uC720\uB85C";
	    	}
	    }
	    
	    return result;
	}
}
