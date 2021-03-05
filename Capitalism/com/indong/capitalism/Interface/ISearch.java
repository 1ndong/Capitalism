package com.indong.capitalism.Interface;

import com.indong.capitalism.Enum.ESearchType;

public interface ISearch {
    boolean isTypeOf(ESearchType type);
    void registerObject();
}
