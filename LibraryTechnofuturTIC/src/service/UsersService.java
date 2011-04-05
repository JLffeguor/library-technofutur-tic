package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UsersDao;

@Service
@Transactional
public class UsersService {
	@Autowired
	private UsersDao dao;

}
