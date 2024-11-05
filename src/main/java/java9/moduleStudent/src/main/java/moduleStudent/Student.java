package moduleStudent;

import moduleUniversity.Faculty;
import moduleUniversity.campus.Campus;

public class Student {

    private String fullname;

    private Campus campus;

    private Faculty faculty;

    private int degree;

    public Student(String fullname, Campus campus, Faculty faculty, int degree) {
        this.fullname = fullname;
        this.campus = campus;
        this.faculty = faculty;
        this.degree = degree;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullname='" + fullname + '\'' +
                ", campus=" + campus +
                ", faculty=" + faculty +
                ", degree=" + degree +
                '}';
    }
}


