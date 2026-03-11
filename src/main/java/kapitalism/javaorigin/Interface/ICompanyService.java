package kapitalism.javaorigin.Interface;

import kapitalism.javaorigin.Classes.CPeople;
import kapitalism.javaorigin.Enum.ECompanyPosition;
import kapitalism.javaorigin.Property.PAAccount;

public interface ICompanyService {
	void joinCompany(CPeople newpeople , PAAccount salaryaccount , ECompanyPosition position);
	void leaveCompany(CPeople leavemember);
}
