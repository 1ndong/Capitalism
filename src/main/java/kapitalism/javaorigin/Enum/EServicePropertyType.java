package kapitalism.javaorigin.Enum;

public enum EServicePropertyType {
    Sector(0)
    ,Major(1)
    ,Model(2)
    ,Price(3)
    ,Brand(4);

    private int val;
    EServicePropertyType(int val)
    {
        this.val = val;
    }

    public int getValue()
    {
        return val;
    }
}
