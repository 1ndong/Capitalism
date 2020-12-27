package com.indong.capitalism.Service;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CBank;
import com.indong.capitalism.Classes.CCountry;

public class ServiceInformation {
	private static ServiceInformation instance;
	private CCountry country;
	
	//국가별로 만들지 말고 전세계에서 특정국가 접근할수있도록 만들어야됨
	//일단은 너무 먼이야기라서 한국만 있다는 가정
	
	public static ServiceInformation getInstance()
	{
		return instance;
	}
	
	public static void MakeServiceInformation(CCountry country)
	{
		instance = new ServiceInformation(country);
	}
	
	private ServiceInformation(CCountry country)
	{
		this.country=country;
	}
	
	public CCountry getCountryName()
	{
		return country;
	}
	
	public LinkedList<CBank> getBankList()
	{
		return country.getBankList();
	}
}
