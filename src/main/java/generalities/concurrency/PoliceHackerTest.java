package generalities.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PoliceHackerTest {

    protected final static int PASSWORD_MAX = 9999;

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
                if (vault.isCorrectPassword(guess)) {
                    System.out.printf(this.getName() + " guessed the password " + guess);
                    System.exit(0);
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
                if (vault.isCorrectPassword(guess)) {
                    System.out.printf(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    public static class PoliceThread extends Thread {
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
            System.exit(0);
        }
    }

    private static class Vault {
        int password;

        public Vault(int password) {
            this.password = password;
            System.out.println("Vault password is " + this.password);
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            return this.password == guess;
        }
    }


    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(PASSWORD_MAX));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }


}
