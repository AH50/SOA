package pl.edu.agh.soa.pro1;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String name;

    @Column
    private Integer ects;

    @Column
    private String teacher;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "subjectList")
    private List<StudentEntity> studentEntities = new ArrayList<>();
}
