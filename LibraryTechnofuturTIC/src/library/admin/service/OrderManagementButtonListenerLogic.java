package library.admin.service;

import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@Service
public class OrderManagementButtonListenerLogic{

	@Autowired GroupService groupService;
	@Autowired OrderDao orderDao;
	
	
	public void execute(final Layout dynamicLayout){
		
		

		final Table table = new Table();
		table.addContainerProperty("Titre du manuel", String.class,  null);
		table.addContainerProperty("Auteur", String.class,  null);
		table.addContainerProperty("ISBN", Long.class,  null);
		table.addContainerProperty("Nom", String.class,  null);
		table.addContainerProperty("Prenom", String.class,  null);
		table.addContainerProperty("Prix", Integer.class,  null);
		table.setPageLength(0);

		table.setSelectable(false);

		dynamicLayout.addComponent(table);
		
		VerticalLayout groupLayout = new VerticalLayout();
		List<String> groupNamesList = groupService.getGroupNames();
		for(String groupName : groupNamesList){
			VerticalLayout button = createGroupButton(groupName);			
			groupLayout.addComponent(button);
		}
		
		
//		addListener(new LayoutClickListener() {
//			
//			public void layoutClick(LayoutClickEvent event) {
//				String name = event.
//				List<Order> orderList = orderDao.getOrderByGroupName();
//				table.removeAllItems();
//
//				int i=0;
//				for(Order o : orderList){
//					i++;
//					table.addItem(new Object[] {o.getBook_title(),o.getAuthor(),o.getIsbn(),o.getUser().getLastName(),o.getUser().getFirstName(),o.getPrice()}, new Integer(i));
//				}
//				
//			}
//		});

		
		
		
	}
	
	private VerticalLayout createGroupButton(String name){
		VerticalLayout button = new VerticalLayout();
		button.setHeight("35px");
		button.setStyleName("gray");
		
		Label label  = new Label();
		label.setValue(name);
				
		button.addComponent(label);
		
		return button;
	}


	
	
	
}
