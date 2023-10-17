package jdk9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FactoryMethodsForCollections {

    @Test
    public void should_create_collections_the_new_way() {

        Set<Integer> set = Set.of(1, 2, 3, 4);
        List<String> list = List.of("Apple", "Banana", "Orange", "Strawberry");
        Map<String, String> map = Map.of("SPAIN", "ORANGE", "ITALY", "grapes");

        assert set.toString().equals("1,2,3,4");
    }
}
