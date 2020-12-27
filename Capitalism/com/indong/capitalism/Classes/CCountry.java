package com.indong.capitalism.Classes;

import java.util.LinkedList;

public class CCountry {
	private String name;
	private CBCentral centralBank = new CBCentral();
	private LinkedList<CBank> bankList = new LinkedList<CBank>();
	private LinkedList<CCompany> companyList = new LinkedList<CCompany>();
	
	public CCountry(String name)
	{
		this.name = name;
	}
	
	public String getCountryName()
	{
		return name;
	}

	public LinkedList<CBank> getBankList() {
		return bankList;
	}

	public CBCentral getCentralBank() {
		return centralBank;
	}

	public LinkedList<CCompany> getCompanyList() {
		return companyList;
	}
}
