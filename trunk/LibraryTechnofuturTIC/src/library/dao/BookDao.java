package library.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Book;

public class BookDao {


	@PersistenceContext
	EntityManager em;
	
	public void addBook(Book book){
		em.persist(book);
	}
	public void removeBook(Book book){
		em.remove(book);
	}
	public void updateBook(Book book){
		em.merge(book);
	}
	
}
