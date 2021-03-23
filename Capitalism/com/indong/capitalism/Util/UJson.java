package com.indong.capitalism.Util;

import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.DataStructure.DService3;
import com.indong.capitalism.DataStructure.DServiceTree;
import com.indong.capitalism.Enum.ECurrency;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UJson {
    private static final UJson instance = new UJson();

    public static UJson getInstance() {
        return instance;
    }

    public DServiceTree makeServiceTree(String name) {
        switch (name) {
            case "삼성":
                name = "samsung";
                break;
            case "lg":
                name = "lg";
                break;
        }

//        {
//            "전자제품":
//    [
//            {"휴대폰":[{"갤럭시S21":"100만원"},{"갤럭시S21울트라":"132만원"}]},
//            {"TV":[{"4k":[{"75인치":"160만원"},{"82인치":"380만원"}]} , {"8k":[{"82인치":"600만원"},{"101인치":"1300만원"}]}]},
//            {"냉장고":[{"지펠":"200만원"},{"비스포크":"600만원"}]}
//      ]
//        }

        //DServiceTree result = new DServiceTree(null);
        ArrayList<DService3> result = new ArrayList<DService3>();
        result.add(new DService3());
        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader("Capitalism/com/indong/capitalism/res/" + name + ".json");

            JSONObject objModel = (JSONObject) parser.parse(reader);

            parseJSON(objModel , result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void parseJSON(Object input , ArrayList<DService3> tree)
    {
        if (input instanceof JSONObject)
        {
            Iterator keys = ((JSONObject) input).keySet().iterator();

            while (keys.hasNext())
            {
                String key = (String) keys.next();
                if ((((JSONObject) input).get(key) instanceof JSONArray) == false)
                {
                    if (((JSONObject) input).get(key) instanceof JSONObject)
                    {
                        //maybe not reach..?
                        //System.out.println("111 = " + key);
                        //parseJSON(((JSONObject)input).get(key),tree);
                    }
                    else
                        //System.out.println(key + "=" + ((JSONObject) input).get(key));
                    {
                        tree.get(tree.size()-1).addPropertyList(key);
                        tree.get(tree.size()-1).setPrice(UCurrency.getInstance().toOriginValue(((JSONObject) input).get(key).toString(), ECurrency.Won));
                        tree.add(new DService3());
                    }
                }
                else
                {
                    //key parent
                    tree.get(tree.size()-1).addPropertyList(key);
                    parseJSON(((JSONObject)input).get(key),tree);
                }
            }
        }
        else if (input instanceof JSONArray)
        {
            for (int i = 0; i < ((JSONArray) input).size(); i++)
            {
                JSONObject item = (JSONObject) ((JSONArray) input).get(i);
                parseJSON(item,tree);
            }
        }
    }
}
