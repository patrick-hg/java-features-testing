package generalities;

import commons.WeekDay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static commons.WeekDay.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.CollectionUtils.makePrintableString;

@DisplayName("Java Collections Testing")
public class CollectionsTesting {

    @Nested
    @DisplayName("Queue testing")
    public class QueueTesting {

        @Test
        @DisplayName("Test Queue LinkedList")
        void test_LinkedList () {
            Queue<String> queue = new LinkedList<>();
            queue.add("monday");
            queue.add("tuesday");
            queue.add("wednesday");
            queue.add("thursday");
            queue.add("friday");

            assertEquals("monday", queue.peek());
            assertEquals("[monday,tuesday,wednesday,thursday,friday]", makePrintableString(queue));

            queue.poll();
            queue.add("saturday");
            assertEquals("[tuesday,wednesday,thursday,friday,saturday]", makePrintableString(queue));
        }

        @Test
        @DisplayName("Test Queue PriorityQueue")
        void test_Link () {

            Queue<WeekDay> priorityQueueWithoutComparator = new PriorityQueue<>();
            priorityQueueWithoutComparator.addAll(List.of(WeekDay.FRIDAY, WeekDay.TUESDAY, MONDAY, WeekDay.THURSDAY, WeekDay.WEDNESDAY));

            Queue<WeekDay> priorityQueueWithComparator = new PriorityQueue<>(WeekDay.comparator);
            priorityQueueWithComparator.addAll(List.of(WeekDay.getWeekDays()));

            String queueStringWithoutComparator = makePrintableString(priorityQueueWithoutComparator);
            String queueStringWithComparator = makePrintableString(priorityQueueWithComparator);

            assertNotEquals(queueStringWithComparator, queueStringWithoutComparator);

            assertNotEquals("[MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY]", queueStringWithoutComparator);
            assertEquals("[MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY]", queueStringWithComparator);
        }

        @Test
        @DisplayName("Test Deque")
        void test_deque() {
            Deque<WeekDay> deque = new ArrayDeque<>();
            deque.add(TUESDAY);
            deque.add(WEDNESDAY);
            deque.add(THURSDAY);

            deque.addFirst(MONDAY);
            deque.addLast(FRIDAY);

            assertEquals("[MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]", deque.toString());
        }
    }
}
