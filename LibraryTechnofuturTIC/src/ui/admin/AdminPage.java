package ui.admin;

import org.vaadin.navigator7.Page;

import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;


@Page
public class AdminPage extends HorizontalLayout implements Button.ClickListener{

	private final Button libraryManagement = new Button("Gestion de livres");
	private final Button orderManagement = new Button("Livre à  commander");
	private final Button groupManagement = new Button("Gestion de group");

	public AdminPage(){

		VerticalLayout buttonLayout = new VerticalLayout();
		buttonLayout.addComponent(libraryManagement);
		buttonLayout.addComponent(orderManagement);
		buttonLayout.addComponent(groupManagement);
		buttonLayout.setSpacing(true);
		buttonLayout.setSizeFull();

		



		addComponent(buttonLayout);
	}

	public void buttonClick(ClickEvent event) {
		Button button = event.getButton();

		if(button.equals(libraryManagement)){
			
		}

		if(button.equals(orderManagement)){

		}

		if(button.equals(groupManagement)){

		}
	}
	
	private VerticalLayout getLibraryManagementResults(){
		
	}

}
