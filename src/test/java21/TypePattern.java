package java21;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypePattern {

    @Nested
    class OldFashion {

        @Test
        void condition_on_instance () {
            // return object's length depending on object Class
            Object objectText = "Some text...";
            Object objectNumber = 123;

            assertEquals(12, getLengthOldFashion(objectText));
            assertEquals(3, getLengthOldFashion(objectNumber));
            assertEquals(12, getLengthUsingTypePattern(objectText));
            assertEquals(3, getLengthUsingTypePattern(objectNumber));
        }

        private int getLengthOldFashion(Object object) {
            if (object instanceof String) {
                String text = (String) object;
                return text.length();

            } else if (object instanceof Integer) {
                Integer number = (Integer) object;
                return number.toString().length();
            }
            else {
                throw new IllegalArgumentException();
            }
        }

        private int getLengthUsingTypePattern(Object object) {
            if (object instanceof String text) {
                return text.length();

            } else if (object instanceof Integer number) {
                return number.toString().length();
            }
            else {
                throw new IllegalArgumentException();
            }

        }
    }

}
