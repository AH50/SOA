package models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.soa.pro1.models.Card;
import pl.edu.agh.soa.pro1.models.Mark;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@XmlType(propOrder = {"name", "surname", "studentId", "photoInBase64","card", "subjectList", "markList"})
@XmlRootElement(name = "student")
public class Student {
    private String name;
    private String surname;
    private int studentId;
    private String photoInBase64;
    private List<Subject> subjectList;
    private List<Mark> markList;
    private Card card;

    @XmlElementWrapper(name = "Subjects")
    @XmlElement(name = "subjectList")
    public List<Subject> getSubjectList() {
        return subjectList;
    }

    @XmlElementWrapper(name = "Marks")
    @XmlElement(name = "markList")
    public List<Mark> getMarkList() {
        return markList;
    }

    @XmlElement(name = "card")
    public Card getCard() {
        return card;
    }


    public Student(String name, String surname, int studentId, String photoInBase64, List<Subject> subjectList, List<Mark> markList, Card card) {
        this.name = name;
        this.surname = surname;
        this.studentId = studentId;
        this.photoInBase64 = photoInBase64;
        this.subjectList = subjectList;
        this.markList = markList;
        this.card=card;
    }
}

