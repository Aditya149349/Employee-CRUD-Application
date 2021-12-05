package com.employee.restapp.model;



//import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
//import javax.validation.constraints.Max;
//import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="employee_table")
public class EmployeeModel {
	
	@NotEmpty(message = "ID cannot be empty")
	@Id
	@Column(name = "employee_id")
	private String id;
	
	@NotEmpty(message = "Name cannot be empty")
	@Column(name = "name")
	private String name;
	
	@NotEmpty(message = "Email cannot be empty")
	@Email(message = "not a valid email")
	@Column(name = "email_id")
	private String mailId;
	
	@NotEmpty(message = "number cannot be empty")
	@Size(min = 10, max = 10, message="Invalid number of digits")
	@Column(name = "number")
	private String number;

	public EmployeeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeModel(String id, String name, String mailId, String number) {
		super();
		this.id = id;
		this.name = name;
		this.mailId = mailId;
		this.number = number;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMailId() {
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public EmployeeModel updateWith(EmployeeModel employee)
	{
		return new EmployeeModel(this.id, employee.name, employee.mailId, employee.number);
	}
	@Override
	public String toString() {
		return "EmployeeModel [id=" + id + ", name=" + name + ", mailId=" + mailId + ", number=" + number + "]";
	}
	
	
	

}
