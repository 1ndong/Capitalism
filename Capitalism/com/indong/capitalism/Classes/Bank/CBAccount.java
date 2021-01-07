package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;

public class CBAccount implements IInterestRate{
	private int accountNumber = 0;
	private EAccountType accountType;
	private CBCommercial bank;
	private long rightsOfCash = 0;
	private float interestRate = 0.0f;
	private DTime interestDay = new DTime(0,0,0,"");
	
	//loan only
	private int loanMonth = 0;
	private long initLoanMoneyAmount = 0L;
	
	public CBAccount(CBCommercial bank , int accountNumber , EAccountType type , float interestRate , DTime interestDay)
	{
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

	public int getLoanMonth() {
		return loanMonth;
	}

	public void setLoanMonth(int loanMonth) {
		this.loanMonth = loanMonth;
	}

	public long getOneMonthRepaymentAmount() {
		//총 개월수만큼 갚아야될 총 금액 나오고
		//일단은 계산하기 어려우니까 원금 균등상환부터 todo 원리금균등상환 , 만기일시
		//원금은 총개월수로 나누면됨
		//잔여원금 * 이자율 / 12 -> repaymentamount
		long oneMonthRepaymentAmount = (long) ((getInitLoanMoneyAmount() * getInterestRate()) / 12.0f);
		
		return oneMonthRepaymentAmount;
	}

	public long getInitLoanMoneyAmount() {
		return initLoanMoneyAmount;
	}

	public void setInitLoanMoneyAmount(long initLoanMoneyAmount) {
		this.initLoanMoneyAmount = initLoanMoneyAmount;
	}
}
