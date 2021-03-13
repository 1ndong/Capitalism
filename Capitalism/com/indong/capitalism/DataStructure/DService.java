package com.indong.capitalism.DataStructure;

import java.util.ArrayList;

public class DService {
    private ArrayList<DService> nextList = null;
    private String name = null;
    private long value = 0L;

    /*
    example
    step1 a구매 , b구매 ,c구매
    step2 alist , blist , clist
    step3 aalist , bblist , cclist
    new 1(a,b,c);
    22 = new 2(alist...)
    a.setnext(22);
    33 = new 3(aalist...)
    22.setnext(33);
    * */

    public DService(String name , long value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public long getValue()
    {
        return value;
    }

    public ArrayList<DService> getNextList()
    {
        return nextList;
    }

    public void setNextList(ArrayList<DService> nextList)
    {
        this.nextList = nextList;
    }
}