package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import library.domain.User;
@Repository
@Transactional
public class UserDao {
	@PersistenceContext
	EntityManager em;
	
	public List<User> getUsersByGroupId(Long id){
		return em.createQuery("select u from User u where u.group.id = :id").setParameter("id", id).getResultList();
	}
}
