package concurrency;



public class SimpleThread extends Thread {

    private final int countTo;
    private final int threadNumber;
    private static int nbOfOccurence;

    public SimpleThread(int max) {
        countTo = (int)(Math.random() * max);
        nbOfOccurence++;
        this.threadNumber = nbOfOccurence;
    }

    @Override
    public void run() {
        log("started and will count to '%d'".formatted(countTo));

        int count = 0;
        while(count < countTo) {
            log("%d / %d {%s}".formatted(count, countTo, this.toString()));

            count++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log("%d / %d ending.".formatted(count, countTo));
    }

    @Override
    public String toString() {
        return ("{" +
                "name: %s," +
                "state: %s," +
                "priority: %d" +
                "}").formatted(this.getName(), this.getState(), this.getPriority());
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    private void log(String msg) {
        System.out.printf("[%s] %s%n", getName(), msg);
    }
}
