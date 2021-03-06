package com.indong.capitalism.Processor.Compiler2;

import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Enum.ECommandType;

public class C2Command{
    protected ECommandType eType;
    protected CCountry country;

    public void setCountry(CCountry country)
    {
        this.country = country;
    }

    //spend
    //1. spend 리스트
    //2. 선택한 1번에서 spend를 할 수 있는 리스트
    //3. 선택 시 그 회사에서 그 재화를 spend

    //finance
    //1. finance 리스트
    //2. 선택한 1번에서 finance를 할 수 있는 리스트
    //3. 선택 시 1번에서 finance를 할수 있는 리스트에서 관련 서비스 실행
}
