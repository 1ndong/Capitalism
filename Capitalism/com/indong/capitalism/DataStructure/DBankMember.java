package com.indong.capitalism.DataStructure;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Item.ItemAccount;

public class DBankMember {
	private CPeople people;
	private LinkedList<ItemAccount> accountList = new LinkedList<ItemAccount>();
	
	public DBankMember(CPeople people)
	{
		this.people = people;
	}

	public CPeople getPeople() {
		return people;
	}

	public LinkedList<ItemAccount> getAccountList() {
		return accountList;
	}
}
