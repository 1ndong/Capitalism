package capitalism.DataStructure;

import capitalism.Classes.CPeople;
import capitalism.Enum.ECompanyPosition;
import capitalism.Property.PAAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DCompanyMember {
	private CPeople member;
	private PAAccount salaryAccount;
	private ECompanyPosition companyPosition;
}
