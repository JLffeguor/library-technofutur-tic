package library.service;

import java.util.ArrayList;
import java.util.List;

import library.dao.GroupDao;
import library.domain.Group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
	@Autowired GroupDao groupDao;
 	public List<String> getGroupNames(){
		List<String> groupNames = new ArrayList<String>();
		List<Group> groups = groupDao.getGroups();
		
		for(Group group : groups){
			groupNames.add(group.getName());
		}
		return groupNames;
	}
	
	public String generatedCode(Group group){
		String code = (String.valueOf(group.getId())+group.getName());
		group.setCode(code);
		groupDao.setCode(group);
		return code; 
	}
	
}
