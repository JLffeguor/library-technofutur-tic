package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Book;

import org.springframework.stereotype.Repository;
@Repository
public class BookDao {


	@PersistenceContext
	EntityManager em;
	
	public void addBook(Book book){
		em.persist(book);
	}
	public void deleteBookById(long id){
		em.createQuery("DELETE FROM Book b WHERE b.id =:id").setParameter("id", id);
	}
	public void updateBook(Book book){
		em.merge(book);
	}
	public List<Book> searchBookByTitle(String title){
		return (List<Book>)em.createQuery("SELECT b FROM Book b WHERE b.title = :title").setParameter("title", title).getResultList();
	}
	public List<Book> searchBookByIsbn(String isbn){
		return (List<Book>)em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn").setParameter("isbn", isbn).getResultList();
		}
	
}
