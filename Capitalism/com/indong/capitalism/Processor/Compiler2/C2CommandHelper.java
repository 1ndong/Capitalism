package com.indong.capitalism.Processor.Compiler2;

import com.indong.capitalism.Classes.CCompany;
import com.indong.capitalism.Classes.CCountry;
import com.indong.capitalism.Classes.CPeople;
import com.indong.capitalism.Classes.CWorld;
import com.indong.capitalism.Classes.Government.CGMinistryOfHealthAndWelfare;
import com.indong.capitalism.Classes.Government.CGMinistryOfTradeIndustryAndEnergy;
import com.indong.capitalism.Classes.Government.CGovernment;
import com.indong.capitalism.Enum.EGovernmentType;

import java.util.Iterator;

public class C2CommandHelper {
    private CCountry country = null;

    public void setCountry(CCountry country)
    {
        this.country = country;
    }
}
