package generalities.concurrency.dataRace;

import java.util.Scanner;

public class DataRaceExample {

    public static void main(String[] args) throws InterruptedException {

        do {
            String input = printMenuAndGetInput();
            if ("Q".equals(input) || "q".equals(input)) {
                return;
            }

            IShared sharedClass = SharedDataFactory.createSharedData(input);
            sharedClass.startCheckForDataRace();

            switch (input) {
                case "1": // un-protected
                    DataRaceExample.dataRaceRun(sharedClass);
                    break;
                case "2": // protected with synchronized
                    DataRaceExample.protectedWithSynchronizedRun((SharedClass) sharedClass);
                    break;
                case "3": // protected with volatile fields
                    DataRaceExample.dataRaceRun(sharedClass);
                    break;
            }

            sharedClass.stopCheckForDataRace();
            System.out.printf("terminated, %d Data Race detected\n", sharedClass.getCountDataRace());
        } while (true);
    }

    private static String printMenuAndGetInput() {
        System.out.print("""
                    Concurrency Exercice: Data Race and prevention
                    1- Unprotected Data Race run
                    2- Protected with Synchronized run
                    3- Protected with volatile run
                    Q- Quit
                    run number:\s""");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void dataRaceRun(IShared iShared) throws InterruptedException {
        Thread unprotectedIncrementThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                iShared.increment();
            }
        });
        unprotectedIncrementThread.start();
        unprotectedIncrementThread.join();
    }

    private static void protectedWithSynchronizedRun(SharedClass sharedClass) throws InterruptedException {
        Thread protectedIncrementThread = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.incrementSyncrhonized();
            }
        });
        protectedIncrementThread.start();
        protectedIncrementThread.join();
    }
}
