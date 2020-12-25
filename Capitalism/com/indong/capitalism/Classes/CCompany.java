package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoCompanyData;

public class CCompany {
	protected int salaryDay = 0;
	protected LinkedList<CPeople> staffList = new LinkedList<CPeople>();
	protected InfoBasicData companyData = new InfoCompanyData(new DTime(0,0,0,""),"");
}
