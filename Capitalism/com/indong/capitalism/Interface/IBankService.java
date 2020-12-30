package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Item.ItemAccount;

public interface IBankService {
	void makeNewAccount(CBeing newclient , EAccountType type);
	boolean sendMoney(ItemAccount sender , ItemAccount receiver , int amount);
	int withdrawCash(ItemAccount account , int amount);
	void depositCash(ItemAccount account , CACCash cash , int amount);
}
