package generalities.concurrency.dataRace;

import org.junit.jupiter.api.*;

@DisplayName("Concurrency Exercice: Data Race and prevention")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class DataRaceExampleTest {

    @Test
    @DisplayName("1- Unprotected data race run")
    void unprotected_dataRace_run() throws InterruptedException {

        IShared sharedClass = SharedDataFactory.createSharedData("1", 10_000);
        sharedClass.startCheckForDataRace();

        DataRaceExampleTest.dataRaceRun(sharedClass);

        sharedClass.stopCheckForDataRace();
        System.out.printf("terminated, %d Data Race detected\n", sharedClass.getCountDataRace());
    }

    @Test
    @DisplayName("2- Protected with synchronize run")
    void protected_with_synchronize_run() throws InterruptedException {

        IShared sharedClass = SharedDataFactory.createSharedData("2", 10_000);
        sharedClass.startCheckForDataRace();

        DataRaceExampleTest.protectedWithSynchronizedRun((SharedClass) sharedClass);

        sharedClass.stopCheckForDataRace();
        System.out.printf("terminated, %d Data Race detected\n", sharedClass.getCountDataRace());
    }

    @Test
    @DisplayName("3- Protected with volatile run")
    void protected_with_volatile_run() throws InterruptedException {

        IShared sharedClass = SharedDataFactory.createSharedData("3", 10_000);
        sharedClass.startCheckForDataRace();

        DataRaceExampleTest.dataRaceRun(sharedClass);
        sharedClass.stopCheckForDataRace();
        System.out.printf("terminated, %d Data Race detected\n", sharedClass.getCountDataRace());
    }

    private static void dataRaceRun(IShared iShared) throws InterruptedException {
        Thread unprotectedIncrementThread = new Thread(() -> {
            for (int i = 0; i < iShared.getMax(); i++) {
                iShared.increment();
            }
        });
        unprotectedIncrementThread.start();
        unprotectedIncrementThread.join();
    }

    private static void protectedWithSynchronizedRun(SharedClass sharedClass) throws InterruptedException {
        Thread protectedIncrementThread = new Thread(() -> {
            for (int i = 0; i < sharedClass.getMax(); i++) {
                sharedClass.incrementSyncrhonized();
            }
        });
        protectedIncrementThread.start();
        protectedIncrementThread.join();
    }
}
