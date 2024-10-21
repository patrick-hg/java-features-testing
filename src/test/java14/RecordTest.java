package java14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class RecordTest {

    record Employee(String name, int age, Profession profession, int salary) {
    }

    class Profession {
        private String title;
        private String diploma;

        public Profession(String title, String diploma) {
            this.title = title;
            this.diploma = diploma;
        }

        public String getTitle() {
            return title;
        }

        public String getDiploma() {
            return diploma;
        }
    }


    @Test
    void should_create_records_and_use_them () {

        // given
        Employee john = new Employee("John", 54, new Profession("Engineer", "Master"), 60000);
        Employee lucas = new Employee("Lucas", 34, new Profession("Data analyst", "Master"), 53000);
        Employee lisa = new Employee("Lisa", 38, new Profession("Finance analyst", "Master"), 55000);
        Set<Employee> employees = Set.of(john, lucas, lisa);

        // then
        String professionOfLucas = employees.stream()
                .filter(employee -> "Lucas".equals(employee.name))
                .map(employee -> employee.profession.getTitle())
                .findFirst().orElse("profession not found for employee");

        Assertions.assertEquals("Data analyst", professionOfLucas);
    }
}
