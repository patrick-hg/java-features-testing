package jdk17;

import commons.shapes.Circle;
import commons.shapes.Square;
import commons.shapes.Triangle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@DisplayName("Switch case with expression testing")
public class SwitchCaseExpression {

    record Human(String name, int age, String sex, String profession) {}


    @Test
    void should_print_message_depending_on_object() {

        Object[] objects = {
                new Human("Jean", 32, "male", "Fleuriste"),
                new Human("Mathilde", 28, "female", "Secouriste"),
                new Human("Bot", 999, "non-binaire", "learning"),

                new Triangle("Red"),
                new Square("blue"),
                new Circle("green")
        };

        Arrays.stream(objects).forEach(obj -> System.out.println(checkObject(obj)));
    }

    private String checkObject(Object obj) {
        return switch (obj) {
            case Human h -> "It's a %s named %s, he is %d years old and working as %s"
                    .formatted("female".equals(h.sex) ? "girl": "guy", h.name, h.age, h.profession);
            case Triangle t ->
                    "It's a %s Triangle".formatted(t.getColor());
            case commons.shapes.Square sq ->
                    "It's a %s Square".formatted(sq.getColor());
            case commons.shapes.Circle c ->
                    "It's a %s Circle".formatted(c.getColor());
            default -> "It's an object";
        };
    }

}
