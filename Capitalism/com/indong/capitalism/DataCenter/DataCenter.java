package com.indong.capitalism.DataCenter;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.DataStructure.DServiceItem;
import com.indong.capitalism.Enum.ESearchType;
import com.indong.capitalism.Interface.ISearchable;
import com.indong.capitalism.Util.UJson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

public class DataCenter {
    private static final DataCenter instance = new DataCenter();

    private ArrayList<ISearchable> allObjects = new ArrayList<ISearchable>();
    private Vector<DServiceItem> catalog;

    public static DataCenter getInstance() {
        return instance;
    }

    public Vector<DServiceItem> getCatalog()
    {
        return catalog;
    }

    public void addNewObject(ISearchable obj) {
        allObjects.add(obj);
    }

    public DataCenter()
    {
        catalog = UJson.getInstance().makeServiceTree();
    }

    public CCompany findCompanyByName(String name)
    {
        for(int i = 0 ; i < allObjects.size() ; i++)
        {
            if(allObjects.get(i).getSearchType() == ESearchType.Company)
            {
                CCompany company = (CCompany) allObjects.get(i);
                if(company.getBasicData().getName().equals(name) == true)
                {
                    return company;
                }
            }
        }
        return null;
    }

    public LinkedList<ISearchable> getList(int searchType)
    {
        boolean returnallType = false;
        if(searchType == -1)
            returnallType = true;
        LinkedList<ISearchable> result = new LinkedList<ISearchable>();

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
