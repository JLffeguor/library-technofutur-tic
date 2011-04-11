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
public class GroupDao {

	@PersistenceContext
	EntityManager em;
	
	public List<Group> getGroups() {
		return em.createQuery("from Group g").getResultList();
	}
	
	public List<Group> getGroupByName(String name){
		return em.createQuery("select g from Group g where g.name = :name").setParameter("name", name).getResultList();
	}
	
	public List<String> getGroupNames(){
		return em.createQuery("select g.name from Group g").getResultList();
	}
	
	public void createGroup(Group group){
		em.persist(group);
	}
	public void deleteGroup(Long id){
		em.createQuery("delete from Group g where g.id =:id").setParameter("id", id).executeUpdate();
	}
	
	public void setCode(Group group){
		em.createQuery("update Group g set g.code  = :code  where g.id = :id").setParameter("code", group.getCode()).setParameter("id", group.getId()).executeUpdate();
	}	
	
	public void upDateGroup(Group group){
		Group g = em.find(Group.class, group.getId());
		g.setName(group.getName());
		g.setCreationDate(group.getCreationDate());
		g.setClosingDate(group.getClosingDate());
		em.merge(g);
	}
	
	public List<Group> getGroupByCode(String code){
		return em.createQuery("select g from Group g where g.code = :code").setParameter("code", code).getResultList();
	}

}
