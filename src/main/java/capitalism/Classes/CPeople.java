package capitalism.Classes;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.DataCenter.DataCenter;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.EBeingType;
import capitalism.Enum.EGovernmentType;
import capitalism.Enum.ESearchType;
import capitalism.Interface.IBankService;
import capitalism.Interface.ISearchable;
import capitalism.Property.PAAccount;
import capitalism.Property.PCompanyData;
import capitalism.Property.PPersonalData;
import capitalism.Util.URandom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CPeople extends CBeing implements ISearchable {
	protected ESearchType searchType = ESearchType.People;

	// 소비 성향: 월급의 몇 %를 한 달간 소비하는지 (0.0 ~ 1.0)
	private float consumptionRate = 0.7f;
	// 월 소비 예산 (급여일에 계산됨)
	private long monthlyBudget = 0;
	// 이번 달 남은 소비 예산
	private long remainingBudget = 0;

	public CPeople(CCountry country, DTime birth, String name) {
		super(country);
		type = EBeingType.Personal;
		basicData = new PPersonalData(birth, name);
		// 소비 성향 개인차: 60% ~ 85%
		consumptionRate = 0.6f + (URandom.getInstance().nextFloat() * 0.25f);
		registerObject();
	}

	@Override
	public void dayChange(DTime newTime) {
		repaymentDebt(newTime);
		consume(newTime);
	}

	private void consume(DTime today) {
		// 예금 계좌에서 소비 가능한 금액 확인
		PAAccount myDepositInfo = findMyDepositAccount();
		if (myDepositInfo == null) return;

		IBankService bankService = (IBankService) myDepositInfo.getBank();
		CBAccount myAccount = bankService.findAccount(myDepositInfo.getOwnerName(), myDepositInfo.getAccountNumber());
		if (myAccount == null || myAccount.getRightsOfCash() <= 0) return;

		// 매일 월예산의 1/30 소비
		long dailySpend = monthlyBudget / 30;
		if (dailySpend <= 0) return;
		if (remainingBudget <= 0) return;

		// 실제 소비 가능 금액
		long actualSpend = Math.min(dailySpend, Math.min(remainingBudget, myAccount.getRightsOfCash()));
		if (actualSpend <= 0) return;

		// 소비 대상 기업 찾기 (랜덤)
		CCompany targetCompany = findRandomCompany();
		if (targetCompany == null) return;
		if (!(targetCompany.getBasicData() instanceof PCompanyData cd)) return;

		PAAccount companyDepositInfo = cd.getMainDepositAccount();
		if (companyDepositInfo == null) return;

		// 계좌이체로 소비
		boolean success = bankService.sendMoney(myDepositInfo, companyDepositInfo, actualSpend);
		if (success) {
			remainingBudget -= actualSpend;
		}
	}

	// 급여 수령 시 호출 (외부에서 급여가 입금된 후)
	public void onSalaryReceived(long salaryAmount) {
		monthlyBudget = (long) (salaryAmount * consumptionRate);
		remainingBudget = monthlyBudget;
	}

	private PAAccount findMyDepositAccount() {
		for (PAAccount account : basicData.getPropertyAsset().getAccountList()) {
			if (account.getAccountType() == EAccountType.Deposit) {
				return account;
			}
		}
		return null;
	}

	private CCompany findRandomCompany() {
		CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy)
				getMyCountry().getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
		if (motia == null) return null;

		List<CCompany> companies = new ArrayList<>(motia.getCompanyMap().values());
		if (companies.isEmpty()) return null;

		int idx = URandom.getInstance().nextInt(companies.size());
		return companies.get(idx);
	}

	@Override
	public boolean isTypeOf(ESearchType type) {
		return type == ESearchType.People;
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
