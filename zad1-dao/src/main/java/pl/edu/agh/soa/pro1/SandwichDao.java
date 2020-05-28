package pl.edu.agh.soa.pro1;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless

public class SandwichDao {
    @PersistenceContext(unitName = "std")
    EntityManager entityManager;

    public void add(SandwichEntity sandwichEntity){
        entityManager.persist(sandwichEntity);

    }
}
