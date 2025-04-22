package generalities.concurrency.locks.cryptocurrencyApp;

import java.util.Random;

public class PriceUpdater extends Thread {

    private PricesContainer pricesContainer;
    private Random random = new Random();

    public PriceUpdater(PricesContainer pricesContainer) {
        this.pricesContainer = pricesContainer;
    }

    @Override
    public void run() {
        while (true) {
            pricesContainer.getLock().lock();
            try {
                System.out.printf("[%s] Updating prices\n", this.getClass().getSimpleName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                pricesContainer.setBitcoin(positive(pricesContainer.getBitcoin() + randomSigned(20)));
                pricesContainer.setEtherium(positive(pricesContainer.getEtherium() + randomSigned(10)));
                pricesContainer.setLightcoin(positive(pricesContainer.getLightcoin() + randomSigned(5)));
                pricesContainer.setRipples(positive(pricesContainer.getRipples() + randomSigned(1)));
            } finally {
                pricesContainer.getLock().unlock();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * returns a random positive or negative float number between [-bound ... +bound]
     * @param bound
     * @return
     */
    private float randomSigned(int bound) {
        boolean isPositive = random.nextBoolean();
        float rand = isPositive ? random.nextFloat((float)bound) : -1 * random.nextFloat(bound);
        return rand;
    }

    private double positive(double value) {
        return value < 0 ? -1 * value : value;
    }
}
