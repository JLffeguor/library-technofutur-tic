package library.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import library.domain.Book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@Transactional
public class BookDao {


	@PersistenceContext
	EntityManager em;
	
	public void addBook(Book book){
		em.persist(book);
	}
	public void deleteBookById(long id){
		em.createQuery("delete from Book b where b.id =:id").setParameter("id", id).executeUpdate();
	}
	public void updateBook(Book book){
		Book b = em.find(Book.class, book.getId());
		b.setTitle(book.getTitle());
		b.setAuthor(book.getAuthor());
		b.setIsbn(book.getIsbn());
		em.merge(book);
	}
	public List<Book> searchBookByTitle(String title){
		return (List<Book>)em.createQuery("select b from Book b where b.title = :title").setParameter("title", title).getResultList();
	}
	public List<Book> searchBookByIsbn(String isbn){
		return (List<Book>)em.createQuery("select b from Book b where b.isbn = :isbn").setParameter("isbn", isbn).getResultList();
		}
	public List<Book> getBooks(){
		return em.createQuery("from Book b").getResultList();
	}
	
}
