package pdv.model.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pdv.model.entities.Cliente;
import pdv.model.entities.Item;

public class ClienteService {
	
	public List<Cliente> findAll(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Cliente> list = em.createQuery("select a from Cliente a",Cliente.class).getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
	public List<Cliente> findByDescricao(String descricao){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List<Cliente> list = em.createQuery("select a from Cliente a WHERE a.name LIKE :descri",Cliente.class)
				.setParameter("descri", "%"+descricao+"%")
				.getResultList();
		em.getTransaction().commit();
		em.close();
		emf.close();
		return list;
	}
	
	public void saveOrUpdate(Cliente obj) {
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
	
	public void remove(Cliente obj){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Cliente i = em.find(Cliente.class, obj.getId());
		em.remove(i);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
}
