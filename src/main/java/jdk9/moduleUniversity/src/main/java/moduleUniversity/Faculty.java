package moduleUniversity;

public class Faculty {

    private FacultyEnum name;

    private Faculty(FacultyEnum name) {
        this.name = name;
    }

    public static Faculty from(String facultyName) {
        return new Faculty(FacultyEnum.valueOf(facultyName.toUpperCase().replace(" ", "_")));
    }

    public static Faculty from(FacultyEnum facultyEnum) {
        return new Faculty(facultyEnum);
    }

    public String getName() {
        return name.name();
    }

    public void setName(FacultyEnum name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name.name() + '\'' +
                '}';
    }
}
