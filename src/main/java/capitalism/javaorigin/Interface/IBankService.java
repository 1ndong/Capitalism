package capitalism.javaorigin.Interface;

import capitalism.javaorigin.Classes.CBeing;
import capitalism.javaorigin.Classes.Asset.CACCash;
import capitalism.javaorigin.Classes.Bank.CBAccount;
import capitalism.javaorigin.DataStructure.DTime;
import capitalism.javaorigin.Enum.EAccountType;
import capitalism.javaorigin.Property.PAAccount;

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
