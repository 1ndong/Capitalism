package com.indong.capitalism.Info;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Item.ItemAccount;

public class InfoBasicData {
	protected DTime birth = new DTime(0,0,0,"");
	protected String name = "";
	protected int ID = 0;//사업자 등록번호 or 주민번호
	protected LinkedList<ItemAccount> accountList = new LinkedList<ItemAccount>();
	
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

	public LinkedList<ItemAccount> getAccountList() {
		return accountList;
	}
	
	public void addNewAccount(ItemAccount newaccount)
	{
		accountList.add(newaccount);
	}
}
