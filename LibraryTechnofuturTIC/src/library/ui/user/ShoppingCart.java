package library.ui.user;

import java.util.ArrayList;
import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Service
public class ShoppingCart extends VerticalLayout{

	@Autowired OrderDao orderDao;
	
	private List<Order> orders = new ArrayList<Order>();
	Label totalPrice = new Label();
	
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
		
		for(Order o:orders){
			final HorizontalLayout orderLayout = new HorizontalLayout();
			orderLayout.setSpacing(true);
			Label titleOrder = new Label(o.getBook_title());
			titleOrder.setWidth("60px");
			Label authorOder = new Label(o.getAuthor());
			authorOder.setWidth("30px");
			Label priceOrder = new Label(Integer.toString(o.getPrice()));
			
			Button deleteButton = new Button("enlever");
			orderLayout.addComponent(titleOrder);
			orderLayout.addComponent(authorOder);
			orderLayout.addComponent(priceOrder);
			orderLayout.addComponent(deleteButton);
			shoppingCartLayout.addComponent(orderLayout);
			
			deleteButton.addListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					VerticalLayout v  = (VerticalLayout)orderLayout.getParent();
					int i = v.getComponentIndex(orderLayout);
					v.removeComponent(orderLayout);
					v.removeComponent(totalPrice);
					orders.remove(i);
					execute();
					totalPrice.setCaption(Integer.toString(totalOrderPrice(orders)));
					shoppingCartLayout.addComponent(totalPrice);
				}
			});
		}
		totalPrice.setValue("<b>Prix total : " + Integer.toString(totalOrderPrice(orders)) + "</b>");
		totalPrice.setContentMode(Label.CONTENT_XHTML);
		HorizontalLayout footerShoppingCart = new HorizontalLayout();
		Button saveOrder = new Button("Commander le panier");
		saveOrder.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				for (Order o : orders) {
					orderDao.addOrder(o);
				}
			}
		});
		footerShoppingCart.addComponent(totalPrice);
		footerShoppingCart.addComponent(saveOrder);
		shoppingCartLayout.addComponent(footerShoppingCart);

		return shoppingCartLayout;
	}
	public int totalOrderPrice(List<Order> orderList){
		int sum = 0;
		for (Order o:orderList){
			sum +=o.getPrice();
		}
		return sum;
		
	}
	
}
