package kapitalism.javaorigin.DataStructure;

public class DLoanRepayment {
	private long repaymentAmountOneMonth;
	private long repaymentInterest;
	private int round;
	
	public DLoanRepayment(int round , long amount , long interest)
	{
		this.round = round;
		repaymentAmountOneMonth = amount;
		repaymentInterest = interest;
	}

	public long getRepaymentAmountOneMonth() {
		return repaymentAmountOneMonth;
	}

	public long getRepaymentInterest() {
		return repaymentInterest;
	}

	public int getRound() {
		return round;
	}
}
