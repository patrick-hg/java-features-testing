package java17;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

@DisplayName("SealedTypes testing")
public class SealedTypes {

    public sealed class Animal permits LandAnimal, WaterAnimal {
        String name;
        String continent;
        int height;
        int weignt;
        int lifeExpectancy;

    }

    public sealed class LandAnimal extends Animal {
        int numberOfLegs;
    }

    public sealed class WaterAnimal extends Animal {
    }

    public non-sealed class Mammals extends LandAnimal {

    }

    public non-sealed class Volatiles extends LandAnimal {
    }

    public non-sealed class Fish extends WaterAnimal {
    }

    public class Dog extends Mammals {

    }

    public class Zoo {
        Map<LandAnimal, Integer> landAnimals;
        Map<WaterAnimal, Integer> waterAnimals;

        public void addAnimal(Animal animal, int count) {
            switch (animal) {
                case LandAnimal landAnimal -> this.landAnimals.put(landAnimal, count);
                case WaterAnimal waterAnimal -> this.waterAnimals.put(waterAnimal, count);
                default -> throw new IllegalStateException("Unexpected value: " + animal);
            }
        }
    }

    class AfricanWildDog extends Dog {
    }

    class Hyenas extends Mammals {

    }

    @Test
    void should_create_all_sort_of_animals_respecting_object_hierarchy() {

        Zoo zoo = new Zoo();
        zoo.animals.put(new AfricanWildDog(), 6);
        zoo.animals.put(new Hyenas(), 4);



    }

}
