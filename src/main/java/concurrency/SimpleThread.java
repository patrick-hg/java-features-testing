package concurrency;


public class SimpleThread extends Thread {

    private int countTo = (int)(Math.random() * 20);

    @Override
    public void run() {
        log("[%s] started and will count to '%d'".formatted(Thread.currentThread().getName(), countTo));

        int count = 0;
        while(count < countTo) {
            log("[%s] %d / %d {%s}".formatted(Thread.currentThread().getName(), count, countTo, this.toString()));

            count++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log("[%s] %d / %d ending.".formatted(this.getName(), count, countTo));
    }

    @Override
    public String toString() {
        return ("{" +
                "name: %s," +
                "state: %s," +
                "priority: %d" +
                "}").formatted(this.getName(), this.getState(), this.getPriority());
    }

    private void log(String msg) {
        System.out.println(msg);
    }
}
