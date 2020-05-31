package pl.edu.agh.soa.pro1;

import pl.edu.agh.soa.pro1.models.Card;
import pl.edu.agh.soa.pro1.models.Mark;
import pl.edu.agh.soa.pro1.models.Student;
import pl.edu.agh.soa.pro1.models.Subject;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.edu.agh.soa.pro1.CardDao.cardToEntity;
import static pl.edu.agh.soa.pro1.CardDao.entityToCard;

@Stateless
public class StudentDao {
    @PersistenceContext(unitName = "std")
    EntityManager entityManager;

    public static StudentEntity studentToEntity(Student student) {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setName(student.getName());
        studentEntity.setSurname(student.getSurname());
        studentEntity.setStudentId(student.getStudentId());
        studentEntity.setPhotoInBase64(student.getPhotoInBase64());

        List<MarkEntity> markEntities = student.getMarkList()
                .stream()
                .map(MarkDao::markToEntity)
                .collect(Collectors.toList());

        studentEntity.setMarksEntities(markEntities);

        List<SubjectEntity> subjectEntities = student.getSubjectList()
                .stream()
                .map(SubjectDao::subjectToEntity)
                .collect(Collectors.toList());

        studentEntity.setSubjectList(subjectEntities);

        studentEntity.setCardEntity(CardDao.cardToEntity(student.getCard()));


    return studentEntity;
    }
    public static Student entityToStudent(StudentEntity studentEntity) {
        Student student = new Student();
        student.setName(studentEntity.getName());
        student.setSurname(studentEntity.getSurname());
        student.setStudentId(studentEntity.getStudentId());
        student.setPhotoInBase64(studentEntity.getPhotoInBase64());

        List<Mark> marks = studentEntity.getMarksEntities()
                .stream()
                .map(MarkDao::entityToMark)
                .collect(Collectors.toList());
        student.setMarkList(marks);

        List<Subject> subjects = studentEntity.getSubjectList()
                .stream()
                .map(SubjectDao::entityToSubject)
                .collect(Collectors.toList());
        student.setSubjectList(subjects);

        student.setCard(CardDao.entityToCard(studentEntity.getCardEntity()));

        return student;
    }

    public void save(Student student) {
        entityManager.persist(StudentDao.studentToEntity(student));
    }

    public Student findbystudentId(int studenid) throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot).where(criteriaBuilder.equal(studentEntityRoot.get("studentId"), Integer.toString(studenid)));

    return entityToStudent(entityManager
            .createQuery(query).getSingleResult());
    }
    public List<Student> findAllstudents() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot);

        return entityManager
                .createQuery(query)
                .getResultList()
                .stream()
                .map(StudentDao::entityToStudent)
                .collect(Collectors.toList());
    }

    public Student findbyname(String name) throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot).where(criteriaBuilder.equal(studentEntityRoot.get("name"), name));


        return entityToStudent(entityManager.createQuery(query).getResultList().get(0));
    }

    public Student findbysurname(String surname) throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot).where(criteriaBuilder.equal(studentEntityRoot.get("surname"), surname));

        return entityToStudent(entityManager.createQuery(query).getResultList().get(0));
    }


    public void update(Student student) throws Exception {
        StudentEntity studentEntity = findbystudentIdEntity(student.getStudentId());
        studentEntity.setName(student.getName());
        studentEntity.setSurname(student.getSurname());
        studentEntity.setPhotoInBase64(student.getPhotoInBase64());

        entityManager.merge(studentEntity);
    }
    public StudentEntity findbystudentIdEntity(int studenid) throws Exception {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StudentEntity> query = criteriaBuilder.createQuery(StudentEntity.class);
        Root<StudentEntity> studentEntityRoot = query.from(StudentEntity.class);
        query.select(studentEntityRoot).where(criteriaBuilder.equal(studentEntityRoot.get("studentId"), studenid));

        return entityManager.createQuery(query).getResultList().get(0);
    }

    public void removeStudentById(int id) throws Exception{
        StudentEntity s = findbystudentIdEntity(id);
        entityManager.remove(s);
    }

}
