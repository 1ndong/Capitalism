package com.indong.capitalism.DataStructure;

import java.util.ArrayList;

public class DServiceTree {
    private DService2 service;
    private DServiceTree parentNode = null;
    private ArrayList<DServiceTree> childNodeArray = new ArrayList<DServiceTree>();

    public DServiceTree(DService2 service)
    {
        this.service = service;
    }

    public void setParentNode(DServiceTree parentNode)
    {
        this.parentNode = parentNode;
    }

    public DServiceTree addChildNode(DService2 service)
    {
        DServiceTree childNode = new DServiceTree(service);
        childNode.setParentNode(this);
        childNodeArray.add(childNode);

        return childNode;
    }

    public DService2 getService()
    {
        return service;
    }
}
