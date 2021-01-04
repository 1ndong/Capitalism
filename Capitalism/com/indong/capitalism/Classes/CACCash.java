package com.indong.capitalism.Classes;

public class CACCash extends CAsset{
	private int balance = 0;
	public CACCash()
	{
		
	}
	public int getBalance() {
		return balance;
	}
	public void addBalance(int balance) {
		this.balance += balance;
	}
}
