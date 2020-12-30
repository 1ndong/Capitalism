package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DCompanyMember;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Enum.ECompanyPosition;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoCompanyData;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ICompanyService;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Item.ItemAccount;

public class CCompany extends CBeing implements ITime,ICompanyService{
	protected int salaryDay = 0;
	protected LinkedList<DCompanyMember> staffList = new LinkedList<DCompanyMember>();
	protected InfoBasicData companyData;
	protected DTime today = new DTime(0,0,0,"");

	public CCompany(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Company;
		companyData = new InfoCompanyData(birth , name);
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		today = newTime;
		if(today.getDay() == salaryDay)
		{
			ItemAccount accountForPaySalary = null;
			for(int i = 0 ; i < companyData.getAccountList().size() ; i++)
			{
				ItemAccount temp = companyData.getAccountList().get(i);
				if(temp.getAccountType() == EAccountType.Deposit)
				{
					accountForPaySalary = temp;
					break;
				}
			}
			if(accountForPaySalary != null)
			{
				IBankService bankservice = (IBankService)accountForPaySalary.getBank();
				//뱅크서비스 통해서 직원들 월급을 은행계좌로 쏴줘야됨 직급따라서
				for(int i = 0 ; i < staffList.size() ; i++)
				{
					DCompanyMember temp = staffList.get(i);
					//move money company account -> temp account
					//기업 급여계좌의 뱅크 찾아서 ibankservice로 캐스팅 해서 sendmoney 기업계좌 to 멤버들 계좌
					//보낼때 직급에 맞춰서 돈 보내야 됨
					int amount = 0;
					switch(temp.getCompanyPosition()) 
					{
					case Clerk:
						amount = 300;
						break;
					case SeniorClerk:
						amount = 350;
						break;
					case AssistantManager:
						amount = 400;
						break;
					case Manager:
						amount = 450;
						break;
					case DeputyGeneralManager:
						amount = 500;
						break;
					case GeneralManager:
						amount = 550;
						break;
					default:
						amount = 0;
						break;
					}
					bankservice.sendMoney(accountForPaySalary, temp.getSalaryAccount(), amount);
				}
			}
			else
			{
				FrameLog.getInstance().addLog("daychange", "급여지급용 계좌 없음");
			}
		}
	}
	
	public void joinCompany(CPeople newpeople , ItemAccount salaryaccount , ECompanyPosition position)
	{
		DCompanyMember newmember = new DCompanyMember(newpeople,salaryaccount , position);
		staffList.add(newmember);
	}
	
	public void leaveCompany(CPeople leavemember)
	{
		for(int i = 0 ; i < staffList.size() ; i++)
		{
			DCompanyMember temp = staffList.get(i);
			if(temp.getMember() == leavemember)
			{
				staffList.remove(i);
				break;
			}
		}
	}

	public InfoBasicData getCompanyData() {
		return companyData;
	}
}
