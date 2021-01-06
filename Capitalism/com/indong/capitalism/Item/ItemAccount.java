package com.indong.capitalism.Item;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;

public class ItemAccount implements IInterestRate{
	private int accountNumber = 0;
	private EAccountType accountType;
	private CBCommercial bank;
	private int rightsOfCash = 0;
	private float interestRate = 0.0f;
	
	public ItemAccount(CBCommercial bank , int accountNumber , EAccountType type , float interestRate)
	{
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.accountType = type;
		this.interestRate = interestRate;
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

	public int getRightsOfCash() {
		return rightsOfCash;
	}

	public void addRightsOfCash(int rightsOfCash) {
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
	
	//todo ITime 인터페이스 받아서 daychage될때마다 이자지급날 혹은 대출이자 회수날 잡아서 eaccounttype에 맞춰서 돈빼거나 넣어주거나 해줘야됨
}
