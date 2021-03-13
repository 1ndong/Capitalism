package com.indong.capitalism.Classes;

import com.indong.capitalism.DataCenter.DataCenter;
import com.indong.capitalism.DataStructure.DCompanyMember;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.*;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Info.IAAccount;
import com.indong.capitalism.Info.InfoCompanyData;
import com.indong.capitalism.Interface.IBankService;
import com.indong.capitalism.Interface.ICompanyService;
import com.indong.capitalism.Interface.ISearchable;

import java.util.LinkedList;

public class CCompany extends CBeing implements ICompanyService , ISearchable {
	private int salaryDay = 0;
	protected LinkedList<DCompanyMember> staffList = new LinkedList<DCompanyMember>();
	protected DTime today = new DTime(0,0,0);

	protected ESectorType sectorType;

	public CCompany(CCountry country , DTime birth , String name , ESectorType sectorType)
	{
		super(country);
		type = EBeingType.Company;
		basicData = new InfoCompanyData(birth , name);
		this.sectorType = sectorType;

		registerObject();
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		today = newTime;
		if(today.getDay() == salaryDay)
		{
			IAAccount accountForPaySalary = null;
			for(int i = 0 ; i < basicData.getInfoAsset().getAccountList().size() ; i++)
			{
				IAAccount infoaccount = basicData.getInfoAsset().getAccountList().get(i);
				if(infoaccount.getAccountType() == EAccountType.Deposit)
				{
					accountForPaySalary = infoaccount;
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
						amount = 3000000;
						break;
					case SeniorClerk:
						amount = 3500000;
						break;
					case AssistantManager:
						amount = 4000000;
						break;
					case Manager:
						amount = 4500000;
						break;
					case DeputyGeneralManager:
						amount = 5000000;
						break;
					case GeneralManager:
						amount = 5500000;
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
		
		repaymentDebt(newTime);
	}
	
	public void joinCompany(CPeople newpeople , IAAccount salaryaccount , ECompanyPosition position)
	{
		DCompanyMember newmember = new DCompanyMember(newpeople,salaryaccount , position);
		staffList.add(newmember);
		FrameLog.getInstance().addLog("joincompany", newpeople.getBasicData().getName() + " " + getBasicData().getName() + " 에 입사완료");
	}

	public void leaveCompany(CPeople leavemember)
	{
		for(int i = 0 ; i < staffList.size() ; i++)
		{
			DCompanyMember temp = staffList.get(i);
			if(temp.getMember() == leavemember)
			{
				staffList.remove(i);
				FrameLog.getInstance().addLog("leaveCompany", leavemember.getBasicData().getName() + " " + getBasicData().getName() + " 에서 퇴사완료");
				break;
			}
		}
	}

	public int getSalaryDay() {
		return salaryDay;
	}

	public void setSalaryDay(int salaryDay) {
		this.salaryDay = salaryDay;
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.Company;
	}

	@Override
	public void registerObject() {
		DataCenter.getInstance().addNewObject(this);
	}

	public ESectorType getSectorType() {
		return sectorType;
	}
}
