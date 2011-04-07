package library.ui.admin;

import library.admin.service.GroupManagementButtonLogic;
import library.admin.service.OrderManagementLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.Page;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;


@Page
@org.springframework.stereotype.Component
@Configurable(preConstruction = true)
public class AdminPage extends VerticalLayout implements Button.ClickListener{

	private final Button libraryManagement = new Button("Gestion de livres");
	private final Button orderManagement = new Button("Livre à  commander");
	private final Button groupManagement = new Button("Gestion de group");
	private final VerticalLayout dynamicLayout = new VerticalLayout();
	final VerticalLayout menu = new VerticalLayout();

	@Autowired  OrderManagementLogic orderManagementService;
	@Autowired  GroupManagementButtonLogic groupManagementService;

	public AdminPage(){

		setSizeFull();

		menu.setStyleName("menu");
		menu.setWidth("250px");
		menu.setHeight("100%");

		dynamicLayout.setSpacing(true);
		dynamicLayout.setSizeFull();

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setStyleName("toolbar");
		buttonLayout.setMargin(true);
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth("100%");

		libraryManagement.setIcon(new ThemeResource("images/library2.png"));
		orderManagement.setIcon(new ThemeResource("images/order.png"));
		groupManagement.setIcon(new ThemeResource("images/groups.png"));

		libraryManagement.addListener(this);
		orderManagement.addListener(this);
		groupManagement.addListener(this);
		buttonLayout.addComponent(libraryManagement);
		buttonLayout.addComponent(orderManagement);
		buttonLayout.addComponent(groupManagement);

		Button empty = new Button();
		buttonLayout.addComponent(empty);
		buttonLayout.setExpandRatio(empty, 1);

		HorizontalLayout menuAndData = new HorizontalLayout();
		menuAndData.setHeight("100%");
		menuAndData.setWidth("100%");
		menuAndData.addComponent(menu);
		menuAndData.addComponent(dynamicLayout);
		menuAndData.setExpandRatio(dynamicLayout, 1);

		addComponent(buttonLayout);
		addComponent(menuAndData);
		setExpandRatio(menuAndData, 1);

	}

	public void buttonClick(ClickEvent event) {
		Button button = event.getButton();
		dynamicLayout.removeAllComponents();
		menu.removeAllComponents();

		if(button.equals(libraryManagement)){

		}

		if(button.equals(orderManagement)){
			orderManagementService.execute(menu, dynamicLayout);
		}

		if(button.equals(groupManagement)){
			groupManagementService.execute(menu, dynamicLayout);
		}
	}


}
