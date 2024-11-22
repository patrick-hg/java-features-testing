package generalities.concurrency.factorialThreadExercise;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class FactorialThread_2_PersonalThreadPool {

    /**
     * First: Generate N random numbers
     * Then: Calculate Factorial value for each
     * **Advanced: use a fixed thread pool
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Concurrency Exercise: Factorial Thread - 2 - Personal Thread Pool");

        // Get input
        Scanner scanner = new Scanner(System.in);
        System.out.printf("[INPUT] nbItems,maxBound,nbThread (ex: 10,100,4): ");
        String[] input = scanner.next().split(",");
        int nbItems = Integer.valueOf(input[0]);
        int maxBound = Integer.valueOf(input[1]);
        int nbThread = Integer.valueOf(input[2]);

        FactorialThread_2_PersonalThreadPool factorialThreadTest = new FactorialThread_2_PersonalThreadPool();
        factorialThreadTest.execute(nbItems, maxBound, nbThread);
    }

    public void execute(int nbItems, int maxBound, int nbThreads) throws InterruptedException {

        // generate the input numbers randomly with a thread
        RandomNumbersThread randomNumbersThread = new RandomNumbersThread(nbItems, maxBound);
        randomNumbersThread.start();
        randomNumbersThread.join();     // wait (indefinitely) for thread to finish
        List<Integer> numbers = randomNumbersThread.getRandomNumbers();  // get the values from the thread

        System.out.printf("Let's calculate the factorial of the following numbers: [%s] with %d threads\n", numbers, nbThreads);

        // configure and start worker threads
        PersonalThreadPool threadPool = new PersonalThreadPool(nbThreads);
        threadPool.startAll();

        // distribute the work
        Thread workDistributorThread = new Thread(() -> {
            numbers.forEach(number -> {
                boolean hasReadyFactorialThread;
                do {
                    try {
                        FactorialThread readyThread = threadPool.getOneReadyThread();
                        System.out.printf("[%s] %d! will be calculated by %s\n", Thread.currentThread().getName(), number, readyThread.getName());
                        readyThread.newInput(number);
                        hasReadyFactorialThread = true;

                    } catch (ExecutionException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while (!hasReadyFactorialThread);
            });
        });
        workDistributorThread.setName("WorkDistributorThread");
        workDistributorThread.start();

        // main thread get the values as soon as calculation is terminated and print value
        AtomicInteger nbItemsCalculated = new AtomicInteger();
        Consumer<FactorialThread> printCalculationThreadState = factorialThread -> {
            if (factorialThread.hasResultBeenPassed()) {
                return;
            }
            if (factorialThread.hasTerminated()) {
                nbItemsCalculated.getAndIncrement();
                System.out.printf("[%s] Factorial of %d is %s\n",
                        factorialThread.getName(),
                        factorialThread.getInputNumber(),
                        factorialThread.getOutputNumber().toString());
            } else {
                System.out.printf("[%s] Still calculating factorial of %d...\n",
                        factorialThread.getName(),
                        factorialThread.getInputNumber());
            }
        };
        do {
            Thread.sleep(1000);
            threadPool.forEach(printCalculationThreadState);
            System.out.printf("---count: %d/%d---------------------------------------------------------------------------\n", nbItemsCalculated.get(), nbItems);
        } while (nbItemsCalculated.get() < nbItems);

        // terminate all threads
        threadPool.terminateAll();
        workDistributorThread.interrupt();

        System.out.println("The end thank you.");
    }

    private class RandomNumbersThread extends Thread {
        private int nbItems;
        private int maxBound;
        private List<Integer> randomNumbers;
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
            randomNumbers = new ArrayList<>();
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

        public List<Integer> getRandomNumbers() {
            return randomNumbers;
        }
    }

    private class FactorialThread extends Thread {

        private Integer inputNumber;
        private BigInteger outputNumber;

        private boolean hasTerminated;  // if calculation job finished and result is ready
        private boolean resultPassed;   // if factorial result have been read from outside

        public FactorialThread() {
        }

        @Override
        public void run() {
            while (!this.isInterrupted()) {

                if (inputNumber != null) {
                    this.outputNumber = calculateFactorial(inputNumber);
                    this.hasTerminated = true;
                }
                sleep(1000);
            }

        }

        public Integer getInputNumber() {
            return this.inputNumber;
        }

        public BigInteger getOutputNumber() {
            this.resultPassed = true;
            this.inputNumber = null;
            return outputNumber;
        }

        public boolean hasTerminated() {
            return hasTerminated;
        }

        public boolean hasResultBeenPassed() {
            return resultPassed;
        }

        public void newInput(int inputNumber) {
            this.inputNumber = inputNumber;
            this.outputNumber = null;
            this.hasTerminated = false;
            this.resultPassed = false;
        }

        // calculate factorial
        private BigInteger calculateFactorial(int number) {
            if (number == 0 || number == 1) {
                return BigInteger.ONE;
            }
            BigInteger value = BigInteger.valueOf(number);
            while (number > 1) {
                value = value.multiply(BigInteger.valueOf(--number));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return value;
        }

        private void sleep(int sleepMs) {
            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

    }

    private class PersonalThreadPool {
        private List<FactorialThread> threads;
        private final int nbThreads;

        public PersonalThreadPool(int nbThreads) {
            this.nbThreads = nbThreads;
            this.configureThreads();
        }

        private void configureThreads() {
            this.threads = new ArrayList<>(nbThreads);
            do {
                FactorialThread factorialThread = new FactorialThread();
                factorialThread.setName(FactorialThread.class.getSimpleName()+ "-" + threads.size());
                this.threads.add(factorialThread);
            } while (threads.size() < nbThreads);
        }

        public void startAll() {
            this.threads.forEach(FactorialThread::start);
        }

        public FactorialThread getOneReadyThread() throws ExecutionException, InterruptedException {
            CompletableFuture<FactorialThread> f = CompletableFuture.supplyAsync(() -> {
                Optional<FactorialThread> opt;
                do {
                    opt = this.threads.stream().filter(factorialThread -> factorialThread.inputNumber == null)
                            .findAny();
                } while(opt.isEmpty());
                return opt.get();
            });
            return f.get();
        }

        public void forEach (Consumer<FactorialThread> consumer) {
            this.threads.forEach(consumer);
        }

        public void terminateAll() {
            threads.forEach(FactorialThread::interrupt);
        }
    }
}
