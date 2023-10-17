package reactiveProgramming.reactiveJavaWorkshop;

import java.io.IOException;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise4 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumberMono()

        print("Print the value from intNumberMono when it emits");
        print(ReactiveSources.intNumberMono().subscribe(integer -> print("with subscribe(): " + integer)));

        print("Get the value from the Mono into an integer variable");
        print("BE AWARE THAT USING .BLOCK() TO GET THE VALUE OF A MONO WILL BLOCKS EXECUTION TILL THE VALUE IS EMITTED");
        int number = ReactiveSources.intNumberMono().block();
        print("with block(): " + number);

        System.out.println("Press a key to end");
        System.in.read();
    }

}
