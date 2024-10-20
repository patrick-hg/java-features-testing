package java9;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@DisplayName("Stream Features example")
class StreamFeatures {

    @Test
    @DisplayName("Skip and limit example")
    public void skip_and_limit_example() {

        List<Integer> stream = Stream.of(12, 15, 24, 3, 7, 35, 12, 30, 64)
                .skip(2)    // skip the number of elements at the beginning
                .limit(3)   // limit the number of returned items
                .collect(Collectors.toList());

        assert stream.get(0).equals(24);
        assert stream.get(1).equals(3);
        assert stream.get(2).equals(7);
        assert stream.size() == 3;
    }

    @Test
    @DisplayName("takeWhile and dropWhile example")
    public void takeWhile_and_dropWhile_example() {
        List<Integer> list = Stream.of(12, 15, 24, 3, 7, 35, 12, 30, 64)
                .dropWhile(integer -> integer < 20)     // 24, 3, 7, 35, 12, 30, 64  exclude items till condition is true
                .takeWhile(integer -> integer != 30)    // 24, 3, 7, 35, 12   include items till condition is true
                .collect(Collectors.toList());

        assert list.get(0).equals(24);
        assert list.get(1).equals(3);
        assert list.get(2).equals(7);
        assert list.get(3).equals(35);
        assert list.get(4).equals(12);
        assert list.size() == 5;
    }

    @Test
    @DisplayName("Stream iterate example: return all pair numbers")
    public void stream_iterate_example() {

        List<Integer> pairs = Stream.iterate(0, i -> i + 2)
                .limit(6)
                .collect(Collectors.toList());

        pairs.forEach(i -> System.out.print(i + ","));
        assert pairs.equals(Arrays.asList(0, 2, 4, 6, 8, 10));
    }


}