package kapitalism.javaorigin.DataStructure;

import kapitalism.javaorigin.Enum.EServicePropertyType;

import java.util.Vector;

public class DServiceItem {
    private Vector<String> propertyList = new Vector<String>();

    public DServiceItem(String sector , String major , String model , String price , String brand)
    {
        propertyList.add(EServicePropertyType.Sector.getValue(), sector);
        propertyList.add(EServicePropertyType.Major.getValue(), major);
        propertyList.add(EServicePropertyType.Model.getValue(), model);
        propertyList.add(EServicePropertyType.Price.getValue(), price);
        propertyList.add(EServicePropertyType.Brand.getValue(), brand);
    }

    public String getProperty(EServicePropertyType type)
    {
        return propertyList.get(type.getValue());
    }
}
