package kapitalism.javaorigin.Property;

import kapitalism.javaorigin.DataStructure.DTime;

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
