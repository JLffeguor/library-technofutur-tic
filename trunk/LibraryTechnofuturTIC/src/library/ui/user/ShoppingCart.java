package library.ui.user;

import java.util.ArrayList;
import java.util.List;

import library.domain.Order;

import org.springframework.stereotype.Service;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Service
public class ShoppingCart extends VerticalLayout{

	private List<Order> orders = new ArrayList<Order>();
	
	
	public List<Order> getOrders() {
		return orders;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public VerticalLayout execute(){
		removeAllComponents();
		final VerticalLayout shoppingCartLayout = new VerticalLayout();
		shoppingCartLayout.removeAllComponents();
		shoppingCartLayout.setSpacing(true);
		shoppingCartLayout.addStyleName("colorfont");
		int sum = 0;
		
		for(Order o:orders){
			final HorizontalLayout orderLayout = new HorizontalLayout();
			orderLayout.setSpacing(true);
			Label titleOrder = new Label(o.getBook_title());
			Label AuthorOder = new Label(o.getAuthor());
			Label priceOrder = new Label(Integer.toString(o.getPrice()));
			priceOrder.addStyleName("labeltoright");
			Button deleteButton = new Button("enlever");
			sum += o.getPrice();
			orderLayout.addComponent(titleOrder);
			orderLayout.addComponent(AuthorOder);
			orderLayout.addComponent(priceOrder);
			orderLayout.addComponent(deleteButton);
			shoppingCartLayout.addComponent(orderLayout);
			
			deleteButton.addListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					VerticalLayout v  = (VerticalLayout)orderLayout.getParent();
					int i = v.getComponentIndex(orderLayout);
					v.removeComponent(orderLayout);
					orders.remove(i);
					execute();
				}
			});
		}
		Label totalPrice = new Label(Integer.toString(sum));
		shoppingCartLayout.addComponent(totalPrice);
		
		
		
		return shoppingCartLayout;
	}
	
}
