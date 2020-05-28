package pl.edu.agh.soa.pro1;
import pl.edu.agh.soa.pro1.models.Mark;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class MarkDao {
    @PersistenceContext(unitName = "std")
    EntityManager entityManager;

    public static MarkEntity markToEntity(Mark mark){
        MarkEntity markEntity = new MarkEntity();
        markEntity.setMark(mark.getMark());
        return markEntity;
    }
    public static Mark entityToMark(MarkEntity markEntity){
        Mark mark =new Mark();
        mark.setMark(markEntity.getMark());
        return mark;
    }
    public void save(Mark mark) {
        entityManager.persist(MarkDao.markToEntity(mark));
    }
    public void add(MarkEntity markEntity) {
        entityManager.persist(markEntity);
    }
}
