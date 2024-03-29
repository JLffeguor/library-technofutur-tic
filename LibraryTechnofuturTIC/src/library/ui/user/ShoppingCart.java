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
		
		//create the shopping cart
		for(Order o:orders){
			final HorizontalLayout orderLayout = new HorizontalLayout();
			Label titleOrder = new Label(o.getBook_title());
			Label authorOder = new Label(o.getAuthor());
			Label priceOrder = new Label(Integer.toString(o.getPrice()));
			Button deleteButton = new Button("enlever");
			
			orderLayout.setWidth("400px");
			orderLayout.setSpacing(true);
			titleOrder.setWidth("150px");
			authorOder.setWidth("60px");
			priceOrder.setWidth("20px");
			
			
			orderLayout.addComponent(titleOrder);
			orderLayout.addComponent(authorOder);
			orderLayout.addComponent(priceOrder);
			orderLayout.addComponent(deleteButton);
			shoppingCartLayout.addComponent(orderLayout);
			
			deleteButton.addListener(new Button.ClickListener() {
				public void buttonClick(ClickEvent event) {
					VerticalLayout v  = (VerticalLayout)orderLayout.getParent();
					int i = v.getComponentIndex(orderLayout);
					//remove all component a the shoppingcart and the footershopping cart
					//maybe use
					//removeAllComponent();
					v.removeComponent(orderLayout);
					v.removeComponent(totalPrice);
					v.removeComponent(saveOrder);
					
					orders.remove(i);//delete the order in the order list
					totalPrice.setValue("<b>Prix total : " + Integer.toString(totalOrderPrice(orders)) + "</b>");
					footerShoppingCart.addComponent(totalPrice);
					footerShoppingCart.addComponent(saveOrder);
				}
			});
		}
		totalPrice.setValue("<b>Prix total : " + Integer.toString(totalOrderPrice(orders)) + "</b>");
		totalPrice.setContentMode(Label.CONTENT_XHTML);
		
		/**
		 * TODO
		 * this code must be change . Read the rapport for more explanation
		 * */
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
	//calculate the total price of a shopping cart
	public int totalOrderPrice(List<Order> orderList){
		int sum = 0;
		for (Order o:orderList){
			sum +=o.getPrice();
		}
		return sum;
		
	}
	
}
