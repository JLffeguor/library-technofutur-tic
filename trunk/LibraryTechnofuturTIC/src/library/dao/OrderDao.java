package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import library.domain.Order;
@Repository
@Transactional
public class OrderDao {

	@PersistenceContext 
	EntityManager em;
	
	public List<Order> getOrderByGroupName(String name){
		return em.createQuery("select o from Order o where o.group.name = :name").setParameter("name", name).getResultList();
	}
}
