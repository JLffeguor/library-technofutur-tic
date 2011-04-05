package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.User;

public class UsersDao {
	@PersistenceContext
	EntityManager em;
	
	public List<User> getUsersByGroupId(Long id){
		return em.createQuery("select u from User u where u.group.id = :id").setParameter("id", id).getResultList();
	}
}
