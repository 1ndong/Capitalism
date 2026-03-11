package capitalism.DataStructure;

import lombok.Getter;

@Getter
public class DHCentralBank extends DHistory {
	private long makeMoneyAmount;

	public DHCentralBank(DTime time, long amount) {
		super(time);
		makeMoneyAmount = amount;
	}
}
