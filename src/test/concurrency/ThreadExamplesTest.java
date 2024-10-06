package concurrency;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

class ThreadExamplesTest {

    @Test
    @DisplayName("run 10 simple threads wait till finish")
    void run_10_simple_threads_wait_till_finish() {

        SimpleThread[] threads = new SimpleThread[10];
        for (int i = 0; i<10; i++) {
            threads[i] = new SimpleThread(10);
            threads[i].start();
        }

        waitForThreadsToComplete(threads, 5000);
    }

    private void waitForThreadsToComplete(Thread[] threads, long maxWaitTime) {
        System.out.println("[master] Waiting for %d threads to complete, maxWaitTime is %ds".formatted(threads.length, maxWaitTime/1000));
        long timeIn = 0L;
        while (timeIn < maxWaitTime) {
            if (Arrays.stream(threads).anyMatch(Thread::isAlive)) {
                try {
                    System.out.printf("[master] time:%ds work still in progress....%n", timeIn/1000);
                    Thread.sleep(1000);
                    timeIn += 1000;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("[master] given time ended, will terminate all worker threads still alive");
        joinOrInterruptThreads(threads);

        String threadState = Arrays.stream(threads)
                .map(thread -> "(%s:%s)".formatted(thread.getName(), thread.getState()))
                .collect(Collectors.joining());
        System.out.printf("[master] thread states: [%s]\n", threadState);
        System.out.println("[master] all work is done!");
    }

    private void joinOrInterruptThreads(Thread[] threads) {
        for (int i=0; i<10; i++) {
            if (threads[i].isAlive()) {
                System.out.printf("[master] thread '%s' will be interrupted immediately!%n", threads[i].getName());
                threads[i].interrupt();
            } else {
                try {
                    threads[i].join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        System.out.println("[master] join threads completed");
    }

}