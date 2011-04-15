package library.ui.admin;

import library.dao.BookDao;
import library.domain.Book;
import library.navigator7.MyApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.navigator7.NavigableApplication;

import com.vaadin.Application;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Service
public class LibraryManagementButtonListenerLogic {

	@Autowired	BookDao bookDao;

	public void execute(final VerticalLayout dynamicLayout){
		
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(true);
		
		VerticalLayout leftSide = new VerticalLayout();
		leftSide.setSpacing(true);
		
		final VerticalLayout rightSide = new VerticalLayout();
		rightSide.setSpacing(true);
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		
		mainLayout.addComponent(leftSide);
		mainLayout.addComponent(rightSide);
		
		dynamicLayout.addComponent(mainLayout);
		
		Button createBook = new Button("Créer un livre");
		createBook.addStyleName("big");
		Button deletBook = new Button("Supprimer un livre");
		deletBook.addStyleName("big");
		Button modifyBook = new Button("Modifier un Livre");
		modifyBook.addStyleName("big");

		buttonsLayout.addComponent(createBook);
		buttonsLayout.addComponent(deletBook);
		buttonsLayout.addComponent(modifyBook);

		final Table table  = new Table();
		table.addStyleName("big strong");
		table.setSelectable(true);
		table.setImmediate(true);

		final BeanItemContainer<Book> bic = new BeanItemContainer<Book>(Book.class, bookDao.getBooks());

		table.setContainerDataSource(bic);
		table.setVisibleColumns(new Object[]{"title", "author", "isbn"});
		table.setColumnHeaders(new String[]{"Titre du manuel", "Auteur", "ISBN"});

		leftSide.addComponent(table);
		leftSide.addComponent(buttonsLayout);

		final TextField title = new TextField("Titre du manuel");
		final TextField author = new TextField("Auteur");
		final TextField isbn = new TextField("ISBN");

		final Button saveCreate = new Button("Valider");
		saveCreate.addStyleName("big");
		final Button saveModify = new Button("Valider");
		saveModify.addStyleName("big");
		final Button cancle = new Button("Annuler");
		cancle.addStyleName("big");

		saveCreate.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				Book book = new Book();
				book.setTitle((String)title.getValue());
				book.setAuthor((String)author.getValue());
				book.setIsbn((String)isbn.getValue());

				bookDao.addBook(book);
				table.addItem(book);

			}
		});

		saveModify.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				

				Item i = table.getItem(table.getValue());
				i.getItemProperty("title").setValue((String)title.getValue());
				i.getItemProperty("author").setValue((String)author.getValue());
				i.getItemProperty("isbn").setValue((String)isbn.getValue());

				Book book = (Book)table.getValue();
				
				bookDao.updateBook(book);

			}
		});
		
		cancle.addListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				rightSide.removeAllComponents();
			}
		});

		createBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				rightSide.removeAllComponents();

				title.setValue("");
				author.setValue("");
				isbn.setValue("");

				rightSide.addComponent(title);
				rightSide.addComponent(author);
				rightSide.addComponent(isbn);
				
				HorizontalLayout createAndCancleHorizontalLayout = new HorizontalLayout();
				createAndCancleHorizontalLayout.setSpacing(true);
				createAndCancleHorizontalLayout.addComponent(saveCreate);
				createAndCancleHorizontalLayout.addComponent(cancle);
				
				rightSide.addComponent(createAndCancleHorizontalLayout);

			}
		});

		modifyBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				Item item = table.getItem(table.getValue());

				if(item == null){
					Application myApp = (MyApplication)NavigableApplication.getCurrent();
					myApp.getMainWindow().showNotification("Vous devez selectionner un element dans la table");
				}else{

					Book book = (Book)table.getValue();

					title.setValue(book.getTitle());
					author.setValue(book.getAuthor());
					isbn.setValue(book.getIsbn());

					rightSide.removeAllComponents();

					rightSide.addComponent(title);
					rightSide.addComponent(author);
					rightSide.addComponent(isbn);
					
					HorizontalLayout createAndModifyHorizontalLayout = new HorizontalLayout();
					createAndModifyHorizontalLayout.setSpacing(true);
					createAndModifyHorizontalLayout.addComponent(saveModify);
					createAndModifyHorizontalLayout.addComponent(cancle);
					
					rightSide.addComponent(createAndModifyHorizontalLayout);

				}
			}
		});

		deletBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				rightSide.removeAllComponents();

				final Book b = (Book)table.getValue();
				final Application myApp = (MyApplication)NavigableApplication.getCurrent();

				if(b!=null){

					final Window window = new Window();

					window.center();

					Button button = new Button("Supprimer");

					window.addComponent(button);

					myApp.getMainWindow().addWindow(window);

					button.addListener(new Button.ClickListener() {

						public void buttonClick(ClickEvent event) {
							myApp.getMainWindow().removeWindow(window);
							bookDao.deleteBookById(b.getId());
							table.removeItem(b);
						}
					});
				}else{
					myApp.getMainWindow().showNotification("Vous devez selectioner un element dans la table");
				}
			}

		});

//		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
//		    public String getStyle(Object itemId, Object propertyId) {
//		       
//		       return "blue";
//
//		    }
//		});

	}
}


