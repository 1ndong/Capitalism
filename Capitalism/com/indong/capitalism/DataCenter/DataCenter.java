package com.indong.capitalism.DataCenter;

import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearchable;

import java.util.ArrayList;

public class DataCenter {
    private static final DataCenter instance = new DataCenter();

    private ArrayList<ISearchable> allObjects = new ArrayList<ISearchable>();

    public static DataCenter getInstance() {
        return instance;
    }

    public ArrayList<ISearchable> getAllObjects() {
        return allObjects;
    }

    public void addNewObject(ISearchable obj) {
        allObjects.add(obj);
    }

    public ArrayList<ISearchable> getList(ESearchType searchType)
    {
        ArrayList<ISearchable> result = new ArrayList<ISearchable>();

        for(int i = 0 ; i < allObjects.size() ; i++)
        {
            ISearchable temp = allObjects.get(i);
            if(temp.isTypeOf(searchType) == true)
            {
                result.add(temp);
            }
        }

        return result;
    }
}
