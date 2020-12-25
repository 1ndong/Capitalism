package com.indong.capitalism.Interface;

public interface ICompany {
	void joinCompany();
	void leaveCompany();
	
	void paySalary();
	
	final int SalaryClerk = 4000;
	final int SalarySeniorClerk = 4500;
	final int SalaryAssistantManager = 5000;
	final int SalaryManager = 5500;
	final int SalaryDeputyGeneralManager = 6000;
	final int SalaryGeneralManager = 6500;
}
