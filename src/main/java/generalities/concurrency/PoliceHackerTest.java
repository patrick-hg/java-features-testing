package generalities.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliceHackerTest {

    protected final static int PASSWORD_MAX = 9999;

    private static class Vault {
        int password;

        public Vault(int password) {
            this.password = password;
            System.out.printf("[%s] Vault password is %d\n", Thread.currentThread().getName(),this.password);
        }

        public boolean isCorrectPassword(int guess) {
            System.out.printf("[%s] Vault password check\n", Thread.currentThread().getName());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                System.out.printf("[%s] VAULT INTERRUPTED\n", Thread.currentThread().getName());
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < PASSWORD_MAX; guess++) {
//                System.out.println(getName() + " - " + guess);
                if (currentThread().isInterrupted()) {
                    System.out.printf("Thread %s have been interrupted\n", getName());
                    return;
                }

                if (vault.isCorrectPassword(guess)) {
                    System.out.printf("%s guessed the password %d\n", this.getName(), guess);
                    return;
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = PASSWORD_MAX; guess >= 0; guess--) {
//                System.out.println(getName() + " - " + guess);
                if (currentThread().isInterrupted()) {
                    System.out.printf("Thread %s have been interrupted\n", getName());
                    return;
                }

                if (vault.isCorrectPassword(guess)) {
                    System.out.printf("%s guessed the password %d\n", this.getName(), guess);
                    return;
                }
            }
        }
    }

    public static class PoliceThread extends Thread {
        private boolean onAlert;

        @Override
        public void run() {

            for (int count = 10; count > 0; count--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(count);
            }
            System.out.println("Game over for you hackers");
            this.onAlert = true;
        }

        public boolean isOnAlert() {
            return onAlert;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(PASSWORD_MAX));
        PoliceThread policeThread = new PoliceThread();

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(policeThread);

        for (Thread thread : threads) {
            thread.start();
        }

        while (true) {
            if (policeThread.isOnAlert()) {
                System.out.println("ALERT!");
                threads.forEach(Thread::interrupt);
                return;
            }
            Thread.sleep(1000);
        }

    }


}
