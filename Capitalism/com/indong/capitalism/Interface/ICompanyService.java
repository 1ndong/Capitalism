package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Info.IAAccount;

public interface ICompanyService {
	void joinCompany(CPeople newpeople , IAAccount salaryaccount , ECompanyPosition position);
	void leaveCompany(CPeople leavemember);
}
