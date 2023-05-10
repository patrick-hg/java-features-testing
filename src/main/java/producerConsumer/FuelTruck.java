package producerConsumer;

public class FuelTruck extends Thread {

    private FuelTank tank;
    private final String name;

    private int refillEvery;
    private boolean atWork;

    public FuelTruck(FuelTank tank, String name, int refillEverySec) {
        this.tank = tank;
        this.name = name;
        this.refillEvery = refillEverySec * 1000;
        this.atWork = false;
    }

    @Override
    public void run() {
        this.atWork = true;
        do {
            try {
                Thread.sleep(1000);

                System.out.println("[%s] is refilling fuelTank with 500L of gaz".formatted(name));
                tank.fill(500);
                Thread.sleep(refillEvery);

            } catch (InterruptedException e) {
                System.out.println("[%s] execution was interrupted, will exit now".formatted(name));
                Thread.currentThread().interrupt();
                this.atWork = false;
            }
        } while (atWork);

        System.out.println("[%s] End of service".formatted(name));
    }

    @Override
    public void interrupt() {
        stopWorking();
        super.interrupt();
    }

    public boolean isAtWork() {
        return atWork;
    }



    public void stopWorking() {
        this.atWork = false;
    }

}
