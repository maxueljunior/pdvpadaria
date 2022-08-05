package pdv.model.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pdv.model.entities.Item;
import pdv.model.entities.VendaItem;

public class VendaItemService {
	
	public Item findItemById(Integer id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item i = em.find(Item.class, id);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return i;
	}
	
	public void saveOrUpdate(VendaItem obj) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(obj.getItem() == null) {
			em.persist(obj);
		}
		else {
			em.merge(obj);
		}
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	public void remove(VendaItem obj){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		VendaItem i = em.find(VendaItem.class, obj.getItem());
		em.remove(i);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
}
