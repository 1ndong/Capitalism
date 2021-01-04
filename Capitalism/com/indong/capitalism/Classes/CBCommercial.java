package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DBankMember;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Frame.FrameLog;
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
	{//은행에 계좌만들기전에 회사처럼 따로 가입할 필요없이 계좌만드는순간 멤버가 됨
		ItemAccount na = new ItemAccount(this, makeUniqueAccountNumber(), type);
		
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
		return na;
	}
	
	private int makeUniqueAccountNumber()
	{
		return ++lastAccountNumber;
	}

	@Override
	public boolean sendMoney(ItemAccount sender, ItemAccount receiver , int amount) {
		// TODO Auto-generated method stub
		if(sender.getRightsOfCash() < amount)
		{
			FrameLog.getInstance().addLog("sendmoney", "잔액부족");
			return false;
		}
		else
		{
			//계좌의 현금권리를 넘겨주고
			sender.addRightsOfCash(-amount);
			receiver.addRightsOfCash(amount);
			
			if(sender.getBank() != receiver.getBank())
			{//실제 현금 현물도 옮겨준다 다른은행이면
				sender.getBank().getBalance().addCash(-amount);
				receiver.getBank().getBalance().addCash(amount);
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
	public int withdrawCash(ItemAccount account, int amount) {
		// TODO Auto-generated method stub
		if(account.getRightsOfCash() < amount)
		{
			FrameLog.getInstance().addLog("withdrawcash","잔액부족");
			return 0;
		}
		else 
		{
			account.addRightsOfCash(-amount);
			if(account.getBank().getBalance().getCash() < amount)
			{
				FrameLog.getInstance().addLog("withdrawcash", "은행 잔고 부족 bank run");
				return 0;
			}
			else
			{
				account.getBank().getBalance().addCash(-amount);
				
				return amount;	
			}
		}
	}

	@Override
	public void depositCash(ItemAccount account, CACCash cash , int amount) {
		// TODO Auto-generated method stub
		if(cash.getCash() < amount)
		{
			FrameLog.getInstance().addLog("depositcash", "맡길 금액 부족");
			return;
		}
		account.addRightsOfCash(amount);
		account.getBank().getBalance().addCash(amount);
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
					ItemAccount account = temp.getAccountList().get(i);
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
	public void raiseLoan(CBeing being, int amount) {
		// TODO Auto-generated method stub
		if(getBalance().getCash() < amount)
		{
			FrameLog.getInstance().addLog("raiseLoan", "은행 잔고 부족");
			return;
		}
		ItemAccount account = makeNewAccount(being, EAccountType.Loan);
		account.addRightsOfCash(amount);
	}
}
