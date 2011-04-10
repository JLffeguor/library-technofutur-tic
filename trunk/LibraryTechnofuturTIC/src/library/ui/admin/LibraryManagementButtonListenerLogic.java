package library.ui.admin;

import java.util.List;

import library.admin.service.TableUtilities;
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
		Button deletBook = new Button("Supprimer un livre");
		Button modifyBook = new Button("Modifier un Livre");

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);

		buttonsLayout.addComponent(createBook);
		buttonsLayout.addComponent(deletBook);
		buttonsLayout.addComponent(modifyBook);

		final Table table  = TableUtilities.createGroupTable();
		table.addStyleName("big strong");
		table.setSelectable(true);
		table.setImmediate(true);

		BeanItemContainer<Book> bic = new BeanItemContainer<Book>(Book.class, bookDao.getBooks());
		
		
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

		final Button save = new Button("Valider");
		
		save.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {
				Book book = new Book();
				book.setTitle((String)title.getValue());
				book.setAuthor((String)author.getValue());
				book.setIsbn((String)isbn.getValue());
				
//				bookDao.addBook(book);
				
		Item i = table.getItem(table.getValue());
		i.getItemProperty("title").setValue((String)title.getValue());
		i.getItemProperty("author").setValue((String)author.getValue());
		i.getItemProperty("isbn").setValue((String)isbn.getValue());
				
		bookDao.updateBook(book);
//				table.addItem(book);
				
				
				
			}
		});
		
		createBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				fieldValidateLayout.removeAllComponents();

				fieldValidateLayout.addComponent(title);
				fieldValidateLayout.addComponent(author);
				fieldValidateLayout.addComponent(isbn);
				fieldValidateLayout.addComponent(save);
				
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
					fieldValidateLayout.addComponent(save);
					

				}


			}
		});

		deletBook.addListener(new Button.ClickListener() {

			public void buttonClick(ClickEvent event) {

				final Book b = (Book)table.getValue();
				final Application myApp = (MyApplication)NavigableApplication.getCurrent();
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
				
			
			}

		});


	}

	private void fillTable(List<Book> bookList, Table table){
		int i=0;
		for(Book book : bookList){
			i++;
			table.addItem(new Object[] {book.getTitle(),book.getAuthor(),book.getTitle()}, new Integer(i));

		}

	}
}


