package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Group;

public class GroupDao {

	@PersistenceContext
	EntityManager em;
	
	public List<Group> getGroups() {
		return em.createQuery("from Group g").getResultList();
	}

}
