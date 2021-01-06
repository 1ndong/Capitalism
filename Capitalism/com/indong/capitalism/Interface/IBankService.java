package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Info.IAAccount;
import com.indong.capitalism.Item.ItemAccount;

public interface IBankService {
	ItemAccount makeNewAccount(CBeing newclient , EAccountType type);
	boolean sendMoney(IAAccount sender , IAAccount receiver , int amount);
	int withdrawCash(IAAccount account , int amount);
	void depositCash(IAAccount account , CACCash cash , int amount);
	ItemAccount findAccount(String name,int accountNumber);
	void raiseLoan(ItemAccount being , int amount);
}
