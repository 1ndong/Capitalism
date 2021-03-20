package com.indong.capitalism.Enum;

public enum ESectorType {
    Consumption(1),
    Finance(2);

    private int val;
    ESectorType(int val)
    {
        this.val = val;
    }

    public int getValue()
    {
        return val;
    }
    
    //todo consumption get하면 자동으로 한글 '소비'리턴되도록 getLocaleName() 구현
    public String getLocaleValue(ESectorType type)
    {
        if(type == ESectorType.Consumption)
        {
            return "소비";
        }
        else if(type == ESectorType.Finance)
        {
            return "금융";
        }
        else
            return "";
    }
}
