package capitalism.Classes.Government;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCentral;
import capitalism.Classes.CCompany;
import capitalism.Classes.CCountry;
import capitalism.Classes.CPeople;
import capitalism.DataStructure.DBankMember;
import capitalism.DataStructure.DCompanyMember;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECurrency;
import capitalism.Enum.EGovernmentType;
import capitalism.Interface.IBankService;
import capitalism.Property.PAAccount;
import capitalism.UI.FrameLog;
import capitalism.Util.UCurrency;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
public class CGNationalTaxService extends CGovernment {

    // 세율 (%)
    @Setter private float earnedIncomeTaxRate = 6.0f;   // 근로소득세
    @Setter private float corporateTaxRate = 10.0f;      // 법인세

    // 징수일
    private int taxCollectionDay = 10; // 매월 10일

    // 누적 징수액
    private long totalEarnedIncomeTax = 0;
    private long totalCorporateTax = 0;
    private long monthlyEarnedIncomeTax = 0;
    private long monthlyCorporateTax = 0;

    public CGNationalTaxService(CCountry country) {
        super(country);
    }

    @Override
    public void dayChange(DTime newTime) {
        if (newTime.getDay() != taxCollectionDay) return;

        monthlyEarnedIncomeTax = 0;
        monthlyCorporateTax = 0;

        CCountry country = getMyCountry();
        CBCentral centralBank = country.getCentralBank();

        // 근로소득세: 개인 예금 잔고 기준 월급의 세율% 징수
        CGMinistryOfHealthAndWelfare mohaw = (CGMinistryOfHealthAndWelfare)
                country.getGovernmentMap().get(EGovernmentType.HealthAndWelfare);
        if (mohaw != null) {
            for (CPeople person : mohaw.getPeopleMap().values()) {
                collectEarnedIncomeTax(person, centralBank);
            }
        }

        // 법인세: 기업 예금 잔고 기준
        CGMinistryOfTradeIndustryAndEnergy motia = (CGMinistryOfTradeIndustryAndEnergy)
                country.getGovernmentMap().get(EGovernmentType.TradeIndustryAndEnergy);
        if (motia != null) {
            for (CCompany company : motia.getCompanyMap().values()) {
                collectCorporateTax(company, centralBank);
            }
        }

        if (monthlyEarnedIncomeTax > 0 || monthlyCorporateTax > 0) {
            FrameLog.getInstance().addLog("tax",
                    "월 세금 징수 완료 - 소득세: " + UCurrency.getInstance().toString(monthlyEarnedIncomeTax, ECurrency.Won)
                            + ", 법인세: " + UCurrency.getInstance().toString(monthlyCorporateTax, ECurrency.Won));
        }
    }

    private void collectEarnedIncomeTax(CPeople person, CBCentral centralBank) {
        for (PAAccount accountInfo : person.getBasicData().getPropertyAsset().getAccountList()) {
            if (accountInfo.getAccountType() != EAccountType.Deposit) continue;

            IBankService bankService = (IBankService) accountInfo.getBank();
            CBAccount account = bankService.findAccount(accountInfo.getOwnerName(), accountInfo.getAccountNumber());
            if (account == null || account.getRightsOfCash() <= 0) continue;

            long tax = (long) (account.getRightsOfCash() * (earnedIncomeTaxRate / 100.0f) / 12.0f);
            if (tax <= 0) continue;

            account.addRightsOfCash(-tax);
            centralBank.getNationalAccount().addRightsOfCash(tax);
            monthlyEarnedIncomeTax += tax;
            totalEarnedIncomeTax += tax;
        }
    }

    private void collectCorporateTax(CCompany company, CBCentral centralBank) {
        for (PAAccount accountInfo : company.getBasicData().getPropertyAsset().getAccountList()) {
            if (accountInfo.getAccountType() != EAccountType.Deposit) continue;

            IBankService bankService = (IBankService) accountInfo.getBank();
            CBAccount account = bankService.findAccount(accountInfo.getOwnerName(), accountInfo.getAccountNumber());
            if (account == null || account.getRightsOfCash() <= 0) continue;

            long tax = (long) (account.getRightsOfCash() * (corporateTaxRate / 100.0f) / 12.0f);
            if (tax <= 0) continue;

            account.addRightsOfCash(-tax);
            centralBank.getNationalAccount().addRightsOfCash(tax);
            monthlyCorporateTax += tax;
            totalCorporateTax += tax;
        }
    }

    public long getTotalTaxRevenue() {
        return totalEarnedIncomeTax + totalCorporateTax;
    }

    public long getNationalBalance() {
        return getMyCountry().getCentralBank().getNationalAccount().getRightsOfCash();
    }
}
