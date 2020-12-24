package com.indong.capitalism.Interface;

public interface ICompany {
	void joinCompany();
	void leaveCompany();
	
	void paySalary();
	
	final int SalaryClerk = 300;
	final int SalarySeniorClerk = 350;
	final int SalaryAssistantManager = 400;
	final int SalaryManager = 450;
	final int SalaryDeputyGeneralManager = 500;
	final int SalaryGeneralManager = 550;
}
