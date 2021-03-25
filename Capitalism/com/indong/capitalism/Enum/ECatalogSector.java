package com.indong.capitalism.Enum;

public enum ECatalogSector {
    Eletronic("전자제품"),
    Food("식품"),
    Clothes("의류");

    private String str;

    ECatalogSector(String str)
    {
        this.str = str;
    }

    public String getValue()
    {
        return str;
    }
}
