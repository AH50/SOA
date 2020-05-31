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
        Subject subject3 = Subject.builder()
                .name("Ekonomia")
                .teacher("Janusz Kowalski")
                .ECTS(3)
                .build();
        Subject subject4 = Subject.builder()
                .name("Bazy")
                .teacher("Tomasz Nowak")
                .ECTS(3)
                .build();

        List<Subject> subjects = new ArrayList<>();
        List<Subject> subjects2 = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject2);

        subjects2.add(subject3);
        subjects2.add(subject4);

        Card card1= Card.builder()
                .number(777)
                .value(1000)
                .build();
        Card card2= Card.builder()
                .number(888)
                .value(2000)
                .build();


        Student student1 = Student.builder()
                .name("Jan")
                .surname("Nowak")
                .studentId(123)
                .subjectList(subjects)
                .photoInBase64("/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAASACkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9Jvj9+1v8QvBP/Bcv4B/B3TPEH2b4c+NfBGt6xrOkfYbZ/tl1brKYZPPaMzpt2r8qSKpxyDXxr/wR8/4LCfHv4l/8FAIfC3xw8XWniT4cfF7WfFHh3wMU0aysTo2qaNOkrWzSQQxs6vayqAZGdi3l853E/ZXx+/ZI+IXjb/guX8A/jFpnh/7T8OfBXgjW9H1nV/t1sn2O6uFlEMfkNIJ33bl+ZI2UZ5Ir4hu/+CMPx/8A+HUOs2OieHH8N/tFfDz433/xO+HkUWr6a7zxyTxKp84ytbqrxbpNkrr81ugYYODjhqjpulUqJtKNXmXe+I5U/wDFGm3KHXljpoFaDm6lODs3KnZ9Fahzb9IuolGfS7d9TpPjR/wV1+O/in/gtV4O8N+AvFlnpf7OsXxTtPhJfab/AGRZzv4g1KK3WbU5PtDxNNH5bTRxjy5VHyocZLZ7bxx+3n8dv27fjv8AGyb4c/Hz4cfslfs9fALxCfCF9481/Q7HWLzX9XRljmRhqEiWsFuJGVVO5WJePBcuVjy/CP8AwR4+J/wa+FX7Amj6T4ci1rWvhh8QX8cfFTUF1O0VrW7uws11O7SSK1yyufKzEJGKwrgEYqx4q/YN/aA/YK+Pnxus/APwC+Hf7Xv7PPx/8SyeMrvwXr2u6do114e1SR1lk8z7fG9tNBvC7AEdv3UZ/dlSX2jS5E6NR35XJX1s5ctB3utXFv23J9hOy0tZDqc8/awTSkoaaXUeasrWeinZUuf7W7W59tf8ExfiJ4q+In7Pd7J4s+OHws/aGu9N1maytfGngYWyW19brFCyrdRWzvBFdqzvuSJtuwxHqTX0ZXwv/wAG/X7DHxA/YI/Y08R+HfiPoGi+E9b8T+NtS8TQaHpl/HexaPa3CwLHbmSICLK+URiPK7dvTlR90VtWd2n1cY320fKrrTTR3TsZUXdPteVt9uZ2euuq11CiiisTUKKKKACiiigD/9k=")
                .card(card1)
                .build();
        Student student2 = Student.builder()
                .name("Tomasz")
                .surname("Kowalksi")
                .studentId(1234)
                .subjectList(subjects2)
                .photoInBase64("/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAIBAQIBAQICAgICAgICAwUDAwMDAwYEBAMFBwYHBwcGBwcICQsJCAgKCAcHCg0KCgsMDAwMBwkODw0MDgsMDAz/2wBDAQICAgMDAwYDAwYMCAcIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAz/wAARCAASACkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD9Jvj9+1v8QvBP/Bcv4B/B3TPEH2b4c+NfBGt6xrOkfYbZ/tl1brKYZPPaMzpt2r8qSKpxyDXxr/wR8/4LCfHv4l/8FAIfC3xw8XWniT4cfF7WfFHh3wMU0aysTo2qaNOkrWzSQQxs6vayqAZGdi3l853E/ZXx+/ZI+IXjb/guX8A/jFpnh/7T8OfBXgjW9H1nV/t1sn2O6uFlEMfkNIJ33bl+ZI2UZ5Ir4hu/+CMPx/8A+HUOs2OieHH8N/tFfDz433/xO+HkUWr6a7zxyTxKp84ytbqrxbpNkrr81ugYYODjhqjpulUqJtKNXmXe+I5U/wDFGm3KHXljpoFaDm6lODs3KnZ9Fahzb9IuolGfS7d9TpPjR/wV1+O/in/gtV4O8N+AvFlnpf7OsXxTtPhJfab/AGRZzv4g1KK3WbU5PtDxNNH5bTRxjy5VHyocZLZ7bxx+3n8dv27fjv8AGyb4c/Hz4cfslfs9fALxCfCF9481/Q7HWLzX9XRljmRhqEiWsFuJGVVO5WJePBcuVjy/CP8AwR4+J/wa+FX7Amj6T4ci1rWvhh8QX8cfFTUF1O0VrW7uws11O7SSK1yyufKzEJGKwrgEYqx4q/YN/aA/YK+Pnxus/APwC+Hf7Xv7PPx/8SyeMrvwXr2u6do114e1SR1lk8z7fG9tNBvC7AEdv3UZ/dlSX2jS5E6NR35XJX1s5ctB3utXFv23J9hOy0tZDqc8/awTSkoaaXUeasrWeinZUuf7W7W59tf8ExfiJ4q+In7Pd7J4s+OHws/aGu9N1maytfGngYWyW19brFCyrdRWzvBFdqzvuSJtuwxHqTX0ZXwv/wAG/X7DHxA/YI/Y08R+HfiPoGi+E9b8T+NtS8TQaHpl/HexaPa3CwLHbmSICLK+URiPK7dvTlR90VtWd2n1cY320fKrrTTR3TsZUXdPteVt9uZ2euuq11CiiisTUKKKKACiiigD/9k=")
                .card(card2)
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
