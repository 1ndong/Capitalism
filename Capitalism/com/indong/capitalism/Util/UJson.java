package com.indong.capitalism.Util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
}
