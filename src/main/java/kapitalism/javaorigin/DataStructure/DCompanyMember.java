package kapitalism.javaorigin.DataStructure;

import kapitalism.javaorigin.Classes.CPeople;
import kapitalism.javaorigin.Enum.ECompanyPosition;
import kapitalism.javaorigin.Property.PAAccount;

public class DCompanyMember {
	private CPeople member;
	private PAAccount salaryAccount;
	private ECompanyPosition companyPosition;
	
	public DCompanyMember(CPeople people , PAAccount account , ECompanyPosition position)
	{
		this.member = people;
		this.salaryAccount = account;
		this.companyPosition = position;
	}
	
	public CPeople getMember() {
		return member;
	}
	public PAAccount getSalaryAccount() {
		return salaryAccount;
	}
	public ECompanyPosition getCompanyPosition() {
		return companyPosition;
	}
}
