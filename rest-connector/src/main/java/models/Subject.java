package models;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@XmlType(propOrder = {"name", "teacher", "ECTS"})

public class Subject {
    private String name;
    private String teacher;
    private int ECTS;

    public Subject(String name, String teacher, int ECTS) {
        this.name = name;
        this.teacher = teacher;
        this.ECTS = ECTS;
    }
}
