package com.indong.capitalism.DataCenter;

import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearch;

import java.util.ArrayList;

public class DataCenter {
    private static final DataCenter instance = new DataCenter();

    private ArrayList<ISearch> allObjects = new ArrayList<ISearch>();

    public static DataCenter getInstance() {
        return instance;
    }

    public ArrayList<ISearch> getAllObjects() {
        return allObjects;
    }

    public void addNewObject(ISearch obj) {
        allObjects.add(obj);
    }

    public ArrayList<ISearch> getList(ESearchType searchType)
    {
        ArrayList<ISearch> result = new ArrayList<ISearch>();

        for(int i = 0 ; i < allObjects.size() ; i++)
        {
            ISearch temp = allObjects.get(i);
            if(temp.isTypeOf(searchType) == true)
            {
                result.add(temp);
            }
        }

        return result;
    }
}
