package generalities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PatHashmapTest {

    @Test
    @DisplayName("Should create a HashMap of employees")
    void should_create_a_hashmap_of_employees_adding_new_elements_and_replace_existing () {

        // given
        PatHashmap hashmap = new PatHashmap(3);
        hashmap.put("Martin", "Developer");
        hashmap.put("Julian", "Web Designer");
        hashmap.put("Andre", "DevOps");
        hashmap.put("Juan", "UX");
        hashmap.put("Alex", "Front");
        hashmap.put("Igor", "Sound Engineer");


        // Then
        System.out.println(hashmap);
        String expected = "HASHMAP: {\n" +
                "  bucket:0 {Martin: Developer}, {Julian: Web Designer}, {Andre: DevOps}, {Juan: UX}, {Alex: Front}\n" +
                "  bucket:1 {Igor: Sound Engineer}\n" +
                "} #[size: 6, nbOfBuckets: 2]";
        assertEquals(expected, hashmap.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 1, 0",
            "10, 2, 0",
            "11, 2, 1",
            "15, 3, 0",
            "16, 3, 1",
            "17, 3, 2"
    })
    @DisplayName("Should reduce HashCode to the number of buckets")
    void should_reduce_hashCode(int hashCode, int nbOfBuckets, int expectedReduce) {

        assertEquals(expectedReduce, PatHashmap.reduceHashCode(hashCode, nbOfBuckets));
    }

    @Test
    void should_increment_number_of_buckets_when_adding_items() {

        PatHashmap<UUID, String> hashmap = new PatHashmap(5);
        assertEquals(0, hashmap.size());
        assertEquals(1, hashmap.nbOfBuckets());
        for (int i=0; i<15; i++) {
            UUID randomUuid = UUID.randomUUID();
            hashmap.put(randomUuid, randomUuid.toString());
            System.out.println(hashmap);
        }

        assertEquals(15, hashmap.size());
        assertEquals(3, hashmap.nbOfBuckets());
    }

}