package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Item.ItemAccount;

public interface IBankService {
	void makeNewAccount(CBeing newclient , EAccountType type);
	boolean sendMoney(ItemAccount sender , ItemAccount receiver , int amount);
}
