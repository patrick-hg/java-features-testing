package generalities.concurrency.factorialThreadExercise;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FactorialThread_1_OneComputationOneThread {

    /**
     * First: Generate N random numbers
     * Then: Calculate Factorial value for each
     * **Simplest: one computation is done by one thread
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Concurrency Exercise: Factorial Thread - 1 - One Computation One Thread");

        // Get input
        Scanner scanner = new Scanner(System.in);
        System.out.printf("[INPUT] nbItems,maxBound (ex: 10,100): ");
        String[] input = scanner.next().split(",");
        int nbItems = Integer.valueOf(input[0]);
        int maxBound = Integer.valueOf(input[1]);

        FactorialThread_1_OneComputationOneThread factorialThreadTest = new FactorialThread_1_OneComputationOneThread();
        factorialThreadTest.execute(nbItems, maxBound);
    }

    public void execute(int nbItems, int maxBound) throws InterruptedException {

        // generate the input numbers randomly with a thread
        RandomNumbersThread randomNumbersThread = new RandomNumbersThread(nbItems, maxBound);
        randomNumbersThread.start();
        randomNumbersThread.join();     // wait (indefinitely) for thread to finish
        Set<Integer> numbers = randomNumbersThread.getRandomNumbers();  // get the values from the thread

        System.out.println("Let's calculate the factorial of the following numbers: " + numbers);

        List<FactorialThread> factorialThreads = new ArrayList<>(nbItems);
        numbers.forEach(number -> factorialThreads.add(new FactorialThread(number)));
        factorialThreads.forEach(FactorialThread::start);

        // main thread get the values as soon as calculation is terminated and print value
        AtomicInteger nbItemsCalculated = new AtomicInteger();
        do {
            Thread.sleep(1000);
            factorialThreads.forEach(factorialThread -> {
                if (factorialThread.hasTerminated()) {
                    if (!factorialThread.hasResultBeenPassed()) {
                        nbItemsCalculated.getAndIncrement();
                    }
                    System.out.printf("[%s-%d] Factorial of %d is %s\n",
                            factorialThread.getName(), factorialThread.threadId(),
                            factorialThread.inputNumber, factorialThread.getOutputNumber().toString());
                } else {
                    System.out.printf("Thread %s-%d is still calculating factorial of %d...\n",
                            factorialThread.getName(),
                            factorialThread.threadId(),
                            factorialThread.inputNumber);
                }
            });
            System.out.printf("---%d/%d---------------------------------------------------------------------------\n", nbItemsCalculated.get(), nbItems);
        } while (nbItemsCalculated.get() < nbItems);
        System.out.println("The end thank you.");
    }

    private class RandomNumbersThread extends Thread {
        private int nbItems;
        private int maxBound;
        private Set<Integer> randomNumbers;
        private boolean jobCompleted;

        public RandomNumbersThread(int nbItems, int maxBound) {
            if (nbItems > maxBound) {
                throw new IllegalArgumentException("The number of random values (nbItems) shouldn't be inferior to maxBound");
            }
            this.setName(this.getClass().getSimpleName());
            this.nbItems = nbItems;
            this.maxBound = maxBound;
        }

        @Override
        public void run() {
            System.out.printf("Thread %s is generating %d random numbers...\n", this.getName(), this.nbItems);
            randomNumbers = new HashSet<>();
            Random random = new Random();
            while (!this.isInterrupted() && randomNumbers.size() < nbItems) {

                Integer randomNumber = random.nextInt(maxBound);
                while (randomNumbers.contains(randomNumber)) {
                    randomNumber = random.nextInt(maxBound);
                }
                randomNumbers.add(randomNumber);

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.jobCompleted = true;
        }

        public Set<Integer> getRandomNumbers() {
            return randomNumbers;
        }
    }

    private class FactorialThread extends Thread {

        private Integer inputNumber;
        private BigInteger outputNumber;

        private boolean hasTerminated;  // if calculation job finished and result is ready
        private boolean resultPassed;   // if factorial result have been read from outside

        public FactorialThread(Integer number) {
            this.setName(this.getClass().getSimpleName());
            this.inputNumber = number;
        }

        @Override
        public void run() {

            if (inputNumber == 0 || inputNumber == 1) {
                outputNumber = BigInteger.ONE;
            } else {
                int number = inputNumber;
                outputNumber = BigInteger.valueOf(inputNumber);
                while (number > 1) {
                    outputNumber = outputNumber.multiply(BigInteger.valueOf(--number));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            this.hasTerminated = true;
        }

        public BigInteger getOutputNumber() {
            this.resultPassed = true;
            return outputNumber;
        }

        public boolean hasTerminated() {
            return hasTerminated;
        }

        public boolean hasResultBeenPassed() {
            return resultPassed;
        }

    }
}
