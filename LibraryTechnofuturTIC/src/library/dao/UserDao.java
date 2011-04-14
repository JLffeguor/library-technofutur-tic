package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Group;
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
	
	public List<User> getUserByFirstNameAndLastName(String firstname, String lastname){
		return em	.createQuery("select u from User u where u.firstName = :firstname and u.lastName = :lastname")
					.setParameter("firstname", firstname)
					.setParameter("lastname", lastname )
					.getResultList();
	}
	public void updateUser(User user){
//		User u = em.find(User.class, user.getId());
//		if(user.getFirstName()!=null)
//			u.setFirstName(user.getFirstName());
//		if(user.getLastName()!=null)
//			u.setLastName(user.getLastName());
//		if(user.getEmail()!=null)
//			u.setEmail(user.getEmail());
//		if(user.getGroup()!=null)
//			u.setGroup(user.getGroup());
		em.merge(user);
	}
	
}
