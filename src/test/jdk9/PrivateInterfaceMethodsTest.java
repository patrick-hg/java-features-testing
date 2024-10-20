package jdk9;

import commons.RestorantEnums.Beverage;
import commons.RestorantEnums.Meal;
import org.junit.jupiter.api.Test;

import static utils.EnumUtils.getRandomValueFromEnum;

public class PrivateInterfaceMethodsTest {


    private interface MyInterface {

        void eat(Meal meal);
        void drink(Beverage beverage);

        private void sleep(int amountSeconds) {
            System.out.println("sleeping for " + amountSeconds + " seconds ...");
            try {
                Thread.sleep(amountSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished sleeping");
        }
    }

    @Test
    void should_run_an_interface_with_some_implemented_private_methods() {
        MyInterface myInterface = new MyInterface() {
            @Override
            public void eat(Meal meal) {
                System.out.println("eating a " + meal);
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 8).longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("finished eatin " + meal);
            }

            @Override
            public void drink(Beverage beverage) {
                System.out.println("drinking a " + beverage);
                try {
                    Thread.sleep(Double.valueOf(Math.random() * 4).longValue());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished drinking " + beverage);
            }
        };

        // random story
        myInterface.eat(getRandomValueFromEnum(Meal.class));
        myInterface.drink(getRandomValueFromEnum(Beverage.class));
        myInterface.sleep(Double.valueOf(Math.random() * 10).intValue());
    }

}
