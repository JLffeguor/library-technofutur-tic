package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Order;
import library.domain.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public class UserDao {
	@PersistenceContext
	EntityManager em;
	
	public List<User> getUsersByGroupName(String name){
		return em.createQuery("select u from User u where u.group.name = :name").setParameter("name", name).getResultList();
	}
	
	public void deleteUsers(List<User> userList){
		em.createQuery("delete from User u where u in (:userList)").setParameter("userList", userList).executeUpdate();
	}
	
	public void addUser(User user){
		em.persist(user);
	}
	
	public List<User> getUserByFirstNameAndCode(String firstName, String code){
		return em	.createQuery("select u from User u where u.firstname = :firstname and u.group.code = :code")
					.setParameter("firstname", firstName)
					.setParameter("code", code)
					.getResultList();
	}
	
}
