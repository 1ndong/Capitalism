package com.indong.capitalism.Behaviors;

import com.indong.capitalism.Classes.CACCash;
import com.indong.capitalism.Interface.IBehavior;
import com.indong.capitalism.Item.ItemAccount;

public class CBehaviorBank implements IBehavior{

	@Override
	public void deposit(ItemAccount account, CACCash cash) {
		// TODO Auto-generated method stub
		account.depositCash(cash.getBalance());
	}

	@Override
	public void withdraw(ItemAccount account, CACCash cash) {
		// TODO Auto-generated method stub
		
	}
}
