package reactiveProgramming.reactiveJavaWorkshop;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.io.IOException;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise5 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumberMono() and ReactiveSources.userMono()

        print("Subscribe to a flux using the error and completion hooks");
//        ReactiveSources.intNumbersFlux()
//                .subscribe(
//                        integer -> print(integer),
//                        err -> print(err),
//                        () -> print("it's complete"));

        print("Subscribe to a flux using an implementation of BaseSubscriber");
        ReactiveSources.intNumbersFlux().subscribe(new MySubscriber<>());

        System.out.println("Press a key to end");
        System.in.read();
    }


    static class MySubscriber<T> extends BaseSubscriber<T> {
        @Override
        protected void hookOnSubscribe(Subscription subscription) {
            print("Subscription happened");
//            super.hookOnSubscribe(subscription);
            request(1);
        }

        @Override
        protected void hookOnNext(T value) {
            print(value.toString() + " value received");
//            super.hookOnNext(value);
            request(1);
        }
    }

}