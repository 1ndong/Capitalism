package capitalism.Util;

import java.util.concurrent.ThreadLocalRandom;

public class URandom {
    private static final URandom instance = new URandom();
    public static URandom getInstance() {
        return instance;
    }

    public long getRandomMoney(long rangeA, long rangeB) {
        long range = Math.max(rangeA, rangeB) - Math.min(rangeA, rangeB);
        long randomvalue = ThreadLocalRandom.current().nextLong(range);
        return randomvalue + range;
    }

    public float nextFloat() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
