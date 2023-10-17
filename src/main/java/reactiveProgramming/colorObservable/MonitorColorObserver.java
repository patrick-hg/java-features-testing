package reactiveProgramming.colorObservable;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

import java.io.IOException;

public class MonitorColorObserver<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        super.hookOnSubscribe(subscription);
        System.out.println("Subscription happened");
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value);
    }


    public static void main(String[] args) throws IOException {

        MonitorColorObserver monitorColorObserver = new MonitorColorObserver();
        ColorObservable.colorStream.subscribe(monitorColorObserver);

        System.out.println("Press a key to end");
        System.in.read();
    }

}
