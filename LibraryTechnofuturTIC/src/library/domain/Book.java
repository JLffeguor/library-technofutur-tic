package library.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue
	private long id;
	@Column (name="author")
	private String author;
	@Column (name="title")
	private String title;
	@Column (name="isbn")
	private String isbn;
	@Column (name = "category") //eg : Java, Web, PHp ...
	private String category;
	
	
	
	public long getId() {
		return id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String toString(){
		return "title : " + title + " author : " + author + " isbn : " + isbn;
	}
	
}
