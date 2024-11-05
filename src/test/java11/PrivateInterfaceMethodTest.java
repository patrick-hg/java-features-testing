package java11;

import org.junit.jupiter.api.Test;

public class PrivateInterfaceMethodTest {

    public interface InefficientTennis {
        static void forehand() {
            System.out.println("Move into position");
            System.out.println("Hitting a forehand");
            System.out.println("Move back into ready position");
        }

        static void backhand() {
            System.out.println("Move into position");
            System.out.println("Hitting a backhand");
            System.out.println("Move back into ready position");
        }

        static void smash() {
            System.out.println("Move into position");
            System.out.println("Hitting a smash");
            System.out.println("Move back into ready position");
        }
    }

    public interface Tennis {
        static void forehand() { hit("forehand"); }
        default void backhand() { hit("backhand"); }
        private void smash() { hit("smash"); }

        private static void hit(String stroke) {
            System.out.println("Move into position");
            System.out.println("Hitting a " + stroke);
            System.out.println("Move back into ready position");
        }
    }

    class ProfessionalTennis implements Tennis {}

    @Test
    void should_play_non_efficient_tennis() {
        InefficientTennis.forehand();
        InefficientTennis.backhand();
        InefficientTennis.smash();
    }

    @Test
    void should_play_professional_tennis() {
        ProfessionalTennis tennis = new ProfessionalTennis();
        Tennis.forehand();
        tennis.backhand();
//        tennis.smash();   it's private
    }
}
