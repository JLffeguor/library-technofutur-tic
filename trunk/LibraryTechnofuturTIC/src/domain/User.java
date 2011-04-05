package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "users")
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Column (name="firstname")
	private String firstName;
	@Column (name="lastname")
	private String lastName;
	@Column (name="group")
	private Group group;
}