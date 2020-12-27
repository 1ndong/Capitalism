package com.indong.capitalism.Classes;

import java.util.LinkedList;

import com.indong.capitalism.DataStructure.DCompanyMember;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EBeingType;
import com.indong.capitalism.Info.InfoBasicData;
import com.indong.capitalism.Info.InfoCompanyData;
import com.indong.capitalism.Interface.ITime;
import com.indong.capitalism.Item.ItemAccount;

public class CCompany implements ITime{
	protected int salaryDay = 0;
	protected LinkedList<DCompanyMember> staffList = new LinkedList<DCompanyMember>();
	protected InfoBasicData companyData = new InfoCompanyData(new DTime(0,0,0,""),"");
	protected final EBeingType type = EBeingType.Company;
	protected DTime today = new DTime(0,0,0,"");

	@Override
	public void dayChange(DTime newTime) {
		// TODO Auto-generated method stub
		today = newTime;
		if(today.getDay() == salaryDay)
		{
			//뱅크서비스 통해서 직원들 월급을 은행계좌로 쏴줘야됨 직급따라서
		}
	}
	
	public void joinCompany(CPeople newpeople , ItemAccount salaryaccount)
	{
		DCompanyMember newmember = new DCompanyMember(newpeople,salaryaccount);
		staffList.add(newmember);
	}
	
	public void leaveCompany(CPeople leavemember)
	{
		for(int i = 0 ; i < staffList.size() ; i++)
		{
			DCompanyMember temp = staffList.get(i);
			if(temp.getMember() == leavemember)
			{
				staffList.remove(i);
				break;
			}
		}
	}
}
