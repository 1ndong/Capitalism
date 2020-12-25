package com.indong.capitalism.Classes;

import java.util.LinkedList;

public class CCountry {
	private String name;
	private CBCentral centralBank = new CBCentral();
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	
	public CCountry(String name)
	{
		this.name = name;
	}
	
	public String getCountryName()
	{
		return name;
	}
}
