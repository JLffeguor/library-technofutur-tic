package library.navigator7;

import library.ui.admin.AdminPage;
import library.ui.user.UserPage;

import org.vaadin.navigator7.WebApplication;


public class MyWebApplication extends WebApplication{

	 public MyWebApplication() {
	        registerPages(new Class[] {
	        		AdminPage.class,
//	        		UserPage.class,
	        });
	    }
}
	

