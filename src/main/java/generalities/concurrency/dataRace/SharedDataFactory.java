package generalities.concurrency.dataRace;

public class SharedDataFactory {

    public static IShared createSharedData(String input) {
        return "3".equals(input)
                ? new SharedClassWithVolatileFields()
                : new SharedClass();
    }
}
