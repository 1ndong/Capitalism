package capitalism.Classes;

import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DCompanyMember;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.EBeingType;
import capitalism.Enum.ECompanyPosition;
import capitalism.Enum.ESearchType;
import capitalism.Interface.IBankService;
import capitalism.Interface.ICompanyService;
import capitalism.Interface.ISearchable;
import capitalism.Property.PAAccount;
import capitalism.Property.PCompanyData;
import capitalism.UI.FrameLog;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class CCompany extends CBeing implements ICompanyService, ISearchable {
	@Setter
	private int salaryDay = 0;
	protected LinkedList<DCompanyMember> staffList = new LinkedList<>();
	protected DTime today = new DTime(0, 0, 0);
	protected ESearchType searchType = ESearchType.Company;

	public CCompany(CCountry country, DTime birth, String name) {
		super(country);
		type = EBeingType.Company;
		basicData = new PCompanyData(birth, name);
		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
		today = newTime;
		if (today.getDay() == salaryDay) {
			PAAccount accountForPaySalary = null;
			for (int i = 0; i < basicData.getPropertyAsset().getAccountList().size(); i++) {
				PAAccount infoaccount = basicData.getPropertyAsset().getAccountList().get(i);
				if (infoaccount.getAccountType() == EAccountType.Deposit) {
					accountForPaySalary = infoaccount;
					break;
				}
			}

			if (accountForPaySalary != null) {
				IBankService bankservice = (IBankService) accountForPaySalary.getBank();
				for (int i = 0; i < staffList.size(); i++) {
					DCompanyMember temp = staffList.get(i);
					int amount = switch (temp.getCompanyPosition()) {
						case Clerk -> 3000000;
						case SeniorClerk -> 3500000;
						case AssistantManager -> 4000000;
						case Manager -> 4500000;
						case DeputyGeneralManager -> 5000000;
						case GeneralManager -> 5500000;
						default -> 0;
					};
					boolean paid = bankservice.sendMoney(accountForPaySalary, temp.getSalaryAccount(), amount);
					if (paid) {
						temp.getMember().onSalaryReceived(amount);
						FrameLog.getInstance().addLog("salary", getBasicData().getName() + " -> " + temp.getMember().getBasicData().getName() + " 급여 " + amount + "원 지급");
					}
				}
			} else {
				FrameLog.getInstance().addLog("daychange", "급여지급용 계좌 없음");
			}
		}

		repaymentDebt(newTime);
	}

	public void joinCompany(CPeople newpeople, PAAccount salaryaccount, ECompanyPosition position) {
		DCompanyMember newmember = new DCompanyMember(newpeople, salaryaccount, position);
		staffList.add(newmember);
		FrameLog.getInstance().addLog("joincompany", newpeople.getBasicData().getName() + " " + getBasicData().getName() + " 에 입사완료");
	}

	public void leaveCompany(CPeople leavemember) {
		for (int i = 0; i < staffList.size(); i++) {
			DCompanyMember temp = staffList.get(i);
			if (temp.getMember() == leavemember) {
				staffList.remove(i);
				FrameLog.getInstance().addLog("leaveCompany", leavemember.getBasicData().getName() + " " + getBasicData().getName() + " 에서 퇴사완료");
				break;
			}
		}
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
	public String getSearchableOriginName() {
		return getBasicData().getName();
	}
}
