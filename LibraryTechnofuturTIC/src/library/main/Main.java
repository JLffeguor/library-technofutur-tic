package library.main;

import java.util.Date;

import library.domain.Book;
import library.domain.Group;
import library.service.BookService;
import library.service.GroupService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args){
		
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		BookService bookService=(BookService)applicationContext.getBean("bookService");
		GroupService groupService =(GroupService)applicationContext.getBean("groupService");
		
		Book book = new Book();
		book.setTitle("java for expert");
		book.setAuthor("jerome");
		book.setIsbn("AAA111BBB");
		bookService.addBook(book);
		Date date = new Date();
		
		Group group = new Group();
		group.setCreationDate(date);
		group.setName("javablackBelt");
		group.setNumberOfStudent(12);
		groupService.createGroup(group);
		
		create50Book(bookService);
		
		
	}
	public static void create50Book(BookService bookService){
		for (int i = 0;i<50;i++){
			Book bk = new Book();
			bk.setAuthor("djey le : " + i);
			bk.setIsbn("AAA" + i + "" + i + "" + i);
			bk.setPrice(i);
			bk.setTitle("java for level : " + i*i);
			bk.setCategory("Java");
			bookService.addBook(bk);
		}
	}
}