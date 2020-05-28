package pl.edu.agh.soa.pro1;


import pl.edu.agh.soa.pro1.models.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SubjectDao {
    @PersistenceContext(unitName = "std")
    EntityManager entityManager;
    public static SubjectEntity subjectToEntity(Subject subject) {
        SubjectEntity subjectEntity = new SubjectEntity();

        subjectEntity.setName(subject.getName());
        subjectEntity.setEcts(subject.getECTS());
        subjectEntity.setTeacher(subject.getTeacher());

        return subjectEntity;
    }
    public static Subject entityToSubject(SubjectEntity subjectEntity) {
        Subject subject = new Subject();

        subject.setName(subjectEntity.getName());
        subject.setTeacher(subjectEntity.getTeacher());
        subject.setECTS(subjectEntity.getEcts());

        return subject;
    }
}
