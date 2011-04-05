package library.service;

import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {

	@Autowired OrderDao orderDao;
	
	public List<DataToDisplay> getData(String groupName){
		List<Order> listOrder = orderDao.getOrderByGroupName(groupName);
		
		for(Order order : listOrder){
			
		}
		
		
	}
	
	
	
	
	
	public static class DataToDisplay{
		
		private String book_title;
		private String author;
		private	Long isbn;
		private int price;
		private String firstname;
		private String lastname;

	}

}
