package library.ui.admin;

import java.util.List;

import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.Order;
import library.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@Service
public class OrderManagementButtonListenerLogic{

	@Autowired GroupDao groupDao;
	@Autowired OrderDao orderDao;
	@Autowired UserDao userDao;
	
	public void execute(final VerticalLayout dynamicLayout){		
		
		final HorizontalLayout groupButton = new HorizontalLayout();
		final VerticalLayout table = new VerticalLayout();
		
		dynamicLayout.addComponent(groupButton);
		dynamicLayout.addComponent(table);
		
		List<String> groupNames = groupDao.getGroupNames();
		
		for (String name :  groupNames){
			
			Button button = new Button(name);
			groupButton.addComponent(button);
			
			button.addListener(new Button.ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					
					
					String groupName = event.getButton().getCaption();
					List<Order> orderList = orderDao.getOrderByGroupName(groupName);
					List<User> userList = userDao.getUsersByGroupName(groupName);
					
					
					OrderLayout orderLayout = new OrderLayout(userList, orderList);
					orderLayout.setSizeFull();
					
					table.removeAllComponents();
					table.addComponent(orderLayout);
//					dynamicLayout.setExpandRatio(orderLayout, 1);
	
				}
			});
		}
	}
}
