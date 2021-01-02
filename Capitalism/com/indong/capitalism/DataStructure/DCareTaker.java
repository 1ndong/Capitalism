package com.indong.capitalism.DataStructure;

import java.util.LinkedList;

public class DCareTaker {
	private LinkedList<DHistory> list = new LinkedList<DHistory>();
	
	public void addMemento(DHistory history)
	{
		list.add(history);
	}
	
	public LinkedList<DHistory> getList() 
	{
		return list;
	}
}
