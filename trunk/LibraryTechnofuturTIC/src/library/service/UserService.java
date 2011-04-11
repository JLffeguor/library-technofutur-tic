package library.service;

import java.util.List;

import library.dao.UserDao;
import library.domain.User;
import library.navigator7.MyApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.navigator7.NavigableApplication;

import com.vaadin.Application;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao dao;
	
	public boolean checkIfUserRegistered(String firstName, String code){
		
		List<User> user = dao.getUserByFirstNameAndCode(firstName, code);
		
		if(user.size()==0){
			Application myApp = (MyApplication)NavigableApplication.getCurrent();
			myApp.getMainWindow().showNotification("Le prénom ou le code est incorrect");
			return false;
		}else{
			return true;
		}
		
	}

}
