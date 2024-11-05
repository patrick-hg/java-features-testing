package moduleUniversity.campus;

import moduleUniversity.Faculty;

import java.util.Set;


public class Campus {

    private String city;

    private Set<moduleUniversity.Faculty> faculties;

    public Campus(String city, Set<Faculty> faculties) {
        this.city = city;
        this.faculties = faculties;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Faculty> getFaculties() {
        return faculties;
    }

    public void setFaculties(Set<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
    public String toString() {
        return "Campus{" +
                "city='" + city + '\'' +
                ", faculties=" + faculties +
                '}';
    }
}
