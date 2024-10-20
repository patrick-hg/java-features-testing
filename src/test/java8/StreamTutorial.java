package java8;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class StreamTutorial {

    @Test
    @DisplayName("Should sort a list of french cities")
    void should_sort_list_of_cities() {

        Stream.of("Paris", "Lyon", "Marseille", "Lille", "Nice", "Bordeaux", "Bayonne", "Nantes", "Strasbourg", "Versailles")
                .sorted()
                .forEach(System.out::println);

    }

}
