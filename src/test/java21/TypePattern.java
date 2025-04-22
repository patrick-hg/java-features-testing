package java21;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TypePattern {


    @Test
    void should_return_length () {
        // return object's length depending on object Class
        Object objectText = "Some text...";
        Object objectNumber = 123;

        assertEquals(12, getLength(objectText));
        assertEquals(3, getLength(objectNumber));
    }

    @Test
    void should_return_length_using_type_pattern () {
        // return object's length depending on object Class
        Object objectText = "Some text...";
        Object objectNumber = 123;

        assertEquals(12, getLengthPatternVariable(objectText));
        assertEquals(3, getLengthPatternVariable(objectNumber));
    }

    private int getLength(Object object) {
        if (object instanceof String) {
            String text = (String) object;
            return text.length();
        } else if (object instanceof Integer) {
            Integer number = (Integer) object;
            return number.toString().length();
        }
        throw new IllegalArgumentException();
    }

    private int getLengthPatternVariable(Object object) {
        if (object instanceof String text) {
            return text.length();
        } else if (object instanceof Integer number) {
            return number.toString().length();
        }
        throw new IllegalArgumentException();
    }
}