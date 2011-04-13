package library.ui.user;

import java.util.ArrayList;
import java.util.List;

import library.domain.Book;

public class ShoppingCart {

	private List<Book> books = new ArrayList<Book>();

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(Book book) {
		books.add(book);
	}
	
}
