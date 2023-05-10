package producerConsumer;

public class FuelPump extends Thread {

    private FuelTank fuelTank;
    private final String name;

    private boolean isWorking;

    public FuelPump(FuelTank fuelTank, String name) {
        this.fuelTank = fuelTank;
        this.name = name;
    }

    @Override
    public void run() {
        this.isWorking = true;
        do {
            int amountRequested = (int) (Math.random() * 79) + 1;   // +1 so it can't be zero
            System.out.println("[%s] Pumping %dL of gaz".formatted(name, amountRequested));

            int pumped = 0;
            try {
                do {
                    try {
                        pumped = fuelTank.pump(amountRequested);
                        System.out.println("[%s] Pumped %dL of gaz".formatted(name, pumped));
                    } catch (RuntimeException re) {
                        System.out.println("[%s] Seems running out of gaz, waiting a moment...".formatted(name));
                        Thread.sleep(4000);
                    }
                } while (pumped == 0 && amountRequested > 0);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[%s] execution was interrupted, will exit now".formatted(name));
                Thread.currentThread().interrupt();
                this.isWorking = false;
            }

        } while (isWorking);
        System.out.println("[%s] is shutting down".formatted(name));
    }

    @Override
    public void interrupt() {
        stopWorking();
        super.interrupt();
    }

    public void stopWorking() {
        this.isWorking = false;
    }
}
