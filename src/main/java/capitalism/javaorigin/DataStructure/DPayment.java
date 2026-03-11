package capitalism.javaorigin.DataStructure;

import capitalism.javaorigin.Classes.CBeing;

public class DPayment {
    private CBeing targetBeing;
    private DServiceItem targetService;

    public DPayment(CBeing being , DServiceItem service)
    {
        targetBeing = being;
        targetService = service;
    }

    public CBeing getTargetBeing()
    {
        return targetBeing;
    }

    public DServiceItem getTargetService()
    {
        return targetService;
    }
}
