package pl.edu.agh.soa.pro1;
import lombok.Data;
import pl.edu.agh.soa.pro1.models.Subject;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private int studentId;

    @Column
    private String photoInBase64;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MarkEntity> marksEntities;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name = "student_has_subject",
            joinColumns = {
                    @JoinColumn(name = "studentID", referencedColumnName = "idx")
            }, inverseJoinColumns = {@JoinColumn (name = "subjectId", referencedColumnName = "idx" )})
    private List<SubjectEntity> subjectList;
}
