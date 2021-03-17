package com.indong.capitalism.UI.CustomTableDashboard;

import com.indong.capitalism.Classes.CBeing;

public class CMainTableComp {
	public String name;
	public long allAsset;
	public long deposit;
	public long cash;
	public long loan;
	public CBeing being;

	public CMainTableComp(CBeing being , String name, long allAsset, long deposit, long cash, long loan) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.allAsset = allAsset;
		this.deposit = deposit;
		this.cash = cash;
		this.loan = loan;
		this.being = being;
	}
}
