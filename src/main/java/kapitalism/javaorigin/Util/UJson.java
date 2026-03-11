package kapitalism.javaorigin.Util;

import kapitalism.javaorigin.DataStructure.DServiceItem;
import kapitalism.javaorigin.Enum.ECatalogSector;
import kapitalism.javaorigin.Enum.EServicePropertyType;
import kapitalism.javaorigin.res.ResCatalog;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.util.Vector;

public class UJson {
    private static final UJson instance = new UJson();

    public static UJson getInstance() {
        return instance;
    }

    public Vector<DServiceItem> makeServiceTree()
    {
        JSONParser parser = new JSONParser();

        Vector<DServiceItem> result = new Vector<DServiceItem>();
        try {
            JSONObject obj = (JSONObject) parser.parse(new ResCatalog().getCatalogJson());

            for(int i = 0 ; i < ECatalogSector.values().length ; i++)
            {
                JSONArray ary = (JSONArray)obj.get(ECatalogSector.values()[i].getValue());
                for(int j = 0 ; j < ary.size() ; j++)
                {
                    JSONObject item = (JSONObject) ary.get(j);
                    result.add(new DServiceItem(ECatalogSector.values()[i].getValue()
                            ,item.get(EServicePropertyType.Major.toString()).toString()
                            ,item.get(EServicePropertyType.Model.toString()).toString()
                            ,item.get(EServicePropertyType.Price.toString()).toString()
                            ,item.get(EServicePropertyType.Brand.toString()).toString()
                    ));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"f","gg",JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
        }

        return result;
    }

    /* // n depth all traverse
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
                        System.out.println("1");
                        tree.get(tree.size()-1).addPropertyList(key);
                        tree.get(tree.size()-1).setPrice(UCurrency.getInstance().toOriginValue(((JSONObject) input).get(key).toString(), ECurrency.Won));
                        tree.add(new DService3());
                    }
                }
                else
                {
                    //key parent
                    System.out.println("2");
                    tree.get(tree.size()-1).addPropertyList(key);
                    parseJSON(((JSONObject)input).get(key),tree);
                }
            }
        }
        else if (input instanceof JSONArray)
        {
            for (int i = 0; i < ((JSONArray) input).size(); i++)
            {
                System.out.println("3");
                JSONObject item = (JSONObject) ((JSONArray) input).get(i);
                parseJSON(item,tree);
            }
        }
    }
     */
}
