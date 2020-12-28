package com.indong.capitalism.Classes;

import java.util.LinkedList;

public class CWorld {
	private LinkedList<CCountry> countryList = new LinkedList<CCountry>();
	private static CWorld instance = new CWorld();
	
	public static CWorld getInstance()
	{
		return instance;
	}

	public LinkedList<CCountry> getCountryList() {
		return countryList;
	}
}
