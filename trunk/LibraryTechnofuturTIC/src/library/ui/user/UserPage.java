package library.ui.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import library.dao.BookDao;
import library.dao.GroupDao;
import library.dao.OrderDao;
import library.dao.UserDao;
import library.domain.Book;
import library.domain.Group;
import library.domain.Order;
import library.domain.User;
import library.navigator7.MyApplication;
import library.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Page;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Page
@Configurable(preConstruction = true)

public class UserPage extends HorizontalLayout{
	
	@Autowired OrderDao orderDao;
	@Autowired BookDao bookDao;
	@Autowired GroupDao groupDao;
	@Autowired UserDao userDao;
	@Autowired UserService userService;
	
	
	
	
	public UserPage(){
		
		setSizeFull();
		
	
		//this Layout contains the shopping cart o the user and all the book choose by the members of his group
		final VerticalLayout cartAndHelpLayout = new VerticalLayout();
		

		final VerticalLayout form = new VerticalLayout();
		form.setSpacing(true);
		
		final TextField groupCode = new TextField("Code");
		form.addComponent(groupCode);
		
		Button enter = new Button("Connecter");
		
		enter.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
//				boolean verify = userService.checkIfUserRegistered((String)firstName.getValue(),(String)code.getValue());
//				if(verify){
//				}
				removeAllComponents();
				
				addComponent(selectStudentLayout(groupCode.getCaption()));
			}
			
		});
		
		
		form.addComponent(enter);
		
		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}
	


	public VerticalLayout selectStudentLayout(String groupCode){
		VerticalLayout selectStudentLayout = new VerticalLayout();
		final Map<Integer,User> listUserMap = new HashMap<Integer, User>();
		List<User> userList = new ArrayList<User>();
		Group group = (Group) groupDao.getGroupByCode(groupCode);
		userList = userDao.getUsersByGroupName(group.getName());
		final ListSelect userSelect = new ListSelect("Veuillez Selectionner votre nom dans la liste. Puis cliquer sur OK");
		userSelect.setNullSelectionAllowed(false); 
		userSelect.setImmediate(true);
		
		Integer i = 0;
		for(User user:userList){
			userSelect.setItemCaption(i, user.getFirstName() + " " + user.getLastName());
			listUserMap.put(i, user);
			i++;
		}
		selectStudentLayout.addComponent(userSelect);
		
		Button validateUser = new Button("OK");
		validateUser.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (!userSelect.isNullSelectionAllowed()){
				removeAllComponents();
				Label userLabel = new Label((Property) listUserMap.get(userSelect.getValue()));
				//this layout contains the form to command a book, and the libraries
				VerticalLayout commandLayout = new VerticalLayout();
				commandLayout.setSpacing(true);
				
				commandLayout.addComponent(commandNewBookLayout());
				commandLayout.addComponent(searchBookInLabraryLayout());
				
				addComponent(commandLayout);
				}else{
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Vous devez selectionner votre nom dans la liste pui scliquer sur \"OK\"");
				}
			}
		});
		return selectStudentLayout;
		
	}
	
	
	//The user know what book he will, thus he can fill in
	public VerticalLayout commandNewBookLayout(){
		
		VerticalLayout newBookLayout = new VerticalLayout();
		newBookLayout.setSpacing(true);
		
		Label titleLayoutLabel = new Label ("Je connais d�j� le livre que je veux");
		newBookLayout.addComponent(titleLayoutLabel);
		final Map<String, TextField> infoBooks = new HashMap<String, TextField>();
		List<String> infoBook = new ArrayList<String>();
		
		infoBook.add("Auteur");
		infoBook.add("Titre");
		infoBook.add("ISBN");
		infoBook.add("Prix");
		
		for(String str: infoBook){
			TextField informationBookTextField = new TextField(str);
			informationBookTextField.setStyleName("align-right");
			infoBooks.put(str, informationBookTextField);
			newBookLayout.addComponent(informationBookTextField);
		}
		Button addToCart = new Button("J'ajoute le livre a mon panier");
		addToCart.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				Order order= new Order();
				order.setAuthor(infoBooks.get("Auteur").getCaption());
				order.setBook_title(infoBooks.get("Titre").getCaption());
				order.setIsbn(Long.parseLong(infoBooks.get("ISBN").getCaption()));
				order.setPrice(Integer.parseInt(infoBooks.get("Prix").getCaption()));
				
			}
		});
		newBookLayout.addComponent(addToCart);
		return newBookLayout;
	}
	public VerticalLayout searchBookInLabraryLayout(){
		
		final boolean bookVisible = false;
		final VerticalLayout bookInLibraryLayout = new VerticalLayout();
		VerticalLayout buttonPanel = new VerticalLayout();
		buttonPanel.setSpacing(true);
		Button displayAllBook = new Button("Liste Compl�te");
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setSpacing(true);
		Button searchByKeyWord = new Button("recherche par mot-clef sur le titre");
		final VerticalLayout library = new VerticalLayout();
		displayAllBook.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (library.getComponentCount() == 0) {
					library.removeAllComponents();
					Table table = new Table();
					table.addStyleName("big strong");
					table.setSelectable(true);
					table.setImmediate(true);

					BeanItemContainer<Book> bic = new BeanItemContainer<Book>(Book.class, bookDao.getBooks());
					table.setContainerDataSource(bic);
					table.setVisibleColumns(new Object[] { "title", "author","isbn" });
					table.setColumnHeaders(new String[] { "Titre du manuel","Auteur", "ISBN" });

					library.addComponent(table);

					Button addToCart = new Button("Ajouter a mon panier");
					addToCart.addListener(new Button.ClickListener() {
						public void buttonClick(ClickEvent event) {
							// TODO add book to cart
						}
					});
					library.addComponent(addToCart);
					bookInLibraryLayout.addComponent(library);
				} else {
					library.removeAllComponents();

				}
			}
		});
		TextField keyword =  new TextField();
		searchLayout.addComponent(searchByKeyWord);
		searchLayout.addComponent(keyword);
		buttonPanel.addComponent(displayAllBook);
		buttonPanel.addComponent(searchLayout);
		bookInLibraryLayout.addComponent(buttonPanel);
		
		return bookInLibraryLayout;
	}
	public VerticalLayout shoppingCart(Book book){
		VerticalLayout shoppingCart = new VerticalLayout();
		
		
		return shoppingCart;
	}
}