package library.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Order {

	@Id
	@GeneratedValue
	private long id;
	
	private String book_title;
	private String author;
	private	Long isbn;
	private int price;
	
	@OneToOne
	@JoinColumn(name="user_id")
	User user;
	
	@OneToOne
	@JoinColumn(name="group_id")
	Group group;
	
	
	
	
}
