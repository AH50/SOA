package pl.edu.agh.soa.pro1.models;

import com.sun.xml.ws.developer.Stateful;


import java.util.ArrayList;
import java.util.List;


@Stateful
public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public StudentRepository() {
        MockData();
    }

    public List<Student> getStudentList() {
        return students;
    }

    public boolean addStudent(Student student) {
        return students.add(student);
    }

    public void MockData() {
        Subject subject = Subject.builder()
                .name("Podstawy algorytmow")
                .teacher("Jan Kowalski")
                .ECTS(5)
                .build();

        Subject subject2 = Subject.builder()
                .name("Prawo")
                .teacher("Andrzej Nowak")
                .ECTS(3)
                .build();

        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject2);

        Student student1 = Student.builder()
                .name("Jan")
                .surname("Nowak")
                .studentId(123)
                .subjectList(subjects)
                .photoInBase64("Photo not seted")
                .build();
        Student student2 = Student.builder()
                .name("Jan")
                .surname("Kowalksi")
                .studentId(1234)
                .subjectList(subjects)
                .photoInBase64("Photo not seted")
                .build();

        students.add(student1);
        students.add(student2);
    }
}
