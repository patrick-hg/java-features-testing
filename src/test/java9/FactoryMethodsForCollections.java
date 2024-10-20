package java9;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryMethodsForCollections {

    @Test
    public void should_create_collections_the_new_way() {

        Set<String> set = Set.of("Red", "Green", "Blue");
        List<String> list = List.of("Red", "Green", "Blue");
        Map<String, String> map = Map.of("FF0000", "Red","00FF00", "Green", "0000FF", "Blue");

        assertTrue(set.contains("Red") && set.contains("Green") && set.contains("Blue"));
        assertTrue(list.contains("Red") && list.contains("Green") && list.contains("Blue"));
        assertTrue(map.containsValue("Red") && map.containsValue("Green") && map.containsValue("Blue"));
    }
}
