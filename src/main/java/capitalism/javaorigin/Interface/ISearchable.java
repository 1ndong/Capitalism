package capitalism.javaorigin.Interface;

import capitalism.javaorigin.Enum.ESearchType;

public interface ISearchable {
    boolean isTypeOf(ESearchType type);
    void registerObject();
    ESearchType getSearchType();
    String getSearchableOriginName();
}
