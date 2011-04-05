package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.GroupDao;
import domain.Group;

@Service
@Transactional
public class GroupService {
	@Autowired GroupDao groupDao;
	
	public List<Group> getGroups(){
		return groupDao.getGroups();
	}
	
	
}
