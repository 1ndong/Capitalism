package com.indong.capitalism.DataStructure;

public class DLoan {
	private int loanMonth;//총 대출기간
	private long repaymentDuty;//원금
	
	public DLoan(int loanMonth , long repaymentDuty)
	{
		this.loanMonth = loanMonth;
		this.repaymentDuty = repaymentDuty;
	}

	public int getLoanMonth() {
		return loanMonth;
	}

	public long getRepaymentDuty() {
		return repaymentDuty;
	}
}
