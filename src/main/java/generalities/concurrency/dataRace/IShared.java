package generalities.concurrency.dataRace;

public interface IShared {
    void increment();
    void startCheckForDataRace();
    void stopCheckForDataRace();
    int getCountDataRace();
}
