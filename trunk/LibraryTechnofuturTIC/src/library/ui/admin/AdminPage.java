package library.ui.admin;

import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Page;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@Page
@org.springframework.stereotype.Component
@Configurable(preConstruction = true)
public class AdminPage extends VerticalLayout implements Button.ClickListener{

	private final Button libraryManagement = new Button("Gestion de livres");
	private final Button orderManagement = new Button("Livre à  commander");
	private final Button groupManagement = new Button("Gestion de group");
	private final VerticalLayout dynamicLayout = new VerticalLayout();
	final VerticalLayout menu = new VerticalLayout();


	@Autowired  GroupService groupService;
	@Autowired  OrderDao orderDao;
	
	public AdminPage(){
		
		menu.setStyleName("menu");
		menu.setWidth("30%");
		menu.setHeight("100%");
		
		dynamicLayout.setSpacing(true);
		dynamicLayout.setSizeFull();
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setStyleName("toolbar");
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth("100%");
		
		
		
		libraryManagement.setIcon(new ThemeResource("images/library2.png"));
		orderManagement.setIcon(new ThemeResource("images/order_2.png"));
		groupManagement.setIcon(new ThemeResource("images/groups.png"));
		
		libraryManagement.addListener(this);
		orderManagement.addListener(this);
		groupManagement.addListener(this);
		buttonLayout.addComponent(libraryManagement);
		buttonLayout.addComponent(orderManagement);
		buttonLayout.addComponent(groupManagement);
		
		Button empty = new Button();
		buttonLayout.addComponent(empty);
		buttonLayout.setExpandRatio(empty, 1);

		
		
		HorizontalLayout menuAndData = new HorizontalLayout();
		menuAndData.setHeight("100%");
		menuAndData.setWidth("100%");
		menuAndData.setStyleName("menu");
//		menuAndData.addComponent(menu);
//		menuAndData.addComponent(dynamicLayout);
//		menuAndData.setExpandRatio(dynamicLayout, 1);
		
		addComponent(buttonLayout);
		addComponent(menuAndData);

	}

	public void buttonClick(ClickEvent event) {
		Button button = event.getButton();
		dynamicLayout.removeAllComponents();
		
		if(button.equals(libraryManagement)){
			
		}

		if(button.equals(orderManagement)){
			selectGroup();
		}

		if(button.equals(groupManagement)){

		}
	}
	

	private void selectGroup(){
		
		ListSelect citySelect = new ListSelect("groups", groupService.getGroupNames());
        citySelect.setRows(7); 
        citySelect.setNullSelectionAllowed(false);
        citySelect.setImmediate(true);
        
        menu.removeAllComponents();
        menu.addComponent(citySelect);
        
        final Table table = new Table();
        table.addContainerProperty("Titre du manuel", String.class,  null);
        table.addContainerProperty("Auteur", String.class,  null);
        table.addContainerProperty("ISBN", Long.class,  null);
        table.addContainerProperty("Nom", String.class,  null);
        table.addContainerProperty("Prenom", String.class,  null);
        table.addContainerProperty("Prix", Integer.class,  null);
        table.setPageLength(0);
        
        
        table.setSelectable(false);
        
        dynamicLayout.addComponent(citySelect);
        dynamicLayout.addComponent(table);
        citySelect.addListener(new Property.ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				table.setCaption("Group:"+(String)event.getProperty().getValue());
				String name =(String)event.getProperty().getValue();
				List<Order> orderList = orderDao.getOrderByGroupName(name);
				table.setPageLength(orderList.get(0).getGroup().getNumberOfStudent());
				table.removeAllItems();
			
				int i=0;
				for(Order o : orderList){
					i++;
					table.addItem(new Object[] {o.getBook_title(),o.getAuthor(),o.getIsbn(),o.getUser().getLastName(),o.getUser().getFirstName(),o.getPrice()}, new Integer(i));
				}
				
			}
		});
        
        
        

	}

}
