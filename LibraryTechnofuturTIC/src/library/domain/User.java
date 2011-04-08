package library.domain;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@Column(name = "email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group;
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName(){
		return firstName;
	}
	public String getLastName(){
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
