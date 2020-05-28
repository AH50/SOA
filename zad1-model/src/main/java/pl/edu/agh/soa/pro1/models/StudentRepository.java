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
                .photoInBase64("null")
               .build();
        Mark mark = new Mark();
        mark.setMark(1234567);


        List<Mark> markList = new ArrayList<>();
        markList.add(mark);

        student1.setMarkList(markList);
        student2.setMarkList(markList);

        students.add(student1);
        students.add(student2);
    }
}
