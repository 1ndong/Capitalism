package kapitalism.javaorigin.Interface;

import kapitalism.javaorigin.Classes.CBeing;
import kapitalism.javaorigin.Classes.Asset.CACCash;
import kapitalism.javaorigin.Classes.Bank.CBAccount;
import kapitalism.javaorigin.DataStructure.DTime;
import kapitalism.javaorigin.Enum.EAccountType;
import kapitalism.javaorigin.Property.PAAccount;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IBankService {
	CBAccount makeNewAccount(CBeing newclient , EAccountType type , DTime interestDay);
	boolean sendMoney(PAAccount sender , PAAccount receiver , long amount);
	long withdrawCash(PAAccount account , long amount);
	boolean transferCash(PAAccount account , CACCash cash , long amount);
	CBAccount findAccount(String name,int accountNumber);
	void raiseLoan(CBAccount account , long amount , int loanMonth , int repaymentDay);
	LinkedList<CBAccount> findAccountList(String name);
}
