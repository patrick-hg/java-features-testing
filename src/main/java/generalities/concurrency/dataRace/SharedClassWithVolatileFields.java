package generalities.concurrency.dataRace;


public class SharedClassWithVolatileFields implements IShared {
    private volatile int x = 0;
    private volatile int y = 0;

    private int countDataRace = 0;
    private final Thread checkForDataRaceThread = new Thread(() -> {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            checkForDataRace();
        }
    });

    public void increment() {
        x++;
        y++;
    }

    public void startCheckForDataRace() {
        this.checkForDataRaceThread.start();
    }

    public void stopCheckForDataRace() {
        this.checkForDataRaceThread.interrupt();
    }

    private void checkForDataRace() {
        if (y > x) {
            System.out.printf("y > x - Data Race is detected! (x:%d, y:%d)\n", x, y);
            countDataRace++;
        }
    }

    @Override
    public int getCountDataRace() {
        return this.countDataRace;
    }
}
