package com.indong.capitalism.Interface;

import com.indong.capitalism.Classes.CBeing;
import com.indong.capitalism.Classes.Asset.CACCash;
import com.indong.capitalism.Classes.Bank.CBAccount;
import com.indong.capitalism.DataStructure.DTime;
import com.indong.capitalism.Enum.EAccountType;
import com.indong.capitalism.Property.PAAccount;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IBankService {
	CBAccount makeNewAccount(CBeing newclient , EAccountType type , DTime interestDay);
	boolean sendMoney(PAAccount sender , PAAccount receiver , long amount);
	long withdrawCash(PAAccount account , long amount);
	boolean depositCash(PAAccount account , CACCash cash , long amount);
	CBAccount findAccount(String name,int accountNumber);
	void raiseLoan(CBAccount account , long amount , int loanMonth , int repaymentDay);
	LinkedList<CBAccount> findAccountList(String name);
}
