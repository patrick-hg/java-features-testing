package generalities.concurrency.dataRace;

public class SharedDataFactory {

    public static IShared createSharedData(String input, int max) {
        return "3".equals(input)
                ? new SharedClassWithVolatileFields(max)
                : new SharedClass(max);
    }
}
