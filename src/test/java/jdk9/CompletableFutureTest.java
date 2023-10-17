package jdk9;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@DisplayName("CompletableFuture example test")
public class CompletableFutureTest {

    @Test
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

}
