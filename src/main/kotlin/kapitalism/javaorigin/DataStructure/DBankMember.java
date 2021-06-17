package kapitalism.javaorigin.DataStructure;

import java.util.LinkedList;

import kapitalism.javaorigin.Classes.CBeing;
import kapitalism.javaorigin.Classes.Bank.CBAccount;

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
