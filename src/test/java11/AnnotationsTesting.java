package java11;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FunctionalInterface
interface Movable {
    void move();
//        void run(); // only one abstract method for functional interface
}

@AllArgsConstructor
@Data
class Person implements Movable {
    private String name;
    private int age;

    @Override
    public void move() {
        System.out.printf("%s is moving...", name);
    }
}

@DisplayName("Annotation Testing")
@TestClassOrder(ClassOrderer.DisplayName.class)
public class AnnotationsTesting {

    @Nested
    @DisplayName("1- Functional Interface Testing")
    class FunctionalInterfaceTesting {

        @Test
        @DisplayName("Should test Functional interface")
        void should_test_functional_interface() {
            Person person = new Person("Brian", 24);
            person.move();
        }

    }

    @Nested
    @DisplayName("2- Custom annotations Testing")
    class CustomAnnotationsTesting {

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @interface WorkSchedule {
            int startTime() default 9;
            int hoursPerWeek();
            int daysPerWeek();
            boolean isHuman() default false;
        }

        final class WorkScheduleChecker {

            record WorkScheduleData(
                    int startTime,
                    int hoursPerWeek,
                    int daysPerWeek,
                    boolean isHuman) {
            }

            public WorkScheduleData process(Object instance) {
                check(instance);

                Person person = (Person) instance;

                Class<?> clazz = person.getClass();
                WorkSchedule workSchedule = clazz.getAnnotation(WorkSchedule.class);

                WorkScheduleData workScheduleData = new WorkScheduleData(
                        workSchedule.startTime(),
                        workSchedule.hoursPerWeek(),
                        workSchedule.daysPerWeek(),
                        workSchedule.isHuman());
                return workScheduleData;
            }

            private void check(Object instance) {
                if (Objects.isNull(instance)) {
                    throw new IllegalArgumentException("The object to check is null");
                }

                Class<?> clazz = instance.getClass();
                if (!clazz.isAnnotationPresent(WorkSchedule.class)) {
                    throw new IllegalArgumentException("The class " + clazz.getSimpleName() + " is not annotated with WorkSchedule");
                }
            }
        }

        @WorkSchedule(
                hoursPerWeek = 35,
                daysPerWeek = 5,
                startTime = 9,
                isHuman = true )
        class Worker extends Person {
            public Worker(String name, int age) {
                super(name, age);
            }
        }

        @WorkSchedule(
                hoursPerWeek = 40,
                daysPerWeek = 5,
                startTime = 8,
                isHuman = true )
        class Student extends Person {
            public Student(String name, int age) {
                super(name, age);
            }
        }

        @Test
        @DisplayName("Should retrieve and process data from type level annotation")
        void should_retrieve_and_process_data_from_type_level_annotation() {
            Worker workerBob = new Worker("Bob", 31);
            Student studentLisa = new Student("Lisa", 19);

            WorkScheduleChecker checker = new WorkScheduleChecker();
            WorkScheduleChecker.WorkScheduleData workerAnnotationData = checker.process(workerBob);
            WorkScheduleChecker.WorkScheduleData studentAnnotationData = checker.process(studentLisa);

            assertEquals(35, workerAnnotationData.hoursPerWeek);
            assertEquals(5, workerAnnotationData.daysPerWeek);
            assertEquals(9, workerAnnotationData.startTime);
            assertTrue(workerAnnotationData.isHuman);

            assertEquals(40, studentAnnotationData.hoursPerWeek);
            assertEquals(5, studentAnnotationData.daysPerWeek);
            assertEquals(8, studentAnnotationData.startTime);
            assertTrue(studentAnnotationData.isHuman);
        }

        @Test
        @DisplayName("Should retrieve and process data from field level annotation")
        void should_retrieve_and_process_data_from_field_level_annotation() {

        }

        @Test
        @DisplayName("Should retrieve and process data from method level annotation")
        void should_retrieve_and_process_data_from_method_level_annotation() {

        }
    }

}
