package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Info.IAAccount;

public interface IBankService {
	CBAccount makeNewAccount(CBeing newclient , EAccountType type);
	boolean sendMoney(IAAccount sender , IAAccount receiver , long amount);
	long withdrawCash(IAAccount account , long amount);
	void depositCash(IAAccount account , CACCash cash , long amount);
	CBAccount findAccount(String name,int accountNumber);
	void raiseLoan(CBAccount being , long amount);
}
