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
	private Date creationDate;
	@Column (name = "closingdate")
	private Date  closingDate;
	@Column(name = "closed", nullable=true)
	private boolean closed;//all book have been received and distributed
	@Column(name = "code")
	private String code;//code generated each time a group is created;
	@Column(name = "students")
	private Integer students;

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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getClosingDate() {
		return closingDate;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}
	public long getId() {
		return id;
	}
	public Integer getStudents() {
		return students;
	}
	public void setStudents(Integer students) {
		this.students = students;
	}
}
