package com.indong.capitalism.Info;

import com.indong.capitalism.DataStructure.DTime;

public class InfoPersonalData {
	private int mAge = 0;
	private String mName = "";
	private int mID = 0;
	private DTime birth = new DTime(0,0,0,"");
	
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

	public int getID() {
		return mID;
	}
	
	public void setID(int ID)
	{
		this.mID = ID;
	}
}
