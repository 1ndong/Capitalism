package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Item.ItemAccount;

public interface IBehavior {
	void deposit(ItemAccount account , CACCash cash);
	void withdraw(ItemAccount account , CACCash cash);
}
