package java14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

@DisplayName("Records testing")
public class Records {

    public record Employee (String name, int age, Profession profession, Integer salary)
            implements Introduce {

        public Employee(String name, int age, Profession profession, Integer salary) {
            this.name = name;
            this.age = age();
            this.profession = profession;
            this.salary = salary;

            salute();
        }

        @Override
        public void salute() {
            System.out.println("Hello! my name is " + name + " and i am a " + profession.getTitle());
        }
    }

    interface Introduce {
        default void salute() {
            System.out.println("Hello!");
        };
    }

    @Test
    void should_instanciate_and_use_a_record_object() {
        Employee john = new Employee("John", 39, new Profession("Architect", "Master in architecture"), 55000);
        Employee lisa = new Employee("Lisa", 37, new Profession("Finance Manager", "Master in Finance"), 60000);

        System.out.println("The sum of salaries is " + (john.salary + lisa.salary));
        Assertions.assertNotEquals(john, lisa);
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
