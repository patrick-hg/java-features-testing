package moduleStudent;

import moduleUniversity.Faculty;
import moduleUniversity.campus.Campus;
import moduleUniversity.campus.CampusEnum;

import java.util.List;
import java.util.Set;

public class StudentApplication {

    public static void main(String[] args) {
        System.out.println("--- Student Application started ---");

        Campus campusLondon = new Campus(CampusEnum.LONDON.name(),
                Set.of(Faculty.from("Science"), Faculty.from("Information technology")));

        List<Student> students = List.of(
                new Student("Peter MARSHALL", campusLondon, Faculty.from("Information technology"), 2),
                new Student("Petra MARCOPOULOS", campusLondon, Faculty.from("Science"), 3));

        students.stream().forEach(System.out::println);

        System.out.println("--- Student Application ended ---");
    }
}
