package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.BookDao;
import domain.Book;

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
}
