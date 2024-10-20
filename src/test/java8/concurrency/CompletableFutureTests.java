package java8.concurrency;


import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTests {

    @Test
    @DisplayName("Should run a task asynchronously with CompletableFuture")
    public void should_run_a_task_asynchronously() {

        CompletableFuture.supplyAsync(this::random)
                .orTimeout(2, TimeUnit.SECONDS)
                .thenAccept(System.out::println);
    }

    @SneakyThrows
    private long random () {
        long sleepDuration = Double.valueOf(Math.random() * 4000).longValue();
        System.out.println("i will be sleeping " + sleepDuration + " ms before return");
        Thread.sleep(sleepDuration);
        return sleepDuration;
    }

    @Test
    @DisplayName("Should run multitasks in parallel with CompletableFuture")
    void should_run_multitasks_in_parallel() throws InterruptedException {
        Worker alex = new Worker("Alex");
        Worker yvan = new Worker("Yvan");

        CompletableFuture<String> alexAsyncWork = CompletableFuture.supplyAsync(() -> {
            alex.doWork(7);
            return alex.name + " finished!";
        });

        CompletableFuture<String> yvanAsyncWork = CompletableFuture.supplyAsync(() -> {
            yvan.doWork(10);
            return yvan.name + " finished!";
        });

        CompletableFuture<Void> bothFutures = CompletableFuture.allOf(alexAsyncWork, yvanAsyncWork);
        while (!bothFutures.isDone()) {
            System.out.println("[%s] Waiting for both workers to finish work...".formatted(LocalDateTime.now()));
            Thread.sleep(1000);
        }
        System.out.println("Both workers have finished");
    }

    record Worker (String name) {
        @SneakyThrows
        public void doWork(int seconds) {
            System.out.println("[%s] just starting work that takes %s seconds".formatted(name, seconds));
            for (int i=0; i<seconds; i++) {
                System.out.println("[%s] work in progress (%d%%)...".formatted(name, (int)((float)i/seconds *100)));
                Thread.sleep(1000);
            }
            System.out.println("[%s] work is complete!".formatted(name));
        }
    }

}
