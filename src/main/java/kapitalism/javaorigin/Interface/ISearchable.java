package kapitalism.javaorigin.Interface;

import kapitalism.javaorigin.Enum.ESearchType;

public interface ISearchable {
    boolean isTypeOf(ESearchType type);
    void registerObject();
    ESearchType getSearchType();
    String getSearchableOriginName();
}
