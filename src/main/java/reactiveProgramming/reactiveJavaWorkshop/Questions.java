package reactiveProgramming.reactiveJavaWorkshop;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Questions {

    private List<String> questions;
    private int nbOfQuestions;

    private static final Scanner scanner = new Scanner(System.in);

    public Questions(String... questions) {
        this.nbOfQuestions = questions.length;
        this.questions = Arrays.asList(questions);
    }

    public String printQuestionsAndWaitForUserInput() {
        for (int i = 0; i < nbOfQuestions; i++) {
            print("%d) %s".formatted(i + 1, questions.get(i)));
        }
        System.out.print("Waiting for the number: ");

        String input = scanner.next();
        return input;
    }

}
