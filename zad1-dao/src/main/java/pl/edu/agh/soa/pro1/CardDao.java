package pl.edu.agh.soa.pro1;

import pl.edu.agh.soa.pro1.models.Card;
import pl.edu.agh.soa.pro1.models.Mark;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CardDao {
    @PersistenceContext(unitName = "std")
    EntityManager entityManager;

    public static CardEntity cardToEntity(Card card){
        CardEntity cardEntity = new CardEntity();
        cardEntity.setNumber(card.getNumber());
        cardEntity.setValue(card.getValue());
        return cardEntity;
    }
    public static Card entityToCard(CardEntity cardEntity){
        Card card =new Card();
        card.setNumber(cardEntity.getNumber());
        card.setValue(cardEntity.getValue());
        return card;
    }
    public void save(Card card) {
        entityManager.persist(CardDao.cardToEntity(card));
    }
    public void add(CardEntity cardEntity) {
        entityManager.persist(cardEntity);
    }
}
