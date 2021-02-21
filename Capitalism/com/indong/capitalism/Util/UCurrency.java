package com.indong.capitalism.Util;

import com.indong.capitalism.Enum.ECurrency;

public class UCurrency {
	private static final UCurrency instance = new UCurrency();
	private String[] han1 = new String[] {
			"","\uC77C","\uC774","\uC0BC","\uC0AC",
		    "\uC624","\uC721","\uCE60","\uD314","\uAD6C"
	};//0,1,2,3,4,5,6,7,8,9
	private String[] han2 = new String[] {
			"","\uC2ED","\uBC31","\uCC9C"
	};//ten , hundred , thousand
	//sib , baek , chun
	private String[] han3 = new String[] {
			"","\uB9CC","\uC5B5","\uC870"//han3[4]=_T("\uACBD");
	};//ten thousand , one hundred million , trillion
	//man , uk , jo
	
	public static UCurrency getInstance()
	{
		return instance;
	}

	public long toOriginValue(String strValue , ECurrency currency)
	{
		if(currency == ECurrency.Won)
		{
			strValue = strValue.replaceAll("\uC6D0","");
		}
		else if(currency == ECurrency.Dollar)
		{
			strValue = strValue.replaceAll("\uB2EC\uB7EC","");
		}
		else if(currency == ECurrency.Euro)
		{
			strValue = strValue.replaceAll("\uC720\uB85C","");
		}

		int joidx = strValue.indexOf(han3[3]);
		int ukidx = strValue.indexOf(han3[2]);
		int manidx = strValue.indexOf(han3[1]);

		long jj = 0;
		long uu = 0;
		long mm = 0;

		if(joidx != -1)
		{
			String temp = strValue.substring(0,joidx);
			jj = Long.parseLong(temp);
			jj *= 1000000000000L;
			strValue = strValue.substring(joidx+1);
		}
		if(ukidx != -1)
		{
			ukidx = strValue.indexOf(han3[2]);
			String temp = strValue.substring(0,ukidx);
			uu = Long.parseLong(temp);
			uu *= 100000000L;
			strValue = strValue.substring(ukidx+1);
		}
		if(manidx != -1)
		{
			manidx = strValue.indexOf(han3[1]);
			String temp = strValue.substring(0,manidx);
			mm = Long.parseLong(temp);
			mm *= 10000L;
			strValue = strValue.substring(manidx+1);
		}

		long etc = 0;
		if(strValue.isEmpty() == false)
			etc = Long.parseLong(strValue);

		return jj+uu+mm+etc;
	}

	public String toString(long money , ECurrency currency)
	{
		return toCurrencyStringInternal(money, currency, false);
	}
	
	public String toStringAllKorean(long money , ECurrency currency)
	{
		return toCurrencyStringInternal(money, currency, true);
	}
	
	private String toCurrencyStringInternal(long money , ECurrency currency , boolean isAllkorean)
	{
		String str = String.valueOf(money);
		
		String result = "";
		int len = str.length();
		
		final int billlimit = 16;//1000trillion
		boolean bOverflow = false;
		if(len > billlimit)
			bOverflow = true;
		
		String[] billStr = new String[]
		{
			"","","",""
		};
		//0won ,1man , 2uk ,3jo
		
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
	        	String temp = str.substring(len-4);
	        	int removeZero = Integer.parseInt(temp);
	            billStr[i] += removeZero;
	            str = str.substring(0, len-4);
	            len = str.length();
	        }
	    }

		if(isAllkorean == true)
		{
		    for(int i = 0 ; i < 4 ; i ++)
		    {
		    	if(billStr[i] == null)
		    		continue;
		        int len2 = billStr[i].length();
		        for(int j = len2-1 ; j >=0 ; j--)
		        {
		            char char1 = (billStr[i].charAt(len2 - j - 1));
		            String character = String.valueOf(char1);
		            if(character.equalsIgnoreCase("0") == true)
		            	continue;
	            	
		            if(isAllkorean == true)
		            {
			            String temp = han1[Integer.parseInt(character)];
		            	resultBillStr[i] += temp;		            	
		            }
		            else
		            {
		            	resultBillStr[i] += char1;	
		            }

		            if(bOverflow && i == 3)
		            {
		                if(Integer.parseInt(character) == 0)
		                    resultBillStr[i] += "\uC601";
		            }
		            else
		            {
		                if(Integer.parseInt(character) > 0)
		                {
		                	resultBillStr[i] += han2[j%4];
		                }
		            }
		        }
		    }	
		}

	    for(int i = 3 ; i >= 0 ; i--)
	    {
	    	if(isAllkorean == true)
	    	{
		        if(resultBillStr[i].length() > 0)
		        {
		            result += resultBillStr[i];
		            result += han3[i];
		        }	    		
	    	}
	    	else
	    	{
		        if(billStr[i].length() > 0 && Integer.parseInt(billStr[i]) > 0)
		        {
		            result += billStr[i];
		            result += han3[i];
		        }	    		
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
