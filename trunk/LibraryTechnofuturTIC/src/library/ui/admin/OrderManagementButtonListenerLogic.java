package library.ui.admin;

import java.util.ArrayList;
import java.util.List;

import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.Order;
import library.domain.User;
import library.jxel.xlsGenerator.XlsGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


@Service
public class OrderManagementButtonListenerLogic{

	@Autowired GroupDao groupDao;
	@Autowired OrderDao orderDao;
	@Autowired UserDao userDao;
	@Autowired XlsGenerator xlsGenerator;
	
	private  List<Order> orderList;
	private  List<User> userList;
	
	public void execute(final VerticalLayout dynamicLayout){		
		
		final HorizontalLayout groupButton = new HorizontalLayout();
		final VerticalLayout table = new VerticalLayout();
		
		dynamicLayout.addComponent(groupButton);
		dynamicLayout.addComponent(table);
		
		Button excel = new Button("Créer fichier excel");
		excel.addStyleName("big default");
		dynamicLayout.addComponent(excel);
		dynamicLayout.setExpandRatio(excel, 1);
		
		List<String> groupNames = groupDao.getGroupNames();
		
		for (String name :  groupNames){
			
			Button button = new Button(name);
			groupButton.addComponent(button);
			
			button.addListener(new Button.ClickListener() {
				
				public void buttonClick(ClickEvent event) {
					
					
					String groupName = event.getButton().getCaption();
					orderList = orderDao.getOrderByGroupName(groupName);
					userList = userDao.getUsersByGroupName(groupName);
					
					
					OrderLayout orderLayout = new OrderLayout(userList, orderList);
					orderLayout.setSizeFull();
					
					table.removeAllComponents();
					table.addComponent(orderLayout);
//					dynamicLayout.setExpandRatio(orderLayout, 1);
	
				}
			});
		}
		
		excel.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				
				List<String> temp = new ArrayList<String>();
				
				for(User user : userList){
					for(Order order : orderList){
						if(order.getUser().getId().equals(user.getId())){
							temp.add(user.getLastName());
							temp.add(user.getFirstName());
							temp.add(user.getEmail());
							
							temp.add(order.getBook_title());
							temp.add(order.getAuthor());
							temp.add(String.valueOf(order.getIsbn()));
							temp.add(String.valueOf(order.getPrice()));
						}
					}
				}
				
				xlsGenerator.exportListToXls(temp, 7);
			}
		});
	}
}
