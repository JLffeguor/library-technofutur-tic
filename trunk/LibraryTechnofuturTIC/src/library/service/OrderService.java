package library.service;

import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired OrderDao orderDao;
	


}
