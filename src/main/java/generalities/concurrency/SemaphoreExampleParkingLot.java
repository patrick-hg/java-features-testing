package generalities.concurrency;

import commons.CarMaker;
import commons.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * In computer science, a semaphore is a variable or abstract data type used to control access to a common resource
 * by multiple threads and avoid critical section problems in a concurrent system such as a multitasking operating system.
 * Semaphores are a type of synchronization primitive.
 */
public class SemaphoreExampleParkingLot {

    public static void main(String[] args) {
        SemaphoreExampleParkingLot s = new SemaphoreExampleParkingLot();
        s.runExample();
    }

    private void runExample() {
        ParkingLot parkingLot = new ParkingLot(10);
        ParkingEntrance parkingEntrance = new ParkingEntrance(parkingLot);
        ParkingExit parkingExit = new ParkingExit(parkingLot);

        parkingEntrance.start();
        parkingExit.start();
    }

    private record Car(String make, Color color, int parkingDuration){}

    @RequiredArgsConstructor
    @Getter
    @Setter
    private class Spot {
        private final int number;
        private Car parkedCar;
        private LocalDateTime parkTill;
    }

    @Getter
    private class ParkingLot {
        private final Spot[] spots;
        private final int NB_SPOTS;
        private int nbFreeSpots = 0;
//        private final ReentrantLock lock;
        private final Semaphore semaphoreLock;

        private Function<Spot[], Optional<Spot>> freeSpot = arr -> Stream.of(arr)
                .filter(spot -> spot.parkedCar == null)
                .findFirst();

        public ParkingLot(int nbSpots) {
            this.NB_SPOTS = nbSpots;
            this.spots = new Spot[nbSpots];
            for (int i=0; i<nbSpots; i++) {
                spots[i] = new Spot(i+1);
            }
            this.nbFreeSpots = nbSpots;
            this.semaphoreLock = new Semaphore(nbSpots + 1);    // +1 reserved for Parking exit thread
            System.out.printf("[%s] initialized for %d spots\n", this.getClass().getSimpleName(), NB_SPOTS);
        }

        public void printSpots() {
            System.out.println(Arrays.stream(spots)
                    .map(spot -> spot.parkedCar != null
                            ? "[" + spot.parkedCar.make + ":" + spot.parkedCar.color.getName() + "]"
                            : "[   ]")
                    .reduce((s, s2) -> s + s2)
                    .get());
        }
    }

    public class ParkingEntrance extends Thread {

        private ParkingLot parkingLot;
        private Queue<Car> queue = new LinkedList<>();
        private Car carAtEntrance;
        private Random random = new Random();

        public ParkingEntrance(ParkingLot parkingLot) {
            this.parkingLot = parkingLot;
            this.setName(getClass().getSimpleName() + "-" + threadId());
        }

        @Override
        public void run() {

            while (true) {
                if (chance(0.7)) {
                    queue.add(randomCar()); // new car arriving in the queue
                }

                if (carAtEntrance == null) {
                    carAtEntrance = queue.poll();
                }
                if (carAtEntrance != null) {
                    System.out.printf("[%s] %s %s is waiting at entrance\n", this.getName(), carAtEntrance.color, carAtEntrance.make);

                    if (parkingLot.semaphoreLock.availablePermits() > 0) {
                        try {
                            parkingLot.semaphoreLock.acquire();
                            System.out.printf("[%s] Access granted for %s %s\n", getName(), carAtEntrance.color, carAtEntrance.make);

                            // park in a free spot
                            if (parkingLot.getNbFreeSpots() > 0) {
                                Arrays.stream(parkingLot.spots)
                                        .filter(spot -> spot.parkedCar == null)
                                        .findFirst()
                                        .ifPresent(spot -> {
                                            spot.setParkedCar(carAtEntrance);
                                            spot.setParkTill(LocalDateTime.now().plus(carAtEntrance.parkingDuration, ChronoUnit.SECONDS));
                                            carAtEntrance = null;
                                        });
                            }
                            parkingLot.printSpots();

                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        } finally {
//                            parkingLot.semaphoreLock.release();
                        }
                    } else {
                        System.out.printf("[%s] Parking is full, please wait\n", getName());
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private Car randomCar() {
            Random random = new Random();
            String make = CarMaker.values()[random.nextInt(CarMaker.values().length)].name();
            Color color = Color.values()[random.nextInt(Color.values().length)];
            return new Car(make, color, random.nextInt(5, 20));
        }

        private boolean chance(double percent) {
            return Math.random() < percent;
        }
    }

    private class ParkingExit extends Thread {
        private ParkingLot parkingLot;

        public ParkingExit (ParkingLot parkingLot) {
            this.parkingLot = parkingLot;
            setName(getClass().getSimpleName() + "-" + threadId());
        }

        @Override
        public void run() {
            // get a permit on shared data and keep it forever
            if (parkingLot.semaphoreLock.availablePermits() > 0) {
                try {
                    parkingLot.semaphoreLock.acquire();
                    while (true) {
                        Thread.sleep(1000);

                        takeOutExpired(parkingLot);
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        private void takeOutExpired(ParkingLot parkingLot) {
            final LocalDateTime now = LocalDateTime.now();

            Arrays.stream(parkingLot.spots)
                    .filter(spot -> spot.parkedCar != null && spot.parkTill.isBefore(now))
                    .forEach(spot -> {
                        // we will free a spot
                        System.out.printf("[%s] %s %s is leaving the parking lot\n", getName(), spot.parkedCar.color, spot.parkedCar.make);
                        spot.parkedCar = null;
                        spot.parkTill = null;
                        parkingLot.semaphoreLock.release(1);
                    });
        }
    }
}
