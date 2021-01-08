package com.indong.capitalism.Classes.Asset;

import com.indong.capitalism.Frame.FrameLog;

public class CACCash extends CACurrency{
	private long cash = 0;
	public CACCash()
	{
		
	}
	public long getCash() {
		return cash;
	}
	public void addCash(long cash) {
		if(cash < 0)
		{
			if(this.cash - cash < 0)
			{
				FrameLog.getInstance().addLog("addcash", "현금 부족");
				return;
			}	
		}
		
		this.cash += cash;
	}
}
