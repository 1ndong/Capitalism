package kapitalism.javaorigin.Property;

import kapitalism.javaorigin.Classes.Bank.CBank;
import kapitalism.javaorigin.Enum.EAccountType;

public class PAAccount {
	private String ownerName;
	private CBank bank;
	private int accountNumber;
	private EAccountType accountType;
	
	public PAAccount(String name , CBank bank , int accountNumber , EAccountType type)
	{
		this.ownerName = name;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
	}
	
	public CBank getBank() {
		return bank;
	}
	public int getAccountNumber() {
		return accountNumber;
	}
	public EAccountType getAccountType() {
		return accountType;
	}

	public String getOwnerName() {
		return ownerName;
	}
}
