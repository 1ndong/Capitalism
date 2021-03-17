package com.indong.capitalism.Property;

import com.indong.capitalism.DataStructure.DTime;

public class PBasicData {
	protected DTime birth = new DTime(0,0,0);
	protected String name = "";
	protected int ID = 0;//사업자 등록번호 or 주민번호
	protected PAsset infoAsset = new PAsset();
	
	public PBasicData(DTime birth , String name)
	{
		this.birth = birth;
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public PAsset getInfoAsset() {
		return infoAsset;
	}
}
