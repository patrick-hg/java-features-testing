package java17;



public class SealedClasses {

    sealed interface Drivable {}

    sealed class Vehicle implements Drivable permits Car {}

    sealed class Car extends Vehicle permits Ford, Volvo {}

    final class Ford extends Car {}

    final class Volvo extends Car {}
}
