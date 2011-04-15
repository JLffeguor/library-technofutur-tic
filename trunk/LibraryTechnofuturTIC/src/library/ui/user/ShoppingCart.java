package library.ui.user;

import java.util.ArrayList;
import java.util.List;

import library.dao.OrderDao;
import library.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Service
public class ShoppingCart{

	@Autowired OrderDao orderDao;
	
	private List<Order> orders = new ArrayList<Order>();
	Label totalPrice = new Label();
	Button saveOrder = new Button("Commander le panier");
	
	public List<Order> getOrders() {
		return orders;
	}

	@Transactional
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public VerticalLayout getShoppingCart(){
		
		final VerticalLayout shoppingCartLayout = new VerticalLayout();
		final HorizontalLayout footerShoppingCart = new HorizontalLayout();
		shoppingCartLayout.removeAllComponents();
		shoppingCartLayout.setSpacing(true);
		shoppingCartLayout.addStyleName("colorfont");
		
		for(Order o:orders){
			final HorizontalLayout orderLayout = new HorizontalLayout();
			orderLayout.setWidth("400px");
			orderLayout.setSpacing(true);
			Label titleOrder = new Label(o.getBook_title());
			titleOrder.setWidth("150px");
			Label authorOder = new Label(o.getAuthor());
			authorOder.setWidth("60px");
			Label priceOrder = new Label(Integer.toString(o.getPrice()));
			priceOrder.setWidth("20px");
			
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
					v.removeComponent(saveOrder);
					orders.remove(i);
					totalPrice.setValue("<b>Prix total : " + Integer.toString(totalOrderPrice(orders)) + "</b>");
					footerShoppingCart.addComponent(totalPrice);
					footerShoppingCart.addComponent(saveOrder);
				}
			});
		}
		totalPrice.setValue("<b>Prix total : " + Integer.toString(totalOrderPrice(orders)) + "</b>");
		totalPrice.setContentMode(Label.CONTENT_XHTML);
		
		
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
