package generalities.concurrency.locks;

//import lombok.Getter;
//import lombok.Setter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//@Getter
//@Setter
public class PricesContainer {

    private Lock lock = new ReentrantLock();

    private double bitcoin;
    private double etherium;
    private double lightcoin;
    private double ripples;

    public double getBitcoin() {
        return bitcoin;
    }

    public void setBitcoin(double bitcoin) {
        this.bitcoin = bitcoin;
    }

    public double getEtherium() {
        return etherium;
    }

    public void setEtherium(double etherium) {
        this.etherium = etherium;
    }

    public double getLightcoin() {
        return lightcoin;
    }

    public void setLightcoin(double lightcoin) {
        this.lightcoin = lightcoin;
    }

    public double getRipples() {
        return ripples;
    }

    public void setRipples(double ripples) {
        this.ripples = ripples;
    }

    public Lock getLock() {
        return lock;
    }
}
