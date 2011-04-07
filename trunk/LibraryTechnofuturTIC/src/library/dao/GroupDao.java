package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import library.domain.Group;
@Repository
public class GroupDao {

	@PersistenceContext
	EntityManager em;
	
	public List<Group> getGroups() {
		return em.createQuery("from Group g").getResultList();
	}
	
	public List<Group> getGroupByName(String name){
		return em.createQuery("select g from Group g where g.name = :name").setParameter("name", name).getResultList();
	}
	public void createGroup(Group group){
		em.persist(group);
	}
	public void removeGroup(long id){
		em.createQuery("delete from Group g where g.id =:id").setParameter("id", id).executeUpdate();
	}
	
}
