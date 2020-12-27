package com.indong.capitalism.Service;

import com.indong.capitalism.Item.ItemAccount;

public class ServiceBank {
	private static ServiceBank instance = new ServiceBank();
	
	public static ServiceBank getInstance()
	{
		return instance;
	}
	
	private ServiceBank()
	{
		
	}
	
	public void moveMoney(ItemAccount sender , ItemAccount receiver)
	{
		
	}
}
