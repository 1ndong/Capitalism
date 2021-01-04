package com.indong.capitalism.Info;

import com.indong.capitalism.DataStructure.DTime;

public class InfoBasicData {
	protected DTime birth = new DTime(0,0,0,"");
	protected String name = "";
	protected int ID = 0;//사업자 등록번호 or 주민번호
	protected InfoAsset infoAsset = new InfoAsset();
	
	public InfoBasicData(DTime birth , String name)
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

	public InfoAsset getInfoAsset() {
		return infoAsset;
	}
}
