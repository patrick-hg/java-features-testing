package reactiveProgramming.reactiveJavaWorkshop;

import java.io.IOException;
import java.util.List;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise3 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux()

        print("Get all numbers in the ReactiveSources.intNumbersFlux stream");
        print("into a List and print the list and its size");
        print("> i will fetch all elements from the flux but it will blocks untill all elements arrive");
        List elems = ReactiveSources.intNumbersFlux().toStream().toList();
        print("%s, size:%d".formatted(elems, elems.size()));

        System.out.println("Press a key to end");
        System.in.read();
    }

}
