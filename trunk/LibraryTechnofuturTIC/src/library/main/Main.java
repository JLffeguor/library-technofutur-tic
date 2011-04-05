package library.main;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import library.domain.Book;
import library.domain.Group;
import library.domain.User;
import library.service.BookService;

public class Main {

	public static void main(String[] args){
		
//		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
//		BookService bookService=(BookService)applicationContext.getBean("bookService");
//		GroupService groupService =(GroupService)applicationContext.getBean("groupService");
//		
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
//		
//		create50Book(bookService);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		for(int i=0;i<10;i++){
			Group group = new Group();
			group.setNumberOfStudent(12);
			group.setCreationDate(new Date());
			group.setName(String.valueOf(i));
			em.persist(group);	
				for(int j=0;j<12;j++){
					User user = new User();
					user.setFirstName("faton");
					user.setLastName("alia");
					user.setGroup(group);
					em.persist(user);
				}
		
		}
		em.getTransaction().commit();
		
		em.close();
		em.close();
		
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
