package java14;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

public class RecordTest {

    record Employee(String name, int age, String profession, int salary) {
    }

    @Test
    void should_create_records () {

        Set<Employee> employees = Set.of(
                new Employee("John", 54, "Engineer", 60000),
                new Employee("Lucas", 34, "Data analyst", 53000),
                new Employee("Rebecca", 38, "Finance analyst", 55000)
        );

        Assertions.assertEquals("Data analyst", employees.stream()
                .filter(employee -> "Lucas".equals(employee.name))
                .map(Employee::name));
    }
}
