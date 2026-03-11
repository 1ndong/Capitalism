package capitalism.Property;

import capitalism.DataStructure.DTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PCompanyData extends PBasicData {

	private PAAccount mainDepositAccount;

	public PCompanyData(DTime birth, String name) {
		super(birth, name);
	}
}
