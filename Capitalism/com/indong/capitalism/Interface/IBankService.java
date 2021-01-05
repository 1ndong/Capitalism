package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Item.ItemAccount;

public interface IBankService {
	ItemAccount makeNewAccount(CBeing newclient , EAccountType type);
	boolean sendMoney(ItemAccount sender , ItemAccount receiver , int amount);
	int withdrawCash(ItemAccount account , int amount);
	void depositCash(ItemAccount account , CACCash cash , int amount);
	ItemAccount findAccount(String name,int accountNumber);
	void raiseLoan(CBeing being , int amount);
}
