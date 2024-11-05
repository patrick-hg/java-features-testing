package udemyAssignementTests.java8;

import udemyAssignementTests.commons.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaAssignementTest {


    /**
     * 1 - In main() invoke the consumer() method; in consumer() do the following:
     * a) Using a lambda expression, implement the Printable interface (typed for String). The relevant method just prints out the String argument it receives. Invoke the relevant method, passing in "Printable lambda".
     * b) Using both a lambda expression and a method reference, implement 1a using a Consumer.
     */
    public static class Exercice1 {

        @FunctionalInterface
        public interface Printable<T> {
            void print (T t);
        }

        public static void main(String[] args) {
            Printable<String> a = s -> System.out.println(s);
            Consumer<String> b = System.out::println;

            a.print("Hello world");
            b.accept("Hello World");
        }

    }

    /**
     * 2 - In main() invoke the supplier() method; in supplier() do the following:
     * a) Using a lambda expression, implement the Retrievable interface (typed for Integer). The relevant method just returns 77. Invoke the relevant method.
     * b) Using a lambda expression, implement 2a using a Supplier.
     */
    public static class Exercice2 {

        @FunctionalInterface
        public interface Retrievable {
            int retrieve ();
        }

        public static void main(String[] args) {
            Retrievable a = () -> 77;
            Supplier<Integer> b = () -> 77;

            System.out.println(a.retrieve());
            System.out.println(b.get());
        }

    }

    /**
     * 3 - In main() invoke the predicate() method; in predicate() do the following:
     * a) Using a lambda expression, implement the Evaluate interface (typed for Integer). The relevant method returns true if the argument passed is < 0, otherwise it returns false.
     * Invoke the relevant method twice – the first time pass in -1 and the second time pass in +1
     * b) Using a lambda expression, implement 3a using a Predicate.
     * c) Declare a generically-typed check() method (not in UML). The first parameter is generic and the second parameter is a Predicate, also generically typed. The check() method returns true/false.
     * Invoke the check() method with the following Predicate lambda expressions:
     * - we want to know if a number is even (true) – invoke check() with 4 and 7 (true and false).
     * - we want to know if a String begins with “Mr.” – invoke check() with “Mr. Joe Bloggs” and “Ms. Ann Bloggs”
     * - we want to know if a person is an adult (age >= 18) – invoke check() with “Mike” who is 33 and 1.8 (metres assumed) in height; and “Ann” who is 13 and 1.4 (metres) in height.
     */
    public static class Exercice3 {

        @FunctionalInterface
        public interface Evaluate<T> {
            boolean evaluate (T t);
        }

        public static <T> Boolean check(T t, Predicate pred) {
            return switch (t) {
                case Integer i -> pred.test(i);
                case String s -> pred.test(s);
                case Person p -> pred.test(p);
                default -> throw new IllegalArgumentException("Can't handle parameter %s"
                        .formatted(t.getClass().toGenericString()));
            };
        }
        public static void main(String[] args) {
            _3a();
            _3b();
            _3c();
        }

        public static void _3a() {
            Evaluate<Integer> a = in -> in < 0;
            System.out.printf("-1 -> %b , 1 -> %b%n", a.evaluate(-1), a.evaluate(1));
        }

        public static void _3b() {
            Predicate<Integer> b = in -> in < 0;
            System.out.printf("-1 -> %b , 1 -> %b%n", b.test(-1), b.test(1));
        }

        public static void _3c() {
            Predicate<Integer> isEven = in -> in % 2 == 0;
            System.out.printf("is 4 even ? %b %n", check(4, isEven));
            System.out.printf("is 7 even ? %b %n", check(7, isEven));

            final String MR_JOE_BLOGGS = "Mr. Joe Bloggs";
            final String MS_ANN_BLOGGS = "Ms. Ann Bloggs";
            Predicate<String> startsWithMr = in -> in.startsWith("Mr.");
            System.out.printf("does string %s begins with 'Mr.' ? %b \n", MR_JOE_BLOGGS, check(MR_JOE_BLOGGS, startsWithMr));
            System.out.printf("does string %s begins with 'Mr.' ? %b \n", MS_ANN_BLOGGS, check(MS_ANN_BLOGGS, startsWithMr));

            Person mike = new Person("Mike", "Johnson", 33);
            Person ann = new Person("Ann", "Franklin", 13);
            Predicate<Person> isAdult = person -> person.getAge() > 18;
            System.out.printf("is Person '%s' an adult ? %b \n", mike.getFirstName(), check(mike, isAdult));
            System.out.printf("is Person '%s' an adult ? %b \n", ann.getFirstName(), check(ann, isAdult));

        }
    }

    /**
     * 4 - In main() invoke the function() method; in function() do the following:
     * a) Using a lambda expression, implement the Functionable interface - the input type is Integer and the return type is String.
     * The relevant method returns the number passed in appended to the String “Number is: ”.
     * Invoke the relevant method passing in 25.
     * b) Using a lambda expression, implement 4a using a Function.
     */
    public static class Exercice4 {

        @FunctionalInterface
        private interface Functionable {
            String apply(Integer in);
        }

        private static void function() {
            Functionable functionable = in -> "Number is: " + in;
            String output = functionable.apply(25);
            System.out.println(output);

            Function<Integer, String> functionLambda = in -> "Number is: " + in;
            System.out.println(functionLambda.apply(25));
        }

        public static void main(String[] args) {
            function();
        }

    }

    /**
     * Given the following implementation of the getPeople() method:
     *
     * private static List<Person> getPeople() {
     *  List<Person> result = new ArrayList<>();
     *  result.add(new Person("Mike", 33, 1.8));
     *  result.add(new Person("Mary", 25, 1.4));
     *  result.add(new Person("Alan", 34, 1.7));
     *  result.add(new Person("Zoe", 30, 1.5));
     *  return result;
     * }
     * In main(), invoke the getPeople() – store the result in a variable named listPeople.
     */
    public static class Exercice5 {

        private static List<Person> getPeople() {
            List<Person> result = new ArrayList<>();
            result.add(new Person("Mike", "Johnson", 33));
            result.add(new Person("Mary", "Lee", 25));
            result.add(new Person("Alan", "Roselin", 34));
            result.add(new Person("Zoe", "Zhong", 30));
            return result;
        }

        public static void main(String[] args) {
            List<Person> listPeople = getPeople();
            System.out.println(listPeople);
        }
    }
}
