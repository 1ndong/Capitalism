package capitalism.javaorigin.Interface;

import capitalism.javaorigin.Classes.CPeople;
import capitalism.javaorigin.Enum.ECompanyPosition;
import capitalism.javaorigin.Property.PAAccount;

public interface ICompanyService {
	void joinCompany(CPeople newpeople , PAAccount salaryaccount , ECompanyPosition position);
	void leaveCompany(CPeople leavemember);
}
