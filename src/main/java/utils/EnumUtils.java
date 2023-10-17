package utils;

import java.util.EnumSet;

import static utils.MathUtils.randomNumberBetween;

public class EnumUtils {

    public static <T extends Enum<T>> T getRandomValueFromEnum(Class<T> anyEnum) {
        EnumSet enumSet = EnumSet.allOf(anyEnum);
        int randomIndex = (int)(randomNumberBetween(0, enumSet.size() - 1));
        return (T) enumSet.toArray()[randomIndex];
    }
}
