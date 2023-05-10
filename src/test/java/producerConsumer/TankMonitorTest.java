package producerConsumer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;

public class TankMonitorTest {

    @SneakyThrows
    @Test()
    void fuelTankWillRunEmptyWithoutRefilling() {
        FuelTank tank = new FuelTank(2000, 0);
        Thread tankMonitorTh = new Thread(new TankMonitor(tank));
        Thread fuelTruck1Th = new Thread(new FuelTruck(tank, "TRUCK#1", 10));
        Thread pumpTh1 = new Thread(new FuelPump(tank, "PUMP#1"));
        Thread pumpTh2 = new Thread(new FuelPump(tank, "PUMP#2"));
        Thread pumpTh3 = new Thread(new FuelPump(tank, "PUMP#3"));

        tankMonitorTh.start();
        fuelTruck1Th.start();
        pumpTh1.start();
        pumpTh2.start();
        pumpTh3.start();

        Thread.sleep(20000);
        fuelTruck1Th.interrupt();
        pumpTh1.interrupt();
        pumpTh2.interrupt();
        pumpTh3.interrupt();
        tankMonitorTh.interrupt();

        tankMonitorTh.join();
        fuelTruck1Th.join();
        pumpTh1.join();
        pumpTh2.join();
        pumpTh3.join();
    }
}