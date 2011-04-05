package ui.admin;

import org.vaadin.navigator7.Page;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;


@Page
public class AdminPage extends HorizontalLayout{

	public AdminPage(){
		
		Button libraryManagement = new Button("Gestion de livres");
		Button orderManagement = new Button("Livre à  commander");
		Button groupManagement = new Button("Gestion de group");
		
		VerticalLayout buttonLayout = new VerticalLayout();
		buttonLayout.addComponent(libraryManagement);
		buttonLayout.addComponent(orderManagement);
		buttonLayout.addComponent(groupManagement);
		buttonLayout.setSpacing(true);
		buttonLayout.setSizeFull();
		
		
		
		
		addComponent(buttonLayout);
	}
	
}
