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
		Button createBook = new Button("Créer un livre");
		createBook.addStyleName("big");
		Button deletBook = new Button("Supprimer un livre");
		deletBook.addStyleName("big");
		Button modifyBook = new Button("Modifier un Livre");
		modifyBook.addStyleName("big");

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);

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


		VerticalLayout tableButtonLayout = new VerticalLayout();
		tableButtonLayout.setSpacing(true);

		tableButtonLayout.addComponent(table);
		tableButtonLayout.addComponent(buttonsLayout);

		dynamicLayout.addComponent(tableButtonLayout);

		final VerticalLayout fieldValidateLayout = new VerticalLayout();
		fieldValidateLayout.setSpacing(true);

		dynamicLayout.addComponent(fieldValidateLayout);
		dynamicLayout.setExpandRatio(fieldValidateLayout, 1);

		final TextField title = new TextField("Titre du manuel");
		final TextField author = new TextField("Auteur");
		final TextField isbn = new TextField("ISBN");

		final Button saveCreate = new Button("Valider");
		saveCreate.addStyleName("big");
		final Button saveModify = new Button("Valider");
		saveModify.addStyleName("big");

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

		createBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				title.setValue("");
				author.setValue("");
				isbn.setValue("");

				fieldValidateLayout.addComponent(title);
				fieldValidateLayout.addComponent(author);
				fieldValidateLayout.addComponent(isbn);
				fieldValidateLayout.addComponent(saveCreate);

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

					fieldValidateLayout.removeAllComponents();

					fieldValidateLayout.addComponent(title);
					fieldValidateLayout.addComponent(author);
					fieldValidateLayout.addComponent(isbn);
					fieldValidateLayout.addComponent(saveModify);


				}


			}
		});

		deletBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

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
					myApp.getMainWindow().showNotification("Vous devez selectioner un element dans le tableau");
				}
			}

		});

		table.setCellStyleGenerator(new Table.CellStyleGenerator() {
		    public String getStyle(Object itemId, Object propertyId) {
		       
		       return "blue";

		    }
		});



	}
}


