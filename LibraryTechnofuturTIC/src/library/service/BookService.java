package library.service;

import java.util.List;

import library.dao.BookDao;
import library.domain.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookService {
	@Autowired BookDao bookDao;
	
	public void addBook(Book book){
		bookDao.addBook(book);
	}
	public void removeBook(Book book){
		bookDao.removeBook(book);
	}
	public void updateBook(Book book){
		bookDao.updateBook(book);
	}
	public List<Book> searchBookByTitle(String title){
		return bookDao.searchBookByTitle(title);
	}
	public List<Book> searchBookByIsbn(int isbn){
		return bookDao.searchBookByIsbn(isbn);
	}

}
