package capitalism.Processor;

import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Bank.CBCommercial;
import capitalism.Classes.CCompany;
import capitalism.Classes.CCountry;
import capitalism.Classes.CPeople;
import capitalism.Classes.CWorld;
import capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import capitalism.Classes.Government.CGNationalTaxService;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECompanyPosition;
import capitalism.Enum.EGovernmentType;
import capitalism.Interface.IBankService;
import capitalism.Interface.ICompanyService;
import capitalism.Property.PAAccount;
import capitalism.Property.PCompanyData;
import capitalism.UI.FrameLog;
import capitalism.Util.URandom;

import java.util.LinkedList;

public class ProcessorMain {
    public ProcessorMain() {
        FrameLog.getInstance().addLog("tag", "시작");
        start();
    }

    private void start() {
        // 국가 생성
        CCountry rok = new CCountry("대한민국");
        CWorld.getInstance().getCountryList().add(rok);

        CGMinistryOfHealthAndWelfare gmohaw = new CGMinistryOfHealthAndWelfare(rok);
        CGMinistryOfTradeIndustryAndEnergy gmotiae = new CGMinistryOfTradeIndustryAndEnergy(rok);
        CGNationalTaxService taxService = new CGNationalTaxService(rok);
        rok.getGovernmentMap().put(EGovernmentType.HealthAndWelfare, gmohaw);
        rok.getGovernmentMap().put(EGovernmentType.TradeIndustryAndEnergy, gmotiae);
        rok.getGovernmentMap().put(EGovernmentType.NationalTaxService, taxService);

        // 은행 3개
        CBCommercial shinhanbank = new CBCommercial(rok, "신한");
        CBCommercial kookminbank = new CBCommercial(rok, "국민");
        CBCommercial wooribank = new CBCommercial(rok, "우리");
        rok.getBankList().add(shinhanbank);
        rok.getBankList().add(kookminbank);
        rok.getBankList().add(wooribank);
        shinhanbank.setSpreadInterestRate(2.1f);
        kookminbank.setSpreadInterestRate(2.4f);
        wooribank.setSpreadInterestRate(2.2f);

        // 통화 발행 + 은행 배분
        rok.getCentralBank().makeMoney(10_000_000_000_000L); // 10조
        rok.getCentralBank().releaseMoney(shinhanbank, 1_000_000_000_000L); // 1조
        rok.getCentralBank().releaseMoney(kookminbank, 1_000_000_000_000L);
        rok.getCentralBank().releaseMoney(wooribank, 1_000_000_000_000L);

        CBCommercial[] banks = {shinhanbank, kookminbank, wooribank};
        DTime interestDay = new DTime(0, 0, 7);

        // 기업 설정: {이름, 설립년, 월, 일, 대출금(억), 직원수, 담당은행인덱스}
        Object[][] companyConfigs = {
                {"삼성", 1955, 2, 12, 5000L, 200, 0},
                {"LG", 1958, 3, 21, 3000L, 150, 1},
                {"SK텔레콤", 1970, 5, 4, 2000L, 100, 2},
                {"CJ", 1984, 2, 3, 1500L, 80, 2},
                {"지오다노", 1988, 6, 7, 1000L, 60, 1},
                {"현대", 1947, 5, 25, 4000L, 180, 0},
                {"롯데", 1967, 4, 3, 2500L, 120, 1},
                {"포스코", 1968, 4, 1, 3000L, 130, 2},
                {"한화", 1952, 10, 8, 2000L, 90, 0},
                {"KT", 1981, 12, 10, 1500L, 70, 1},
        };

        // 직급 분포 비율
        ECompanyPosition[] positions = ECompanyPosition.values();
        // Clerk 60%, SeniorClerk 20%, AssistantManager 10%, Manager 5%, Deputy 3%, General 2%
        int[] positionWeights = {60, 20, 10, 5, 3, 2};

        int peopleCounter = 0;

        for (Object[] config : companyConfigs) {
            String companyName = (String) config[0];
            int year = (int) config[1];
            int month = (int) config[2];
            int day = (int) config[3];
            long loanBillion = (long) config[4]; // 억 단위
            int staffCount = (int) config[5];
            int bankIdx = (int) config[6];

            long loanAmount = loanBillion * 100_000_000L; // 억 → 원
            CBCommercial bank = banks[bankIdx];
            IBankService bankService = bank;

            // 기업 생성
            CCompany company = new CCompany(rok, new DTime(year, month, day), companyName);
            gmotiae.registerCompany(company);
            company.setSalaryDay(25);

            // 기업 예금/대출 계좌
            CBAccount depositAccount = bankService.makeNewAccount(company, EAccountType.Deposit, interestDay);
            CBAccount loanAccount = bankService.makeNewAccount(company, EAccountType.Loan, interestDay);
            bankService.raiseLoan(loanAccount, loanAmount, 120, 7);

            // 대출금의 일부를 예금으로 이동 (운영자금)
            long operatingCash = loanAmount / 5;
            bankService.sendMoney(
                    company.getBasicData().getPropertyAsset().findAccountInfo(loanAccount),
                    company.getBasicData().getPropertyAsset().findAccountInfo(depositAccount),
                    operatingCash
            );

            if (company.getBasicData() instanceof PCompanyData cd) {
                cd.setMainDepositAccount(company.getBasicData().getPropertyAsset().findAccountInfo(depositAccount));
            }

            // 직원 자동 생성
            for (int i = 0; i < staffCount; i++) {
                peopleCounter++;
                int birthYear = 1970 + URandom.getInstance().nextInt(25); // 1970~1994
                int birthMonth = 1 + URandom.getInstance().nextInt(12);
                int birthDay = 1 + URandom.getInstance().nextInt(28);
                String peopleName = "시민" + peopleCounter;

                CPeople person = new CPeople(rok, new DTime(birthYear, birthMonth, birthDay), peopleName);
                gmohaw.registerPeople(person);

                // 급여 계좌 (랜덤 은행)
                CBCommercial salaryBank = banks[URandom.getInstance().nextInt(banks.length)];
                CBAccount salaryAccount = ((IBankService) salaryBank).makeNewAccount(person, EAccountType.Deposit, interestDay);
                PAAccount salaryAccountInfo = person.getBasicData().getPropertyAsset().findAccountInfo(salaryAccount);

                // 직급 배분
                ECompanyPosition position = pickPosition(positions, positionWeights);
                ((ICompanyService) company).joinCompany(person, salaryAccountInfo, position);
            }
        }

        FrameLog.getInstance().addLog("init", "초기화 완료: 기업 " + companyConfigs.length + "개, 시민 " + peopleCounter + "명");
    }

    private ECompanyPosition pickPosition(ECompanyPosition[] positions, int[] weights) {
        int total = 0;
        for (int w : weights) total += w;
        int rand = URandom.getInstance().nextInt(total);
        int cumulative = 0;
        for (int i = 0; i < positions.length; i++) {
            cumulative += weights[i];
            if (rand < cumulative) return positions[i];
        }
        return positions[0];
    }
}
