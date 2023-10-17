package reactiveProgramming.colorObservable;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColorObservable {

    public static Flux<String> colorStream = Flux.just(
            "Blue", "Red", "Green", "Yellow", "Black", "White", "Grey", "Cyan", "pink",
            "Magenta","Dark-blue", "Dark-green", "lime", "orange", "olive", "fuchsia"
    ).delayElements(Duration.ofSeconds(1));

    public Flux<String> getColorStream() {
        return colorStream;
    }
}
