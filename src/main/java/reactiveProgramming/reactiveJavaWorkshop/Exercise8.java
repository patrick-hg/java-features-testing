package reactiveProgramming.reactiveJavaWorkshop;

import reactor.core.publisher.Flux;

import java.io.IOException;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise8 {


    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFluxWithException()
        String input = new Questions(
                "Print values from intNumbersFluxWithException and print a message when error happens",
                "Print values from intNumbersFluxWithException and continue on errors",
                "Print values from intNumbersFluxWithException and when errors \n " +
                        "happen, replace with a fallback sequence of -1 and -2"
        ).printQuestionsAndWaitForUserInput();

        switch (input) {
            case "1":
                ReactiveSources.intNumbersFluxWithException()
                        .subscribe(
                                integer -> print(integer),
                                err -> print(err));
                break;
            case "2":
                ReactiveSources.intNumbersFluxWithException()
                        .onErrorContinue((throwable, item) -> print("Error!! on item: " + item + ", exception: " + throwable))
                        .subscribe(integer -> print(integer));
                break;
            case "3":
                ReactiveSources.intNumbersFluxWithException()
                        .onErrorResume(err -> Flux.just(-1, -2))
                        .subscribe(integer -> print(integer));
                break;
        }

        System.out.println("Press a key to end");
        System.in.read();
    }

}
