package com.indong.capitalism.info;

public class InfoPersonalData {
	private int mAge = 0;
	private String mName = "";
	private int mID = 0;
	private String mCompany = "";
	
	public InfoPersonalData(int age , String name)
	{
		this.mAge = age;
		this.mName = name;
	}

	public int getAge() {
		return mAge;
	}

	public String getName() {
		return mName;
	}

	public String getCompany() {
		return mCompany;
	}

	public void setCompany(String company) {
		this.mCompany = company;
	}

	public int getID() {
		return mID;
	}
	
	public void setID(int ID)
	{
		this.mID = ID;
	}
}
