package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DBankMember;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Info.IAAccount;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Item.ItemAccount;

public class CBCommercial extends CBank implements IBankService{
	private LinkedList<DBankMember> bankmemberList = new LinkedList<DBankMember>();
	private int lastAccountNumber = 0;
	private float spreadInterestRate = 2.0f;
	
	public CBCommercial(CCountry country , String name)
	{
		super(country ,name);
	}
	
	public LinkedList<DBankMember> getBankMemberList() {
		return bankmemberList;
	}
	
	@Override
	public ItemAccount makeNewAccount(CBeing newclient , EAccountType type)
	{
		ItemAccount na = new ItemAccount(this, makeUniqueAccountNumber(), type);
		
		IAAccount ia = new IAAccount(newclient.getBasicData().getName(),this,na.getAccountNumber(),type);
		newclient.getBasicData().getInfoAsset().addNewBankInfo(ia);
		
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
	public boolean sendMoney(IAAccount sender, IAAccount receiver , int amount) {
		// TODO Auto-generated method stub
		ItemAccount senderAccount = ((IBankService)sender.getBank()).findAccount(sender.getOwnerName() , sender.getAccountNumber());
		ItemAccount receiverAccount = ((IBankService)receiver.getBank()).findAccount(receiver.getOwnerName(), receiver.getAccountNumber());
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
	}

	@Override
	public int withdrawCash(IAAccount account, int amount) {
		// TODO Auto-generated method stub
		ItemAccount realaccount = ((IBankService)account.getBank()).findAccount(account.getOwnerName(), account.getAccountNumber());
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
	public void depositCash(IAAccount account, CACCash cash , int amount) {
		// TODO Auto-generated method stub
		ItemAccount realAccount = ((IBankService)account.getBank()).findAccount(account.getOwnerName(), account.getAccountNumber());
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
	public ItemAccount findAccount(String name, int accountNumber) {
		// TODO Auto-generated method stub
		ItemAccount result = null;
		
		for(int i = 0 ; i < bankmemberList.size() ; i++)
		{
			DBankMember temp = bankmemberList.get(i);
			CBeing being = temp.getClient();
			String tempname = being.getBasicData().getName();
			if(tempname.equalsIgnoreCase(name) == true)
			{
				for(int j = 0 ; j < temp.getAccountList().size() ; j++)
				{
					ItemAccount account = temp.getAccountList().get(j);
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
	public void raiseLoan(ItemAccount account, int amount) {
		// TODO Auto-generated method stub
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
		account.addRightsOfCash(amount);
		FrameLog.getInstance().addLog("raiseLoan","대출 성공");
	}
}
