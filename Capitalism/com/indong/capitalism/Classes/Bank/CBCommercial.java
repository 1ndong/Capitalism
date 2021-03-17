package com.indong.capitalism.Classes.Bank;

import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.DataStructure.DBankMember;
import com.indong.capitalism.DataStructure.DLoan;
import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.UI.FrameLog;
import com.indong.capitalism.Property.PAAccount;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.IInterestChanger;
import com.indong.capitalism.Interface.IInterestRate;
import com.indong.capitalism.Interface.ISector;

import java.util.ArrayList;
import java.util.LinkedList;

public class CBCommercial extends CBank implements IBankService , IInterestChanger , IInterestRate , ISector {
	private LinkedList<DBankMember> bankmemberList = new LinkedList<DBankMember>();
	private int lastAccountNumber = 0;
	private float spreadInterestRate = 0.0f;
	private LinkedList<IInterestRate> accountList = new LinkedList<IInterestRate>();

	protected int sector = 0;
	protected ArrayList<DService> serviceList = null;

	public CBCommercial(CCountry country , String name)
	{
		super(country ,name);
		((IInterestChanger)getMyCountry().getCentralBank()).addAccount(this);
	}
	
	public LinkedList<DBankMember> getBankMemberList() {
		return bankmemberList;
	}
	
	@Override
	public CBAccount makeNewAccount(CBeing newclient , EAccountType type , DTime interestDay)
	{
		float confirmInterestRate = getMyCountry().getCentralBank().getBaseInterestRate() + getSpreadInterestRate();
		CBAccount na = new CBAccount(newclient.getBasicData().getName() , this, makeUniqueAccountNumber(), type , confirmInterestRate , interestDay);
		
		PAAccount ia = new PAAccount(newclient.getBasicData().getName(),this,na.getAccountNumber(),type);
		newclient.getBasicData().getInfoAsset().addNewAccountInfo(ia);
		
		for(int i = 0 ; i < bankmemberList.size() ; i++)
		{
			DBankMember bm = bankmemberList.get(i);
			if(bm.getClient() == newclient)
			{
				//people이 잇으면 있는거에 리스트에다가 넣고
				bm.getAccountList().add(na);
				return na;
			}
		}
		
		//없으면 새로 만들어서 리스트에다가 새로 넣고
		DBankMember newmember = new DBankMember(newclient);
		newmember.getAccountList().add(na);
		bankmemberList.add(newmember);
		return na;
	}
	
	private int makeUniqueAccountNumber()
	{
		return ++lastAccountNumber;
	}

	@Override
	public boolean sendMoney(PAAccount sender, PAAccount receiver , long amount) {
		// TODO Auto-generated method stub
		CBAccount senderAccount = ((IBankService)sender.getBank()).findAccount(sender.getOwnerName() , sender.getAccountNumber());
		CBAccount receiverAccount = ((IBankService)receiver.getBank()).findAccount(receiver.getOwnerName(), receiver.getAccountNumber());
		if(senderAccount.getRightsOfCash() < amount)
		{
			FrameLog.getInstance().addLog("sendmoney", "잔액부족");
			return false;
		}
		else
		{
			//계좌의 현금권리를 넘겨주고
			senderAccount.addRightsOfCash(-amount);
			receiverAccount.addRightsOfCash(amount);
			
			if(sender.getBank() != receiver.getBank())
			{//실제 현금 현물도 옮겨준다 다른은행이면
				senderAccount.getBank().getBalance().addCash(-amount);
				receiverAccount.getBank().getBalance().addCash(amount);
			}
		}
		return true;
	}

	public float getSpreadInterestRate() {
		return spreadInterestRate;
	}

	public void setSpreadInterestRate(float spreadInterestRate) {
		this.spreadInterestRate = spreadInterestRate;
		FrameLog.getInstance().addLog("interestchange", getName() + "은행 가산금리 변동" + spreadInterestRate + "%");
		interestChange();
	}

