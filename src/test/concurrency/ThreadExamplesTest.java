package concurrency;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ThreadExamplesTest {

    @Test
    void build_and_run_simple_threads() {

        SimpleThread[] threads = new SimpleThread[10];
        for (int i = 0; i<10; i++) {
            threads[i] = new SimpleThread();
            threads[i].start();
        }

        while (Arrays.stream(threads).anyMatch(SimpleThread::isAlive)) {
            try {
                System.out.println("maestro> work still in progress....");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        System.out.println("maestro> all work is done!");

    }

}