package kapitalism.javaorigin.Classes;

import kapitalism.javaorigin.DataCenter.DataCenter;
import kapitalism.javaorigin.DataStructure.DCompanyMember;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.EAccountType;
import kapitalism.javaorigin.Enum.EBeingType;
import kapitalism.javaorigin.Enum.ECompanyPosition;
import kapitalism.javaorigin.Enum.ESearchType;
import kapitalism.javaorigin.Interface.IBankService;
import kapitalism.javaorigin.Interface.ICompanyService;
import kapitalism.javaorigin.Interface.ISearchable;
import kapitalism.javaorigin.Property.PAAccount;
import kapitalism.javaorigin.Property.PCompanyData;
import kapitalism.javaorigin.UI.FrameLog;

import java.util.LinkedList;

public class CCompany extends CBeing implements ICompanyService , ISearchable  {
	private int salaryDay = 0;
	protected LinkedList<DCompanyMember> staffList = new LinkedList<DCompanyMember>();
	protected DTime today = new DTime(0,0,0);

	protected ESearchType searchType = ESearchType.Company;

	public CCompany(CCountry country , DTime birth , String name)
	{
		super(country);
		type = EBeingType.Company;
		basicData = new PCompanyData(birth , name);

		registerObject();
	}
	
	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		today = newTime;
		if(today.getDay() == salaryDay)
		{
			PAAccount accountForPaySalary = null;
			for(int i = 0; i < basicData.getPropertyAsset().getAccountList().size() ; i++)
			{
				PAAccount infoaccount = basicData.getPropertyAsset().getAccountList().get(i);
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
	
	public void joinCompany(CPeople newpeople , PAAccount salaryaccount , ECompanyPosition position)
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

	@Override
	public ESearchType getSearchType() {
		return searchType;
	}

	@Override
	public String getSearchableOriginName() {
		return getBasicData().getName();
	}
}
