package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Item.ItemAccount;

public interface ICompanyService {
	void joinCompany(CPeople newpeople , ItemAccount salaryaccount , ECompanyPosition position);
	void leaveCompany(CPeople leavemember);
}
