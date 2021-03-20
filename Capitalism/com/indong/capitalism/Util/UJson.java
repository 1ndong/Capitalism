package com.indong.capitalism.Util;

import com.indong.capitalism.DataStructure.DService2;
import com.indong.capitalism.DataStructure.DServiceTree;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Node;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UJson {
    private static final UJson instance = new UJson();
    public static UJson getInstance()
    {
        return instance;
    }

    public Map<String,Object> parsingJson(JSONObject orgData)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = (JSONObject)orgData.get("properties");
        Iterator<String> keysItr = jsonObj.keySet().iterator();
        while (keysItr.hasNext())
        {
            String key = keysItr.next();
            Object value = jsonObj.get(key);
            //System.out.println(key + " : " + value);
            if(value instanceof JSONArray)
            {
                //System.out.println("json array");
            }
            else if(value instanceof JSONObject)
            {
                //System.out.println("json object");
            }
            map.put(key, value);
        }

        return map;
    }

    public void makeobjTest()
    {
        JSONParser parser = new JSONParser();
        String jsonString = "{\"stat\":{\"sdr\": \"aaaaaaaaaaaaaaaaaaaaa\",\"rcv\": \"bbbbbbbbbbbbbbbbbbbb\",\"time\": \"UTC in millis\"" + ",\"type\": 1,\"subt\": 1,\"argv\": [{\"1\":2},{\"2\":3}]}}";
        try {
            Map json = (Map)parser.parse(jsonString);
            Iterator iter = json.entrySet().iterator();
            Object entry = json.get("stat");
            System.out.println(entry);
            System.out.println( entry.getClass() );
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
    }

    public DServiceTree makeServiceTree(String name)
    {
        switch(name)
        {
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

        DServiceTree result = new DServiceTree(null);

        JSONParser parser = new JSONParser();
        try {
            Reader reader = new FileReader("Capitalism/com/indong/capitalism/res/" + name + ".json");

            JSONObject json = (JSONObject)parser.parse(reader);

            Set set = json.keySet();
            Iterator iter = set.iterator();
            while(iter.hasNext())
            {
                Object key = iter.next();
                result.addChildNode(new DService2(key.toString() , 0L));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return result;
    }
}
