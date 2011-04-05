package library.service;

import java.util.ArrayList;
import java.util.List;

import library.dao.GroupDao;
import library.domain.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupService {
	@Autowired GroupDao groupDao;
	
	public List<String> getStructuredGroupNames(){
		List<String> groupNames = new ArrayList<String>();
		List<Group> groups = groupDao.getGroups();
		
		for(Group group : groups){
			groupNames.add(group.getName());
		}
		
		return groupNames;
	}
	
	public void createGroup (Group group){
		groupDao.createGroup(group);
	}
	
}
