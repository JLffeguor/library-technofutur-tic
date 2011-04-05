package navigator7;

import org.vaadin.navigator7.WebApplication;

import ui.admin.AdminPage;

public class MyWebApplication extends WebApplication{

	 public MyWebApplication() {
	        registerPages(new Class[] {
	        		AdminPage.class
	        });
	    }
}
	

