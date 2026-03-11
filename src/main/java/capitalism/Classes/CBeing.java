package capitalism.Classes;

import capitalism.Classes.Asset.CACCash;
import capitalism.Classes.Bank.CBAccount;
import capitalism.Classes.Stuff.CStuff;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.EBeingType;
import capitalism.Property.PAAccount;
import capitalism.Property.PBasicData;
import capitalism.Interface.IBankService;
import capitalism.Interface.ITime;
import capitalism.Interface.ITimeKeeper;
import capitalism.Processor.ProcessorDay;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class CBeing extends CBelong implements ITime {
	protected EBeingType type;
	protected PBasicData basicData;
	private final CACCash wallet;
	protected ArrayList<CStuff> stuffList = new ArrayList<>();

	public CBeing(CCountry country) {
		super(country);
		wallet = new CACCash();
		ITimeKeeper timekeeper = ProcessorDay.GetInstance();
		timekeeper.addTimeSlave(this);
	}

	@Override
	public void dayChange(DTime newTime) {
	}

	protected void repaymentDebt(DTime today) {
		for (int i = 0; i < basicData.getPropertyAsset().getAccountList().size(); i++) {
			PAAccount account = basicData.getPropertyAsset().getAccountList().get(i);
			if (account.getAccountType() == EAccountType.Loan) {
				IBankService bankservice = ((IBankService) account.getBank());
				CBAccount realaccount = bankservice.findAccount(account.getOwnerName(), account.getAccountNumber());
				if (realaccount.getInterestDay().getDay() == today.getDay()) {
					realaccount.repaymentDebt();
				}
			}
		}
	}

	public void addCash(long cash) {
		this.wallet.addCash(cash);
	}
}
