package com.indong.capitalism.DataStructure;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Item.ItemAccount;

public class DBankMember {
	private CBeing client;
	private LinkedList<ItemAccount> accountList = new LinkedList<ItemAccount>();
	
	public DBankMember(CBeing client)
	{
		this.client = client;
	}

	public CBeing getClient() {
		return client;
	}

	public LinkedList<ItemAccount> getAccountList() {
		return accountList;
	}
}
