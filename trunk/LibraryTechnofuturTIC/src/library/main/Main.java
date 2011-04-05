package library.main;

import library.domain.Book;
import library.domain.Group;
import library.service.BookService;
import library.service.GroupService;

public class Main {

	public static void main(String[] args){
		BookService bookService = new BookService();
		GroupService groupService = new GroupService();
		
		Book book = new Book();
		Group group = new Group();
		
		group.setName("vaadin");
		groupService.createGroup(group);
		
		book.setAuthor("jerome lengelé");
		book.setCategory("Java");
		book.setIsbn("AAA111BBB222");
		book.setPrice(12);
		book.setTitle("Java for expert");
		
		
		bookService.addBook(book);
		
		
		
	}
}
