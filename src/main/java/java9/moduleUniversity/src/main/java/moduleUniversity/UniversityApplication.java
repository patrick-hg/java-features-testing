package src.main.java.moduleUniversity;

import src.main.java.moduleUniversity.campus.Campus;

import java.util.List;
import java.util.Set;

import static src.main.java.moduleUniversity.FacultyEnum.*;
import static src.main.java.moduleUniversity.campus.CampusEnum.LONDON;
import static src.main.java.moduleUniversity.campus.CampusEnum.PARIS;

public class UniversityApplication {

    public static void main (String[] args) {
        System.out.println("--- University Application started ---");

        List<Campus> campusList = List.of(
                new Campus(LONDON.name(), Set.of(Faculty.from(SCIENCE), Faculty.from(INFORMATION_TECHNOLOGY))),
                new Campus(PARIS.name(), Set.of(Faculty.from(ART), Faculty.from(BIOLOGY))));

        campusList.stream().forEach(System.out::println);

        System.out.println("--- University Application ended ---");

    }
}
