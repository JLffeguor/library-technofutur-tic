package ui.admin;

import org.vaadin.navigator7.Page;

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@Page
public class AdminPage extends VerticalLayout{

	public AdminPage(){
		Button libraryManagement = new Button("Gestion de livres");
		Button orderManagement = new Button("Livre à  commander");
		Button groupManagement = new Button("Gestion de group");
		this.addComponent(libraryManagement);
		this.addComponent(orderManagement);
		this.addComponent(groupManagement);
		this.setSpacing(true);
	}
	
}
