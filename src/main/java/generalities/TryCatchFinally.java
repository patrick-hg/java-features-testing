package generalities;

import static utils.ConsoleUtils.print;

public class TryCatchFinally {

    public static void main(String[] args) {

        int returnValue = TryCatchFinally.test_1();
        print("returned value from catch block: " + returnValue);

        TryCatchFinally.test_2();
    }

    public static int test_1() {
        print("test#1: Exception will be catched and return, then will execute finally block anyway");
        try {
            print("in try block, will throw an exception");
            int i = 0 / 0;

        } catch (Exception e) {
            print("in catch block");
            return 0;
        } finally {
            print("will execute finally block anyway");
        }
        return 1;
    }

    public static void test_2() {
        print("test#2: Exception will be catched and propagated, then will execute finally block anyway");
        try {
            print("in try block, will provoc an exception");
            int i = 0 / 0;

        } catch (Exception e) {
            print("in catch block and will propagate exception");
            throw e;
        } finally {
            print("will execute finally block anyway");
        }
    }

}
