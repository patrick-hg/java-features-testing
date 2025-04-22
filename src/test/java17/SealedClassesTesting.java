package java17;


import lombok.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A Sealed class:
 * - can implement another Sealed class
 * - can be implemented from a Permitted Class
 *
 *      DRIVABLE
 *         |
 *      VEHICLE
 *        |
 *       [CAR]
 *      /  \
 *   FORD, VOLVO
 */

@DisplayName("Sealed Classes Testing")
public class SealedClassesTesting {
    interface Drivable {
        int getTopSpeed();
    }

    sealed class Vehicle implements Drivable permits Car {
        int nbOfWheels;
        String makerCountry;
        String model;
        int topSpeed;

        public Vehicle(int nbOfWheels, String makerCountry, String model, int topSpeed) {
            this.nbOfWheels = nbOfWheels;
            this.makerCountry = makerCountry;
            this.model = model;
            this.topSpeed = topSpeed;
        }

        void setNbOfWheels(int nbOfWheels) {
            this.nbOfWheels = nbOfWheels;
        }

        void setMakerCountry(String country) {
            this.makerCountry = country;
        }

        @Override
        public int getTopSpeed() {
            return topSpeed;
        }
    }

    sealed class Car extends Vehicle permits Ford, Volvo {
        public Car(int nbOfWheels, String makerCountry, String model, int topSpeed) {
            super(nbOfWheels, makerCountry, model, topSpeed);
        }
    }

    final class Ford extends Car {
        public Ford(String model, int topSpeed) {
            super(4, "USA", model, topSpeed);
        }
    }

    final class Volvo extends Car {
        public Volvo(String model, int topSpeed) {
            super(4, "Sweden", model, topSpeed);
        }
    }

    final class RollsRoyce implements Drivable {

        public RollsRoyce() {
        }

        @Override
        public int getTopSpeed() {
            return 180;
        }
    }

    @Test
    @DisplayName("Should instanciate successfully when permitted")
    void should_instanciate_successfully_when_permitted() {
        Drivable ford_focus = new Ford("Focus", 210);
        Drivable volvo_s60 = new Volvo("S60", 230);

        assertNotNull(ford_focus);
        assertNotNull(volvo_s60);

        assertTrue(ford_focus instanceof Car);
        assertTrue(volvo_s60 instanceof Car);
    }

    @Test
    @DisplayName("Should fail for not permitted instantiations")
    void should_fail_for_non_permitted_instantiations() {
        Drivable rollsRoyce = new RollsRoyce();
        assertFalse(rollsRoyce instanceof Car);
    }
}
