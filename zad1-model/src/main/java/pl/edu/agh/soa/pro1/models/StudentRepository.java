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
    public Student getStudentByID(int id){
        for (Student student:students){
            if (student.getStudentId()==id) {
                return student;
            }
        }
        return null;
    }
    public Student getStudentByName(String name){
        for (Student student:students){
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
    public Student getStudentBySurname(String surname){
        for (Student student:students){
            if (student.getSurname().equals(surname)) {
                return student;
            }
        }
        return null;
    }
    public Student changeStudentSurname(int id,String newSurname){

        for(Student student:students){
            if(student.getStudentId()==id){
                student.setSurname(newSurname);
                return student;
            }
        }
        return null;
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
                .photoInBase64("")
                .build();
        Student student2 = Student.builder()
                .name("Jan")
                .surname("Kowalksi")
                .studentId(1234)
                .subjectList(subjects)
                .photoInBase64("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")
                .build();

        students.add(student1);
        students.add(student2);
    }
}
