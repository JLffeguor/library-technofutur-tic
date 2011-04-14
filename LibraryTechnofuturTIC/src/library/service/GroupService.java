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

		if(orderList.size()!=0){
			orderDao.deleteOrders(orderList);
		}
		if(userList.size()!=0){
			userDao.deleteUsers(userList);
		}

		groupDao.deleteGroup(g.getId());

	}

	public String generatedCode(Group group){

		Long id = group.getId();
		Long begin;

		if((10000L+id)>99999){
			begin = group.getId();
		}else{
			begin = 10000L+id;
		}

		String code = (String.valueOf(begin));
		group.setCode(code);
		groupDao.setCode(group);
		return code; 
	}

}
