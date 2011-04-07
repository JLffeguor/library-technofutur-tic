package library.main;

import java.util.ArrayList;
import java.util.List;

import library.dao.BookDao;
import library.domain.Book;
import library.service.BookService;
import library.service.GroupService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args){
		
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		BookDao bookDao=(BookDao)applicationContext.getBean("bookDao");
//		GroupService groupService =(GroupService)applicationContext.getBean("groupService");
		
//		Book book = new Book();
//		book.setTitle("java for expert");
//		book.setAuthor("jerome");
//		book.setIsbn("AAA111BBB");
//		bookService.addBook(book);
//		Date date = new Date();
//		
//		Group group = new Group();
//		group.setCreationDate(date);
//		group.setName("javablackBelt");
//		group.setNumberOfStudent(12);
//		groupService.createGroup(group);
		List<Book> bookList = new ArrayList<Book>();
		bookDao.deleteBookById(12L);
		System.out.println("Search by isbn : \n");
		bookList = bookDao.searchBookByIsbn("AAA444");
		if (bookList.isEmpty()){
			System.out.println("NO RESULT");
		}else{
			for(Book b : bookList){
				System.out.println(b);
			}
		}
		
	}
	public static void create50Book(BookDao bookDao){
		for (int i = 0;i<50;i++){
			Book bk = new Book();
			bk.setAuthor("djey le : " + i);
			bk.setIsbn("AAA" + i + "" + i + "" + i);
			bk.setTitle("java for level : " + i*i);
			bk.setCategory("Java");
			bookDao.addBook(bk);
		}
	}
}
