package com.indong.capitalism.Interface;

import com.indong.capitalism.Enum.ESearchType;

public interface ISearchable {
    boolean isTypeOf(ESearchType type);
    void registerObject();
    ESearchType getSearchType();
    String getSearchableOriginName();
}
