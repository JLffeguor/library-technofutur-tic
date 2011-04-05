package ui.admin;

import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.navigator7.Page;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@Page
public class AdminPage extends HorizontalLayout implements Button.ClickListener{

	private final Button libraryManagement = new Button("Gestion de livres");
	private final Button orderManagement = new Button("Livre à  commander");
	private final Button groupManagement = new Button("Gestion de group");
	private final VerticalLayout dynamicLayout = new VerticalLayout();

	@Autowired GroupService groupService;
	
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

		dynamicLayout.setSpacing(true);
		dynamicLayout.removeAllComponents();
		
		if(button.equals(libraryManagement)){
			
		}

		if(button.equals(orderManagement)){

		}

		if(button.equals(groupManagement)){

		}
	}
	
	private void selectGroup(){
		
		ListSelect citySelect = new ListSelect("Please select a city", groupService.getStructuredGroupNames());
        citySelect.setRows(7); // perfect length in out case
        citySelect.setNullSelectionAllowed(true); // user can not 'unselect'
        citySelect.setImmediate(true); // send the change to the server at once
        
        final Table table = new Table();
        table.setColumnHeaders(new String[] { "Titre du manuel", "Auteur", "ISBN", "Nom", "Prix" });
        dynamicLayout.addComponent(citySelect);
        dynamicLayout.addComponent(table);
        citySelect.addListener(new Property.ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				table.setCaption((String)event.getProperty().getValue());
//				table.setContainerDataSource( new BeanItemContainer<Book>(bookList) );
				
			}
		});
        
        
        

	}

}
