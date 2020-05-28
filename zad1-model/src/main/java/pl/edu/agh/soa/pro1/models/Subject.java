package pl.edu.agh.soa.pro1.models;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@XmlType(propOrder = {"name", "teacher", "ECTS","studentList"})

public class Subject {
    private String name;
    private String teacher;
    private int ECTS;
    private List<Student> studentList;

    @XmlElementWrapper(name = "Students")
    @XmlElement(name = "studentList")
    public List<Student> getStudentList() {
        return studentList;
    }

    public Subject(String name, String teacher, int ECTS, List<Student> studentList) {
        this.name = name;
        this.teacher = teacher;
        this.ECTS = ECTS;
        this.studentList = studentList;
    }
}
