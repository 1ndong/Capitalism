package capitalism.Interface;

import capitalism.Classes.CPeople;
import capitalism.Enum.ECompanyPosition;
import capitalism.Property.PAAccount;

public interface ICompanyService {
	void joinCompany(CPeople newpeople , PAAccount salaryaccount , ECompanyPosition position);
	void leaveCompany(CPeople leavemember);
}
