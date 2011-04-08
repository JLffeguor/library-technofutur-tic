package library.ui.admin;

import java.util.List;

import library.domain.Order;
import library.domain.User;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class OrderLayout extends VerticalLayout {
	
	public OrderLayout(List<User> userList, List<Order> orderList){
	
		String[] colums = {"Nom","Prenom","Email","Titre du manuelle","Auteur","ISBN","Commander","Arriver","Enlever","Indisponible"};
		
		setSpacing(false);
		
		for(User user  : userList){
			
			int lineOfOrder = 0;
			
			Label lastName = new Label(user.getLastName());
			Label firstName = new Label(user.getFirstName());
			Label email = new Label(user.getEmail());
			
			HorizontalLayout userLayout = new HorizontalLayout();
			userLayout.setSizeFull();
			
			userLayout.addComponent(cell(lastName));
			userLayout.addComponent(cell(firstName));
			userLayout.addComponent(cell(email));
			
			
			VerticalLayout orderLayout = new VerticalLayout();
			
			for(Order order : orderList){
				if(order.getUser().getId().equals(user.getId())){
					
				
					Label title = new Label(order.getBook_title());
					Label author = new Label(order.getAuthor());
					Label isbn = new Label(String.valueOf(order.getIsbn()));
					
					CheckBox succeed = new CheckBox();
					CheckBox inalienable = new CheckBox();
					CheckBox ordered = new CheckBox();
					CheckBox taken = new CheckBox();
					
					HorizontalLayout horizontalOrederLayout = new HorizontalLayout();
					
					horizontalOrederLayout.setSpacing(false);
				
					
					lineOfOrder++;
					
					horizontalOrederLayout.addComponent(cell(title));
					horizontalOrederLayout.addComponent(cell(author));
					horizontalOrederLayout.addComponent(cell(isbn));
					horizontalOrederLayout.addComponent(cell(succeed));
					horizontalOrederLayout.addComponent(cell(inalienable));
					horizontalOrederLayout.addComponent(cell(ordered));
					horizontalOrederLayout.addComponent(cell(taken));
					orderLayout.addComponent(cell(horizontalOrederLayout));
					
				}
			}
			

			
			HorizontalLayout line = new HorizontalLayout();
			line.addComponent(userLayout);
			line.addComponent(orderLayout);
			addComponent(line);
		}
	}
	
	public VerticalLayout cell(Component component){
		VerticalLayout cell = new VerticalLayout();
		cell.setSizeFull();
		cell.setStyleName("tableLines");
		cell.addComponent(component);
		cell.setComponentAlignment(component, Alignment.MIDDLE_CENTER);
		return cell;
	}
}
