package library.ui.user;

import library.dao.BookDao;
import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.User;
import library.navigator7.MyApplication;
import library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Page;

import com.vaadin.Application;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Page
@Configurable(preConstruction = true)

public class UserPage extends HorizontalLayout{
	
	@Autowired OrderDao orderDao;
	@Autowired BookDao bookDao;
	@Autowired GroupDao groupDao;
	@Autowired UserDao userDao;
	@Autowired UserService userService;
	
	
	
	
	public UserPage(){
		
		setSizeFull();
		
		final VerticalLayout form = new VerticalLayout();
		form.setSpacing(true);
		
		final TextField firstName = new TextField("Prenom");
		final TextField code = new TextField("Code");
	
		form.addComponent(firstName);
		form.addComponent(code);
		
		Button enter = new Button("Connecter");
		
		enter.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				boolean verify = userService.checkIfUserRegistered((String)firstName.getValue(),(String)code.getValue());
				if(verify){
					////////////
				}
			}
			
		});
		
		Button signIn = new Button("Enregistrer");
		signIn.addStyleName("link");
		signIn.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Application myApp = (MyApplication)NavigableApplication.getCurrent();
				myApp.getMainWindow().addWindow(signInWidow());
			}
		});
		
		form.addComponent(enter);
		form.addComponent(signIn);
		
		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}
	
	public Window signInWidow(){
		final Window window = new Window();
		window.center();
		window.setHeight("300px");
		window.setWidth("250px");
		
		final VerticalLayout form = new VerticalLayout();
		form.setSpacing(true);
		
		final TextField lastName = new TextField("Nom");
		final TextField firstName = new TextField("Prenom");
		final TextField email = new TextField("Email");
		final TextField code = new TextField("Code");
		
		form.addComponent(lastName);
		form.addComponent(firstName);
		form.addComponent(email);
		form.addComponent(code);
		
		Button button = new Button("Valider");
		form.addComponent(button);
		
		button.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				User user = new User();
				user.setLastName((String)lastName.getValue());
				user.setFirstName((String)firstName.getValue());
				user.setEmail((String)email.getValue());
				user.setGroup(groupDao.getGroupByCode((String)code.getValue()).get(0));
				userDao.addUser(user);		
				Application myApp = (MyApplication)NavigableApplication.getCurrent();
				myApp.getMainWindow().removeWindow(window);
			}
		});
		
		window.setContent(form);
		
		return window;
	}
}
