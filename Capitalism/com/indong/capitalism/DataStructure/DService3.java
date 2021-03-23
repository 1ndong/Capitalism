package com.indong.capitalism.DataStructure;

import java.util.ArrayList;

public class DService3 {
    private ArrayList<String> propertyList = new ArrayList<String>();
    private long price = 0L;

    public DService3() {

    }

    public ArrayList<String> getPropertyList() {
        return propertyList;
    }

    public void addPropertyList(String newProperty) {
        propertyList.add(newProperty);
    }

    public void setPrice(long price)
    {
        this.price = price;
    }

    public long getPrice()
    {
        return price;
    }
}
