package com.indong.capitalism.Interface;

import com.indong.capitalism.DataStructure.DTime;

public interface ITime {
	DTime today = new DTime(0,0,0,"");
	
	void dayChange(DTime newTime);
}
