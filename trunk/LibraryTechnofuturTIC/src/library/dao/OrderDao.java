package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Order;

public class OrderDao {

	@PersistenceContext 
	EntityManager em;
	
	public List<Order> getOrderByGroupId(Long id){
		return em.createQuery("select o from Order o where o.group.id = :id").setParameter("id", id).getResultList();
	}
}
