package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoCompanyData;
import com.indong.capitalism.Interface.ICompany;
import com.indong.capitalism.Interface.ICompanyMember;
import com.indong.capitalism.Interface.ITime;

public class CCompany implements ICompany , ITime{
	protected int salaryDay = 0;
	protected LinkedList<ICompanyMember> staffList = new LinkedList<ICompanyMember>();
	protected InfoBasicData companyData = new InfoCompanyData(new DTime(0,0,0,""),"");
	protected final EBeingType type = EBeingType.Company;
	protected DTime today = new DTime(0,0,0,"");
	
	@Override
	public void joinCompany(ICompanyMember member) {
		// TODO Auto-generated method stub
		staffList.add(member);
	}

	@Override
	public void leaveCompany(ICompanyMember member) {
		// TODO Auto-generated method stub
		staffList.remove(member);
	}

	@Override
	public void paySalary() {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < staffList.size() ; i++)
		{

			
		}
	}

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		today = newTime;
		if(today.getDay() == salaryDay)
		{
			//뱅크서비스 통해서 직원들 월급을 은행계좌로 쏴줘야됨 직급따라서
		}
	}
}
