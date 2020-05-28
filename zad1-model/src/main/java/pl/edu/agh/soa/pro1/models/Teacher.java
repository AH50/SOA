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
@XmlType(propOrder = {"name","subjectList"})

public class Teacher{
    private String name;
    private List<Subject> subjectList;

    @XmlElementWrapper(name = "Subjects")
    @XmlElement(name = "subjectList")
    public List<Subject> getSubjectList() {
        return subjectList;
    }

    public Teacher(String name, List<Subject> subjectList) {
        this.name = name;
        this.subjectList = subjectList;
    }
}
