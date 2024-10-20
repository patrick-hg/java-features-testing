package java8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionalInterfacesTest {

    @FunctionalInterface
    interface Capitalize {
        String apply(String str);
    }

    @FunctionalInterface
    interface Power {
        int apply(int x, int y);
    }

    @Test
    void should_create_and_implement_lambda() {
        Capitalize toUpperCaseLambda = (str) -> str.toUpperCase();
        assertEquals("WELCOME HOME", toUpperCaseLambda.apply("welcome home"));

        Power powerLambda = (x, y) -> basicPower(x,y);
        assertEquals(4, powerLambda.apply(2, 2));
        assertEquals(8, powerLambda.apply(2, 3));
        assertEquals(16, powerLambda.apply(2, 4));
    }

    int basicPower(int x, int y) {
        int result = x;
        for (int i=1; i<y; i++) {
            result = result * x;
        }
        return result;
    }
}
