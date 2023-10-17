package utils;

public class MathUtils {

    public static long randomNumberBetween(long min, long max) {
        return Double.valueOf(Math.random() * (max - min)).longValue() + min;
    }
}
