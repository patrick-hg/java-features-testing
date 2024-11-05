package java11;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Annotations {

    @Nested
    class FunctionalInterfaceTest {

        @FunctionalInterface
        interface Movable {
            void move();
//        void run(); // only one abstract method for functional interface

        }
        class Person implements Movable {

            private String name;

            public Person(String name) {
                this.name = name;
            }

            @Override
            public void move() {
                System.out.printf("%s is moving...", name);
            }
        }

        @Test
        void should_test_annotations() {
            Person person = new Person("Brian");
            person.move();
        }

    }

    class CustomAnnotationsDeclarationAndUse {

        @Retention(RetentionPolicy.CLASS)
        @interface WorkSchedule {
            int startTime() default 9;
            int hoursPerDay();
            int daysPerWeek();
            boolean isHuman() default false;
        }

        @WorkSchedule(hoursPerDay = 7, daysPerWeek = 5)
        class Worker {
        }

        @WorkSchedule(hoursPerDay = 8, daysPerWeek = 5)
        class Student {
        }

        void should_use_custom_annotatioN() {
            Worker bob = new Worker();
            Student lisa = new Student();
        }
    }

}
