package library.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import library.dao.OrderDao;
import library.domain.Order;
import library.service.GroupService;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;


@Service
public class OrderManagementLogic{

	@Autowired GroupService groupService;
	@Autowired OrderDao orderDao;
	
	
	public void execute(final Layout menu, final Layout dynamicLayout){
		ListSelect citySelect = new ListSelect("groups", groupService.getGroupNames());
		citySelect.setRows(7); 
		citySelect.setNullSelectionAllowed(false);
		citySelect.setImmediate(true);
		citySelect.setWidth("100px");

		menu.addComponent(citySelect);

		final Table table = new Table();
		table.addContainerProperty("Titre du manuel", String.class,  null);
		table.addContainerProperty("Auteur", String.class,  null);
		table.addContainerProperty("ISBN", Long.class,  null);
		table.addContainerProperty("Nom", String.class,  null);
		table.addContainerProperty("Prenom", String.class,  null);
		table.addContainerProperty("Prix", Integer.class,  null);
		table.setPageLength(0);
		final Label selectedGroup = new Label("Group : ...");

		table.setSelectable(false);

		dynamicLayout.addComponent(table);
		dynamicLayout.addComponent(selectedGroup);
		citySelect.addListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {
				selectedGroup.setValue("Group:"+(String)event.getProperty().getValue());
				String name =(String)event.getProperty().getValue();
				List<Order> orderList = orderDao.getOrderByGroupName(name);
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
