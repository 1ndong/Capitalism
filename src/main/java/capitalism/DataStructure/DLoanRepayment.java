package capitalism.DataStructure;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DLoanRepayment {
	private int round;
	private long repaymentAmountOneMonth;
	private long repaymentInterest;
}
