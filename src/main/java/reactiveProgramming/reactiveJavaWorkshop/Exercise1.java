package reactiveProgramming.reactiveJavaWorkshop;

import static reactiveProgramming.reactiveJavaWorkshop.Utils.print;

public class Exercise1 {

    public static void main(String[] args) {

        // Use StreamSources.intNumbersStream() and StreamSources.userStream()
        print("Print all numbers in the intNumbersStream stream");
        print(StreamSources.intNumbersStream().toList());

        print("Print numbers from intNumbersStream that are less than 5");
        print(StreamSources.intNumbersStream()
                .filter(integer -> integer < 5)
                .toList());

        print("Print the second and third numbers in intNumbersStream that's greater than 5");
        print(StreamSources.intNumbersStream()
                .filter(integer -> integer > 5)
                .skip(1)
                .limit(2)
                .toList());

        print("Print the first number in intNumbersStream that's greater than 5. If nothing is found, print -1");
        print(StreamSources.intNumbersStream().filter(integer -> integer > 5).findFirst().orElse(-1));

        print("Print first names of all users in userStream");
        print(StreamSources.userStream().map(user -> user.getFirstName()).toList());

        print("Print first names in userStream for users that have IDs from number stream");
        print(StreamSources.userStream()
                .filter(user -> StreamSources.intNumbersStream().anyMatch(integer -> integer.equals(user.getId())))
                .map(user -> "[%d] %s".formatted(user.getId(), user.getFirstName()))
                .toList());
    }


}
