package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Order;

public class OrderDao {

	@PersistenceContext 
	EntityManager em;
	
	public List<Order> getOrderByGroupName(String name){
		return em.createQuery("select o from Order o where o.group.id = :name").setParameter("name", name).getResultList();
	}
}
