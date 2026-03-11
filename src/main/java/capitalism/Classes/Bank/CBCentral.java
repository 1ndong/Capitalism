package capitalism.Classes.Bank;

import capitalism.Classes.CCountry;
import capitalism.DataStructure.DCareTaker;
import capitalism.DataStructure.DHCentralBank;
import capitalism.DataStructure.DTime;
import capitalism.Enum.EAccountType;
import capitalism.Enum.ECurrency;
import capitalism.UI.FrameLog;
import capitalism.Interface.IInterestChanger;
import capitalism.Interface.IInterestRate;
import capitalism.Processor.ProcessorDay;
import capitalism.Util.UCurrency;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class CBCentral extends CBank implements IInterestChanger {
	private LinkedList<CBank> bankList = new LinkedList<>();
	private float baseInterestRate = 0.5f;
	private DCareTaker careTaker = new DCareTaker();
	private LinkedList<IInterestRate> ibankList = new LinkedList<>();
	private CBAccount nationalAccount = new CBAccount("국고", this, 1111, EAccountType.NationalAccount, 0.0f, new DTime(0, 0, 0));

	public CBCentral(CCountry country, String name) {
		super(country, name);
	}

	public void makeMoney(long amount) {
		balance.addCash(amount);
		FrameLog.getInstance().addLog("makeMoney", "중앙은행 현금 발행 " + UCurrency.getInstance().toString(amount, ECurrency.Won));
		DHCentralBank date = new DHCentralBank(ProcessorDay.GetInstance().getToday(), amount);
		careTaker.addMemento(date);
	}

	public void releaseMoney(CBank bank, long amount) {
		if (amount > balance.getCash()) {
			FrameLog.getInstance().addLog("centralbank", "잔액 부족");
			return;
		}
		bank.addBalance(amount);
		balance.addCash(-amount);
		FrameLog.getInstance().addLog("releaseMoney", "중앙은행에서 " + bank.getName() + "은행으로 " + UCurrency.getInstance().toString(amount, ECurrency.Won) + " 지급");
	}

	public void addNewBank(CBank newbank) {
		bankList.add(newbank);
	}

	public void setBaseInterestRate(float baseInterestRate) {
		this.baseInterestRate = baseInterestRate;
		FrameLog.getInstance().addLog("setbaseinterestrate", "기준금리 변동 : " + baseInterestRate + "%");
		interestChange();
	}

	@Override
	public void addAccount(IInterestRate account) {
		ibankList.add(account);
	}

	@Override
	public void interestChange() {
		for (int i = 0; i < ibankList.size(); i++) {
			ibankList.get(i).interestChange(getBaseInterestRate());
		}
	}

	public void setNationalAccount(CBAccount nationalAccount) {
		this.nationalAccount = nationalAccount;
	}
}
