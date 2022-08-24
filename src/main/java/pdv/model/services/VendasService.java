package pdv.model.services;

import java.time.Instant;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pdv.model.entities.Vendas;
import pdv.model.entities.enums.VendaStatus;

public class VendasService {
	
	public List<Vendas> findAll(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Vendas> list = em.createQuery("select a from Vendas a",Vendas.class).getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
	public void saveOrUpdate(Vendas obj) {
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
	
	public void remove(Vendas obj){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Vendas i = em.find(Vendas.class, obj.getId());
		em.remove(i);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	public Vendas findVendasById(Long id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Vendas i = em.find(Vendas.class, id);
		em.getTransaction().commit();
		em.close();
		emf.close();
		return i;
	}
	
	public List<Vendas> findByRelatorio(Long id, Instant dataI, Instant dataF){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Vendas> list = em.createQuery("select v from Vendas v WHERE v.cliente.id = :id_cliente AND v.data >= :dataI AND v.data <=:dataF",Vendas.class)
				.setParameter("id_cliente", id)
				.setParameter("dataI", dataI)
				.setParameter("dataF", dataF)
				.getResultList(); 
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
}
