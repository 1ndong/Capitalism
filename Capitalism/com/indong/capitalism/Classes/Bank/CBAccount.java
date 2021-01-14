package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.DataStructure.DLoan;
import com.indong.capitalism.DataStructure.DLoanRepayment;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.ECurrency;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;
import com.indong.capitalism.Util.UCurrency;

import java.util.LinkedList;

public class CBAccount implements IInterestRate{
	private int accountNumber = 0;
	private EAccountType accountType;
	private CBCommercial bank;
	private long rightsOfCash = 0;
	private float interestRate = 0.0f;
	private DTime interestDay = new DTime(0,0,0);
	private String ownerName;
	
	//loan only
	private DLoan loanData;
	private LinkedList<DLoanRepayment> repaymentPlanList = new LinkedList<DLoanRepayment>();
	
	public CBAccount(String ownerName , CBCommercial bank , int accountNumber , EAccountType type , float interestRate , DTime interestDay)
	{
		this.ownerName = ownerName; 
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
		this.interestRate = interestRate;
		this.interestDay = interestDay;
		((IInterestChanger)bank).addAccount(this);
		FrameLog.getInstance().addLog("Itemaccount", "계좌 생성 완료 금리 : "+ interestRate + "%");
	}
	
	public CBCommercial getBank()
	{
		return bank;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public EAccountType getAccountType()
	{
		return accountType;
	}

	public long getRightsOfCash() {
		return rightsOfCash;
	}

	public void addRightsOfCash(long rightsOfCash) {
		this.rightsOfCash += rightsOfCash;
	}

	public void setInterestRate(float newinterestrate)
	{
		this.interestRate = newinterestrate;
	}
	
	public float getInterestRate() {
		return interestRate;
	}

	@Override
	public void interestChange(float newinterestrate) {
		// TODO Auto-generated method stub
		interestRate = newinterestrate;
		FrameLog.getInstance().addLog("interestChange", "최종금리 : " + newinterestrate + "%");
	}

	public DTime getInterestDay() {
		return interestDay;
	}

	public void setInterestDay(DTime interestDay) {
		this.interestDay = interestDay;
	}

	public void repaymentDebt()
	{
		if(getAccountType() != EAccountType.Loan)
		{
			return;
		}
		
		if(repaymentPlanList.size() == 0)
		{
			return;
		}
		DLoanRepayment temp = repaymentPlanList.removeFirst();
		long amount = temp.getRepaymentAmountOneMonth() + temp.getRepaymentInterest();
		if(rightsOfCash < amount)
		{
			FrameLog.getInstance().addLog("repaymentDebt", "[todo]상환능력 부족 신용도 하락 연체율 상승 가산이자 발생");
			return;
		}
		//은행에 돈줄필요없음 현금받은것도 아니고 애초에 현금에대한 권리만 받은거임
		rightsOfCash -= amount;
		
		if(repaymentPlanList.size() == 0)
		{
			FrameLog.getInstance().addLog("repaymentDebt", "대출 상환완료!");
		}
		else
		{
			FrameLog.getInstance().addLog("repaymentDebt" 
					, getOwnerName() + "님의 " +temp.getRound() + " 회차 대출원금 "
					+ UCurrency.getInstance().toString(temp.getRepaymentAmountOneMonth(), ECurrency.Won)
					+ "원, 이자 "
					+ UCurrency.getInstance().toString(temp.getRepaymentInterest(),ECurrency.Won)
					+" 원이 상환되었습니다");
		}
	}
	
	public long getRepaymentDuty() {//total 원금
		if(loanData != null)
		{
			return loanData.getRepaymentDuty();
		}
		else
			return 0L;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public DLoan getLoanData() {
		return loanData;
	}

	public void setLoanData(DLoan loanData) {
		this.loanData = loanData;
		
		long repaymentDuty = loanData.getRepaymentDuty();
		//원금 균등상환
		long onemonth = loanData.getRepaymentDuty() / loanData.getLoanMonth();
		for(int i = 0 ; i < loanData.getLoanMonth() ; i++)
		{
			long interest = (long) ((repaymentDuty * (getInterestRate() / 100)) / 12);//interestrate는 %이므로 실제 곱할때는 /100해줘야됨
			repaymentDuty -= onemonth;
			repaymentPlanList.add(new DLoanRepayment(i+1 , onemonth, interest));
		}	
	}
}
