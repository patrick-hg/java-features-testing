package reactiveProgramming.reactiveJavaWorkshop;

import java.io.IOException;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise9 {


    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux()
        String input = new Questions(
                "Print size of intNumbersFlux after the last item returns",
                "Collect all items of intNumbersFlux into a single list and print it",
                "Transform to a sequence of sums of adjacent two numbers"
        ).printQuestionsAndWaitForUserInput();

        switch (input) {
            case "1":
                ReactiveSources.intNumbersFlux().count().subscribe(aLong -> print(aLong));
                break;
            case "2":
                print(ReactiveSources.intNumbersFlux().collectList());
                break;
            case "3":
                ReactiveSources.intNumbersFlux()
                        .buffer(2)
                        .map(integers -> integers.get(0) + integers.get(1))
                        .subscribe(integer -> print(integer));
                break;
        }

        System.out.println("Press a key to end");
        System.in.read();
    }

}
