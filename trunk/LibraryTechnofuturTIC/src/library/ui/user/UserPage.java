package library.ui.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.ChangeEvent;

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
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Page
@Configurable(preConstruction = true)

public class UserPage extends HorizontalLayout implements Property.ValueChangeListener{
	
	@Autowired OrderDao orderDao;
	@Autowired BookDao bookDao;
	@Autowired GroupDao groupDao;
	@Autowired UserDao userDao;
	@Autowired UserService userService;
	@Autowired ShoppingCart shoppingCart;
	
	final ListSelect userSelect = new ListSelect();
	
	//this Layout contains the shopping cart o the user and all the book choose by the members of his group
	final VerticalLayout cartAndHelpLayout = new VerticalLayout();
	final Application myApp = (MyApplication) NavigableApplication.getCurrent();

	
	public UserPage(){
		
		setSizeFull();
		final VerticalLayout form = new VerticalLayout();
		form.setSpacing(true);
		final TextField groupCode = new TextField("Code");
		form.addComponent(groupCode);
		Button enter = new Button("Connecter");
		enter.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				
				List<Group> listGroup = new ArrayList<Group>();
				listGroup = groupDao.getGroupByCode((String)groupCode.getValue());
				if (listGroup.isEmpty()) {
					Application myApp = (MyApplication) NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Aucun groupe n'a ce code. Ré-essaier");

				} else {
					addComponent(selectStudentLayout(((String)groupCode.getValue()), listGroup.get(0)));
				}

			}

		});
		
		
		form.addComponent(enter);
		
		addComponent(form);
		setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	}
	


	public VerticalLayout selectStudentLayout(String groupCode, final Group g){
		removeAllComponents();
		final VerticalLayout selectStudentLayout = new VerticalLayout();
		List<User> userList = new ArrayList<User>();
		List<String> nameList = new ArrayList<String>();
		
	
		userList = userDao.getUsersByGroupName(g.getName());//we find all users in the group
	
		for(User u:userList){
			nameList.add(u.getFirstName() + " " + u.getLastName()); //create the list with only FirstName and LastName
		}
		
		userSelect.setCaption("Selectionner votre nom dans la liste. Puis cliquer sur OK");
		userSelect.setContainerDataSource(new BeanItemContainer<String>(String.class, nameList));
		
		userSelect.setNullSelectionAllowed(false); // user can not 'unselect'
		userSelect.setImmediate(true); // send the change to the server at once

		
		Button validateUser = new Button("OK");
		validateUser.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				removeAllComponents();
				
				if (userSelect.getValue()!=null) {
					String lastNameAndFirstName = (String) userSelect.getValue();
					final String firstName = lastNameAndFirstName.substring(0,lastNameAndFirstName.indexOf(" "));
					final String lastName = lastNameAndFirstName.substring(lastNameAndFirstName.indexOf(" ") + 1);
					
					final User u = userDao.getUserByFirstNameAndLastName(firstName, lastName).get(0);

					if (u.getEmail() == null) {
						final Window enterEmail = new Window();
						enterEmail.center();
						enterEmail.setResizable(false);
						enterEmail.setHeight("50px");
						enterEmail.setWidth("450px");
						
						final TextField email = new TextField("Entrer votre adresse email");
						Button validateEmail = new Button("Enregistrer email et continuer");
						enterEmail.addComponent(email);
						enterEmail.addComponent(validateEmail);

						validateEmail.addListener(new Button.ClickListener() {
							public void buttonClick(ClickEvent event) {
								if (!((String)email.getValue()).isEmpty()) {
									
									u.setEmail((String) email.getValue());
									userDao.updateUser(u);
									myApp.getMainWindow().removeWindow(enterEmail);
									removeAllComponents();
									commandLayout(u);
								} else {
									myApp.getMainWindow().showNotification("Vous devez entrer votre adresse mail");
									removeAllComponents();
									myApp.getMainWindow().addWindow(enterEmail);
								}
									
							}
						});
						myApp.getMainWindow().addWindow(enterEmail);
						
					} else {
						
						commandLayout(u);
						
					}
				}else{
					
					myApp.getMainWindow().showNotification("Vous devez selectionner votre nom dans la liste puis cliquer sur \"OK\"");
					removeAllComponents();
					addComponent(selectStudentLayout);
				}
				
			}
		});
		selectStudentLayout.addComponent(userSelect);
		selectStudentLayout.addComponent(validateUser);

		return selectStudentLayout;

	}
	//right part of the window how contain command option
	public void commandLayout(User user){
		removeAllComponents();
		Label nameLabel = new Label(user.getFirstName() + " " + user.getLastName() + ": " + user.getEmail());

		VerticalLayout commandLayout = new VerticalLayout();
		commandLayout.addComponent(nameLabel);
		commandLayout.setSpacing(true);

		commandLayout.addComponent(commandNewBookLayout(user,user.getGroup()));
		commandLayout.addComponent(searchBookInLabraryLayout(user,user.getGroup()));
		

		addComponent(commandLayout);
		addComponent(cartAndHelpLayout);
	}


	//The user knows what book he will, thus he can fill in
	public VerticalLayout commandNewBookLayout(final User user, final Group group){
		
		VerticalLayout commandNewBookLayout = new VerticalLayout();
		commandNewBookLayout.setSpacing(true);
		
		Label titleLayoutLabel = new Label ("Je connais déjà le livre que je veux");
		commandNewBookLayout.addComponent(titleLayoutLabel);
		final Map<String, TextField> infoBooks = new HashMap<String, TextField>();
		List<String> infoBook = new ArrayList<String>();
		
		infoBook.add("Auteur");
		infoBook.add("Titre");
		infoBook.add("ISBN");
		infoBook.add("Prix");
		
		for(String str: infoBook){
			TextField informationBookTextField = new TextField(str);
			informationBookTextField.setStyleName("align-right");
			if(str.equals("Prix")){
				informationBookTextField.addValidator(new IntegerValidator("Vous n'avez pas rentré un chiffre pour le champ prix"));
			}
			infoBooks.put(str, informationBookTextField);
			commandNewBookLayout.addComponent(informationBookTextField);
		}
		Button addToCart = new Button("J'ajoute le livre a mon panier");
		addToCart.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				cartAndHelpLayout.removeAllComponents();
				Order order= new Order();
				order.setAuthor((String)infoBooks.get("Auteur").getValue());
				order.setBook_title((String)infoBooks.get("Titre").getValue());
				order.setIsbn((String)infoBooks.get("ISBN").getValue());
				order.setGroup(group);
				order.setUser(user);
				if (infoBooks.get("Prix").isValid()) {
					order.setPrice(Integer.parseInt((String) infoBooks.get("Prix").getValue()));
					shoppingCart.addOrder(order);
					cartAndHelpLayout.addComponent(shoppingCart.getShoppingCart());
				
					addComponent(cartAndHelpLayout);
				} else {
					myApp.getMainWindow().showNotification("Vous devez rentrer un chiffre pour le champ prix");
				}
				
			}
		});
		commandNewBookLayout.addComponent(addToCart);
		return commandNewBookLayout;
	}
	public VerticalLayout searchBookInLabraryLayout(final User user, final Group group){
		
		final boolean bookVisible = false;
		final VerticalLayout bookInLibraryLayout = new VerticalLayout();
		VerticalLayout buttonPanel = new VerticalLayout();
		buttonPanel.setSpacing(true);
		Button displayAllBook = new Button("Liste Complète");
		HorizontalLayout searchLayout = new HorizontalLayout();
		searchLayout.setSpacing(true);
		Button searchByKeyWord = new Button("recherche par mot-clef sur le titre");
		final VerticalLayout library = new VerticalLayout();
		displayAllBook.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				if (library.getComponentCount() == 0) {
					library.removeAllComponents();
					final Table table = new Table();
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
							cartAndHelpLayout.removeAllComponents();
							Book book = (Book)table.getValue();
							Order order = new Order();
							order.setAuthor(book.getAuthor());
							order.setBook_title(book.getTitle());
							order.setIsbn(book.getIsbn());
							order.setUser(user);
							order.setGroup(group);
							shoppingCart.addOrder(order);
							cartAndHelpLayout.addComponent(shoppingCart.getShoppingCart());
							addComponent(cartAndHelpLayout);
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
	public void valueChange(ValueChangeEvent event) {
		String name =  (String)userSelect.getValue();
	}
//	public VerticalLayout OrderForStudentGroupLayout(Group group){
//		
//		VerticalLayout orderForStudentGroupLayout = new VerticalLayout();
//		Table table = new Table();
//		table.setSelectable(false);
//		BeanItemContainer<Order> bic = new BeanItemContainer<Order>(Order.class, orderDao.getOrderByGroupName(group.getName()));
//		table.setContainerDataSource(bic);
//		table.setVisibleColumns(new Object[] { "book_title", "author","isbn","price","user" });
//		table.setColumnHeaders(new String[] { "Titre du manuel","Auteur", "ISBN" , "Prix" , "étudiant" });
//		
//		orderForStudentGroupLayout.addComponent(table);
//		
//		
//		return orderForStudentGroupLayout;
//		
//	}



	
}