	@Override
	public long withdrawCash(PAAccount account, long amount) {
		// TODO Auto-generated method stub
		CBAccount realaccount = ((IBankService)account.getBank()).findAccount(account.getOwnerName(), account.getAccountNumber());
		if(realaccount.getRightsOfCash() < amount)
		{
			FrameLog.getInstance().addLog("withdrawcash","잔액부족");
			return 0;
		}
		else 
		{
			realaccount.addRightsOfCash(-amount);
			if(realaccount.getBank().getBalance().getCash() < amount)
			{
				FrameLog.getInstance().addLog("withdrawcash", "은행 잔고 부족 bank run");
				return 0;
			}
			else
			{
				realaccount.getBank().getBalance().addCash(-amount);
				
				return amount;	
			}
		}
	}

	@Override
	public void depositCash(PAAccount account, CACCash cash , long amount) {
		// TODO Auto-generated method stub
		CBAccount realAccount = ((IBankService)account.getBank()).findAccount(account.getOwnerName(), account.getAccountNumber());
		if(cash.getCash() < amount)
		{
			FrameLog.getInstance().addLog("depositcash", "맡길 금액 부족");
			return;
		}
		realAccount.addRightsOfCash(amount);
		realAccount.getBank().getBalance().addCash(amount);
		cash.addCash(-amount);
	}

	@Override
	public CBAccount findAccount(String name, int accountNumber) {
		// TODO Auto-generated method stub
		CBAccount result = null;
		
		for(int i = 0 ; i < bankmemberList.size() ; i++)
		{
			DBankMember temp = bankmemberList.get(i);
			CBeing being = temp.getClient();
			String tempname = being.getBasicData().getName();
			if(tempname.equalsIgnoreCase(name) == true)
			{
				for(int j = 0 ; j < temp.getAccountList().size() ; j++)
				{
					CBAccount account = temp.getAccountList().get(j);
					if(account.getAccountNumber() == accountNumber)
					{
						result = account;
					}
				}
			}
		}
		return result;
	}

	@Override
	public void raiseLoan(CBAccount account, long amount , int loanMonth,int repaymentDay) {
		// TODO Auto-generated method stub
		if(account.getBank() != this)
		{
			FrameLog.getInstance().addLog("raiseLoan", "해당은행 계좌가 아니므로 대출 불가");
			return;
		}
		if(getBalance().getCash() < amount)
		{
			FrameLog.getInstance().addLog("raiseLoan", "은행 잔고 부족");
			return;
		}
		if(account.getAccountType() != EAccountType.Loan)
		{
			FrameLog.getInstance().addLog("raiseLoan", "대출통장 아님");
			return;
		}
		//todo 빌려주는 amount 를 은행 잔고의 지급준비율을 제외한 한도내에서 빌려줘야 한다
		account.addRightsOfCash(amount);
		account.setLoanData(new DLoan(loanMonth , amount));
		account.setInterestDay(new DTime(0,0,repaymentDay));
		FrameLog.getInstance().addLog("raiseLoan","대출 성공");
	}

	@Override
	public void addAccount(IInterestRate account) {
		// TODO Auto-generated method stub
		accountList.add(account);
	}

	@Override
	public void interestChange() {
		// TODO Auto-generated method stub
		float newinterestRate = getMyCountry().getCentralBank().getBaseInterestRate() + getSpreadInterestRate();
		for(int i = 0 ; i < accountList.size() ; i++)
		{
			accountList.get(i).interestChange(newinterestRate);
		}
	}

	@Override
	public void interestChange(float newinterestrate) {
		// TODO Auto-generated method stub
		interestChange();
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		//이자 나눠주기
		for(int i = 0 ; i < getBankMemberList().size() ; i++)
		{
			DBankMember member = getBankMemberList().get(i);
			for(int j = 0 ; j < member.getAccountList().size() ; j++)
			{
				CBAccount account = member.getAccountList().get(j);
				if(account.getInterestDay().getDay() == newTime.getDay())
				{
					if(account.getAccountType() == EAccountType.Deposit)
					{
						//+[todo]
						//이자계산 어렵다

					}
				}
			}
		}
	}

	@Override
	public int getSector() {
		return sector;
	}

	@Override
	public void setSector(int newsector) {
		sector = newsector;
	}

	@Override
	public ArrayList<DService> getServiceList() {
		return serviceList;
	}

	@Override
	public void setServiceList(ArrayList<DService> list) {
		serviceList = list;
	}
}
