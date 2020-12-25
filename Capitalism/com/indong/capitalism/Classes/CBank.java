package com.indong.capitalism.Classes;

public class CBank {
	protected CACCash balance = new CACCash();
	protected float interestRate = 2.0f;
	
	protected CACCash getBalance()
	{
		return balance;
	}
	
	protected void addBalance(int cash)
	{
		balance.addBalance(cash);
	}
}
