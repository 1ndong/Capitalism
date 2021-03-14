package com.indong.capitalism.DataCenter;

import com.indong.capitalism.Classes.Bank.CBCommercial;
import com.indong.capitalism.Classes.Bank.CBank;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearchable;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public LinkedList<ISearchable> getList(int searchType)
    {
        boolean returnallType = false;
        if(searchType == -1)
            returnallType = true;
        LinkedList<ISearchable> result = new LinkedList<ISearchable>();

        ESearchType[] types = null;

        for(int i = 0 ; i < allObjects.size() ; i++)
        {
            ISearchable temp = allObjects.get(i);

            if(returnallType == false)
            {
                if((searchType | temp.getSearchType().getValue()) == temp.getSearchType().getValue())
                {
                    result.add(temp);
                }
            }
            else
                result.add(temp);
        }

        return result;
    }
}
