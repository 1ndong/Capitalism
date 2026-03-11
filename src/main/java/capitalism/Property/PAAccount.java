package capitalism.Property;

import capitalism.Classes.Bank.CBank;
import capitalism.Enum.EAccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PAAccount {
	private String ownerName;
	private CBank bank;
	private int accountNumber;
	private EAccountType accountType;
}
