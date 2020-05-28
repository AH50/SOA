package pl.edu.agh.soa.pro1;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<SubjectEntity> subjectEntityList;
}
