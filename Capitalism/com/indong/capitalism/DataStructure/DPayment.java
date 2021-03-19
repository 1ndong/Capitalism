package com.indong.capitalism.DataStructure;

import com.indong.capitalism.Classes.CBeing;

public class DPayment {
    private CBeing targetBeing;
    private DService targetService;

    public DPayment(CBeing being , DService service)
    {
        targetBeing = being;
        targetService = service;
    }

    public CBeing getTargetBeing()
    {
        return targetBeing;
    }

    public DService getTargetService()
    {
        return targetService;
    }
}
