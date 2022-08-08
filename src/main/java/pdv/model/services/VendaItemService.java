package pdv.model.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pdv.model.entities.Item;
import pdv.model.entities.VendaItem;
import pdv.model.entities.Vendas;

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
	
	public void remove(VendaItem vendaItem, Vendas vendas){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM VendaItem c WHERE c.id.itens = :itemid AND c.id.vendas = :vendaid");
		query.setParameter("itemid", vendaItem.getItem());
		query.setParameter("vendaid", vendas);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
}
