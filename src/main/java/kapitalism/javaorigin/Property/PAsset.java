package kapitalism.javaorigin.Property;

import java.util.LinkedList;

import kapitalism.javaorigin.Classes.Bank.CBAccount;

public class PAsset {
	private LinkedList<PAAccount> accountList = new LinkedList<PAAccount>();

	public LinkedList<PAAccount> getAccountList() {
		return accountList;
	}

	public void addNewAccountInfo(PAAccount newaccount) {
		this.accountList.add(newaccount);
	}
	
	public PAAccount findAccountInfo(CBAccount account)
	{
		PAAccount result = null;
		
		for(int i = 0 ; i < accountList.size() ; i++)
		{
			PAAccount temp = accountList.get(i);
			if(temp.getAccountNumber() == account.getAccountNumber())
			{
				if(temp.getBank() == account.getBank())
				{
					if(temp.getAccountType() == account.getAccountType())
					{
						result = temp;
						break;
					}
				}
			}
		}
		
		return result;
	}
}
