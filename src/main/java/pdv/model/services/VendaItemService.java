package pdv.model.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pdv.model.entities.VendaItem;

public class VendaItemService {
	
	public List<VendaItem> findAll(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<VendaItem> list = em.createQuery("select a from VendaItem a",VendaItem.class).getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
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
