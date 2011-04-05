package library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import dao.BookDao;
import dao.GroupDao;
import dao.UserDao;
import domain.User;

public class OrderService {

	@Autowired BookDao bookDao;
	@Autowired GroupDao groupDao;
	@Autowired UserDao userDao;

}
