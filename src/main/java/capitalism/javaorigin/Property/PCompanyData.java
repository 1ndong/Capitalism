package capitalism.javaorigin.Property;

import capitalism.javaorigin.DataStructure.DTime;

public class PCompanyData extends PBasicData {

	private PAAccount mainDepositAccount;

	public PCompanyData(DTime birth, String name) {
		super(birth, name);
	}

	public void setMainDepositAccount(PAAccount account)
	{
		mainDepositAccount = account;
	}

	public PAAccount getMainDepositAccount()
	{
		return mainDepositAccount;
	}
}
