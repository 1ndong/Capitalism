package kapitalism.javaorigin.Enum;

public enum ESearchType {
    Bank(1),
    Government(2),
    World(3),
    Country(4),
    Company(5),
    People(6);

    private int val;
    ESearchType(int val)
    {
        this.val = val;
    }

    public int getValue()
    {
        return val;
    }
}
