package capitalism.Interface;

import capitalism.Enum.ESearchType;

public interface ISearchable {
    boolean isTypeOf(ESearchType type);
    void registerObject();
    ESearchType getSearchType();
    String getSearchableOriginName();
}
