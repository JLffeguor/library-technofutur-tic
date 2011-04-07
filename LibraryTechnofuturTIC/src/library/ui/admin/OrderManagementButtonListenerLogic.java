package library.ui.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.dao.OrderDao;
import library.domain.Order;
import library.service.GroupService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@Service
public class OrderManagementButtonListenerLogic{

	@Autowired GroupService groupService;
	@Autowired OrderDao orderDao;
//	final Map<String, VerticalLayout> tabMap = new HashMap<String, VerticalLayout>();
	
	public void execute(final VerticalLayout dynamicLayout){
		
		

		final Table table = new Table();
		table.addContainerProperty("Titre du manuel", String.class,  null);
		table.addContainerProperty("Auteur", String.class,  null);
		table.addContainerProperty("ISBN", Long.class,  null);
		table.addContainerProperty("Nom", String.class,  null);
		table.addContainerProperty("Prenom", String.class,  null);
		table.addContainerProperty("Prix", Integer.class,  null);
		table.setPageLength(0);

		table.setSelectable(false);
		
		VerticalLayout orderscreen = new VerticalLayout();
		orderscreen.setSizeFull();
		
		List<String> groupNamesList = groupService.getGroupNames();
		
		final HorizontalLayout groupButton = new HorizontalLayout();
		final VerticalLayout data = new VerticalLayout();
		data.setSizeFull();
		
		data.addComponent(table);
		
		orderscreen.addComponent(groupButton);
		orderscreen.addComponent(data);
		orderscreen.setExpandRatio(data, 1);
		
		for (String name :  groupNamesList){
			Button button = new Button(name);
			groupButton.addComponent(button);
			button.addListener(new Button.ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					String groupName = event.getButton().getCaption();
					List<Order> orderList = orderDao.getOrderByGroupName(groupName);
				

					table.removeAllItems();
					
					int i=0;
					for(Order o : orderList){
						i++;
						table.addItem(new Object[] {o.getBook_title(),o.getAuthor(),o.getIsbn(),o.getUser().getLastName(),o.getUser().getFirstName(),o.getPrice()}, new Integer(i));
						
					}
					
				}
			});
		}
		
		dynamicLayout.addComponent(orderscreen);
	}
}
