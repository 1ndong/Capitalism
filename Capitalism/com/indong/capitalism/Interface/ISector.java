package com.indong.capitalism.Interface;

import com.indong.capitalism.DataStructure.DService;
import com.indong.capitalism.DataStructure.DServiceTree;

import java.util.ArrayList;

public interface ISector {
    int getSector();
    void setSector(int newsector);
    ArrayList<DService> getServiceList();
    void setServiceList(ArrayList<DService> list);
    void setServiceList2(DServiceTree serviceTree);
    DServiceTree getServiceList2();
}
