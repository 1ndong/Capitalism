package com.indong.capitalism.DataStructure;

import java.util.LinkedList;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Bank.CBAccount;

public class DBankMember {
	private CBeing client;
	private LinkedList<CBAccount> accountList = new LinkedList<CBAccount>();
	
	public DBankMember(CBeing client)
	{
		this.client = client;
	}

	public CBeing getClient() {
		return client;
	}

	public LinkedList<CBAccount> getAccountList() {
		return accountList;
	}
}
