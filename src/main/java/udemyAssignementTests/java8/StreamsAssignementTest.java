package udemyAssignementTests.java8;

import udemyAssignementTests.commons.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsAssignementTest {

    public static class Question1 {
        public static void main(String[] args) {

            IntStream.range(0, 5)
                    .average()
                    .ifPresent(avg -> System.out.println("Average is " + avg));

        }
    }

    public static class Question2 {
        public static void main(String[] args) {

            List<Item> items = List.of(
                    new Item(1, "Screw"),
                    new Item(2, "Nail"),
                    new Item(3, "Bolt")
            );

            items.stream()
                    .sorted(Comparator.comparing(Item::getName))
                    .forEach(item -> System.out.print(item.getName()));
        }
    }

    public static class Question3 {
        public static void main(String[] args) {

            Stream.of(Arrays.asList("a", "b"), Arrays.asList("a", "c"))
                    .filter(list -> list.contains("c"))
                    .flatMap(List::stream)
                    .forEach(System.out::print);
        }
    }

    public static class Question4 {
        public static void main(String[] args) {

            int sum = IntStream.range(0, 4).sum();
            int max = IntStream.range(0, 4).max().getAsInt();

            System.out.printf("Sum is %d and max is %d\n", sum, max);

            List<Person> persons = List.of(
                    new Person("Alan", "Burke", 22),
                    new Person("Zoe", "Peters", 20),
                    new Person("Peter", "Castle", 29));

            Person oldest = persons.stream()
                    .max(Comparator.comparing(Person::getAge))
                    .orElseThrow();
            System.out.printf("Oldest person is %s %s\n", oldest.getFirstName(), oldest.getLastName());

            List<Integer> integers = List.of(10, 47, 33, 23);
            BinaryOperator<Integer> greatest = (i1, i2) -> i1 > i2 ? i1 : i2;

            Optional<Integer> reduced1 = integers.stream().reduce(greatest);
            System.out.println("Reduce v1 produce : " + reduced1.get());

            Integer reduced2withIdentity = integers.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
            System.out.println("Reduce v2 (with identity) produce : " + reduced2withIdentity);
        }
    }

    public static class Question5 {

        public static Optional<String> getGrade (int marks) {
            Optional<String> grade = Optional.empty();
            if (marks > 50) {
                grade = Optional.of("PASS");
            } else {
                grade.of("FAIL");   // Optionals are immutable!!!
            }
            return grade;
        }
        public static void main(String[] args) {
            Optional<String> grade1 = getGrade(50);
            Optional<String> grade2 = getGrade(55);

            System.out.printf("iii) Grade1 value is '%s'\n", grade1.orElse("UNKNOWN"));
            System.out.printf(" iv) Grade2 value is '%s'\n", grade2.isPresent() ? grade2.get() : "Empty");
        }
    }

    public static class Question6 {
        public static void main(String[] args) {

            List<Book> books = List.of(
                    new Book("Thinking in java", 30),
                    new Book("Java in 24 hrs", 20),
                    new Book("Java Recipes", 10));

            System.out.printf("Average price for books with price > 10 is %f\n",
                    books.stream()
                            .filter(book -> book.getPrice() > 10)
                            .mapToDouble(Book::getPrice)
                            .average().orElse(0.0));

            System.out.printf("Average price for books with price > 90 is %f\n",
                    books.stream()
                            .filter(book -> book.getPrice() > 90)
                            .mapToDouble(Book::getPrice)
                            .average()
                            .orElse(0.0));
        }
    }

    public static class Question7 {
        public static void main(String[] args) {

            List<Book> books = List.of(
                    new Book("Atlas Shrugged", 10),
                    new Book("Freedom at Midnight", 5),
                    new Book("Gone with the wind", 5));

            Map<String, Double> booksMap = books.stream()
                    .collect(Collectors.toMap(Book::getTitle, Book::getPrice));

            BiConsumer<String, Double> printPriceIfTitleStartsWithA = (title, price) -> {
                if (title.startsWith("A")) {
                    System.out.println("Book '%s' price is %f".formatted(title, price));
                }
            };

            booksMap.forEach(printPriceIfTitleStartsWithA);
        }
    }

    public static class Question8 {
        public static void main(String[] args) {

            List<Book> books = List.of(
                    new Book("Gone with the wind", 5.0),
                    new Book("Gone with the wind", 10.0),
                    new Book("Atlas Shrugged", 15.0));

            BiConsumer<String, Double> printPriceIfTitleStartsWithA = (title, price) -> {
                if (title.startsWith("A")) {
                    System.out.println("Book '%s' price is %f".formatted(title, price));
                }
            };

            books.stream()
                    .collect(Collectors.toMap(Book::getTitle, Book::getPrice, (aDouble, aDouble2) -> Math.min(aDouble, aDouble2)))
                    .forEach((title, price) -> System.out.println(title + " " + price));
        }
    }

    public static class Question14 {
        public static void main(String[] args) {

            AtomicInteger ai = new AtomicInteger(); // initial value of 0
            Stream<Integer> stream = Stream.of(11, 11, 22, 33, 34).parallel();
            Stream<Integer> stream2 = stream.filter( e->{
                ai.incrementAndGet();
                return e%2==0; });
            stream2.forEach(System.out::println);// 22
            System.out.println(ai);

        }
    }


}
