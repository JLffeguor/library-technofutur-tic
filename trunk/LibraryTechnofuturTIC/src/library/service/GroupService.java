package library.service;

import java.util.List;

import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.Group;
import library.domain.Order;
import library.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupService {
	@Autowired GroupDao groupDao;
	@Autowired OrderDao orderDao;
	@Autowired UserDao userDao;
	
	
	public void deleteGroup(Group g){
		List<Order> orderList = orderDao.getOrderByGroupName(g.getName());
		List<User> userList = userDao.getUsersByGroupName(g.getName());
		
		orderDao.deleteOrders(orderList);
		userDao.deleteUsers(userList);
		
		groupDao.deleteGroup(g.getId());
		
		
		
	}
	
	public String generatedCode(Group group){
		String code = (String.valueOf(group.getId())+group.getName());
		group.setCode(code);
		groupDao.setCode(group);
		return code; 
	}
	
}
