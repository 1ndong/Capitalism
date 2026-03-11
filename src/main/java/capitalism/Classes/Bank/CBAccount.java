package capitalism.Classes.Bank;

import capitalism.DataStructure.DLoan;
import capitalism.DataStructure.DLoanRepayment;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECurrency;
import capitalism.UI.FrameLog;
import capitalism.Interface.IInterestChanger;
import capitalism.Interface.IInterestRate;
import capitalism.Util.UCurrency;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class CBAccount implements IInterestRate {
	private int accountNumber = 0;
	private EAccountType accountType;
	private CBank bank;
	private long rightsOfCash = 0;
	@Setter
	private float interestRate = 0.0f;
	@Setter
	private DTime interestDay = new DTime(0, 0, 0);
	private String ownerName;

	//loan only
	private DLoan loanData;
	private LinkedList<DLoanRepayment> repaymentPlanList = new LinkedList<>();

	//saving only
	private DTime expireDay = new DTime(0, 0, 0);

	public CBAccount(String ownerName, CBank bank, int accountNumber, EAccountType type, float interestRate, DTime interestDay) {
		this.ownerName = ownerName;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
		this.interestRate = interestRate;
		this.interestDay = interestDay;
		((IInterestChanger) bank).addAccount(this);
		FrameLog.getInstance().addLog("Itemaccount", "계좌 생성 완료 금리 : " + interestRate + "%");
	}

	public void addRightsOfCash(long rightsOfCash) {
		this.rightsOfCash += rightsOfCash;
	}

	@Override
	public void interestChange(float newinterestrate) {
		interestRate = newinterestrate;
		FrameLog.getInstance().addLog("interestChange", "최종금리 : " + newinterestrate + "%");
	}

	public void repaymentDebt() {
		if (getAccountType() != EAccountType.Loan) {
			return;
		}
		if (repaymentPlanList.size() == 0) {
			return;
		}
		DLoanRepayment temp = repaymentPlanList.removeFirst();
		long amount = temp.getRepaymentAmountOneMonth() + temp.getRepaymentInterest();
		if (rightsOfCash < amount) {
			FrameLog.getInstance().addLog("repaymentDebt", "[todo]상환능력 부족 신용도 하락 연체율 상승 가산이자 발생");
			return;
		}
		rightsOfCash -= amount;

		if (repaymentPlanList.size() == 0) {
			FrameLog.getInstance().addLog("repaymentDebt", "대출 상환완료!");
		} else {
			FrameLog.getInstance().addLog("repaymentDebt",
					getOwnerName() + "님의 " + temp.getRound() + " 회차 대출원금 "
							+ UCurrency.getInstance().toString(temp.getRepaymentAmountOneMonth(), ECurrency.Won)
							+ "원, 이자 "
							+ UCurrency.getInstance().toString(temp.getRepaymentInterest(), ECurrency.Won)
							+ " 원이 상환되었습니다");
		}
	}

	public long getRepaymentDuty() {
		if (loanData != null) {
			return loanData.getRepaymentDuty();
		} else
			return 0L;
	}

	public void setLoanData(DLoan loanData) {
		this.loanData = loanData;

		long repaymentDuty = loanData.getRepaymentDuty();
		long onemonth = loanData.getRepaymentDuty() / loanData.getLoanMonth();
		for (int i = 0; i < loanData.getLoanMonth(); i++) {
			long interest = (long) ((repaymentDuty * (getInterestRate() / 100)) / 12);
			repaymentDuty -= onemonth;
			repaymentPlanList.add(new DLoanRepayment(i + 1, onemonth, interest));
		}
	}
}
