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
    void should_create_and_implement_capitalize_lambda() {
        Capitalize capitalize = (str) -> str.toUpperCase();
        assertEquals("WELCOME HOME", capitalize.apply("welcome home"));
    }

    @Test
    void should_create_and_implement_power_lambda() {
        Power power = (x, y) -> powerFunction(x,y);
        assertEquals(4, power.apply(2, 2));
        assertEquals(8, power.apply(2, 3));
        assertEquals(16, power.apply(2, 4));
    }

    private int powerFunction(int x, int y) {
        int result = x;
        for (int i=1; i<y; i++) {
            result = result * x;
        }
        return result;
    }
}
