package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	
	public List<String> getStructuredGroupNames(){
		List<String> groupNames = new ArrayList<String>();
		List<Group> groups = groupDao.getGroups();
		
		for(Group group : groups){
			groupNames.add(group.getName());
		}
		
		return groupNames;
	}
	
}
