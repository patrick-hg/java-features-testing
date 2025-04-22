package generalities.concurrency;

import org.junit.jupiter.api.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TestClassOrder(ClassOrderer.DisplayName.class)
public class ThreadPoolTesting {

    public static final int NUMBER_OF_TASKS = 10_000;

    @Nested
    @DisplayName("0- Single Thread Pool")
    class SingleThreadPool {

    }

    @Nested
    @DisplayName("1- Cached Thread Pool")
    public class CachedThreadPool {
        @Test
        void should_perform_tasks_1000_with_cached_thread_pool() {
            System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
            try (ExecutorService executorService = Executors.newCachedThreadPool()) {
                for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                    executorService.submit(() -> blockingIoOperation());
                }
            }
        }
    }

    @Nested
    @DisplayName("2- Fixed Thread Pool")
    public class FixedThreadPool {
        @Test
        void should_perform_hundred_tasks_100x10_with_fixed_thread_pool() {
            System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
            try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
                for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 100; j++) {
                                blockingIoOperationShort();
                            }
                        }
                    });
                }
            }
        }
    }

    @Nested
    @DisplayName("3- Virtual Thread Pool test")
    public class VirtualThreadPool {
        @Test
        void should_perform_hundred_tasks_with_virtual_thread_pool() {
            System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                    executorService.submit(() -> blockingIoOperation());
                }
            }
        }

        @Test
        void should_perform_hundred_100x10_tasks_with_virtual_thread_pool() {
            System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);
            try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            for (int j = 0; j < 100; j++) {
                                blockingIoOperationShort();
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * Simulate a long blocking IO operation
     */
    private static void blockingIoOperation() {
        sleep(1000);
    }

    private static void blockingIoOperationShort() {
        sleep(10);
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
