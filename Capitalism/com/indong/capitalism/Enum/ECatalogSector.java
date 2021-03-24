package com.indong.capitalism.Enum;

public enum ECatalogSector {
    Eletronic("전자제품"),
    Food("식품"),
    Communication("통신");

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
