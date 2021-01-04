package com.indong.capitalism.Classes;

public class CACCash extends CACurrency{
	private int cash = 0;
	public CACCash()
	{
		
	}
	public int getCash() {
		return cash;
	}
	public void addCash(int cash) {
		this.cash += cash;
	}
}
