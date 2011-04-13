package library.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "Groups")
public class Group {

	@Id
	@GeneratedValue
	private long id;
	@Column(name = "name", unique = true)
	private String name;
	@Column(name = "creationdate")
	private String creationDate;
	@Column (name = "closingdate")
	private String  closingDate;
	@Column(name = "closed", nullable=true)
	private boolean closed;//all book have been received and distributed
	@Column(name = "code")
	private String code;//code generated each time a group is created;
	
	
	public boolean isClosed() {
		return closed;
	}
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	public long getId() {
		return id;
	}
}
