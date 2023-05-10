package producerConsumer;

public class TankMonitor implements Runnable {

    private FuelTank fuelTank;
    private boolean isWorking;

    public TankMonitor(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    @Override
    public void run() {
        this.isWorking = true;
        int t=0;
        do {
            t++;
            System.out.println("[%d] MONITOR: %s".formatted(t, fuelTank.toString()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[%d] MONITOR execution was interrupted, will exit now".formatted(t));
                Thread.currentThread().interrupt();
                this.isWorking = false;
            }
        } while (isWorking);
        System.out.println("[%s] MONITOR is shutting down".formatted(t));
    }

    public void stop() {
        this.isWorking = false;
    }
}
