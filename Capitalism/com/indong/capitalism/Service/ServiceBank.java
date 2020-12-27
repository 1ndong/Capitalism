package com.indong.capitalism.Service;

import com.indong.capitalism.Classes.CBCommercial;
import com.indong.capitalism.Frame.FrameLog;
import com.indong.capitalism.Item.ItemAccount;

public class ServiceBank {
	private static ServiceBank instance = new ServiceBank();
	
	public static ServiceBank sgetInstance()
	{
		return instance;
	}
	
	private ServiceBank()
	{
		
	}
	
	public void moveMoney(ItemAccount sender , ItemAccount receiver)
	{
		CBCommercial senderbank = sender.getBank();
		int senderindex = senderbank.getAccountList().indexOf(sender);
		if(senderindex == -1)
		{
			FrameLog.GetInstance().addLog("movemoney", "보내는쪽 계좌 없음");
			return;
		}
		
		CBCommercial receiverbank = receiver.getBank();
		int receiverindex = receiverbank.getAccountList().indexOf(receiver);
		if(receiverindex == -1)
		{
			FrameLog.GetInstance().addLog("movemoney", "받는쪽 계좌 없음");
			return;
		}
		
		sender.getBank().getAccountList().get(senderindex);
		//계좌에서 바로 옮기면 되는거 아닌가? 
	}
}
