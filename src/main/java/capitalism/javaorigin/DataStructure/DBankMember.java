package capitalism.javaorigin.DataStructure;

import java.util.LinkedList;

import capitalism.javaorigin.Classes.CBeing;
import capitalism.javaorigin.Classes.Bank.CBAccount;

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
