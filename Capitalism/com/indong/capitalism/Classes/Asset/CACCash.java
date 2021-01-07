package com.indong.capitalism.Classes.Asset;

public class CACCash extends CACurrency{
	private long cash = 0;
	public CACCash()
	{
		
	}
	public long getCash() {
		return cash;
	}
	public void addCash(long cash) {
		this.cash += cash;
	}
}
