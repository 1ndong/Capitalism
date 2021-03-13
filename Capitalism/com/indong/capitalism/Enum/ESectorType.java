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
}
