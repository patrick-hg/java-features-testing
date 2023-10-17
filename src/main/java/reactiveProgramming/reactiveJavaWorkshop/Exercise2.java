package reactiveProgramming.reactiveJavaWorkshop;

import java.io.IOException;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise2 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux() and ReactiveSources.userFlux()
        String input = new Questions(
                "Print all numbers in the ReactiveSources.intNumbersFlux stream",
                "Print all users in the ReactiveSources.userFlux stream"
        ).printQuestionsAndWaitForUserInput();

        switch (input) {
            case "1":
                ReactiveSources.intNumbersFlux().subscribe(integer -> print(integer));
                break;
            case "2":
                ReactiveSources.userFlux().subscribe(user -> print(user));
                break;
        }

        System.out.print("Press a key to end");
        System.in.read();
    }

}
