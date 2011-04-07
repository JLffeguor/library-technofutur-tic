package library.service;

import java.util.List;

import library.dao.BookDao;
import library.domain.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {
	@Autowired BookDao bookDao;
	

}
