package generalities;

import commons.WeekDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

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
            priorityQueueWithoutComparator.addAll(List.of(WeekDay.FRIDAY, WeekDay.TUESDAY, WeekDay.MONDAY, WeekDay.THURSDAY, WeekDay.WEDNESDAY));

            Queue<WeekDay> priorityQueueWithComparator = new PriorityQueue<>(WeekDay.comparator);
            priorityQueueWithComparator.addAll(List.of(WeekDay.getWeekDays()));

            String queueStringWithoutComparator = makePrintableString(priorityQueueWithoutComparator);
            String queueStringWithComparator = makePrintableString(priorityQueueWithComparator);

            assertNotEquals(queueStringWithComparator, queueStringWithoutComparator);

            assertNotEquals("[MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY]", queueStringWithoutComparator);
            assertEquals("[MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY]", queueStringWithComparator);
        }
    }
}
