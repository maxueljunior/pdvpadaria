package pdv.model.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pdv.model.entities.Item;
import pdv.model.entities.VendaItem;
import pdv.model.entities.Vendas;

public class ItemService {

	public List<Item> findAll(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Item> list = em.createQuery("select a from Item a",Item.class).getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
	public List<Item> findByDescricao(String descricao){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Item> list = em.createQuery("select a from Item a WHERE a.name LIKE :descri",Item.class)
				.setParameter("descri", "%"+descricao+"%")
				.getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
	public void saveOrUpdate(Item obj) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(obj.getId() == null) {
			em.persist(obj);
		}
		else {
			em.merge(obj);
		}
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	public void remove(Item obj){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Item i = em.find(Item.class, obj.getId());
		em.remove(i);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
}
