package kapitalism.javaorigin.Util;

import kapitalism.javaorigin.DataStructure.DTime;

import java.util.Calendar;

public class UTime {
    private static final UTime instance = new UTime();
    public static UTime getInstance()
    {
        return instance;
    }

    public int getPeriod(DTime oldtime , DTime latelytime)
    {
        Calendar cOld = Calendar.getInstance();
        Calendar cLately = Calendar.getInstance();

        cOld.set(oldtime.getYear(),oldtime.getMonth(),oldtime.getDay());
        cLately.set(latelytime.getYear(),latelytime.getMonth(),latelytime.getDay());

        long l_oldday = cOld.getTimeInMillis() / (24 * 60 * 60 * 1000);
        long l_latelyday = cLately.getTimeInMillis() / (24 * 60 * 60 * 1000);

        long result = l_latelyday - l_oldday;
        return (int)result;
    }
}
